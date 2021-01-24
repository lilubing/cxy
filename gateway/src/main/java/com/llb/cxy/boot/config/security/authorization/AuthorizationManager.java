package com.llb.cxy.boot.config.security.authorization;

import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.llb.cxy.core.constants.UserConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.google.common.collect.Lists;

import cn.hutool.core.convert.Convert;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.boot.config.security.authorization
 * @Description: 鉴权管理器，用于判断是否有资源的访问权限
 * @ClassName: Test
 * @date 2021-01-19 下午8:19
 * @ProjectName cxy
 * @Version V1.0
 */
@Component
@Slf4j
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    @Autowired
    private ReactiveRedisTemplate reactiveRedisTemplate;
    //内存中的值
    Map<String, List<String>> resourceRolesMap = new TreeMap<>();
    PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext authorizationContext) {
        //从Redis中获取当前路径可访问角色列表
        URI uri = authorizationContext.getExchange().getRequest().getURI();
        /*System.out.println(uri.getPath());
        resourceRolesMap.put("/api/hello", Lists.newArrayList("110"));
        resourceRolesMap.put("/mybatis-web/**", Lists.newArrayList("110", "111"));
        // 4.请求路径匹配到的资源需要的角色权限集合authorities
        List<String> authorities = Lists.newArrayList();
        Iterator<String> iterator = resourceRolesMap.keySet().iterator();
        while (iterator.hasNext()) {
            String pattern = (String) iterator.next();
            if (pathMatcher.match(pattern, uri.getPath())) {
                authorities.addAll(Convert.toList(String.class, resourceRolesMap.get(pattern)));
            }
        }*/

        ReactiveHashOperations<String, String, Set<String>> opsForHash = reactiveRedisTemplate.opsForHash();
        Mono<Set<String>> listMono = opsForHash.get(UserConstants.REDIS_USER_PRIVILEGES_KEY_PREFIX, uri.getPath());


        //认证通过且角色匹配的用户可访问当前路径
        return authentication
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(toSimple -> {
                    //此处默认获取的是SCOPE 需要转换成角色
                    //Collection collection = obj11.getAuthorities();
                    return ((List<String>)((Jwt) toSimple.getPrincipal()).getClaims().get("authorities")).stream()
                            .map(mt -> {return new SimpleGrantedAuthority(mt);}).collect(Collectors.toList());
                })
                .map(GrantedAuthority::getAuthority)
                /*.map(objj -> {
                    String authority = objj.getAuthority();
                    System.out.println(authority);
                    return authority;
                })*/
                // .any(authorities::contains)
                .any(roleId -> {
                    return listMono.filter(list -> {
                        /*list.forEach(a -> {
                            System.out.println("role:" + a);
                        });*/
                        return list.contains(roleId);
                    }).hasElement().block();
                })
                /*.any(roleId -> {
                    // 5. roleId是请求用户的角色(格式:ROLE_{roleId})，authorities是请求资源所需要角色的集合
                    log.info("用户角色roleId：{}", roleId);
                    System.out.println("用户角色roleId：{}" + roleId);
                    log.info("资源需要权限authorities：{}", authorities);
                    System.out.println("资源需要权限authorities：{}" + authorities);
                    listMono.subscribe(System.out::println);
                    return authorities.contains(roleId);
                })*/
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }
}