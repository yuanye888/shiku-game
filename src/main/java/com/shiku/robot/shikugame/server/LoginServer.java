package com.shiku.robot.shikugame.server;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author lijun
 * @Description
 * @Date 2019-12-21 4:43 下午
 **/

public interface LoginServer {
    JSONObject login(String account, String password);

    JSONObject getCurrentTime();
}
