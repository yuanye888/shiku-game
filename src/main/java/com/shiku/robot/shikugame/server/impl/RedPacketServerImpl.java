package com.shiku.robot.shikugame.server.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.UpdateResult;
import com.shiku.robot.shikugame.base.BaseResponseModel;
import com.shiku.robot.shikugame.base.RespCode;
import com.shiku.robot.shikugame.base.RespMsg;
import com.shiku.robot.shikugame.entity.imapi.*;
import com.shiku.robot.shikugame.entity.imroom.ShikuRoom;
import com.shiku.robot.shikugame.entity.imroom.ShikuRoomMember;
import com.shiku.robot.shikugame.mapper.imapi.*;
import com.shiku.robot.shikugame.mapper.imroom.ShikuRoomMemberRepository;
import com.shiku.robot.shikugame.mapper.imroom.ShikuRoomRepository;
import com.shiku.robot.shikugame.server.RedPacketServer;
import com.shiku.robot.shikugame.untils.GameRuleUtil;
import com.shiku.robot.shikugame.untils.HttpUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.http.message.BasicNameValuePair;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author lijun
 * @Description
 * @Date 2019-12-27 11:05 上午
 **/
@Service
@Log4j2
public class RedPacketServerImpl implements RedPacketServer {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RedPacketRepository redPacketRepository;

    @Autowired
    ConsumeRecordRepository consumeRecordRepository;

    @Autowired
    RedReceiveRepository redReceiveRepository;

    @Autowired
    RoomGameRuleRepository roomGameRuleRepository;

    @Autowired
    ShikuRoomRepository shikuRoomRepository;

    @Autowired
    ShikuRoomMemberRepository shikuRoomMemberRepository;


    @Value("${im.api.uri}")
    String imApiUri;

    @Value("${xmpp.api.uri}")
    String xmppApiUri;


    @Override
    public synchronized BaseResponseModel sendRedPacket(JSONObject reqData) {
        BaseResponseModel response = new BaseResponseModel();
        JSONObject jsonObject = reqData.getJSONObject("packet");
        Integer userId = jsonObject.getInteger("userId");

        // 用户操作校验
        User user = userRepository.selectUserById(userId);

        // 添加红包
        RedPacket redPacket = new RedPacket();
        redPacket.setUserId(userId);
        redPacket.setUserName(user.getNickname());
        redPacket.setToUserId("0");
        redPacket.setOver(jsonObject.getDouble("money"));
        Long cuTime = System.currentTimeMillis() / 1000;
        redPacket.setSendTime(cuTime);
        redPacket.setOutTime(cuTime + (24 * 60 * 60));
        redPacket.setGreetings(jsonObject.getString("greetings"));
        redPacket.setType(jsonObject.getInteger("type"));
        // 自己设置
        redPacket.setCount(Integer.valueOf(jsonObject.getString("count")));
        redPacket.setReceiveCount(0);
        redPacket.setMoney(jsonObject.getDouble("money"));
        redPacket.setStatus(1); // 成功
        redPacket.setIsLock("1");
        redPacket.setRoomJid(jsonObject.getString("roomJid"));
        redPacket = redPacketRepository.insetRedPacket(redPacket);
        if (redPacket.getId() == null || redPacket.equals("")) {
            log.info("------------------------------------新增红包失败------------------------------------");
            response.setRepCode(RespCode.INSERT_REDPACKET_ERROR);
            response.setRepMsg(RespMsg.INSERT_REDPACKET_ERROR_MSG);
            return response;
        }

        // 修改发送者金额
        UpdateResult updateResult = userRepository.updateUseralance(userId, user.getBalance(), redPacket.getMoney(), user.getTotalConsume(), 0);
        if (updateResult.getMatchedCount() == 0) {
            log.info("------------------------------------修改用户消费失败------------------------------------");
            response.setRepCode(RespCode.UPDATE_USER_CONSUME_ERROR);
            response.setRepMsg(RespMsg.UPDATE_USER_CONSUME_ERROR_MSG);
            return response;
        }

        //添加一条消费记录
        String tradeNo = getOutTradeNo();
        //创建充值记录
        ConsumeRecord consumeRecord = new ConsumeRecord();
        consumeRecord.setUserId(userId);
        consumeRecord.setToUserId(redPacket.getUserId());
        consumeRecord.setTradeNo(tradeNo);
        consumeRecord.setMoney(jsonObject.getDouble("money"));
        consumeRecord.setStatus(1); // 成功
        consumeRecord.setType(4);
        consumeRecord.setPayType(3); // 余额支付
        consumeRecord.setDesc("红包发送");
        consumeRecord.setTime(cuTime);
        consumeRecord.setCurrentBalance(user.getBalance() - redPacket.getMoney());
        consumeRecord.setOperationAmount(redPacket.getMoney());
        consumeRecord.setRedPacketId(redPacket.getId());
        consumeRecord = consumeRecordRepository.insetConsumeRecord(consumeRecord);
        if (consumeRecord.getId() == null || consumeRecord.equals("")) {
            log.info("------------------------------------添加消费记录失败------------------------------------");
            response.setRepCode(RespCode.INSERT_CONSUMERECORD_ERROR);
            response.setRepMsg(RespMsg.INSERT_CONSUMERECORD_ERROR_MSG);
            return response;
        }
        response.setRepCode(RespCode.SUCCESS);
        response.setRepMsg(RespMsg.SUCCESS_MSG);
        response.setRepData(redPacket);
        return response;
    }

