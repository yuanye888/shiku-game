package com.shiku.robot.shikugame.controller;

import com.alibaba.fastjson.JSONObject;
import com.shiku.robot.shikugame.base.BaseRequestModel;
import com.shiku.robot.shikugame.base.BaseResponseModel;
import com.shiku.robot.shikugame.base.RespCode;
import com.shiku.robot.shikugame.base.RespMsg;
import com.shiku.robot.shikugame.server.LoginServer;
import com.shiku.robot.shikugame.untils.RedisUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author lijun
 * @Description
 * @Date 2019-12-21 4:09 下午
 **/

@RestController
@Log4j2
public class LoginController {

    @Autowired
    LoginServer loginServer;

    @Autowired
    RedisUtil redisUtil;

    // 用户登陆
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public BaseResponseModel login(@RequestBody BaseRequestModel request) {
        log.info("------------------------------------登陆开始------------------------------------");
        BaseResponseModel response = new BaseResponseModel();
        String account = request.getReqData().getString("account");
        String password = request.getReqData().getString("password");
        JSONObject jsonObject = loginServer.login(account, password);
        if (jsonObject.getString("resultCode") == null || jsonObject.getString("resultCode").equals("")) {
            response.setRepCode(RespCode.ERROR);
            response.setRepMsg(RespMsg.ERROR_MSG);
        } else if (jsonObject.getString("resultCode").equals("1")) {
            JSONObject object =  jsonObject.getJSONObject("data");
            redisUtil.set(object.getString("account"), object.getString("access_Token"), 30 * 60);
            response.setRepCode(RespCode.SUCCESS);
            response.setRepMsg(RespMsg.SUCCESS_MSG);
            response.setRepData(jsonObject);
        } else {
            response.setRepCode(jsonObject.getString("resultCode"));
            response.setRepMsg(jsonObject.getString("resultMsg"));
        }
        log.info("------------------------------------登陆结束------------------------------------");
        return response;
    }

    // 获取imapi系统时间
    @RequestMapping(value = "/getCurrentTime", method = RequestMethod.POST)
    public BaseResponseModel getCurrentTime() {
        log.info("------------------------------------获取时间开始------------------------------------");
        JSONObject jsonObject = loginServer.getCurrentTime();
        BaseResponseModel response = new BaseResponseModel();
        response.setRepCode(RespCode.SUCCESS);
        response.setRepMsg(RespMsg.SUCCESS_MSG);
        response.setRepData(jsonObject);
        log.info("------------------------------------获取时间结束------------------------------------");
        return response;
    }
}
