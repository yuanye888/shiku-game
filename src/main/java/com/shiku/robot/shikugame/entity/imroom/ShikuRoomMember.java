package com.shiku.robot.shikugame.entity.imroom;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Author lijun
 * @Description
 * @Date 2020-01-05 5:23 下午
 **/

@Data
@ToString
@Document(collection = "shiku_room_member")
public class ShikuRoomMember {

    private String id;

    // 房间Id
    private String roomId;

    // 成员Id
    private Integer userId;

    // 成员昵称
    private String nickname;

    // 成员角色：1=创建者、2=管理员、3=普通成员、4=隐身人、5=监控人
    private int role;

    // 订阅群信息：0=否、1=是
    private Integer sub;

    //语音通话标识符
    private String call;

    //视频会议标识符
    private String videoMeetingNo;

    //消息免打扰（1=是；0=否）
    private Integer offlineNoPushMsg=0;

    // 大于当前时间时禁止发言
    private Long talkTime;

    // 最后一次互动时间
    private Long active;

    // 创建时间
    private Long createTime;

    // 修改时间
    private Long modifyTime;

    // 是否开启置顶聊天 0：关闭，1：开启
    private byte isOpenTopChat = 0;

    // 开启置顶聊天时间
    private long openTopChatTime = 0;
}
