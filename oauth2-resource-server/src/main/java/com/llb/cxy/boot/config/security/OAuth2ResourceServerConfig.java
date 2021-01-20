package com.llb.cxy.boot.config.security;

import com.llb.cxy.core.exception.LLBAccessDeniedHandler;
import com.llb.cxy.core.exception.LLBAuthExceptionEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * description: OAuth2ResourceServerConfig <br>
 * date: 2019/11/22 13:48 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    //'不能为空，用逗号分隔。客户端能访问的资源id集合，注册客户端时，根据实际需要可选择资源id，也可以根据不同的额注册流程，赋予对应的额资源id
    //oauth_client_details.resource_ids
    public static final String RESOURCE_ID = "res2";
    @Value("${project-name}")
    private String projectName;

    @Autowired
    private CustomAccessTokenConverter customAccessTokenConverter;

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("123");
        converter.setAccessTokenConverter(customAccessTokenConverter);
        // final KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("mytest.jks"), "mypass".toCharArray());
        // converter.setKeyPair(keyStoreKeyFactory.getKeyPair("mytest"));
        return converter;
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                //.authorizeRequests().anyRequest().permitAll()

                .requestMatchers()//拦截请求
                //.antMatchers("/login", "/oauth/**")
                .and()
                .authorizeRequests()//请求不需要权限认证
                //.antMatchers("/login").permitAll()
                //.antMatchers("/login/**","/logout/**","/oauth/**", "/wx/**").permitAll()
                // "/oauth/**" 不能代替 "/oauth/login"
                //.antMatchers( "/oauth/login", "/login/**", "/logout/**", "/wx/**").permitAll()
                .anyRequest()// 任何请求
                .authenticated()// 需要身份认证

                /*.and()
                // 认证鉴权错误处理,为了统一异常处理。每个资源服务器都应该加上。
                .exceptionHandling()
                .accessDeniedHandler(new LLBAccessDeniedHandler())
                //加了之后authorization_code会跳转不过来
                .authenticationEntryPoint(new LLBAuthenticationEntryPoint(projectName))*/
        ;
        // @formatter:on
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        /*DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());*/

        resources
                .resourceId(RESOURCE_ID)//资源 id
                .tokenStore(tokenStore())
//                .authenticationManager(authenticationManager)
                //.tokenServices(defaultTokenServices)

                // 如果关闭 stateless，则 accessToken 使用时的 session id 会被记录，
                // 后续请求不携带 accessToken 也可以正常响
                //待验证
                .stateless(true)
                .accessDeniedHandler(new LLBAccessDeniedHandler())
                //加了之后authorization_code会跳转不过来
                .authenticationEntryPoint(new LLBAuthExceptionEntryPoint())
        ;
    }

}