    @Override
    public BaseResponseModel openRedPacket(JSONObject reqData) {
        BaseResponseModel response = new BaseResponseModel();
        RedPacket redPacket = redPacketRepository.selectRedPacketLockById(reqData.getString("id"));
        Map<String, Object> map = new HashMap<>();
        map.put("packet", redPacket);
        //判断红包是否超时
        long currTime = System.currentTimeMillis() / 1000;
        if (currTime > redPacket.getOutTime()) {
            log.info("------------------------------------该红包已超过24小时------------------------------------");
            map.put("list", redReceiveRepository.selectAllRedReceiveByRedPacketId(redPacket.getId()));
            response.setRepCode(RespCode.OPEN_REDPACKET_OVER_TIME_ERROR);
            response.setRepMsg(RespMsg.OPEN_REDPACKET_OVER_TIME_ERROR_MSG);
            response.setRepData(map);
            return response;
        }
        // 计算雷
        JSONObject bomb = GameRuleUtil.getBomb(redPacket.getMoney().toString(), redPacket.getGreetings());

        // 祝福语不符合符合规则
        if (bomb.getString("code").equals("7")) {
            log.info("------------------------------------红包已锁定------------------------------------");
            map.put("list", redReceiveRepository.selectAllRedReceiveByRedPacketId(redPacket.getId()));
            response.setRepCode(RespCode.OPEN_REDPACKET_IS_LOCK_ERROR);
            response.setRepMsg(RespMsg.OPEN_REDPACKET_IS_LOCK_ERROR_MSG);
            return response;
        }

        // 红包符合规则时才判断余额，否则是福利包
        if (bomb.getString("code").equals("1")) {
            // 判断领取人是否有足够余额赔付
            User user = userRepository.selectUserById(Integer.valueOf(reqData.getString("userId")));
            RoomGameRule roomGameRule = roomGameRuleRepository.selectRoomGameRuleByRBCount(redPacket.getRoomJid(), redPacket.getCount().toString(), String.valueOf(bomb.getJSONArray("data").size()), "1");
            if (roomGameRule != null) {
                double chance = Double.valueOf(roomGameRule.getChance());
                bomb.put("chance", chance);
                if (user.getBalance() < redPacket.getMoney() * chance) {
                    log.info("------------------------------------赔付余额不足------------------------------------");
                    map.put("list", redReceiveRepository.selectAllRedReceiveByRedPacketId(redPacket.getId()));
                    response.setRepCode(RespCode.OPEN_REDPACKET_BALANCE_NOT_ENOUGH_ERROR);
                    response.setRepMsg(RespMsg.OPEN_REDPACKET_BALANCE_NOT_ENOUGH_ERROR_MSG);
                    return response;
                }
            } else {
                bomb.put("code", "-8");
            }
        }

        //判断红包是否已领完
        if (redPacket.getCount() > redPacket.getReceiveCount()) {
            //判断当前用户是否领过该红包
            if (null == redPacket.getUserIds() || !redPacket.getUserIds().contains(reqData.getString("userId"))) {
                log.info("------------------------------------红包未领过------------------------------------");
                redPacket = openRedPacket(reqData.getInteger("userId"), redPacket, bomb);
                map.put("packet", redPacket);
                map.put("list", redReceiveRepository.selectAllRedReceiveByRedPacketId(redPacket.getId()));
                response.setRepCode(RespCode.SUCCESS);
                response.setRepMsg(RespMsg.SUCCESS_MSG);
                response.setRepData(map);
                return response;
            } else {
                log.info("------------------------------------红包已领过------------------------------------");
                map.put("list", redReceiveRepository.selectAllRedReceiveByRedPacketId(redPacket.getId()));
                response.setRepCode(RespCode.OPEN_REDPACKET_REPEAT_ERROR);
                response.setRepMsg(RespMsg.OPEN_REDPACKET_REPEAT_ERROR_MSG);
                response.setRepData(map);
                return response;
            }
        } else {
            log.info("------------------------------------红包已经领完------------------------------------");
            map.put("list", redReceiveRepository.selectAllRedReceiveByRedPacketId(redPacket.getId()));
            response.setRepCode(RespCode.OPEN_REDPACKET_REPEAT_ERROR);
            response.setRepMsg(RespMsg.OPEN_REDPACKET_FINISH_ERROR_MSG);
            response.setRepData(map);
            return response;
        }
    }

