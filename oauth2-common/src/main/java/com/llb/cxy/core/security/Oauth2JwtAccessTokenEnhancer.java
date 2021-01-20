package com.llb.cxy.core.security;

import com.llb.cxy.core.constants.Oauth2SecurityConstants;
import com.llb.cxy.core.security.userdetails.WebUserDetail;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * description: 自定义JwtAccessToken转换器 <br>
 * date: 2019/12/17 13:48 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
public class Oauth2JwtAccessTokenEnhancer extends JwtAccessTokenConverter {


    /**
     * 生成token
     * @param accessToken
     * @param authentication
     * @return
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken defaultOAuth2AccessToken = new DefaultOAuth2AccessToken(accessToken);
        if(authentication.getPrincipal()!=null && authentication.getPrincipal() instanceof WebUserDetail){
            // 设置额外用户信息
            WebUserDetail baseUser = ((WebUserDetail) authentication.getPrincipal());
            final Map<String, Object> additionalInfo = new HashMap<>(8);
            additionalInfo.put(Oauth2SecurityConstants.OPEN_ID, baseUser.getId());
            additionalInfo.put(Oauth2SecurityConstants.DOMAIN, baseUser.getDomain());
            defaultOAuth2AccessToken.setAdditionalInformation(additionalInfo);
        }

        return super.enhance(defaultOAuth2AccessToken, authentication);
    }

    /**
     * 解析token
     * @param value
     * @param map
     * @return
     */
    @Override
    public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map){
        OAuth2AccessToken oauth2AccessToken = super.extractAccessToken(value, map);
        return oauth2AccessToken;
    }
}
