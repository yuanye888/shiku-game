package com.shiku.robot.shikugame.entity.imapi;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Author lijun
 * @Description
 * @Date 2019-12-28 5:46 下午
 **/
@Data
@ToString
@Document(collection = "RedReceive")
public class RedReceive {
    private String id;

    // 红包ID
    private String redId;

    // 接收人ID
    private Integer userId;

    // 发送人ID
    private Integer sendId;

    // 接收人姓名
    private String userName;

    // 发送人姓名
    private String sendName;

    // 金额
    private Double money;

    // 时间
    private Long time;
}
