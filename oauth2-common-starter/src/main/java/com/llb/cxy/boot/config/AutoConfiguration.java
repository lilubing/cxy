package com.llb.cxy.boot.config;

import com.llb.cxy.core.configuration.LLBCommonProperties;
import com.llb.cxy.core.configuration.LLBIdGenProperties;
import com.llb.cxy.core.configuration.LLBScanProperties;
import com.llb.cxy.core.exception.GlobalExceptionHandler;
import com.llb.cxy.core.exception.LLBRestResponseErrorHandler;
import com.llb.cxy.core.filter.XFilter;
import com.llb.cxy.core.health.DbHealthIndicator;
import com.llb.cxy.core.security.http.LLBRestTemplate;
import com.llb.cxy.core.security.oauth2.client.LLBOAuth2ClientProperties;
import com.llb.cxy.core.snowflake.SnowflakeIdWorker;
import com.llb.cxy.core.spring.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

/**
 * description: 默认配置类 <br>
 * date: 2019/12/18 9:57 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({LLBCommonProperties.class, LLBIdGenProperties.class, LLBOAuth2ClientProperties.class, LLBScanProperties.class})
public class AutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(LLBCommonProperties.class)
    public  LLBCommonProperties scanProperties(){
        return  new LLBCommonProperties();
    }

    /**
     * xss过滤
     * body缓存
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean XssFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new XFilter());
        log.info("XFilter [{}]", filterRegistrationBean);
        return filterRegistrationBean;
    }

    /**
     * 默认加密配置
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(BCryptPasswordEncoder.class)
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        log.info("BCryptPasswordEncoder [{}]", encoder);
        return encoder;
    }


    /**
     * Spring上下文工具配置
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SpringContextUtil.class)
    public SpringContextUtil springContextHolder() {
        SpringContextUtil holder = new SpringContextUtil();
        log.info("SpringContextHolder [{}]", holder);
        return holder;
    }

    /**
     * 统一异常处理配置
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(GlobalExceptionHandler.class)
    public GlobalExceptionHandler exceptionHandler() {
        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();
        log.info("OpenGlobalExceptionHandler [{}]", exceptionHandler);
        return exceptionHandler;
    }

    /**
     * ID生成器配置
     *
     * @param properties
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(LLBIdGenProperties.class)
    public SnowflakeIdWorker snowflakeIdWorker(LLBIdGenProperties properties) {
        SnowflakeIdWorker snowflakeIdGenerator = new SnowflakeIdWorker(properties.getWorkId(), properties.getCenterId());
        log.info("SnowflakeIdGenerator [{}]", snowflakeIdGenerator);
        return snowflakeIdGenerator;
    }

    /**
     * 自定义注解扫描
     *
     * @return
     */
    /*@Bean
    @ConditionalOnMissingBean(RequestMappingScan.class)
    public RequestMappingScan resourceAnnotationScan(AmqpTemplate amqpTemplate, LLBScanProperties scanProperties) {
        RequestMappingScan scan = new RequestMappingScan(amqpTemplate,scanProperties);
        log.info("RequestMappingScan [{}]", scan);
        return scan;
    }*/

    /**
     * 自定义Oauth2请求类
     *
     * @param LLBCommonProperties
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(LLBRestTemplate.class)
    public LLBRestTemplate openRestTemplate(LLBCommonProperties LLBCommonProperties, ApplicationEventPublisher publisher) {
        LLBRestTemplate restTemplate = new LLBRestTemplate(LLBCommonProperties, publisher);
        //设置自定义ErrorHandler
        restTemplate.setErrorHandler(new LLBRestResponseErrorHandler());
        log.info("OpenRestTemplate [{}]", restTemplate);
        return restTemplate;
    }
    /*@Bean
    @ConditionalOnMissingBean(LLBRestTemplate.class)
    public LLBRestTemplate openRestTemplate(LLBCommonProperties LLBCommonProperties, BusProperties busProperties, ApplicationEventPublisher publisher) {
        LLBRestTemplate restTemplate = new LLBRestTemplate(LLBCommonProperties, busProperties, publisher);
        //设置自定义ErrorHandler
        restTemplate.setErrorHandler(new LLBRestResponseErrorHandler());
        log.info("OpenRestTemplate [{}]", restTemplate);
        return restTemplate;
    }*/

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        //设置自定义ErrorHandler
        restTemplate.setErrorHandler(new LLBRestResponseErrorHandler());
        log.info("RestTemplate [{}]", restTemplate);
        return restTemplate;
    }

    @Bean
    @ConditionalOnMissingBean(DbHealthIndicator.class)
    public DbHealthIndicator dbHealthIndicator() {
        DbHealthIndicator dbHealthIndicator = new DbHealthIndicator();
        log.info("DbHealthIndicator [{}]", dbHealthIndicator);
        return dbHealthIndicator;
    }

    /**
     * 自动填充模型数据
     *https://gitee.com/liuyadu/open-cloud
     * @return
     */
    /*@Bean
    @ConditionalOnMissingBean(ModelMetaObjectHandler.class)
    public ModelMetaObjectHandler modelMetaObjectHandler() {
        ModelMetaObjectHandler metaObjectHandler = new ModelMetaObjectHandler();
        log.info("ModelMetaObjectHandler [{}]", metaObjectHandler);
        return metaObjectHandler;
    }*/

}