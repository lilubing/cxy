package com.llb.cxy.boot.config.security;

import com.llb.cxy.core.exception.LLBAccessDeniedHandler;
import com.llb.cxy.core.exception.LLBAuthenticationEntryPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * description: OAuth2ResourceServerConfig <br>
 * date: 2019/11/25 8:16 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
@Slf4j
@EnableResourceServer
@Configuration
@Order(6)
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    public static final String RESOURCE_ID = "res2";

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private DefaultTokenServices tokenServices;

    @Override
    public void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().antMatchers("/**");//.hasRole("ADMIN");

        //http.requestMatchers().antMatchers("/user/**");

        http
                .antMatcher("/user/extra")
                .authorizeRequests().anyRequest().authenticated();

        /*http
                .authorizeRequests()
                //判断是否有指定权限，在认证服务器使用，会登录不了
                //.antMatchers("/**").access("#oauth2.hasScope('ROLE_API')")
                .antMatchers("/user/**").access("#oauth2.hasScope('ROLE_API')")
                .and()
                .authorizeRequests()
                //放行login oauth
                .antMatchers("/login/**","/logout/**","/oauth/**",
                        "/cxy-authorization-server/oauth/**",
                        "/cxy-authorization-server/login",
                        "/wx/**").permitAll()

//                .and().formLogin().loginPage("/cxy-authorization-server/login").permitAll()
//                .loginProcessingUrl("/cxy-authorization-server/login")
                *//*.and()
                .logout().permitAll()
                // /logout退出清除cookie
                .addLogoutHandler(new CookieClearingLogoutHandler("token", "remember-me"))
                .logoutSuccessHandler(new LogoutSuccessHandler(tokenStore))

                .and()
                // 认证鉴权错误处理,为了统一异常处理。每个资源服务器都应该加上。
                .exceptionHandling()
                .accessDeniedHandler(new LLBAccessDeniedHandler())
                .authenticationEntryPoint(new LLBAuthenticationEntryPoint())
                .and().csrf().disable()*//*
        ;*/

       /* // 配置那些资源需要保护的
        http.requestMatchers().antMatchers("/api/**")
                .and()
                .authorizeRequests()
                .antMatchers("/api/**").authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler())  //权限认证失败业务处理
                .authenticationEntryPoint(customAuthenticationEntryPoint());  //认证失败的业务处理
        http.addFilterBefore(permitAuthenticationFilter,AbstractPreAuthenticatedProcessingFilter.class); //自定义token过滤 token校验失败后自定义返回数据格式
        */
        //spring secuity提供了requestMatchers接口，等价于http.authorizeRequests().anyRequest().access("permitAll");
        //http.authorizeRequests().requestMatchers(AnyRequestMatcher.INSTANCE).access("permitAll");

        //参数中type等于1的就不做权限认证,
        // 当访问的url地址为http://localhost:8001/web/v1/hello?type=1，因为type值是1，所以匹配
        //http.authorizeRequests().requestMatchers((RequestMatcher) request -> "1".equals(request.getParameter("type"))).access("permitAll");

        //http.authorizeRequests().antMatchers("/**/*.html").access("permitAll");
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        /*DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore);*/

        resources
                .resourceId(RESOURCE_ID)//资源 id
                //.tokenStore(tokenStore())
//                .authenticationManager(authenticationManager)
                .tokenServices(tokenServices)

                // 如果关闭 stateless，则 accessToken 使用时的 session id 会被记录，
                // 后续请求不携带 accessToken 也可以正常响
                //待验证
                .stateless(true)
        ;
        //resourceId:指定可访问的资源id
        /*resources
                .resourceId(RESOURCE_ID)//资源 id
                //.tokenServices(tokenServices())//验证令牌的服务
                .tokenServices(tokenService())//验证令牌的服务
                //stateless:标记，以指示在这些资源上只允许基于标记的身份验证。
                .stateless(true)
        ;*/
    }

}