    @Override
    public BaseResponseModel getRedPacket(JSONObject reqData) {
        BaseResponseModel response = new BaseResponseModel();
        RedPacket redPacket = redPacketRepository.selectRedPacketById(reqData.getString("id"));
        Map<String, Object> map = new HashMap<>();
        map.put("packet", redPacket);
        //判断红包是否超时
        if ((System.currentTimeMillis() / 1000) > redPacket.getOutTime()) {
            map.put("list", redReceiveRepository.selectAllRedReceiveByRedPacketId(redPacket.getId()));
            response.setRepCode(RespCode.OPEN_REDPACKET_OVER_TIME_ERROR);
            response.setRepMsg(RespMsg.OPEN_REDPACKET_OVER_TIME_ERROR_MSG);
            response.setRepData(map);
            ;
            return response;
        }

        //判断红包是否已领完
        if (redPacket.getCount() > redPacket.getReceiveCount()) {
            //判断当前用户是否领过该红包
            if (null == redPacket.getUserIds() || !redPacket.getUserIds().contains(reqData.getInteger("userId"))) {
                map.put("list", redReceiveRepository.selectAllRedReceiveByRedPacketId(redPacket.getId()));
                response.setRepCode(RespCode.SUCCESS);
                response.setRepMsg(RespMsg.SUCCESS_MSG);
                response.setRepData(map);
                return response;
            } else {
                map.put("list", redReceiveRepository.selectAllRedReceiveByRedPacketId(redPacket.getId()));
                response.setRepCode(RespCode.OPEN_REDPACKET_REPEAT_ERROR);
                response.setRepMsg(RespMsg.OPEN_REDPACKET_REPEAT_ERROR_MSG);
                response.setRepData(map);
                return response;
            }
        } else {//红包已经领完了
            map.put("list", redReceiveRepository.selectAllRedReceiveByRedPacketId(redPacket.getId()));
            response.setRepCode(RespCode.OPEN_REDPACKET_FINISH_ERROR);
            response.setRepMsg(RespMsg.OPEN_REDPACKET_FINISH_ERROR_MSG);
            response.setRepData(map);
            return response;
        }
    }

