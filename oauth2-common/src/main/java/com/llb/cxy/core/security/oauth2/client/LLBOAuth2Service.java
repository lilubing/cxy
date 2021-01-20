package com.llb.cxy.core.security.oauth2.client;

import com.alibaba.fastjson.JSONObject;

/**
 * description: 第三方登录接口 <br>
 * date: 2020/7/28 14:28 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
public interface LLBOAuth2Service {
    /**
     * 根据code获得Token
     *
     * @param code code
     * @return token
     */
    String getAccessToken(String code);

    /**
     * 根据Token获得OpenId
     *
     * @param accessToken Token
     * @return openId
     */
    String getOpenId(String accessToken);

    /**
     * 拼接授权URL
     *
     * @return URL
     */
    String getAuthorizationUrl();

    /**
     * 根据Token和OpenId获得用户信息
     *
     * @param accessToken Token
     * @param openId      openId
     * @return 第三方应用给的用户信息
     */
    JSONObject getUserInfo(String accessToken, String openId);

    /**
     * 刷新Token
     *
     * @param code code
     * @return 新的token
     */
    String refreshToken(String code);

    /**
     * 获取登录成功地址
     *
     * @return
     */
    String getLoginSuccessUrl();

    /**
     * 获取客户端配置信息
     *
     * @return
     */
    LLBOAuth2ClientDetails getClientDetails();
}
