package com.shiku.robot.shikugame.server.impl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shiku.robot.shikugame.base.BaseRequestModel;
import com.shiku.robot.shikugame.mapper.imapi.UserRepository;
import com.shiku.robot.shikugame.server.UserService;
import com.shiku.robot.shikugame.untils.HttpUtil;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Value("${im.api.uri}")
    String imApiUri;
    @Override
    public JSONObject selectAllRobot(BaseRequestModel request) {
        // 路径带参
        Map<String, Object> map = new HashMap<>();
        map.put("secret", request.getSign());
        map.put("access_token", request.getToken());
        map.put("time", request.getTime());
        map.put("page", request.getPage());
        map.put("limit", request.getLimit());
        String url = HttpUtil.getUrl(imApiUri, "/console/userList", map);
        if (request.getReqData().getString("name") != null && !request.getReqData().getString("name").equals("")) {
            url += "&keyWorld=" + request.getReqData().getString("name");
        }
        if (request.getReqData().getString("userSize") != null && !request.getReqData().getString("userSize").equals("")) {
            url += "&leastNumbers=" + request.getReqData().getString("userSize");
        }

        url += "&userType=99";
        String result = HttpUtil.sendGet(url);
        return JSON.parseObject(result);
    }

    @Override
    public JSONObject addShikuRobot(BaseRequestModel request) {
        // 路径带参
        Map<String, Object> map = new HashMap<>();
        map.put("secret", request.getSign());
        map.put("access_token", request.getToken());
        map.put("time", request.getTime());
        String url = HttpUtil.getUrl(imApiUri, "/console/updateUser", map);
        if( request.getReqData().getString("userId") != null && !"".equals( request.getReqData().getString("userId"))){
            url += "&userId=" + request.getReqData().getString("userId");
        }
        // 创建一个提交数据的容器
        List<BasicNameValuePair> parames = new ArrayList<>();
//        parames.add(new BasicNameValuePair("userId", request.getReqData().getString("userId")));
        parames.add(new BasicNameValuePair("nickname", request.getReqData().getString("userName")));
        parames.add(new BasicNameValuePair("birthday", request.getReqData().getString("birthday")));
        parames.add(new BasicNameValuePair("sex", request.getReqData().getString("sex")));
        parames.add(new BasicNameValuePair("telephone", request.getReqData().getString("telephone")));
        parames.add(new BasicNameValuePair("userType", "99"));
        parames.add(new BasicNameValuePair("password", "123456"));

        String result = HttpUtil.sendPostFrom(url, parames);
        return JSON.parseObject(result);
    }



    @Override
    public JSONObject deleteRobot(BaseRequestModel request) {
        // 路径带参
        Map<String, Object> map = new HashMap<>();
        map.put("secret", request.getSign());
        map.put("access_token", request.getToken());
        map.put("time", request.getTime());
        String url = HttpUtil.getUrl(imApiUri, "/console/deleteUser", map);
        // 拼接参数
        String param = "userId=" +request.getReqData().getString("userId");
        String result = HttpUtil.sendPost(url, param);
        return JSON.parseObject(result);
    }

    @Override
    public JSONObject recharge(BaseRequestModel request) {
        // 路径带参
        Map<String, Object> map = new HashMap<>();
        map.put("secret", request.getSign());
        map.put("access_token", request.getToken());
        map.put("time", request.getTime());
        String url = HttpUtil.getUrl(imApiUri, "/console/Recharge", map);
        // 拼接参数
        url += "&money=" + request.getReqData().getString("money");
        url += "&userId=" + request.getReqData().getString("userId");
        String param = "userId=" + request.getUserId();
        String result = HttpUtil.sendPost(url, param);
        return JSON.parseObject(result);
    }
    @Override
    public JSONObject handCash(BaseRequestModel request) {
        // 路径带参
        Map<String, Object> map = new HashMap<>();
        map.put("secret", request.getSign());
        map.put("access_token", request.getToken());
        map.put("time", request.getTime());
        String url = HttpUtil.getUrl(imApiUri, "/console/handCash", map);
        // 拼接参数
        url += "&money=" + request.getReqData().getString("money");
        url += "&userId=" + request.getReqData().getString("userId");
        String param = "userId=" + request.getUserId();
        String result = HttpUtil.sendPost(url, param);
        return JSON.parseObject(result);
    }





}
