package com.llb.cxy.core.exception;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.core.exception
 * @Description: 提示消息异常
 * @ClassName: LlbAlertException
 * @date 2021-01-20 上午9:32
 * @ProjectName cxy
 * @Version V1.0
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
