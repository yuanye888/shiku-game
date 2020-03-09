package com.shiku.robot.shikugame.server;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.DeleteResult;
import com.shiku.robot.shikugame.base.BaseRequestModel;
import com.shiku.robot.shikugame.entity.imapi.User;

public interface UserService {
    JSONObject addShikuRobot(BaseRequestModel request);
    JSONObject selectAllRobot(BaseRequestModel request);
    JSONObject deleteRobot(BaseRequestModel request);
    JSONObject recharge(BaseRequestModel request);
    JSONObject handCash(BaseRequestModel request);
}
