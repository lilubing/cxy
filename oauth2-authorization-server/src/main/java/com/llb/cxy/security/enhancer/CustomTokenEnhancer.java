package com.llb.cxy.security.enhancer;

import com.llb.cxy.core.security.userdetails.WebUserDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * description: CustomTokenEnhancer
 * TokenEnhancer
 * <br>
 * date: 2019/11/25 10:49 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
public class CustomTokenEnhancer implements TokenEnhancer {

    private static final Logger log = LoggerFactory.getLogger(CustomTokenEnhancer.class);

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        final Map<String, Object> additionalInfo = new HashMap<>();
        // 注意添加的额外信息，最好不要和已有的json对象中的key重名，容易出现错误
        // additionalInfo.put("userInfo", authentication.getPrincipal());
        additionalInfo.put("userId", (((WebUserDetail) authentication.getPrincipal()).getId()).toString());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }


}