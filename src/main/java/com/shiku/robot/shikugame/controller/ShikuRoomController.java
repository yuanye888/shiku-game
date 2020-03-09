package com.shiku.robot.shikugame.controller;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.shiku.robot.shikugame.base.BaseRequestModel;
import com.shiku.robot.shikugame.base.BaseResponseModel;
import com.shiku.robot.shikugame.base.RespCode;
import com.shiku.robot.shikugame.base.RespMsg;
import com.shiku.robot.shikugame.entity.imapi.RoomGameRule;
import com.shiku.robot.shikugame.server.ShikuRoomServer;
import com.shiku.robot.shikugame.untils.PageUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lijun
 * @Description
 * @Date 2019-12-22 2:00 下午
 **/

@RestController
@RequestMapping("/shikuRoom")
@Log4j2
public class ShikuRoomController {

    @Autowired
    ShikuRoomServer shikuRoomServer;

    @RequestMapping(value = "/selectAllShikuRoom", method = RequestMethod.POST)
    public BaseResponseModel selectAllShikuRoom(@RequestBody BaseRequestModel request) {
        log.info("------------------------------------分页查询群组列表开始------------------------------------");
        BaseResponseModel response = new BaseResponseModel();
        JSONObject jsonObject = shikuRoomServer.selectAllShikuRoom(request);
        if (jsonObject.getString("resultCode") == null || jsonObject.getString("resultCode").equals("")) {
            response.setRepCode(RespCode.ERROR);
            response.setRepMsg(RespMsg.ERROR_MSG);
        } else if (jsonObject.getString("resultCode").equals("1")) {
            response.setRepCode(RespCode.SUCCESS);
            response.setRepMsg(RespMsg.SUCCESS_MSG);
            response.setRepData(jsonObject);
        } else {
            response.setRepCode(jsonObject.getString("resultCode"));
            response.setRepMsg(jsonObject.getString("resultMsg"));
        }
        log.info("------------------------------------分页查询群组列表结束------------------------------------");
        return response;
    }

    @RequestMapping(value = "/addShikuRoom", method = RequestMethod.POST)
    public BaseResponseModel addShikuRoom(@RequestBody BaseRequestModel request) {
        log.info("------------------------------------新增群组开始------------------------------------");
        BaseResponseModel response = new BaseResponseModel();
        JSONObject jsonObject = shikuRoomServer.addShikuRoom(request);
        if (jsonObject.getString("resultCode") == null || jsonObject.getString("resultCode").equals("")) {
            response.setRepCode(RespCode.ERROR);
            response.setRepMsg(RespMsg.ERROR_MSG);
        } else if (jsonObject.getString("resultCode").equals("1")) {
            response.setRepCode(RespCode.SUCCESS);
            response.setRepMsg(RespMsg.SUCCESS_MSG);
            response.setRepData(jsonObject);
        } else {
            response.setRepCode(jsonObject.getString("resultCode"));
            response.setRepMsg(jsonObject.getString("resultMsg"));
        }
        log.info("------------------------------------新增群组结束------------------------------------");
        return response;
    }

    @RequestMapping(value = "/deleteShikuRoom", method = RequestMethod.POST)
    public BaseResponseModel deleteShikuRoom(@RequestBody BaseRequestModel request) {
        log.info("------------------------------------删除群组开始------------------------------------");
        BaseResponseModel response = new BaseResponseModel();
        JSONObject jsonObject = shikuRoomServer.deleteShikuRoom(request);
        if (jsonObject.getString("resultCode") == null || jsonObject.getString("resultCode").equals("")) {
            response.setRepCode(RespCode.ERROR);
            response.setRepMsg(RespMsg.ERROR_MSG);
        } else if (jsonObject.getString("resultCode").equals("1")) {
            response.setRepCode(RespCode.SUCCESS);
            response.setRepMsg(RespMsg.SUCCESS_MSG);
            response.setRepData(jsonObject);
        } else {
            response.setRepCode(jsonObject.getString("resultCode"));
            response.setRepMsg(jsonObject.getString("resultMsg"));
        }
        log.info("------------------------------------删除群组结束------------------------------------");
        return response;
    }

