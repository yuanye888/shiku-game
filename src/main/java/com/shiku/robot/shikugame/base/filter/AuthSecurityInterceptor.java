package com.shiku.robot.shikugame.base.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shiku.robot.shikugame.base.BaseResponseModel;
import com.shiku.robot.shikugame.base.RespCode;
import com.shiku.robot.shikugame.base.RespMsg;
import com.shiku.robot.shikugame.untils.RedisUtil;
import com.shiku.robot.shikugame.untils.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component("authSecurityInterceptor")
@Log4j2
public class AuthSecurityInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("------------------------------------拦截器参数验签------------------------------------");
        BaseResponseModel responseModel = new BaseResponseModel();
        try {
            response.setHeader("Content-type", "application/json;charset=UTF-8");
            String requestMethord = request.getRequestURI();//请求方法
            if (StringUtil.isEmpty(requestMethord, true)) {
                return false;
            }

            // 获取Content-Type=application/json 请求参数
            JSONObject parameterMap = JSON.parseObject(new RequestWrapper(request).getBodyString(request));
            if (StringUtil.isEmpty(parameterMap, true)) {
                responseModel.setRepCode(RespCode.CHECK_REQUEST_DATA_ERROR);
                responseModel.setRepMsg(RespMsg.CHECK_REQUEST_DATA_ERROR_MSG);
                responseModel.setRepData("");
                ajaxResponseJsonReturn(response, request, responseModel);
                return false;
            }
            log.info("请求参数：" + parameterMap.toJSONString());
            if (!StringUtil.isEmpty(parameterMap.getString("token"), true) &&
                    !StringUtil.isEmpty(redisUtil.get(parameterMap.getString("userId")), true)) {
                if (!parameterMap.getString("token").equals(redisUtil.get(parameterMap.getString("userId")))) {
                    responseModel.setRepCode(RespCode.CHECK_TOKEN_OUT_ERROR);
                    responseModel.setRepMsg(RespMsg.CHECK_TOKEN_OUT_ERROR_MSG);
                    responseModel.setRepData("");
                    ajaxResponseJsonReturn(response, request, responseModel);
                    return false;
                } else {
                    redisUtil.set(parameterMap.getString("userId"), redisUtil.get(parameterMap.getString("userId")), 30 * 60);
                }
            } else {
                responseModel.setRepCode(RespCode.CHECK_TOKEN_NOT_ERROR);
                responseModel.setRepMsg(RespMsg.CHECK_TOKEN_NOT_ERROR_MSG);
                responseModel.setRepData("");
                ajaxResponseJsonReturn(response, request, responseModel);
                return false;
            }

        } catch (Exception e) {
            log.error("接口调用异常：", e);
            e.printStackTrace();
            responseModel.setRepCode(RespCode.CHECK_TOKEN_ERROR);
            responseModel.setRepMsg(RespMsg.CHECK_TOKEN_ERROR_MSG);
            responseModel.setRepData("");
            ajaxResponseJsonReturn(response, request, responseModel);
            return false;
        }
        log.info("------------------------------------验签通过------------------------------------");
        return true;
    }


    protected void ajaxResponseJsonReturn(HttpServletResponse response, HttpServletRequest request, BaseResponseModel responseJson) throws IOException {
        response.setCharacterEncoding("UTF-8");//Constant.ENCODE_UTF8
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(JSONObject.parseObject(responseJson.toJsonString()));
        out.flush();
        out.close();
    }
}
