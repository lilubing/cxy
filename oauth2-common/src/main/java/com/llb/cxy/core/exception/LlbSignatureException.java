package com.llb.cxy.core.exception;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.core.exception
 * @Description: 签名异常
 * @ClassName: LlbSignatureException
 * @date 2021-01-20 上午9:35
 * @ProjectName cxy
 * @Version V1.0
 */
public class LlbSignatureException extends LlbException {
    private static final long serialVersionUID = 4908906410210213271L;

    public LlbSignatureException() {
    }

    public LlbSignatureException(String msg) {
        super(msg);
    }

    public LlbSignatureException(int code, String msg) {
        super(code, msg);
    }

    public LlbSignatureException(int code, String msg, Throwable cause) {
        super(code, msg, cause);
    }
}
