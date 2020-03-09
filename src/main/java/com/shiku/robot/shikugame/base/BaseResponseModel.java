/**
 * anji-allways.com Inc.
 * Copyright (c) 2016-2017 All Rights Reserved.
 */
package com.shiku.robot.shikugame.base;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author lijun
 * @Description 基础应答对象
 * @Date 2019-12-21 4:09 下午
 **/
@Data
public class BaseResponseModel<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean success     = true;

    private String            repCode;

    private String            repMsg;

    private T                 repData;

    public BaseResponseModel() {
        // 默认成功
        this.repCode = RespCode.SUCCESS;
        this.repMsg = RespMsg.SUCCESS_MSG;
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }

    public boolean isSuccess() {
        return success;
    }
}
