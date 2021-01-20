package com.llb.cxy.security.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.llb.cxy.core.model.ResultBody;
import com.llb.cxy.core.utils.MyStringUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * description: 授权成功handler <br>
 * date: 2019/12/2 8:24 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
@Component
public class JsonAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final Logger log = LoggerFactory.getLogger(JsonAuthenticationSuccessHandler.class);

    //SpringSecurity会把需要认证的请求缓存到HttpSessionRequestCache中
    private RequestCache requestCache = new HttpSessionRequestCache();

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationServerTokenServices tokenServices;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("【AppLoginInSuccessHandler】 onAuthenticationSuccess authentication={}", authentication);
        //将认证通过的请求路径返回给前端，以便前端跳转到该请求路径
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        String successRequestUrl = "";
        if(null != savedRequest) {
            successRequestUrl = savedRequest.getRedirectUrl();
        }

        log.info("用户登录成功 [{}]  会跳地址为： {}", authentication.getName(), successRequestUrl);
        // 获取登录成功信息
        /*response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(jacksonmapper.writeValueAsString(SktResult.build(EnumCode.OK.getValue(),
                successRequestUrl, authentication.getPrincipal())));*/
//                "处理成功", successRequestUrl)));

        String clientId = request.getParameter("client_id");
        String clientSecret = request.getParameter("client_secret");

        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("clientId 对应的配置信息不存在" + clientId);
        } else if (!MyStringUtils.equals(clientDetails.getClientSecret(), clientSecret)) {
            throw new UnapprovedClientAuthenticationException("clientSecret 不匹配" + clientId);
        }

        TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "custom");

        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

        OAuth2AccessToken accessToken = tokenServices.createAccessToken(oAuth2Authentication);

        final Map<String, Object> additionalInfo = accessToken.getAdditionalInformation();
        // 注意添加的额外信息，最好不要和已有的json对象中的key重名，容易出现错误
        additionalInfo.put("authorities", authentication.getAuthorities());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        response.getWriter().write(objectMapper.writeValueAsString(ResultBody.ok().path(successRequestUrl).data(accessToken)));
        //response.getWriter().write(objectMapper.writeValueAsString(accessToken));
    }
}
