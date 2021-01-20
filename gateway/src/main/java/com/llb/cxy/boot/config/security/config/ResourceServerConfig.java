package com.llb.cxy.boot.config.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.llb.cxy.boot.config.security.authorization.AuthorizationManager;
import com.llb.cxy.boot.config.security.component.RestAuthenticationEntryPoint;
import com.llb.cxy.boot.config.security.component.RestfulAccessDeniedHandler;
import com.llb.cxy.boot.config.security.filter.IgnoreUrlsRemoveJwtFilter;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.boot.config.security.config
 * @Description: 资源服务器配置
 * @ClassName: Test
 * @date 2021-01-19 下午8:19
 * @ProjectName cxy
 * @Version V1.0
 */
@AllArgsConstructor
@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfig {
    private final AuthorizationManager authorizationManager;
    private final IgnoreUrlsConfig ignoreUrlsConfig;
    private final RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final IgnoreUrlsRemoveJwtFilter ignoreUrlsRemoveJwtFilter;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());
        // 自定义处理JWT请求头过期或签名错误的结果
        http.oauth2ResourceServer().authenticationEntryPoint(restAuthenticationEntryPoint);
        // 对白名单路径，直接移除JWT请求头
        http.addFilterBefore(ignoreUrlsRemoveJwtFilter, SecurityWebFiltersOrder.AUTHENTICATION);
        http.authorizeExchange()
            // 白名单配置
            .pathMatchers(ignoreUrlsConfig.getUrls().toArray(new String[ignoreUrlsConfig.getUrls().size()])).permitAll()
            // option 请求默认放行
            .pathMatchers(HttpMethod.OPTIONS).permitAll()
            // 鉴权管理器配置
            .anyExchange().access(authorizationManager).and().exceptionHandling()
            // 处理未授权
            .accessDeniedHandler(restfulAccessDeniedHandler)
            // 处理未认证
            .authenticationEntryPoint(restAuthenticationEntryPoint)
            // 必须支持跨域
            .and().csrf().disable();
        return http.build();
    }

    @Bean
    public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        /*jwtGrantedAuthoritiesConverter.setAuthorityPrefix(AuthConstant.AUTHORITY_PREFIX);
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(AuthConstant.AUTHORITY_CLAIM_NAME);*/
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }

}