    /**
     * 自动奖励与赔付
     *
     * @return
     */
    private synchronized void autoGetAndPay(String redId, Integer userId) {
        RedPacket redPacket = redPacketRepository.selectRedPacketById(redId);
        JSONObject jsonObject = GameRuleUtil.getBomb(redPacket.getMoney().toString(), redPacket.getGreetings());
        JSONArray integers = jsonObject.getJSONArray("data");
        RoomGameRule roomGameRule = roomGameRuleRepository.selectRoomGameRuleByRBCount(redPacket.getRoomJid(), redPacket.getCount().toString(), String.valueOf(integers.size()), "1");
        if (roomGameRule != null) {
            Double money = redPacket.getMoney() * Double.valueOf(roomGameRule.getChance());
            DecimalFormat df = new DecimalFormat("#.00");
            money = Double.valueOf(df.format(money));

            // 修改发送者金额
            User user = userRepository.selectUserById(redPacket.getUserId());
            userRepository.updateUseralance(redPacket.getUserId(), user.getBalance(), money, user.getTotalConsume(), 1);
            //添加一条消费记录
            String tradeNo = getOutTradeNo();
            //创建中雷奖励记录
            ConsumeRecord consumeRecord = new ConsumeRecord();
            consumeRecord.setUserId(redPacket.getUserId());
            consumeRecord.setTradeNo(tradeNo);
            consumeRecord.setMoney(money);
            consumeRecord.setStatus(1); // 成功
            consumeRecord.setType(5);
            consumeRecord.setPayType(3); // 余额支付
            consumeRecord.setDesc("中雷奖励");
            consumeRecord.setTime((System.currentTimeMillis() / 1000) + 1);
            consumeRecord.setCurrentBalance(user.getBalance() + money);
            consumeRecord.setOperationAmount(money);
            consumeRecord.setRedPacketId(redPacket.getId());
            consumeRecordRepository.insetConsumeRecord(consumeRecord);

            // 修改接收者金额
            user = userRepository.selectUserById(userId);
            userRepository.updateUseralance(userId, user.getBalance(), money, user.getTotalConsume(), 0);
            //添加一条消费记录
            tradeNo = getOutTradeNo();
            //创建中雷赔付记录
            consumeRecord = new ConsumeRecord();
            consumeRecord.setUserId(userId);
            consumeRecord.setToUserId(0);
            consumeRecord.setTradeNo(tradeNo);
            consumeRecord.setMoney(money);
            consumeRecord.setStatus(1); // 成功
            consumeRecord.setType(4);
            consumeRecord.setPayType(3); // 余额支付
            consumeRecord.setDesc("中雷赔付");
            consumeRecord.setTime((System.currentTimeMillis() / 1000) + 1);
            consumeRecord.setCurrentBalance(user.getBalance() - money);
            consumeRecord.setOperationAmount(money);
            consumeRecord.setRedPacketId(redPacket.getId());
            consumeRecordRepository.insetConsumeRecord(consumeRecord);
        }
    }

