package com.llb.cxy.core.exception;

import com.llb.cxy.core.model.ResultBody;
import com.llb.cxy.core.utils.WebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * description: 自定义未认证处理 <br>
 * date: 2019/12/18 8:40 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
public class LLBAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private String loginPath = "login";

    public LLBAuthenticationEntryPoint(String projectName) {
        super();
        this.loginPath = projectName + this.loginPath;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException exception) throws IOException {
        if(isAjaxRequest(request)) {
            //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());
            ResultBody resultBody = GlobalExceptionHandler.resolveException(exception,request.getRequestURI());
            response.setStatus(resultBody.getHttpStatus());
            WebUtils.writeJson(response, resultBody);
        } else {
            response.sendRedirect(loginPath);
        }
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String ajaxFlag = request.getHeader("X-Requested-With");
        return ajaxFlag != null && "XMLHttpRequest".equals(ajaxFlag);
    }

}
