package com.shiku.robot.shikugame.controller;

import com.alibaba.fastjson.JSONObject;
import com.shiku.robot.shikugame.base.BaseRequestModel;
import com.shiku.robot.shikugame.base.BaseResponseModel;
import com.shiku.robot.shikugame.base.RespCode;
import com.shiku.robot.shikugame.base.RespMsg;
import com.shiku.robot.shikugame.entity.imapi.RedPacket;
import com.shiku.robot.shikugame.entity.imroom.ShikuRoom;
import com.shiku.robot.shikugame.server.SendMsgService;
import com.shiku.robot.shikugame.server.RedPacketServer;
import com.shiku.robot.shikugame.server.ShikuRoomServer;
import com.shiku.robot.shikugame.untils.GameRuleUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lijun
 * @Description
 * @Date 2019-12-27 11:03 上午
 **/

@RestController
@RequestMapping("/redPacket")
@Log4j2
public class RedPacketController {

    @Autowired
    RedPacketServer redPacketServer;

    @Autowired
    ShikuRoomServer shikuRoomServer;

    @Autowired
    SendMsgService feePlatMqService;

    @RequestMapping(value = "/sendRedPacket", method = RequestMethod.POST)
    public BaseResponseModel sendRedPacket(@RequestBody JSONObject reqData) {
        log.info("------------------------------------发送红包开始------------------------------------");
        BaseResponseModel response = redPacketServer.sendRedPacket(reqData);
        log.info("------------------------------------发送红包结束------------------------------------");
        return response;
    }

    @RequestMapping(value = "/openRedPacket", method = RequestMethod.POST)
    public BaseResponseModel openRedPacket(@RequestBody JSONObject reqData) {
        log.info("------------------------------------打开红包开始------------------------------------");
        BaseResponseModel response = redPacketServer.openRedPacket(reqData);
        log.info("------------------------------------打开红包结束------------------------------------");
        return response;
    }

    @RequestMapping(value = "/getRedPacket", method = RequestMethod.POST)
    public BaseResponseModel getRedPacket(@RequestBody JSONObject reqData) {
        log.info("------------------------------------查询红包详情开始------------------------------------");
        BaseResponseModel response = redPacketServer.getRedPacket(reqData);
        log.info("------------------------------------查询红包详情结束------------------------------------");
        return response;
    }

    @RequestMapping(value = "/selectRoomRedPacketLockByRoomId", method = RequestMethod.POST)
    public BaseResponseModel selectRoomRedPacketLockByRoomId(@RequestBody BaseRequestModel request) {
        log.info("------------------------------------查询群红包锁定状态开始------------------------------------");
        BaseResponseModel response = new BaseResponseModel();
        ShikuRoom shikuRoom = shikuRoomServer.selectShikuRoomByRoomId(request);
        if (shikuRoom != null) {
            response.setRepCode(RespCode.SUCCESS);
            response.setRepMsg(RespMsg.SUCCESS_MSG);
            response.setRepData(shikuRoom.getRedPacketLock());
        } else {
            response.setRepCode(RespCode.SELECT_ROOM_REDPACKET_LOCK_BYID_ERROR);
            response.setRepMsg(RespMsg.SELECT_ROOM_REDPACKET_LOCK_BYID_ERROR_MSG);
        }
        log.info("------------------------------------查询群红包锁定状态结束------------------------------------");
        return response;
    }

    @RequestMapping(value = "/selectRedPacketLockById", method = RequestMethod.POST)
    public BaseResponseModel selectRedPacketLockById(@RequestBody BaseRequestModel request) {
        log.info("------------------------------------查询红包锁定状态开始------------------------------------");
        BaseResponseModel response = new BaseResponseModel();
        RedPacket redPacket = shikuRoomServer.selectRedPacketLockById(request);
        if (redPacket != null) {
            response.setRepCode(RespCode.SUCCESS);
            response.setRepMsg(RespMsg.SUCCESS_MSG);
            response.setRepData(redPacket.getIsLock());
        } else {
            response.setRepCode(RespCode.SELECT_REDPACKET_LOCK_BYID_ERROR);
            response.setRepMsg(RespMsg.SELECT_REDPACKET_LOCK_BYID_ERROR_MSG);
        }
        log.info("------------------------------------查询红包锁定状态结束------------------------------------");
        return response;
    }

    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    public BaseResponseModel sendMessage(@RequestBody BaseRequestModel request) {
        log.info("------------------------------------发送消息开始------------------------------------");
        BaseResponseModel response = new BaseResponseModel();
        JSONObject sendResult = feePlatMqService.openAccountMsg(request.getReqData().getString("msg"));
        if (sendResult != null) {
            response.setRepCode(RespCode.SUCCESS);
            response.setRepMsg(RespMsg.SUCCESS_MSG);
            response.setRepData(sendResult);
        } else {
            response.setRepCode(RespCode.SELECT_REDPACKET_LOCK_BYID_ERROR);
            response.setRepMsg(RespMsg.SELECT_REDPACKET_LOCK_BYID_ERROR_MSG);
        }
        log.info("------------------------------------发送消息结束------------------------------------");
        return response;
    }

    @RequestMapping(value = "/checkGreetings", method = RequestMethod.POST)
    public BaseResponseModel checkGreetings(@RequestBody BaseRequestModel request) {
        log.info("------------------------------------检测雷开始------------------------------------");
        BaseResponseModel response = new BaseResponseModel();
        JSONObject jsonObject = GameRuleUtil.getBomb(request.getReqData().getString("money"), request.getReqData().getString("greetings").trim());
        if (jsonObject.get("code").equals("1")) {
            response.setRepCode(RespCode.SUCCESS);
            response.setRepMsg(RespMsg.SUCCESS_MSG);
            response.setRepData(jsonObject.get("data"));
        } else if (jsonObject.get("code").equals("-1")) {
            response.setRepCode("-1");
            response.setRepMsg("祝福语必须不为空");
        } else if (jsonObject.get("code").equals("-2")) {
            response.setRepCode("-2");
            response.setRepMsg("除以特殊字符开头，还有特殊字符");
        } else if (jsonObject.get("code").equals("-3")) {
            response.setRepCode("-3");
            response.setRepMsg("除以特殊字符结尾，还有特殊字符");
        } else if (jsonObject.get("code").equals("-4")) {
            response.setRepCode("-4");
            response.setRepMsg("开头结尾没有特殊字符，中间有多个特殊字符");
        } else if (jsonObject.get("code").equals("-5")) {
            response.setRepCode("-5");
            response.setRepMsg("开头结尾没有特殊字符，中间有多个特殊字符");
        } else if (jsonObject.get("code").equals("-6")) {
            response.setRepCode("-6");
            response.setRepMsg("福利包");
        } else if (jsonObject.get("code").equals("-7")) {
            response.setRepCode("-7");
            response.setRepMsg("锁定红包");
        }
        log.info("------------------------------------检测雷结束------------------------------------");
        return response;
    }
}
