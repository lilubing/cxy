package com.llb.cxy.core.exception;

import com.llb.cxy.core.constants.ErrorCode;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.core.exception
 * @Description: 基础错误异常
 * @ClassName: LlbAlertException
 * @date 2021-01-20 上午9:32
 * @ProjectName cxy
 * @Version V1.0
 */
public class LlbException extends RuntimeException {

    private static final long serialVersionUID = 3655050728585279326L;

    private int code = ErrorCode.ERROR.getCode();

    public LlbException() {

    }

    public LlbException(String msg) {
        super(msg);
    }

    public LlbException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public LlbException(int code, String msg, Throwable cause) {
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