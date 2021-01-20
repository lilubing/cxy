package com.llb.cxy.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.http.HttpServletRequest;

/**
 * description: IndexController <br>
 * date: 2020/7/29 14:06 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
@Controller
public class IndexController {

    /**
     * index页
     */
    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
        return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/index.jsp";
    }

    /**
     * login页
     */
//	@RequestMapping("/login")
    public String login() {
//		return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/login.jsp";
        return "loginView";
    }
}
