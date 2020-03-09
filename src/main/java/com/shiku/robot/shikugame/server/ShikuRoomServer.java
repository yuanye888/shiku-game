package com.shiku.robot.shikugame.server;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.shiku.robot.shikugame.base.BaseRequestModel;
import com.shiku.robot.shikugame.base.BaseResponseModel;
import com.shiku.robot.shikugame.entity.imapi.RedPacket;
import com.shiku.robot.shikugame.entity.imapi.RoomGameRule;
import com.shiku.robot.shikugame.entity.imroom.ShikuRoom;
import com.shiku.robot.shikugame.untils.PageUtil;

/**
 * @Author lijun
 * @Description
 * @Date 2019-12-22 1:55 下午
 **/

public interface ShikuRoomServer {
    JSONObject selectAllShikuRoom(BaseRequestModel request);

    JSONObject addShikuRoom(BaseRequestModel request);

    JSONObject deleteShikuRoom(BaseRequestModel request);

    JSONObject updateShikuRoom(BaseRequestModel request);

    JSONObject selectAllGroupcahtlogs(BaseRequestModel request);

    JSONObject selectAllRoomUsermanager(BaseRequestModel request);

    BaseResponseModel selectRoomGameRule(BaseRequestModel request);

    RoomGameRule addRoomGameRule(BaseRequestModel request);

    DeleteResult deleteRoomGameRule(BaseRequestModel request);

    RoomGameRule selectRoomGameRuleById(BaseRequestModel request);

    UpdateResult editRoomGameRule(BaseRequestModel request);

    UpdateResult editRoomRedPacketLock(BaseRequestModel request);

    ShikuRoom selectShikuRoomByRoomId(BaseRequestModel request);

    RedPacket selectRedPacketLockById(BaseRequestModel request);

    UpdateResult editRoomRedPacketMinMax(BaseRequestModel request);
}
