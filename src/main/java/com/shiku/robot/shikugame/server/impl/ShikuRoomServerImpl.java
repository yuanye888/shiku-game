package com.shiku.robot.shikugame.server.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.shiku.robot.shikugame.base.BaseRequestModel;
import com.shiku.robot.shikugame.base.BaseResponseModel;
import com.shiku.robot.shikugame.base.RespCode;
import com.shiku.robot.shikugame.base.RespMsg;
import com.shiku.robot.shikugame.entity.imapi.RedPacket;
import com.shiku.robot.shikugame.entity.imapi.RoomGameRule;
import com.shiku.robot.shikugame.entity.imroom.ShikuRoom;
import com.shiku.robot.shikugame.mapper.imapi.RedPacketRepository;
import com.shiku.robot.shikugame.mapper.imapi.RoomGameRuleRepository;
import com.shiku.robot.shikugame.mapper.imroom.ShikuRoomRepository;
import com.shiku.robot.shikugame.server.ShikuRoomServer;
import com.shiku.robot.shikugame.untils.HttpUtil;
import com.shiku.robot.shikugame.untils.PageUtil;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author lijun
 * @Description
 * @Date 2019-12-22 1:55 下午
 **/

@Service
public class ShikuRoomServerImpl implements ShikuRoomServer {

    @Autowired
    ShikuRoomRepository shikuRoomRepository;

    @Autowired
    RoomGameRuleRepository roomGameRuleRepository;

    @Autowired
    RedPacketRepository redPacketRepository;

    @Value("${im.api.uri}")
    String imApiUri;

    @Override
    public JSONObject selectAllShikuRoom(BaseRequestModel request) {
        // 路径带参
        Map<String, Object> map = new HashMap<>();
        map.put("secret", request.getSign());
        map.put("access_token", request.getToken());
        map.put("time", request.getTime());
        map.put("page", request.getPage());
        map.put("limit", request.getLimit());
        String url = HttpUtil.getUrl(imApiUri, "/console/roomList", map);
        if (request.getReqData().getString("name") != null && !request.getReqData().getString("name").equals("")) {
            url += "&keyWorld=" + request.getReqData().getString("name");
        }
        if (request.getReqData().getString("userSize") != null && !request.getReqData().getString("userSize").equals("")) {
            url += "&leastNumbers=" + request.getReqData().getString("userSize");
        }
        String result = HttpUtil.sendGet(url);
        return JSON.parseObject(result);
    }

    @Override
    public JSONObject addShikuRoom(BaseRequestModel request) {
        // 路径带参
        Map<String, Object> map = new HashMap<>();
        map.put("secret", request.getSign());
        map.put("access_token", request.getToken());
        map.put("time", request.getTime());
        String url = HttpUtil.getUrl(imApiUri, "/console/addRoom", map);
        // 创建一个提交数据的容器
        List<BasicNameValuePair> parames = new ArrayList<>();
        parames.add(new BasicNameValuePair("userId", request.getUserId().toString()));
        parames.add(new BasicNameValuePair("name", request.getReqData().getString("name")));
        parames.add(new BasicNameValuePair("desc", request.getReqData().getString("desc")));
        parames.add(new BasicNameValuePair("access_token", request.getToken()));
        String result = HttpUtil.sendPostFrom(url, parames);
        return JSON.parseObject(result);
    }

    @Override
    public JSONObject deleteShikuRoom(BaseRequestModel request) {
        // 路径带参
        Map<String, Object> map = new HashMap<>();
        map.put("secret", request.getSign());
        map.put("access_token", request.getToken());
        map.put("time", request.getTime());
        String url = HttpUtil.getUrl(imApiUri, "/console/deleteRoom", map);
        // 拼接参数
        String param = "userId=" + request.getUserId() + "&roomId=" + request.getReqData().getString("id");
        String result = HttpUtil.sendPost(url, param);
        return JSON.parseObject(result);
    }

    @Override
    public JSONObject updateShikuRoom(BaseRequestModel request) {
        // 路径带参
        Map<String, Object> map = new HashMap<>();
        map.put("secret", request.getSign());
        map.put("access_token", request.getToken());
        map.put("time", request.getTime());
        String url = HttpUtil.getUrl(imApiUri, "/console/updateRoom", map);
        // 创建一个提交数据的容器
        List<BasicNameValuePair> parames = new ArrayList<>();
        parames.add(new BasicNameValuePair("userId", request.getUserId().toString()));
        parames.add(new BasicNameValuePair("roomId", request.getReqData().getString("id")));
        parames.add(new BasicNameValuePair("s", request.getReqData().getString("status")));
        parames.add(new BasicNameValuePair("access_token", request.getToken()));
        String result = HttpUtil.sendPostFrom(url, parames);
        return JSON.parseObject(result);
    }

