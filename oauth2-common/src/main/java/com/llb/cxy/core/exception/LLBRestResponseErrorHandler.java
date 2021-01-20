package com.llb.cxy.core.exception;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

/**
 * description: 自定RestTemplate异常处理 <br>
 * date: 2019/12/18 8:49 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
public class LLBRestResponseErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        // false表示不管response的status是多少都返回没有错
        return false;
    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {

    }
}
