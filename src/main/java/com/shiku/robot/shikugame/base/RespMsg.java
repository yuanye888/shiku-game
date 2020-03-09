package com.shiku.robot.shikugame.base;

/**
 * @Author lijun
 * @Description 返回消息常量
 * @Date 2019-12-21 4:09 下午
 **/
public class RespMsg {

    /** 0000 成功 */
    public static final String SUCCESS_MSG                                                       = "成功";

    /** 9999 失败 */
    public static final String ERROR_MSG                                                         = "操作失败，请重试！";

    /**  0001-1000 用户代码  **/
    public static final String DELETE_GAME_RULE_MSG                                              = "删除失败，请重试";

    public static final String CHECK_TOKEN_OUT_ERROR_MSG                                         = "您的账号在异地登陆，请重新登陆";

    public static final String CHECK_TOKEN_NOT_ERROR_MSG                                         = "登陆过期，请重新登陆";

    public static final String CHECK_TOKEN_ERROR_MSG                                             = "服务器异常，请稍后再试";

    public static final String CHECK_BALANCE_NOT_ENOUGH_ERROR_MSG                                = "余额不足,请先充值!";

    public static final String CHECK_BALANCE_NOT_EXIST_ERROR_MSG                                 = "红包总金额在0.01~500之间哦!";

    public static final String CHECK_BALANCE_NOT_ENOUGH_EVERYONE_ERROR_MSG                       = "每人最少0.01元!";

    public static final String CHECK_BALANCE_NOT_PAYPASSWORD_ERROR_MSG                           = "请设置支付密码!";

    public static final String CHECK_BALANCE_PAYPASSWORD_ERROR_MSG                               = "支付密码错误!";

    public static final String INSERT_REDPACKET_ERROR_MSG                                        = "发送红包失败，请稍后重试";

    public static final String UPDATE_USER_CONSUME_ERROR_MSG                                     = "发送红包失败，请稍后重试";

    public static final String INSERT_CONSUMERECORD_ERROR_MSG                                    = "发送红包失败，请稍后重试";

    public static final String ROOM_REDPACKET_LOCK_ERROR_MSG                                     = "锁定或解锁失败，请重试";

    public static final String SELECT_ROOM_REDPACKET_LOCK_BYID_ERROR_MSG                         = "未找到房间锁，请重试";

    public static final String SELECT_REDPACKET_LOCK_BYID_ERROR_MSG                              = "未找到红包锁，请重试";

    public static final String OPEN_REDPACKET_AUTH_ERROR_MSG                                     = "权限校验失败！";

    public static final String OPEN_REDPACKET_OVER_TIME_ERROR_MSG                                = "该红包已超过24小时！";

    public static final String OPEN_REDPACKET_FINISH_ERROR_MSG                                   = "你手太慢啦  已经被领完了!";

    public static final String ADD_ROOM_GAME_RULE_ERROR_MSG                                      = "新增游戏规则失败，请稍后重试";

    public static final String ADD_ROOM_GAME_RULE_REPEAT_ERROR_MSG                               = "包数和雷数已经存在，不能重复添加";

    public static final String CHECK_REQUEST_DATA_ERROR_MSG                                      = "请求参数为空";

    public static final String OPEN_REDPACKET_REPEAT_ERROR_MSG                                   = "该红包已经领过了";

    public static final String OPEN_REDPACKET_BALANCE_NOT_ENOUGH_ERROR_MSG                       = "你的余额不足以赔付，请充值后领取！";

    public static final String OPEN_REDPACKET_IS_LOCK_ERROR_MSG                                  = "红包已经锁定，无法领取！";
}
