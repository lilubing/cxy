package com.llb.cxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * description: ASApplication <br>
 * date: 2020/7/29 11:29 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
@Controller
@EnableWebMvc
@SpringBootApplication//(scanBasePackages = "")
@MapperScan(basePackages = "com.llb.cxy.*.mapper")
@EnableDiscoveryClient
@Slf4j
public class ASApplication implements CommandLineRunner, WebMvcConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(ASApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("服务启动完成!");
    }

    /**
     * 配置JSP视图解析器
     * 如果没有配置视图解析器。Spring会使用BeanNameViewResolver，通过查找ID与逻辑视图名称匹配且实现了View接口的beans
     *
     * @return
     */
    @Bean
    public InternalResourceViewResolver setupViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        /** 设置视图路径的前缀 */
        resolver.setPrefix("/WEB-INF/page/");
        /** 设置视图路径的后缀 */
        resolver.setSuffix(".jsp");
        return resolver;
    }
}
