package com.llb.cxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * description: GWApplication <br>
 * date: 2020/3/24 8:00 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy
 * @Description: 启动类
 * @ClassName: Test
 * @date 2021-01-19 下午8:19
 * @ProjectName cxy
 * @Version V1.0
 */
@SpringBootApplication
//@EnableDiscoveryClient
@EnableEurekaClient
public class GWApplication {

    /*@Bean
    public PatternServiceRouteMapper serviceRouteMapper() {
        // 调用构造函数PatternServiceRouteMapper(String servicePattern, String routePattern)
        // servicePattern指定微服务的正则
        // routePattern指定路由正则
        return new PatternServiceRouteMapper("(?<name>^.+)-(?<version>v.+$)",
                "${version}/${name}");
    }*/

    public static void main(String[] args) {
        SpringApplication.run(GWApplication.class, args);
    }
}
