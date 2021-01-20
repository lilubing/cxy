package com.llb.cxy.core.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * description: 自定义oauth2异常提示 <br>
 * date: 2019/12/18 8:42 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
@JsonSerialize(using = LLBOAuth2ExceptionSerializer.class)
public class LLBOAuth2Exception extends org.springframework.security.oauth2.common.exceptions.OAuth2Exception {
    private static final long serialVersionUID = 4257807948611076101L;

    public LLBOAuth2Exception(String msg) {
        super(msg);
    }
}
