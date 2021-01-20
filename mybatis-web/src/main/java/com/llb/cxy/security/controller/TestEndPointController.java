package com.llb.cxy.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/*import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;*/
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;

/**
 * description: TestEndPointController <br>
 * date: 2019/12/30 16:31 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
@RestController
public class TestEndPointController {

    Logger logger = LoggerFactory.getLogger(TestEndPointController.class);

    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable String id) {
        return "product id : " + id;
    }

    @GetMapping("/order/{id}")
    public String getOrder(@PathVariable String id) {
        return "order id : " + id;
    }

    @GetMapping("oauth2user")
    public Principal user(Principal principal) {
        return principal;
    }

    /*@Autowired
    private LLBOAuth2ClientProperties clientProperties;
    @Autowired
    private RestTemplate restTemplate;*/

    /*@GetMapping("/getPrinciple")
    @PreAuthorize("hasRole('111')")
    public OAuth2Authentication getPrinciple(OAuth2Authentication oAuth2Authentication, Principal principal, Authentication authentication) {
        logger.info(oAuth2Authentication.getUserAuthentication().getAuthorities().toString());
        logger.info(oAuth2Authentication.toString());
        logger.info("principal.toString() " + principal.toString());
        logger.info("principal.getName() " + principal.getName());
        logger.info("authentication: " + authentication.getAuthorities().toString());

        return oAuth2Authentication;
    }*/

    /*public JSONObject getToken(String type, String grant_type, HttpHeaders headers) {
        LLBOAuth2ClientDetails clientDetails =  clientProperties.getOauth2().get("cxy-web");
        String url = WebUtils.getServerUrl(WebUtils.getHttpServletRequest()) + "/login/wechat";
        // 使用oauth2密码模式登录.
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("client_id", clientDetails.getClientId());
        postParameters.add("client_secret", clientDetails.getClientSecret());
        postParameters.add("grant_type", "refresh_token");
        // 添加参数区分,第三方登录
        postParameters.add("login_type", type);
        // 使用客户端的请求头,发起请求
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // 强制移除 原来的请求头,防止token失效
        headers.remove(HttpHeaders.AUTHORIZATION);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity(postParameters, headers);
        return restTemplate.postForObject(url, request, JSONObject.class);
    }*/
}