package com.llb.cxy.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.llb.cxy.domain.user.repository.mapper.UserMapper;
import com.llb.cxy.security.filter.JsonAuthenticationFilter;
import com.llb.cxy.security.provider.JsonAuthenticationProvider;
import com.llb.cxy.security.service.SpringDataUserDetailsService;
import com.llb.cxy.security.utils.DefindeAuthenticationFailureHandler;
import com.llb.cxy.security.utils.DefinedAuthenticationSuccessHandler;
import com.llb.cxy.security.utils.JsonAuthenticationSuccessHandler;

/**
 * description: JsonAuthenticationSecurityConfig <br>
 * date: 2019/12/2 14:01 <br>
 * author: LiLuBing <br>
 * version: 1.0 <br>
 */
@Component
public class JsonAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    @Autowired
    private SpringDataUserDetailsService springDataUserDetailsService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JsonAuthenticationSuccessHandler jsonAuthenticationSuccessHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        JsonAuthenticationFilter authenticationFilter = new JsonAuthenticationFilter();
        authenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        authenticationFilter.setAuthenticationSuccessHandler(new DefinedAuthenticationSuccessHandler());
        authenticationFilter.setAuthenticationFailureHandler(new DefindeAuthenticationFailureHandler());

        JsonAuthenticationProvider authenticationProvider = new JsonAuthenticationProvider();
        authenticationProvider.setUserDetailsService(springDataUserDetailsService);
        authenticationProvider.setUserMapper(userMapper);

        http.authenticationProvider(authenticationProvider)
                .addFilterAfter(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
