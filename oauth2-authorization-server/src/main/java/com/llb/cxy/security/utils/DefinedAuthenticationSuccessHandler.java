package com.llb.cxy.security.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.llb.cxy.core.model.ResultBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * description: 授权成功handler <br>
 * date: 2019/12/2 8:24 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
public class DefinedAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger log = LoggerFactory.getLogger(DefinedAuthenticationSuccessHandler.class);

    //SpringSecurity会把需要认证的请求缓存到HttpSessionRequestCache中
    private RequestCache requestCache = new HttpSessionRequestCache();

    private static ObjectMapper jacksonmapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //将认证通过的请求路径返回给前端，以便前端跳转到该请求路径
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        String successRequestUrl = "";
        if(null != savedRequest) {
            successRequestUrl = savedRequest.getRedirectUrl();
        }

        log.info("用户登录成功 [{}]  会跳地址为： {}", authentication.getName(), successRequestUrl);
        // 获取登录成功信息
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        response.getWriter().write(jacksonmapper.writeValueAsString(
                ResultBody.ok().path(successRequestUrl).data(authentication.getPrincipal())));
//                "处理成功", successRequestUrl)));
    }
}
