package com.shiku.robot.shikugame.entity.imapi;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @Author lijun
 * @Description
 * @Date 2019-12-26 1:00 下午
 **/
@Data
@ToString
@Document(collection = "tb_room_game_rule")
public class RoomGameRule {

    private String id;

    // 群组ID
    private String roomId;

    // 类型：1：可发可抢 2：禁枪 3：接龙 4：斗牛
    private String type;

    // 指定红包可抢个数
    private String receiveCount;

    // 中雷个数
    private String bombCount;

    // 赔率
    private String chance;

    // 房间ID
    private String roomJidId;

    // 创建人ID
    private String createUserId;

    // 创建时间
    private Date createTime;

    // 修改人ID
    private String updateUserId;

    // 修改时间
    private Date updateTime;
}
