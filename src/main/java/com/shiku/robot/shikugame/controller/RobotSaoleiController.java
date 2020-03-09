package com.shiku.robot.shikugame.controller;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.shiku.robot.shikugame.base.BaseRequestModel;
import com.shiku.robot.shikugame.base.BaseResponseModel;
import com.shiku.robot.shikugame.base.RespCode;
import com.shiku.robot.shikugame.base.RespMsg;
import com.shiku.robot.shikugame.entity.imapi.User;
import com.shiku.robot.shikugame.entity.imroom.ShikuRobotSaoLei;
import com.shiku.robot.shikugame.mapper.imapi.RobotSaoleiRepository;
import com.shiku.robot.shikugame.mapper.imapi.UserRepository;
import com.shiku.robot.shikugame.server.UserService;
import com.shiku.robot.shikugame.untils.PageUtil;
import com.shiku.robot.shikugame.untils.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author yuanye
 * @Description
 * @Date 2019-3-8 2:00 下午
 **/

@RestController
@RequestMapping("/robotSaolei")
@Log4j2
public class RobotSaoleiController {

    @Autowired
    RobotSaoleiRepository robotSaoleiRepository;
    @RequestMapping(value = "/selectRobotSaolei", method = RequestMethod.POST)
    public BaseResponseModel selectRobotSaolei(@RequestBody BaseRequestModel request) {
        log.info("------------------------------------分页查询机器人列表开始------------------------------------");
        BaseResponseModel response = new BaseResponseModel();
        PageUtil<ShikuRobotSaoLei> data= robotSaoleiRepository.selectAllPage(request.getPage(), request.getLimit());
        response.setRepCode(RespCode.SUCCESS);
        response.setRepMsg(RespMsg.SUCCESS_MSG);
        response.setRepData(data);
        log.info("------------------------------------分页查询机器人列表结束------------------------------------");
        return response;
    }
    @RequestMapping(value = "/selectById", method = RequestMethod.POST)
    public BaseResponseModel selectById(@RequestBody BaseRequestModel request) {
        log.info("------------------------------------查询机器人列表开始------------------------------------");
        BaseResponseModel response = new BaseResponseModel();
        ShikuRobotSaoLei obj = robotSaoleiRepository.selectById(request.getReqData().getString("id"));
        if (obj != null) {
            response.setRepCode(RespCode.SUCCESS);
            response.setRepMsg(RespMsg.SUCCESS_MSG);
            response.setRepData(obj);
        } else {
            response.setRepCode(RespCode.ERROR);
            response.setRepMsg(RespMsg.ERROR_MSG);
            response.setSuccess(false);
        }
        log.info("------------------------------------查询机器人列表结束------------------------------------");
        return response;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public BaseResponseModel add(@RequestBody BaseRequestModel request) {
        log.info("------------------------------------新增机器人开始------------------------------------");
        BaseResponseModel response = new BaseResponseModel();
        ShikuRobotSaoLei obj = new ShikuRobotSaoLei();
        obj.setUserId(request.getReqData().getString("userId"));
        obj.setUserKey(request.getReqData().getString("userKey"));
        obj = robotSaoleiRepository.addObj(obj);
        if (StringUtil.isEmpty(obj.getId(),false)) {
            response.setRepCode(RespCode.SUCCESS);
            response.setRepMsg(RespMsg.SUCCESS_MSG);
            response.setRepData(obj);
        } else {
            response.setRepCode(RespCode.ERROR);
            response.setRepMsg(RespMsg.ERROR_MSG);
            response.setSuccess(false);
        }
        log.info("------------------------------------新增机器人结束------------------------------------");
        return response;
    }

    @RequestMapping(value = "/updateRobot", method = RequestMethod.POST)
    public BaseResponseModel updateRobot(@RequestBody BaseRequestModel request) {
        log.info("-----------------------------------修改机器人开始------------------------------------");
        BaseResponseModel response = new BaseResponseModel();
        ShikuRobotSaoLei obj = new ShikuRobotSaoLei();
        obj.setId(request.getReqData().getString("id"));
        obj.setIsStartUp(request.getReqData().getString("sex"));
        obj.setRapPacketSpeed(request.getReqData().getString("birthday"));
        obj.setSendPacketBlessings(request.getReqData().getString("sex"));
        obj.setSendPacketMin(request.getReqData().getString("birthday"));
        obj.setSendPacketMax(request.getReqData().getString("sex"));
        obj.setSendPacketNum(request.getReqData().getString("birthday"));
        obj.setSendPacketSize(request.getReqData().getString("birthday"));

        UpdateResult updateResult = robotSaoleiRepository.updateObj(obj);
        if (updateResult.getMatchedCount() > 0) {
            response.setRepCode(RespCode.SUCCESS);
            response.setRepMsg(RespMsg.SUCCESS_MSG);
        } else {
            response.setRepCode(RespCode.ERROR);
            response.setRepMsg(RespMsg.ERROR_MSG);
            response.setSuccess(false);
        }

        log.info("------------------------------------修改 机器人结束------------------------------------");
        return response;
    }
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public BaseResponseModel delete(@RequestBody BaseRequestModel request) {
        log.info("------------------------------------删除机器人开始------------------------------------");
        BaseResponseModel response = new BaseResponseModel();
        DeleteResult del = robotSaoleiRepository.delById(request.getReqData().getString("id"));
        if (del.getDeletedCount() > 0) {
            response.setRepCode(RespCode.SUCCESS);
            response.setRepMsg(RespMsg.SUCCESS_MSG);
        } else {
            response.setRepCode(RespCode.DELETE_GAME_RULE);
            response.setRepMsg(RespMsg.DELETE_GAME_RULE_MSG);
        }
        log.info("------------------------------------删除机器人结束------------------------------------");
        return response;
    }


}
