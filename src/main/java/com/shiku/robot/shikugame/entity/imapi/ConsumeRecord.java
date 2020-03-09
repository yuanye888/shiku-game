package com.shiku.robot.shikugame.entity.imapi;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Author lijun
 * @Description
 * @Date 2019-12-27 1:40 下午
 **/

@Data
@ToString
@Document(collection = "ConsumeRecord")
public class ConsumeRecord {
    private String id;

    // 交易单号
    private String tradeNo;

    // 用户ID
    private int userId;

    // 接收人
    private int toUserId;

    // 金额
    private Double money;

    // 时间
    private Long time;
    // 类型 1：用户充值 2：用户提现 3：后台充值 4：发红包 5：领取红包 6：红包退款
    private int type;

    // 备注 余额充值、红包发送、红包退款
    private String desc;

    // 支付方式 1；支付宝 2：微信 3：余额
    private int payType;

    // 状态 ：创建  1：支付完成  2：交易完成  -1：交易关闭
    private int status;

    // 当前余额
    private Double currentBalance;

    // 操作金额
    private Double operationAmount;

    // 红包ID
    private String redPacketId;
}