    @Override
    public JSONObject selectAllGroupcahtlogs(BaseRequestModel request) {
        // 路径带参
        Map<String, Object> map = new HashMap<>();
        map.put("secret", request.getSign());
        map.put("access_token", request.getToken());
        map.put("time", request.getTime());
        map.put("room_jid_id", request.getReqData().getString("jidId"));
        map.put("page", request.getPage());
        map.put("limit", request.getLimit());
        String url = HttpUtil.getUrl(imApiUri, "/console/groupchat_logs_all", map);
        String result = HttpUtil.sendGet(url);
        return JSON.parseObject(result);
    }

    @Override
    public JSONObject selectAllRoomUsermanager(BaseRequestModel request) {
        // 路径带参
        Map<String, Object> map = new HashMap<>();
        map.put("secret", request.getSign());
        map.put("access_token", request.getToken());
        map.put("time", request.getTime());
        map.put("id", request.getReqData().getString("roomId"));
        map.put("page", request.getPage());
        map.put("limit", request.getLimit());
        String url = HttpUtil.getUrl(imApiUri, "/console/roomUserManager", map);
        String result = HttpUtil.sendGet(url);
        return JSON.parseObject(result);
    }

    @Override
    public BaseResponseModel selectRoomGameRule(BaseRequestModel request) {
        BaseResponseModel response = new BaseResponseModel();
        Map<String, Object> map = new HashMap<>();
        ShikuRoom shikuRoom = shikuRoomRepository.selectShikuRoomByRoomJidId(request.getReqData().getString("roomJidId"));
        PageUtil<RoomGameRule> roomGameRules = roomGameRuleRepository.selectRoomGameRule(request.getReqData().getString("roomJidId"), request.getPage(), request.getLimit());
        map.put("shikuRoom", shikuRoom);
        map.put("roomGameRules", roomGameRules);
        response.setRepMsg(RespMsg.SUCCESS_MSG);
        response.setRepCode(RespCode.SUCCESS);
        response.setRepData(map);
        return response;
    }

    @Override
    public RoomGameRule addRoomGameRule(BaseRequestModel request) {
        RoomGameRule roomGameRule = roomGameRuleRepository.selectRoomGameRuleByRBCount(request.getReqData().getString("roomId"), request.getReqData().getString("receiveCount"), request.getReqData().getString("bombCount"), request.getReqData().getString("type"));
        if (roomGameRule != null) {
            roomGameRule = new RoomGameRule();
            roomGameRule.setId("-1");
            return roomGameRule;
        }
        roomGameRule = new RoomGameRule();
        roomGameRule.setRoomId(request.getReqData().getString("roomId"));
        roomGameRule.setRoomJidId(request.getReqData().getString("roomJidId"));
        roomGameRule.setReceiveCount(request.getReqData().getString("receiveCount"));
        roomGameRule.setBombCount(request.getReqData().getString("bombCount"));
        roomGameRule.setChance(request.getReqData().getString("chance"));
        roomGameRule.setType(request.getReqData().getString("type"));
        roomGameRule.setCreateUserId(request.getUserId().toString());
        roomGameRule.setCreateTime(new Date());
        roomGameRule = roomGameRuleRepository.addRoomGameRule(roomGameRule);
        return roomGameRule;
    }

    @Override
    public DeleteResult deleteRoomGameRule(BaseRequestModel request) {
        return roomGameRuleRepository.roomGameRuleRepository(request.getReqData().getString("roomGameId"));
    }

    @Override
    public RoomGameRule selectRoomGameRuleById(BaseRequestModel request) {
        return roomGameRuleRepository.selectRoomGameRuleById(request.getReqData().getString("roomGameId"));
    }

    @Override
    public UpdateResult editRoomGameRule(BaseRequestModel request) {
        RoomGameRule roomGameRule = new RoomGameRule();
        roomGameRule.setId(request.getReqData().getString("id"));
        roomGameRule.setReceiveCount(request.getReqData().getString("receiveCount"));
        roomGameRule.setBombCount(request.getReqData().getString("bombCount"));
        roomGameRule.setChance(request.getReqData().getString("chance"));
        return roomGameRuleRepository.editRoomGameRule(roomGameRule);
    }

    @Override
    public UpdateResult editRoomRedPacketLock(BaseRequestModel request) {
        return shikuRoomRepository.editRoomRedPacketLock(request.getReqData().getString("roomId"), request.getReqData().getString("redPacketLock"));
    }

    @Override
    public ShikuRoom selectShikuRoomByRoomId(BaseRequestModel request) {
        return shikuRoomRepository.selectShikuRoomByRoomId(request.getReqData().getString("roomId"));
    }

    @Override
    public RedPacket selectRedPacketLockById(BaseRequestModel request) {
        return redPacketRepository.selectRedPacketLockById(request.getReqData().getString("redPacketId"));
    }

    @Override
    public UpdateResult editRoomRedPacketMinMax(BaseRequestModel request) {
        return shikuRoomRepository.editRoomRedPacketMinMax(request.getReqData().getString("id"), request.getReqData().getString("minMoney"), request.getReqData().getString("maxMoney"));
    }

}
