package com.llb.cxy.core.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * description: LLBScanProperties <br>
 * date: 2019/12/18 10:16 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
@Component
@ConfigurationProperties(prefix = "llb.scan")
public class LLBScanProperties {
    /**
     * 请求资源注册到API列表
     */
    private Boolean registerRequestMapping = false;

    public boolean isRegisterRequestMapping() {
        return registerRequestMapping;
    }

    public void setRegisterRequestMapping(boolean registerRequestMapping) {
        this.registerRequestMapping = registerRequestMapping;
    }
}