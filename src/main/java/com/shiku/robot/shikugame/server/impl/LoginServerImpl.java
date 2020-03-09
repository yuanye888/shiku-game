package com.shiku.robot.shikugame.server.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shiku.robot.shikugame.mapper.imapi.UserRepository;
import com.shiku.robot.shikugame.server.LoginServer;
import com.shiku.robot.shikugame.untils.HttpUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lijun
 * @Description
 * @Date 2019-12-21 4:44 下午
 **/
@Service
public class LoginServerImpl implements LoginServer {

    @Autowired
    UserRepository userRepository;

    @Value("${im.api.uri}")
    String imApiUri;

    @Override
    public JSONObject login(String account, String password) {
        String url = imApiUri + "/console/login";
        // 拼接参数
        List<BasicNameValuePair> parames = new ArrayList<>();
        parames.add(new BasicNameValuePair("account", account));
        parames.add(new BasicNameValuePair("password", DigestUtils.md5Hex(password)));
        String result = HttpUtil.sendPostFrom(url, parames);
        return JSON.parseObject(result);
    }

    @Override
    public JSONObject getCurrentTime() {
        String url = imApiUri + "/getCurrentTime";
        return JSON.parseObject(HttpUtil.sendPost(url, null));
    }
}
