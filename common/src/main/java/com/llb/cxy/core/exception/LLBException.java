package com.llb.cxy.core.exception;

import com.llb.cxy.core.constants.ErrorCode;

/**
 * description: 基础错误异常 <br>
 * date: 2019/12/18 8:33 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
public class LLBException extends RuntimeException {

    private static final long serialVersionUID = 3655050728585279326L;

    private int code = ErrorCode.ERROR.getCode();

    public LLBException() {

    }

    public LLBException(String msg) {
        super(msg);
    }

    public LLBException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public LLBException(int code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}