    @RequestMapping(value = "/updateShikuRoom", method = RequestMethod.POST)
    public BaseResponseModel updateShikuRoom(@RequestBody BaseRequestModel request) {
        log.info("------------------------------------修改群组开始------------------------------------");
        BaseResponseModel response = new BaseResponseModel();
        JSONObject jsonObject = shikuRoomServer.updateShikuRoom(request);
        if (jsonObject.getString("resultCode") == null || jsonObject.getString("resultCode").equals("")) {
            response.setRepCode(RespCode.ERROR);
            response.setRepMsg(RespMsg.ERROR_MSG);
        } else if (jsonObject.getString("resultCode").equals("1")) {
            response.setRepCode(RespCode.SUCCESS);
            response.setRepMsg(RespMsg.SUCCESS_MSG);
            response.setRepData(jsonObject);
        } else {
            response.setRepCode(jsonObject.getString("resultCode"));
            response.setRepMsg(jsonObject.getString("resultMsg"));
        }
        log.info("------------------------------------修改群组结束------------------------------------");
        return response;
    }

    @RequestMapping(value = "/selectAllGroupcahtlogs", method = RequestMethod.POST)
    public BaseResponseModel selectAllGroupcahtlogs(@RequestBody BaseRequestModel request) {
        log.info("------------------------------------查询群组聊天记录开始------------------------------------");
        BaseResponseModel response = new BaseResponseModel();
        JSONObject jsonObject = shikuRoomServer.selectAllGroupcahtlogs(request);
        if (jsonObject.getString("resultCode") == null || jsonObject.getString("resultCode").equals("")) {
            response.setRepCode(RespCode.ERROR);
            response.setRepMsg(RespMsg.ERROR_MSG);
        } else if (jsonObject.getString("resultCode").equals("1")) {
            response.setRepCode(RespCode.SUCCESS);
            response.setRepMsg(RespMsg.SUCCESS_MSG);
            response.setRepData(jsonObject);
        } else {
            response.setRepCode(jsonObject.getString("resultCode"));
            response.setRepMsg(jsonObject.getString("resultMsg"));
        }
        log.info("------------------------------------查询群组聊天记录结束------------------------------------");
        return response;
    }

    @RequestMapping(value = "/selectAllRoomUsermanager", method = RequestMethod.POST)
    public BaseResponseModel selectAllRoomUsermanager(@RequestBody BaseRequestModel request) {
        log.info("------------------------------------查询群组成员开始------------------------------------");
        BaseResponseModel response = new BaseResponseModel();
        JSONObject jsonObject = shikuRoomServer.selectAllRoomUsermanager(request);
        if (jsonObject.getString("resultCode") == null || jsonObject.getString("resultCode").equals("")) {
            response.setRepCode(RespCode.ERROR);
            response.setRepMsg(RespMsg.ERROR_MSG);
        } else if (jsonObject.getString("resultCode").equals("1")) {
            response.setRepCode(RespCode.SUCCESS);
            response.setRepMsg(RespMsg.SUCCESS_MSG);
            response.setRepData(jsonObject);
        } else {
            response.setRepCode(jsonObject.getString("resultCode"));
            response.setRepMsg(jsonObject.getString("resultMsg"));
        }
        log.info("------------------------------------查询群组成员结束------------------------------------");
        return response;
    }

    @RequestMapping(value = "/selectRoomGameRule", method = RequestMethod.POST)
    public BaseResponseModel selectRoomGameRule(@RequestBody BaseRequestModel request) {
        log.info("------------------------------------查询群组游戏规则开始------------------------------------");
        BaseResponseModel response = shikuRoomServer.selectRoomGameRule(request);
        log.info("------------------------------------查询群组游戏规则结束------------------------------------");
        return response;
    }

    @RequestMapping(value = "/addRoomGameRule", method = RequestMethod.POST)
    public BaseResponseModel addRoomGameRule(@RequestBody BaseRequestModel request) {
        log.info("------------------------------------新增群组游戏规则开始------------------------------------");
        BaseResponseModel response = new BaseResponseModel();
        RoomGameRule roomGameRule = shikuRoomServer.addRoomGameRule(request);
        if (roomGameRule != null && roomGameRule.getId().equals("-1")) {
            response.setRepCode(RespCode.ADD_ROOM_GAME_RULE_REPEAT_ERROR);
            response.setRepMsg(RespMsg.ADD_ROOM_GAME_RULE_REPEAT_ERROR_MSG);
        } else if (roomGameRule != null){
            response.setRepCode(RespCode.SUCCESS);
            response.setRepMsg(RespMsg.SUCCESS_MSG);
        }else {
            response.setRepCode(RespCode.ADD_ROOM_GAME_RULE_ERROR);
            response.setRepMsg(RespMsg.ADD_ROOM_GAME_RULE_ERROR_MSG);
        }
        log.info("------------------------------------新增群组游戏规则结束------------------------------------");
        return response;
    }

