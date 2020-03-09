package com.shiku.robot.shikugame.entity.imroom;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Author lijun
 * @Description
 * @Date 2019-12-21 4:53 下午
 **/

@Data
@ToString
@Document(collection = "shiku_room")
public class ShikuRoom {

    private String id;

    private String allowConference;

    private String allowHostUpdate;

    private String allowInviteFriend;

    private String allowSendCard;

    private String allowSpeakCourse;

    private String allowUploadFile;

    private String areaId;

    private String call;

    private String category;

    private String chatRecordTimeOut;

    private String cityId;

    private String countryId;

    private String createTime;

    private String desc;

    private String isAttritionNotice;

    private String isLook;

    private String isNeedVerify;

    private String jid;

    private String latitude;

    private String longitude;

    private String maxUserSize;

    private String modifyTime;

    private String name;

    private String nickname;

    private String notice;

    private String provinceId;

    private String s;

    private String showMember;

    private String showRead;

    private String subject;

    private String talkTime;

    private String userId;

    private String userSize;

    private String videoMeetingNo;

    private String redPacketLock;

    private Integer minMoney;

    private Integer maxMoney;
}
