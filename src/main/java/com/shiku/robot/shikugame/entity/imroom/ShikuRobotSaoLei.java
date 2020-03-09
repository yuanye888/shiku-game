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
@Document(collection = "shiku_robot_saolei")
public class ShikuRobotSaoLei {

    private String id;

    private String userId;
    private String userName; //从用户表获取
    private String userKey;
    private String isStartUp;////是否启动自动发抢包  0 关闭 1 启动
    private String rapPacketSpeed; //抢包速度  （比如5秒内随机时间抢群内红包）默认是0

    private String sendPacketMin;//每分钟 0-10个红包  输入一个区间值
    private String sendPacketMax;//每分钟 0-10个红包  输入一个区间值
    private String sendPacketNum;////发包金额  多个数 随机发一个  逗号间隔


    private String sendPacketSize;///包数   几个人红包    多个数  随机一个
    private String sendPacketBlessings;////发包祝福语   多个数  随机一个

}
