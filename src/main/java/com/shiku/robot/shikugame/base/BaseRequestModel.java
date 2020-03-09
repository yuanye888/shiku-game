/**
 * anji-allways.com Inc.
 * Copyright (c) 2016-2017 All Rights Reserved.
 */
package com.shiku.robot.shikugame.base;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.Serializable;

/**
 * @Author lijun
 * @Description 基础请求对象
 * @Date 2019-12-21 4:09 下午
 **/
@Data
public class BaseRequestModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String token;

    private Integer userId;

    private String sysId;

    private String sign;

    private String time;

    private Integer limit;

    private Integer page;

    private JSONObject reqData;

}
