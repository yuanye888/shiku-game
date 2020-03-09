package com.shiku.robot.shikugame.controller;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.shiku.robot.shikugame.base.BaseRequestModel;
import com.shiku.robot.shikugame.base.BaseResponseModel;
import com.shiku.robot.shikugame.base.RespCode;
import com.shiku.robot.shikugame.base.RespMsg;
import com.shiku.robot.shikugame.entity.imapi.RoomGameRule;
import com.shiku.robot.shikugame.entity.imapi.User;
import com.shiku.robot.shikugame.mapper.imapi.UserRepository;
import com.shiku.robot.shikugame.server.ShikuRoomServer;
import com.shiku.robot.shikugame.server.UserService;
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
@RequestMapping("/user")
@Log4j2
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @RequestMapping(value = "/selectAllRobot", method = RequestMethod.POST)
    public BaseResponseModel selectAllRobot(@RequestBody BaseRequestModel request) {
        log.info("------------------------------------分页查询机器人列表开始------------------------------------");
        BaseResponseModel response = new BaseResponseModel();
        JSONObject jsonObject = userService.selectAllRobot(request);
        if (jsonObject.getString("resultCode") == null || "".equals(jsonObject.getString("resultCode"))) {
            response.setRepCode(RespCode.ERROR);
            response.setRepMsg(RespMsg.ERROR_MSG);
            response.setSuccess(false);
        } else if (jsonObject.getString("resultCode").equals("1")) {
            response.setRepCode(RespCode.SUCCESS);
            response.setRepMsg(RespMsg.SUCCESS_MSG);
            response.setRepData(jsonObject);
        } else {
            response.setRepCode(jsonObject.getString("resultCode"));
            response.setRepMsg(jsonObject.getString("resultMsg"));
            response.setSuccess(false);
        }
        log.info("------------------------------------分页查询机器人列表结束------------------------------------");
        return response;
    }
    @RequestMapping(value = "/selectUserById", method = RequestMethod.POST)
    public BaseResponseModel selectUserById(@RequestBody BaseRequestModel request) {
        log.info("------------------------------------分页查询机器人列表开始------------------------------------");
        BaseResponseModel response = new BaseResponseModel();
        User user = userRepository.selectUserById(request.getReqData().getString("userId"));
        if (user != null) {
            response.setRepCode(RespCode.SUCCESS);
            response.setRepMsg(RespMsg.SUCCESS_MSG);
            response.setRepData(user);
        } else {
            response.setRepCode(RespCode.ERROR);
            response.setRepMsg(RespMsg.ERROR_MSG);
            response.setSuccess(false);
        }
        log.info("------------------------------------分页查询机器人列表结束------------------------------------");
        return response;
    }

    @RequestMapping(value = "/addRobot", method = RequestMethod.POST)
    public BaseResponseModel addRobot(@RequestBody BaseRequestModel request) {
        log.info("------------------------------------新增机器人开始------------------------------------");
        BaseResponseModel response = new BaseResponseModel();
        JSONObject jsonObject = userService.addShikuRobot(request);
        if (jsonObject.getString("resultCode") == null || "".equals(jsonObject.getString("resultCode"))) {
            response.setRepCode(RespCode.ERROR);
            response.setRepMsg(RespMsg.ERROR_MSG);
            response.setSuccess(false);
        } else if (jsonObject.getString("resultCode").equals("1")) {
            response.setRepCode(RespCode.SUCCESS);
            response.setRepMsg(RespMsg.SUCCESS_MSG);
            response.setRepData(jsonObject);
        } else {
            response.setRepCode(jsonObject.getString("resultCode"));
            response.setRepMsg(jsonObject.getString("resultMsg"));
            response.setSuccess(false);
        }
        log.info("------------------------------------新增机器人结束------------------------------------");
        return response;
    }

    @RequestMapping(value = "/updateRobot", method = RequestMethod.POST)
    public BaseResponseModel updateRobot(@RequestBody BaseRequestModel request) {
        log.info("-----------------------------------修改机器人开始------------------------------------");
        BaseResponseModel response = new BaseResponseModel();
        User user = new User();
        user.setId(request.getReqData().getString("userId"));
        user.setNickname(request.getReqData().getString("userName"));
        user.setTelephone(request.getReqData().getString("telephone"));
        user.setSex(request.getReqData().getString("sex"));
        user.setBirthday(request.getReqData().getString("birthday"));

        UpdateResult updateResult = userRepository.updateRobot(user);
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
    @RequestMapping(value = "/deleteRobot", method = RequestMethod.POST)
    public BaseResponseModel deleteRobot(@RequestBody BaseRequestModel request) {
        log.info("------------------------------------删除机器人开始------------------------------------");
        BaseResponseModel response = new BaseResponseModel();
        JSONObject jsonObject = userService.deleteRobot(request);
        if (jsonObject.getString("resultCode") == null || "".equals(jsonObject.getString("resultCode"))) {
            response.setRepCode(RespCode.ERROR);
            response.setRepMsg(RespMsg.ERROR_MSG);
            response.setSuccess(false);
        } else if (jsonObject.getString("resultCode").equals("1")) {
            response.setRepCode(RespCode.SUCCESS);
            response.setRepMsg(RespMsg.SUCCESS_MSG);
            response.setRepData(jsonObject);
        } else {
            response.setRepCode(jsonObject.getString("resultCode"));
            response.setRepMsg(jsonObject.getString("resultMsg"));
            response.setSuccess(false);
        }
        log.info("------------------------------------删除机器人结束------------------------------------");
        return response;
    }

    @RequestMapping(value = "/handCash", method = RequestMethod.POST)
    public BaseResponseModel handCash(@RequestBody BaseRequestModel request) {
        log.info("------------------------------------删除机器人开始------------------------------------");
        BaseResponseModel response = new BaseResponseModel();
        JSONObject jsonObject = userService.handCash(request);
        if (jsonObject.getString("resultCode") == null ||"".equals(jsonObject.getString("resultCode"))) {
            response.setRepCode(RespCode.ERROR);
            response.setRepMsg(RespMsg.ERROR_MSG);
            response.setSuccess(false);
        } else if (jsonObject.getString("resultCode").equals("1")) {
            response.setRepCode(RespCode.SUCCESS);
            response.setRepMsg(RespMsg.SUCCESS_MSG);
            response.setRepData(jsonObject);
        } else {
            response.setRepCode(jsonObject.getString("resultCode"));
            response.setRepMsg(jsonObject.getString("resultMsg"));
            response.setSuccess(false);
        }
        log.info("------------------------------------删除机器人结束------------------------------------");
        return response;
    }
    @RequestMapping(value = "/recharge", method = RequestMethod.POST)
    public BaseResponseModel recharge(@RequestBody BaseRequestModel request) {
        log.info("------------------------------------充值机器人开始------------------------------------");
        BaseResponseModel response = new BaseResponseModel();
        JSONObject jsonObject = userService.recharge(request);
        if (jsonObject.getString("resultCode") == null || "".equals(jsonObject.getString("resultCode"))) {
            response.setRepCode(RespCode.ERROR);
            response.setRepMsg(RespMsg.ERROR_MSG);
            response.setSuccess(false);
        } else if (jsonObject.getString("resultCode").equals("1")) {
            response.setRepCode(RespCode.SUCCESS);
            response.setRepMsg(RespMsg.SUCCESS_MSG);
            response.setRepData(jsonObject);
        } else {
            response.setRepCode(jsonObject.getString("resultCode"));
            response.setRepMsg(jsonObject.getString("resultMsg"));
            response.setSuccess(false);
        }
        log.info("------------------------------------充值机器人结束------------------------------------");
        return response;
    }
}
