package com.llb.cxy.infrastructure.client;

import com.llb.cxy.core.model.ResultBody;
import com.llb.cxy.domain.user.entity.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.infrastructure.client
 * @Description: DemoFeignClient
 * @ClassName: DemoFeignClient
 * @date 2021-01-13 上午7:28
 * @ProjectName cxy
 * @Version V1.0
 */
@FeignClient(name = "auth-service", path = "/demo/auth")
public interface DemoFeignClient {

    /**
     * Demo
     * @Author LiLuBing
     * @Date 2021-01-13 07:30
     * @Param  * @param userInfo
     * @return {@link com.llb.cxy.core.model.ResultBody}
     **/
    @PostMapping(value = "/login")
    ResultBody login(UserInfo userInfo);
}
