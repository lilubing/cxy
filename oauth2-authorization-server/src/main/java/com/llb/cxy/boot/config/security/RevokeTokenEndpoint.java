package com.llb.cxy.boot.config.security;

import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * description: 登出接口 <br>
 * date: 2019/11/25 11:20 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
@FrameworkEndpoint
@Order(100)
public class RevokeTokenEndpoint {

    @Resource(name = "tokenServices")
    @Lazy
    ConsumerTokenServices tokenServices;

    //退出登录
    @RequestMapping(method = RequestMethod.DELETE, value = "/oauth/token")
    @ResponseBody
    public void revokeToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.contains("Bearer")) {
            String tokenId = authorization.substring("Bearer".length() + 1);
            tokenServices.revokeToken(tokenId);
        }
    }

    /*@DeleteMapping("/oauth/token")
    @ApiOperation("退出登录")
    public Result<String> deleteAccessToken(@RequestParam("access_token") String accessToken){
        tokenServices.revokeToken(accessToken);
        return Result.buildSuccess("");
    }*/
}