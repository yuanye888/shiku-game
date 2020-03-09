package com.shiku.robot.shikugame.entity.imapi;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Author lijun
 * @Description
 * @Date 2019-12-21 9:34 下午
 **/

@Data
@ToString
@Document(collection = "user")
public class User {
    private String id;

    private String account;

    private String active;

    private String areaCode;

    private String areaId;

    private String attCount;

    private Double balance;

    private String birthday;

    private String cityId;

    private String countryId;

    private String createTime;

    private String description;

    private String encryAccount;

    private String fansCount;

    private String friendsCount;

    private String idcard;

    private String idcardUrl;

    private String isAuth;

    private String isPasuse;

    private String level;

    private String loc;

    private String modifyTime;

    private String money;

    private String moneyTotal;

    private String msgBackGroundUrl;

    private String msgNum;

    private String name;

    private String nickname;

    private String num;

    private String offlineNoPushMsg;

    private String onlinestate;

    private String password;

    private String payPassword;

    private String phone;

    private String provinceId;

    private String redPackageMode;

    private String regInviteCode;

    private String setAccountCount;

    private String settings;

    private String sex;

    private String status;

    private String telephone;

    private Double totalConsume;

    private String totalRecharge;

    private String userKey;

    private String username;

    private String userType;

    private String vip;

    private String isRobot;// 1:机器人
}
