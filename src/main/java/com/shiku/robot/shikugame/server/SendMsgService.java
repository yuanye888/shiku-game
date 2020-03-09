package com.shiku.robot.shikugame.server;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.producer.SendResult;

/**
 * @Author lijun
 * @Description
 * @Date 2019-12-31 5:37 下午
 **/

public interface SendMsgService {
    JSONObject openAccountMsg(String msgInfo);
}
