package com.llb.cxy.security.controller;

import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.llb.cxy.application.service.UserApplicationService;
import com.llb.cxy.interfaces.dto.UserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.llb.cxy.core.convert.MapTrunPojo;

/**
 * description: UserController <br>
 * date: 2019/11/22 13:41 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
@RestController
public class CxyWebServerUserController {

    @Autowired
    private UserApplicationService userApplicationService;

    private ObjectMapper mapper = new ObjectMapper();

    @RequestMapping("/user/me")
    public Principal user(Principal principal) {

        // (com.llb.cxy.core.UserInfo) ((OAuth2Authentication) principal).getUserAuthentication().getPrincipal();
        System.out.println(principal);
        return principal;
    }

    // @PreAuthorize("#oauth2.hasScope('ROLE_API')")
    @RequestMapping(value = "/user/extra", method = {RequestMethod.GET, RequestMethod.POST})
    public UserInfoDTO getExtraInfo() {
        ServletRequestAttributes servletRequestAttributes =
            (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        JSONObject user = JSONObject.parseObject(request.getHeader("user"));
        UserInfoDTO userInfoDTO = userApplicationService.findUserByLoginName(user.getString("user_name"));
        userInfoDTO.setRolesList(user.getJSONArray("authorities").toArray(new String[0]));
        return userInfoDTO;
    }
}