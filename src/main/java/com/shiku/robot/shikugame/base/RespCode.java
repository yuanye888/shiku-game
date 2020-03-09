package com.shiku.robot.shikugame.base;

/**
 * @Author lijun
 * @Description 返回应答码常量
 * @Date 2019-12-21 4:09 下午
 **/
public class RespCode {

    /** 0000 成功 */
    public static final String SUCCESS                                                       = "0000";

    /** 9999 失败 */
    public static final String ERROR                                                         = "9999";

    /**  0001-1000 用户代码  **/
    public static final String DELETE_GAME_RULE                                              = "0001";

    public static final String CHECK_TOKEN_OUT_ERROR                                         = "0002";

    public static final String CHECK_TOKEN_NOT_ERROR                                         = "0003";

    public static final String CHECK_TOKEN_ERROR                                             = "0004";

    public static final String CHECK_BALANCE_NOT_ENOUGH_ERROR                                = "0005";

    public static final String CHECK_BALANCE_NOT_EXIST_ERROR                                 = "0006";

    public static final String CHECK_BALANCE_NOT_ENOUGH_EVERYONE_ERROR                       = "0007";

    public static final String CHECK_BALANCE_NOT_PAYPASSWORD_ERROR                           = "0008";

    public static final String CHECK_BALANCE_PAYPASSWORD_ERROR                               = "0009";

    public static final String INSERT_REDPACKET_ERROR                                        = "0010";

    public static final String UPDATE_USER_CONSUME_ERROR                                     = "0011";

    public static final String INSERT_CONSUMERECORD_ERROR                                    = "0012";

    public static final String ROOM_REDPACKET_LOCK_ERROR                                     = "0013";

    public static final String SELECT_ROOM_REDPACKET_LOCK_BYID_ERROR                         = "0014";

    public static final String SELECT_REDPACKET_LOCK_BYID_ERROR                              = "0015";

    public static final String OPEN_REDPACKET_AUTH_ERROR                                     = "0016";

    public static final String OPEN_REDPACKET_OVER_TIME_ERROR                                = "0017";

    public static final String OPEN_REDPACKET_FINISH_ERROR                                   = "0018";

    public static final String ADD_ROOM_GAME_RULE_ERROR                                      = "0019";

    public static final String ADD_ROOM_GAME_RULE_REPEAT_ERROR                               = "0020";

    public static final String CHECK_REQUEST_DATA_ERROR                                      = "0021";

    public static final String OPEN_REDPACKET_REPEAT_ERROR                                   = "0022";

    public static final String OPEN_REDPACKET_IS_LOCK_ERROR                                  = "0023";

    public static final String OPEN_REDPACKET_BALANCE_NOT_ENOUGH_ERROR                       = "0024";
}
