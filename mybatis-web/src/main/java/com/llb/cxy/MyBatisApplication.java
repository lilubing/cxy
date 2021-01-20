package com.llb.cxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * description: MyBatisApplication <br>
 * date: 2020/7/29 17:14 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
@EnableWebMvc
@SpringBootApplication//(scanBasePackages = "")
@MapperScan(basePackages = "com.llb.cxy.*.mapper")
@EnableDiscoveryClient
@Slf4j
public class MyBatisApplication implements CommandLineRunner, WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(MyBatisApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("服务启动完成!");
    }
}
