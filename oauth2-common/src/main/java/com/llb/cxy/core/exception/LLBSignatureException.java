package com.llb.cxy.core.exception;

/**
 * description: 签名异常 <br>
 * date: 2019/12/18 8:51 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
public class LLBSignatureException extends LLBException {
    private static final long serialVersionUID = 4908906410210213271L;

    public LLBSignatureException() {
    }

    public LLBSignatureException(String msg) {
        super(msg);
    }

    public LLBSignatureException(int code, String msg) {
        super(code, msg);
    }

    public LLBSignatureException(int code, String msg, Throwable cause) {
        super(code, msg, cause);
    }
}
