package com.llb.cxy.core.exception;

import com.llb.cxy.core.model.ResultBody;
import com.llb.cxy.core.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * description: 自定义访问拒绝 <br>
 * date: 2019/12/18 8:35 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
@Slf4j
public class LLBAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException exception) {
        ResultBody resultBody = GlobalExceptionHandler.resolveException(exception, request.getRequestURI());
        response.setStatus(resultBody.getHttpStatus());
        WebUtils.writeJson(response, resultBody);
    }
}