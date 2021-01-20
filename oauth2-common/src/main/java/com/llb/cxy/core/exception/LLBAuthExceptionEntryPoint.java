package com.llb.cxy.core.exception;

import com.alibaba.fastjson.JSONObject;
import com.llb.cxy.core.model.ResultBody;
import com.llb.cxy.core.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * description: 自定义无效token 或token不存在异常类重写 <br>
 * date: 2020/1/3 15:51 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */

public class LLBAuthExceptionEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) {
        ResultBody resultBody = GlobalExceptionHandler.resolveException(authException, request.getRequestURI());
        response.setStatus(resultBody.getHttpStatus());
        WebUtils.writeJson(response, resultBody);
    }
}
