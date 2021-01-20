package com.llb.cxy.core.security.oauth2.client;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * description: OpenOAuth2ClientProperties <br>
 * date: 2019/12/17 8:26 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
@ConfigurationProperties(prefix = "oauth2.client")
public class LLBOAuth2ClientProperties {
    private Map<String, LLBOAuth2ClientDetails> oauth2;

    public Map<String, LLBOAuth2ClientDetails> getOauth2() {
        return oauth2;
    }

    public void setOauth2(Map<String, LLBOAuth2ClientDetails> oauth2) {
        this.oauth2 = oauth2;
    }
}
