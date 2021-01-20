package com.llb.cxy.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * description: UserServerApplication <br>
 * date: 2020/7/29 8:12 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
@Controller
@EnableWebMvc
@SpringBootApplication//(scanBasePackages = "")
@MapperScan(basePackages = "com.llb.cxy.domain.*.repository.mapper")
@EnableDiscoveryClient
@Slf4j
public class UserServerApplication implements CommandLineRunner, WebMvcConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(UserServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("服务启动完成!");
    }

    @RequestMapping("/")
    String home() {
        return "redirect:countries";
    }
}
