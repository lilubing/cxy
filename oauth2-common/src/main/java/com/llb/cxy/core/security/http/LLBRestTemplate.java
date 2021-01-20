package com.llb.cxy.core.security.http;

import com.llb.cxy.core.configuration.LLBCommonProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.web.client.RestTemplate;

/**
 * description: 自定义RestTemplate请求工具类 <br>
 * date: 2019/12/18 9:39 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
@Slf4j
public class LLBRestTemplate extends RestTemplate {

    private LLBCommonProperties common;
    private ApplicationEventPublisher publisher;
    //private BusProperties busProperties;
    public LLBRestTemplate(LLBCommonProperties common, ApplicationEventPublisher publisher) {
        this.common = common;
        this.publisher = publisher;
    }

    /**
     * 构建网关Oauth2 client_credentials方式请求
     *
     * @return
     */
    public OAuth2RestTemplate buildOAuth2ClientRequest() {
        return buildOAuth2ClientRequest(common.getClientId(), common.getClientSecret(), common.getAccessTokenUri());
    }

    /**
     * 构建网关Oauth2 client_credentials方式请求
     *
     * @param clientId
     * @param clientSecret
     * @param accessTokenUri
     * @return
     */
    public OAuth2RestTemplate buildOAuth2ClientRequest(String clientId, String clientSecret, String accessTokenUri) {
        ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();
        resource.setClientId(clientId);
        resource.setClientSecret(clientSecret);
        resource.setAccessTokenUri(accessTokenUri);
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resource);
        return restTemplate;
    }

    /**
     * 构建网关Oauth2 password方式请求
     *
     * @return
     */
    public OAuth2RestTemplate buildOAuth2PasswordRequest(String username, String password) {
        return buildOAuth2PasswordRequest(common.getClientId(), common.getClientSecret(), common.getAccessTokenUri(), username, password);
    }

    /**
     * 构建网关Oauth2 password方式请求
     *
     * @param clientId
     * @param clientSecret
     * @param accessTokenUri
     * @param username
     * @param password
     * @return
     */
    public OAuth2RestTemplate buildOAuth2PasswordRequest(String clientId, String clientSecret, String accessTokenUri, String username, String password) {
        ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
        resource.setUsername(username);
        resource.setPassword(password);
        resource.setClientId(clientId);
        resource.setClientSecret(clientSecret);
        resource.setAccessTokenUri(accessTokenUri);
        resource.setAuthenticationScheme(AuthenticationScheme.form);
        resource.setGrantType("password");
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resource);
        return restTemplate;
    }

    /**
     * 刷新网关
     * 注:不要频繁调用!
     * 1.资源权限发生变化时可以调用
     * 2.流量限制变化时可以调用
     * 3.IP访问发生变化时可以调用
     * 4.智能路由发生变化时可以调用
     */
    /*public void refreshGateway() {
        try {
            publisher.publishEvent(new RemoteRefreshRouteEvent(this,busProperties.getId(),null));
            log.info("refreshGateway:success");
        } catch (Exception e) {
            log.error("refreshGateway error:{}", e.getMessage());
        }
    }*/

    public LLBCommonProperties getCommon() {
        return common;
    }

    public void setCommon(LLBCommonProperties common) {
        this.common = common;
    }
}