package com.llb.cxy.core.exception;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.core.exception
 * @Description: PathVariable异常检查
 * @ClassName: LlbPathVariableCheckException
 * @date 2021-01-20 上午9:35
 * @ProjectName cxy
 * @Version V1.0
 */
public class LlbPathVariableCheckException extends LlbException {

    private static final long serialVersionUID = 4908906410210213272L;

    public LlbPathVariableCheckException() {
    }

    public LlbPathVariableCheckException(String msg) {
        super(msg);
    }

    public LlbPathVariableCheckException(int code, String msg) {
        super(code, msg);
    }

    public LlbPathVariableCheckException(int code, String msg, Throwable cause) {
        super(code, msg, cause);
    }
}
