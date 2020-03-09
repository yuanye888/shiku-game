package com.shiku.robot.shikugame.entity.imapi;


import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @Author lijun
 * @Description
 * @Date 2019-12-27 11:09 上午
 **/

@Data
@ToString
@Document(collection = "RedPacket")
public class RedPacket {
    private String id;

    // 用户ID
    private Integer userId;

    // 接收人
    private String toUserId;

    // 用户名
    private String userName;

    // 祝福语
    private String greetings;

    //发送时间
    private Long sendTime;

    // 类型 1：普通红包 2：拼手气红包 3：口令红包
    private Integer type;

    // 红包个数
    private Integer count;

    // 已领取个数
    private Integer receiveCount;

    // 总金额
    private Double money;

    // 剩余金额
    private Double over;

    // 超时时间
    private Long outTime;

    // 状态
    private Integer status;

    // 领取用户集合
    private List<Integer> userIds;

    // 用户红包类型
    private String userRedType;

    // 接收留言
    private String receivedRemark;

    // 房间ID
    private String roomJid;

    // 红包是否锁定
    private String isLock;
}
