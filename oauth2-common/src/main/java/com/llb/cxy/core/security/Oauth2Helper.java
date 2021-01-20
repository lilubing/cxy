package com.llb.cxy.core.security;

import com.llb.cxy.core.configuration.LLBCommonProperties;
import com.llb.cxy.core.convert.BeanConvertUtils;
import com.llb.cxy.core.security.userdetails.WebUserDetail;
import com.llb.cxy.core.utils.ReflectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * description: 认证信息帮助类 <br>
 * date: 2019/12/17 8:36 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
@Slf4j
public class Oauth2Helper {
    /**
     * 获取认证用户信息
     *
     * @return
     */
    public static WebUserDetail getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication instanceof OAuth2Authentication) {
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) authentication.getDetails();
                if(oauthDetails != null) {
                    Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
                    return WebUserDetail.mapToObject((Map) details.get("userInfo"));
                }
                if (authentication.getPrincipal() instanceof WebUserDetail) {
                    return (WebUserDetail) authentication.getPrincipal();
                }
                if (authentication.getPrincipal() instanceof Map) {
                    return BeanConvertUtils.mapToObject((Map) authentication.getPrincipal(), WebUserDetail.class);
                }
            } else {
                WebUserDetail openUser = new WebUserDetail();
                OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
                OAuth2Request clientToken = oAuth2Authentication.getOAuth2Request();
                openUser.setClientId(clientToken.getClientId());
                openUser.setAuthorities(clientToken.getAuthorities());
                return openUser;
            }
        }
        return null;
    }


    /**
     * 更新OpenUser
     *
     * @param webUserDetail
     */
    public static void updateOpenUser(TokenStore tokenStore, WebUserDetail webUserDetail) {
        if (webUserDetail == null) {
            return;
        }
        Assert.notNull(webUserDetail.getClientId(), "客户端ID不能为空");
        Assert.notNull(webUserDetail.getUsername(), "用户名不能为空");
        // 动态更新客户端生成的token
        Collection<OAuth2AccessToken> accessTokens = tokenStore.findTokensByClientIdAndUserName(webUserDetail.getClientId(), webUserDetail.getUsername());
        if (accessTokens != null && !accessTokens.isEmpty()) {
            for (OAuth2AccessToken accessToken : accessTokens) {
                // 由于没有set方法,使用反射机制强制赋值
                OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(accessToken);
                if (oAuth2Authentication != null) {
                    Authentication authentication = oAuth2Authentication.getUserAuthentication();
                    ReflectUtils.setFieldValue(authentication, "principal", webUserDetail);
                    // 重新保存
                    tokenStore.storeAccessToken(accessToken, oAuth2Authentication);
                }
            }
        }
    }


    /***
     * 更新客户端权限
     * @param tokenStore
     * @param clientId
     * @param authorities
     */
    public static void updateOpenClientAuthorities(TokenStore tokenStore, String clientId, Collection<? extends GrantedAuthority> authorities) {
        if (authorities == null) {
            return;
        }
        // 动态更新客户端生成的token
        Collection<OAuth2AccessToken> accessTokens = tokenStore.findTokensByClientId(clientId);
        if (accessTokens != null && !accessTokens.isEmpty()) {
            Iterator<OAuth2AccessToken> iterator = accessTokens.iterator();
            while (iterator.hasNext()) {
                OAuth2AccessToken token = iterator.next();
                OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(token);
                if (oAuth2Authentication != null && oAuth2Authentication.isClientOnly()) {
                    // 只更新客户端权限
                    // 由于没有set方法,使用反射机制强制赋值
                    ReflectUtils.setFieldValue(oAuth2Authentication, "authorities", authorities);
                    // 重新保存
                    tokenStore.storeAccessToken(token, oAuth2Authentication);
                }
            }
        }
    }


    /**
     * 获取认证用户Id
     *
     * @return
     */
    public static Long getUserId() {
        return getUser().getId();
    }

    /**
     * 是否拥有权限
     *
     * @param authority
     * @return
     */
    public static Boolean hasAuthority(String authority) {
        WebUserDetail auth = getUser();
        if (auth == null) {
            return false;
        }
        if (AuthorityUtils.authorityListToSet(auth.getAuthorities()).contains(authority)) {
            return true;
        }
        return false;
    }

    /**
     * 构建token转换器
     *
     * @return
     */
    public static DefaultAccessTokenConverter buildAccessTokenConverter() {
        Oauth2UserConverter userAuthenticationConverter = new Oauth2UserConverter();
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(userAuthenticationConverter);
        return accessTokenConverter;
    }

    /**
     * 构建jwtToken转换器
     *
     * @param properties
     * @return
     */
    public static JwtAccessTokenConverter buildJwtTokenEnhancer(LLBCommonProperties properties) throws Exception {
        JwtAccessTokenConverter converter = new Oauth2JwtAccessTokenEnhancer();
        converter.setSigningKey(properties.getJwtSigningKey());
        converter.afterPropertiesSet();
        return converter;
    }

    /**
     * 构建自定义远程Token服务类
     *
     * @param properties
     * @return
     */
    /*public static RemoteTokenServices buildRemoteTokenServices(LLBCommonProperties properties) {
        // 使用自定义系统用户凭证转换器
        DefaultAccessTokenConverter accessTokenConverter = buildAccessTokenConverter();
        RemoteTokenServices tokenServices = new RemoteTokenServices();
        tokenServices.setCheckTokenEndpointUrl(properties.getTokenInfoUri());
        tokenServices.setClientId(properties.getClientId());
        tokenServices.setClientSecret(properties.getClientSecret());
        tokenServices.setAccessTokenConverter(accessTokenConverter);
        log.info("buildRemoteTokenServices[{}]", tokenServices);
        return tokenServices;
    }*/

    /**
     * 构建资源服务器JwtToken服务类
     *
     * @param properties
     * @return
     */
    public static ResourceServerTokenServices buildJwtTokenServices(LLBCommonProperties properties) throws Exception {
        // 使用自定义系统用户凭证转换器
        DefaultAccessTokenConverter accessTokenConverter = buildAccessTokenConverter();
        Oauth2JwtTokenService tokenServices = new Oauth2JwtTokenService();
        // 这里的签名key 保持和认证中心一致
        JwtAccessTokenConverter converter = buildJwtTokenEnhancer(properties);
        JwtTokenStore jwtTokenStore = new JwtTokenStore(converter);
        tokenServices.setTokenStore(jwtTokenStore);
        tokenServices.setJwtAccessTokenConverter(converter);
        tokenServices.setDefaultAccessTokenConverter(accessTokenConverter);
        log.info("buildJwtTokenServices[{}]", tokenServices);
        return tokenServices;
    }

    /**
     * 构建资源服务器RedisToken服务类
     *
     * @return
     */
    /*public static ResourceServerTokenServices buildRedisTokenServices(RedisConnectionFactory redisConnectionFactory) throws Exception {
        OpenRedisTokenService tokenServices = new OpenRedisTokenService();
        // 这里的签名key 保持和认证中心一致
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
        tokenServices.setTokenStore(redisTokenStore);
        log.info("buildRedisTokenServices[{}]", tokenServices);
        return tokenServices;
    }*/
}
