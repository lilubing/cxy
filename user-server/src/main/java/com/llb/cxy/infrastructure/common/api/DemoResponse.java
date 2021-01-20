package com.llb.cxy.infrastructure.common.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.infrastructure.common.api
 * @Description: Demo返回值对象
 * @ClassName: DemoResponse
 * @date 2021-01-13 上午7:32
 * @ProjectName cxy
 * @Version V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DemoResponse {
    Status status;
    String msg;
    Object data;

    public static DemoResponse ok() {
        return DemoResponse.builder().status(Status.SUCCESS).build();
    }

    public static DemoResponse ok(Object data) {
        return DemoResponse.builder().status(Status.SUCCESS).data(data).build();
    }

    public static DemoResponse failed(String msg) {
        return DemoResponse.builder().status(Status.FAILED).msg(msg).build();
    }

    public enum Status {
        SUCCESS, FAILED
    }
}
