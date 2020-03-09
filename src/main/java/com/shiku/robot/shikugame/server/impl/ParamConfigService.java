package com.shiku.robot.shikugame.server.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Author lijun
 * @Description
 * @Date 2019-12-31 5:35 下午
 **/

@Service
public class ParamConfigService {
    @Value("${fee-plat.fee-plat-group}")
    public String feePlatGroup ;
    @Value("${fee-plat.fee-plat-topic}")
    public String feePlatTopic ;
    @Value("${fee-plat.fee-account-tag}")
    public String feeAccountTag ;
}
