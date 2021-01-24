package com.llb.cxy.core.exception;

/**
 * description: 提示消息异常 <br>
 * date: 2019/12/18 8:39 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
public class LlbAlertException extends LlbException {
    private static final long serialVersionUID = 4908906410210213271L;

    public LlbAlertException() {
    }

    public LlbAlertException(String msg) {
        super(msg);
    }

    public LlbAlertException(int code, String msg) {
        super(code, msg);
    }

    public LlbAlertException(int code, String msg, Throwable cause) {
        super(code, msg, cause);
    }
}