    /**
     * 领取红包
     *
     * @param userId
     * @param redPacket
     * @return
     */
    private synchronized RedPacket openRedPacket(Integer userId, RedPacket redPacket, JSONObject bomb) {
        int overCount = redPacket.getCount() - redPacket.getReceiveCount();
        User user = userRepository.selectUserById(userId);
        Double money = 0.0;
        //普通红包
        if (("1").equals(redPacket.getType())) {
            if (1 == redPacket.getCount() - redPacket.getReceiveCount()) {
                //剩余一个  领取剩余红包
                money = redPacket.getOver();
            } else {
                money = redPacket.getMoney() / redPacket.getCount();
                //保留两位小数
                DecimalFormat df = new DecimalFormat("#.00");
                money = Double.valueOf(df.format(money));
            }
        } else { //拼手气红包或者口令红包
            money = getRandomMoney(overCount, redPacket.getOver());
        }

        Double over = (redPacket.getOver() - money);
        DecimalFormat df = new DecimalFormat("#.00");
        redPacket.setOver(Double.valueOf(df.format(over)));
        if (redPacket.getUserIds() == null) {
            List<Integer> list = new ArrayList<>();
            list.add(userId);
            redPacket.setUserIds(list);
        } else {
            redPacket.getUserIds().add(userId);
        }
        redPacket.setReceiveCount(redPacket.getReceiveCount() + 1);
        Update update = new Update();
        update.set("receiveCount", redPacket.getReceiveCount());
        update.set("over", redPacket.getOver());
        update.set("userIds", redPacket.getUserIds());
        if (0 == redPacket.getOver()) {
            update.set("status", 2);
            redPacket.setStatus(2);
        }
        redPacketRepository.updateRedPacketByIdAndUpdate(redPacket.getId(), update);


        //实例化一个红包接受对象
        RedReceive redReceive = new RedReceive();
        redReceive.setMoney(money);
        redReceive.setUserId(userId);
        redReceive.setSendId(redPacket.getUserId());
        redReceive.setRedId(redPacket.getId());
        redReceive.setTime(System.currentTimeMillis() / 1000);
        redReceive.setUserName(userRepository.selectUserById(userId).getNickname());
        redReceive.setSendName(userRepository.selectUserById(userId).getNickname());
        redReceive = redReceiveRepository.insetRedReceive(redReceive);

        // 修改接收者金额
        userRepository.updateUseralance(userId, user.getBalance(), money, user.getTotalConsume(), 1);

        // 消息推送
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "83");
        jsonObject.put("fromUserId", user.getId());
        jsonObject.put("fromUserName", user.getNickname());
        if (redPacket.getRoomJid() != null) {
            jsonObject.put("objectId", redPacket.getRoomJid());
            if (0 == redPacket.getOver()) {
                jsonObject.put("fileSize", 1);
                jsonObject.put("fileName", redPacket.getSendTime());
            }
        }
        jsonObject.put("content", redPacket.getId());
        jsonObject.put("toUserId", redPacket.getUserId());
        jsonObject.put("timeSend", System.currentTimeMillis() / 1000);
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString().replace("-", "");
        jsonObject.put("messageId", uuidStr);
        List<BasicNameValuePair> parames = new ArrayList<>();
        String url = HttpUtil.getUrl(xmppApiUri, "/message/send", new HashMap<>());
        parames.add(new BasicNameValuePair("msg", jsonObject.toJSONString()));
        try {
            HttpUtil.sendPostFrom(url, parames);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 添加一条消费记录
        String tradeNo = getOutTradeNo();
        //创建充值记录
        ConsumeRecord consumeRecord = new ConsumeRecord();
        consumeRecord.setUserId(userId);
        consumeRecord.setTradeNo(tradeNo);
        consumeRecord.setMoney(money);
        consumeRecord.setStatus(1);
        consumeRecord.setType(5);
        consumeRecord.setPayType(3); //余额支付
        consumeRecord.setDesc("红包接收");
        consumeRecord.setTime(System.currentTimeMillis() / 1000);
        consumeRecord.setCurrentBalance(user.getBalance() + money);
        consumeRecord.setOperationAmount(money);
        consumeRecord.setRedPacketId(redPacket.getId());
        consumeRecordRepository.insetConsumeRecord(consumeRecord);

        // 判断是不是中雷了并且是单雷
        if (bomb.getString("code").equals("1") && bomb.getJSONArray("data").size() == 1) {
            JSONArray jsonArray = bomb.getJSONArray("data");
            String m = money.toString();
            boolean flag = false;
            for (int i = 0; i < jsonArray.size(); i++) {
                if (money.toString().substring(m.length() - 1, m.length()).equals(jsonArray.get(i))) {
                    flag = true;
                }
            }
            if (flag) {
                log.info("------------------------------------中雷奖励与赔付------------------------------------");
                autoGetAndPay(redPacket.getId(), userId);
            }
        }

        //监控人发送消息
        if (0 == redPacket.getOver() && bomb.getString("code").equals("1")) {
            log.info("------------------------------------红包领取结束------------------------------------");
            ShikuRoom shikuRoom = shikuRoomRepository.selectShikuRoomByRoomJidId(redPacket.getRoomJid());
            ShikuRoomMember shikuRoomMember = shikuRoomMemberRepository.selectShikuRoomControlMember(new ObjectId(shikuRoom.getId()));
            if (shikuRoomMember != null) {
                log.info("------------------------------------监控人发送消息开始------------------------------------");
                url = HttpUtil.getUrl(imApiUri, "/console/sendMsg", new HashMap<>());
                parames = new ArrayList<>();
                StringBuffer buffer = new StringBuffer();
                buffer.append("@" + redPacket.getUserName() + "\n");
                buffer.append("金额：" + redPacket.getMoney() + "元\n");
                buffer.append("埋雷：" + bomb.getJSONArray("data") + ";包数：" + redPacket.getCount() + "包\n");
                buffer.append("赔率：" + bomb.get("chance") + "\n");
                List<RedReceive> redReceives = redReceiveRepository.selectAllRedReceiveByRedPacketId(redPacket.getId());
                String lastCount = "";
                List<String> lastInt = new ArrayList<>();
                List<Integer> userIds = new ArrayList<>();
                int count = 0;
                for (int j = 0; j < redReceives.size(); j++) {
                    double money1 = redReceives.get(j).getMoney();
                    String receiveMoney = df.format(money1);
                    lastCount += receiveMoney.substring(receiveMoney.length() - 1, receiveMoney.length()) + " ";
                    lastInt.add(receiveMoney.substring(receiveMoney.length() - 1, receiveMoney.length()));
                    JSONArray jsonArray = bomb.getJSONArray("data");
                    for (int k = 0; k < jsonArray.size(); k++) {
                        if (receiveMoney.substring(receiveMoney.length() - 1, receiveMoney.length()).equals(jsonArray.get(k))) {
                            count += 1;
                            userIds.add(redReceives.get(j).getUserId());
                        }
                    }
                }
                buffer.append("识别：" + count + "尾;中:" + count + "个\n");
                buffer.append("开包：" + lastCount + "\n");
                buffer.append("总赔：" + (count * bomb.getDouble("chance") * redPacket.getMoney()) + "元\n");
                SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
                buffer.append("时间：" + sdf.format(new Date()));
                parames.add(new BasicNameValuePair("content", buffer.toString()));
                parames.add(new BasicNameValuePair("jidArr", redPacket.getRoomJid()));
                parames.add(new BasicNameValuePair("userId", shikuRoomMember.getUserId().toString()));
                parames.add(new BasicNameValuePair("type", "1"));

                // 单雷
                if (count > 0 && bomb.getJSONArray("data").size() == 1) {
                    HttpUtil.sendPostFrom(url, parames);
                    // 多雷
                } else if (count > 0 && bomb.getJSONArray("data").size() > 1) {
                    List<String> bombInt = new ArrayList<>();
                    for (int i = 0; i < bomb.getJSONArray("data").size(); i++) {
                        bombInt.add(bomb.getJSONArray("data").getString(i));
                    }
                    if (lastInt.containsAll(bombInt)) {
                        log.info("------------------------------------中雷奖励与赔付------------------------------------");
                        for (int i = 0; i < userIds.size(); i++) {
                            autoGetAndPay(redPacket.getId(), userIds.get(i));
                        }
                        HttpUtil.sendPostFrom(url, parames);
                    } else {
                        log.info("------------------------------------没有中雷------------------------------------");
                    }
                } else {
                    log.info("------------------------------------没有中雷------------------------------------");
                }
                log.info("------------------------------------监控人发送消息结束------------------------------------");
            }
        }
        return redPacket;
    }

