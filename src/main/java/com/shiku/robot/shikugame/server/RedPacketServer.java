package com.shiku.robot.shikugame.server;

import com.alibaba.fastjson.JSONObject;
import com.shiku.robot.shikugame.base.BaseResponseModel;

/**
 * @Author lijun
 * @Description
 * @Date 2019-12-27 11:05 上午
 **/

public interface RedPacketServer {
    BaseResponseModel sendRedPacket(JSONObject reqData);

    BaseResponseModel openRedPacket(JSONObject reqData);

    BaseResponseModel getRedPacket(JSONObject reqData);
}
