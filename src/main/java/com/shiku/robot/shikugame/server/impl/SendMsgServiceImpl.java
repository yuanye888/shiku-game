package com.shiku.robot.shikugame.server.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.shiku.robot.shikugame.server.SendMsgService;
import com.shiku.robot.shikugame.untils.HttpUtil;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author lijun
 * @Description
 * @Date 2019-12-31 5:36 下午
 **/

@Service
public class SendMsgServiceImpl implements SendMsgService {

    @Value("${im.api.uri}")
    String imApiUri;

    @Resource
    private DefaultMQProducer defaultMQProducer;
    @Resource
    private ParamConfigService paramConfigService ;
    @Override
    public JSONObject openAccountMsg(String msgInfo) {
        String url = HttpUtil.getUrl(imApiUri, "/console/sendMsg", new HashMap<>());
        List<BasicNameValuePair> parames = new ArrayList<>();
        parames.add(new BasicNameValuePair("content", msgInfo));
        parames.add(new BasicNameValuePair("jidArr", "24331588c89c43fca563929d6a1557e0"));
        parames.add(new BasicNameValuePair("userId", "1000"));
        parames.add(new BasicNameValuePair("type", "1"));
        String result = HttpUtil.sendPostFrom(url, parames);
        return JSON.parseObject(result);
    }
}