    @RequestMapping(value = "/deleteRoomGameRule", method = RequestMethod.POST)
    public BaseResponseModel deleteRoomGameRule(@RequestBody BaseRequestModel request) {
        log.info("------------------------------------删除游戏规则开始------------------------------------");
        BaseResponseModel response = new BaseResponseModel();
        DeleteResult deleteResult = shikuRoomServer.deleteRoomGameRule(request);
        if (deleteResult.getDeletedCount() > 0) {
            response.setRepCode(RespCode.SUCCESS);
            response.setRepMsg(RespMsg.SUCCESS_MSG);
        } else {
            response.setRepCode(RespCode.DELETE_GAME_RULE);
            response.setRepMsg(RespMsg.DELETE_GAME_RULE_MSG);
        }
        log.info("------------------------------------删除游戏规则结束------------------------------------");
        return response;
    }

    @RequestMapping(value = "/selectRoomGameRuleById", method = RequestMethod.POST)
    public BaseResponseModel selectRoomGameRuleById(@RequestBody BaseRequestModel request) {
        log.info("------------------------------------通过ID查询游戏规则开始------------------------------------");
        BaseResponseModel response = new BaseResponseModel();
        JSONObject jsonObject = new JSONObject();
        RoomGameRule roomGameRule = shikuRoomServer.selectRoomGameRuleById(request);
        jsonObject.put("roomGameRule", roomGameRule);
        response.setRepCode(RespCode.SUCCESS);
        response.setRepMsg(RespMsg.SUCCESS_MSG);
        response.setRepData(jsonObject);
        log.info("------------------------------------通过ID查询游戏规则结束------------------------------------");
        return response;
    }

    @RequestMapping(value = "/editRoomGameRule", method = RequestMethod.POST)
    public BaseResponseModel editRoomGameRule(@RequestBody BaseRequestModel request) {
        log.info("------------------------------------编辑游戏规则开始------------------------------------");
        BaseResponseModel response = new BaseResponseModel();
        UpdateResult updateResult = shikuRoomServer.editRoomGameRule(request);
        if (updateResult.getMatchedCount() > 0) {
            response.setRepCode(RespCode.SUCCESS);
            response.setRepMsg(RespMsg.SUCCESS_MSG);
        } else {
            response.setRepCode(RespCode.DELETE_GAME_RULE);
            response.setRepMsg(RespMsg.DELETE_GAME_RULE_MSG);
        }
        log.info("------------------------------------编辑游戏规则结束------------------------------------");
        return response;
    }

    @RequestMapping(value = "/editRoomRedPacketLock", method = RequestMethod.POST)
    public BaseResponseModel editRoomRedPacketLock(@RequestBody BaseRequestModel request) {
        log.info("------------------------------------编辑群红包锁定/解锁开始------------------------------------");
        BaseResponseModel response = new BaseResponseModel();
        UpdateResult updateResult = shikuRoomServer.editRoomRedPacketLock(request);
        if (updateResult.getMatchedCount() > 0) {
            response.setRepCode(RespCode.SUCCESS);
            response.setRepMsg(RespMsg.SUCCESS_MSG);
        } else {
            response.setRepCode(RespCode.ROOM_REDPACKET_LOCK_ERROR);
            response.setRepMsg(RespMsg.ROOM_REDPACKET_LOCK_ERROR_MSG);
        }
        log.info("------------------------------------编辑群红包锁定/解锁结束------------------------------------");
        return response;
    }

    @RequestMapping(value = "/editRoomRedPacketMinMax", method = RequestMethod.POST)
    public BaseResponseModel editRoomRedPacketMinMax(@RequestBody BaseRequestModel request) {
        log.info("------------------------------------编辑群红包最大最小值开始------------------------------------");
        BaseResponseModel response = new BaseResponseModel();
        UpdateResult updateResult = shikuRoomServer.editRoomRedPacketMinMax(request);
        if (updateResult.getMatchedCount() > 0) {
            response.setRepCode(RespCode.SUCCESS);
            response.setRepMsg(RespMsg.SUCCESS_MSG);
        } else {
            response.setRepCode(RespCode.ROOM_REDPACKET_LOCK_ERROR);
            response.setRepMsg(RespMsg.ROOM_REDPACKET_LOCK_ERROR_MSG);
        }
        log.info("------------------------------------编辑群红包最大最小值结束------------------------------------");
        return response;
    }

}