    /**
     * 拼手气红包
     *
     * @param remainSize
     * @param remainMoney
     * @return
     */
    private synchronized Double getRandomMoney(int remainSize, Double remainMoney) {
        // remainSize 剩余的红包数量
        // remainMoney 剩余的钱
        Double money = 0.0;
        if (remainSize == 1) {
            remainSize--;
            money = (double) Math.round(remainMoney * 100) / 100;
            System.out.println("=====> " + money);
            return money;
        }
        Random r = new Random();
        double min = 0.01; //
        double max = remainMoney / remainSize * 2;
        money = r.nextDouble() * max;
        money = money <= min ? 0.01 : money;
        money = Math.floor(money * 100) / 100;
        System.out.println("=====> " + money);
        remainSize--;
        remainMoney -= money;
        DecimalFormat df = new DecimalFormat("#.00");
        return Double.valueOf(df.format(money));
    }

    /**
     * 生产订单号
     *
     * @return
     */
    public static String getOutTradeNo() {
        // 产生2个0-9的随机数
        int r1 = (int) (Math.random() * (10));
        int r2 = (int) (Math.random() * (10));
        // 一个13位的时间戳
        long now = System.currentTimeMillis();
        StringBuffer buffer = new StringBuffer()
                .append(r1).append(r2).append(now);
        return buffer.toString();
    }
}
