package com.llb.cxy.boot.config.security.authorization;

import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

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
    /*@Autowired
    private RedisTemplate<String,Object> redisTemplate;*/
    //内存中的值
    Map<String, List<String>> resourceRolesMap = new TreeMap<>();
    PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext authorizationContext) {
        //从Redis中获取当前路径可访问角色列表
        URI uri = authorizationContext.getExchange().getRequest().getURI();
        resourceRolesMap.put("/api/hello", Lists.newArrayList("110"));
        resourceRolesMap.put("/mybatis-web/**", Lists.newArrayList("110", "111"));
//        resourceRolesMap.put("/mybatis-web/user/sys-menuButton/0/user_menu", Lists.newArrayList("ADMIN", "TEST", "ROLE_110"));
        // mybatis-web/user/sys-menuButton/user_menu
        // mybatis-web/user/sys-menuButton/user_menu/0
        Iterator<String> iterator = resourceRolesMap.keySet().iterator();
        // Object obj = redisTemplate.opsForHash().get(RedisConstant.RESOURCE_ROLES_MAP, uri.getPath());
        // 4.请求路径匹配到的资源需要的角色权限集合authorities
        List<String> authorities = Lists.newArrayList();//Convert.toList(String.class, resourceRolesMap);
        // authorities = authorities.stream().map(i -> i = AuthConstant.AUTHORITY_PREFIX + i).collect(Collectors.toList());

        while (iterator.hasNext()) {
            String pattern = (String) iterator.next();
            if (pathMatcher.match(pattern, uri.getPath())) {
                authorities.addAll(Convert.toList(String.class, resourceRolesMap.get(pattern)));
            }
        }
        //认证通过且角色匹配的用户可访问当前路径
        return authentication
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(toSimple -> {
                    //此处默认获取的是SCOPE 需要转换成角色
                    //Collection collection = obj11.getAuthorities();
                    return ((List<String>)((Jwt) toSimple.getPrincipal()).getClaims().get("authorities")).stream()
                            .map(mt -> {return new SimpleGrantedAuthority(mt);}).collect(Collectors.toList());
                    /*return ((List<String>)((Jwt) toSimple.getPrincipal())
                            .getClaims().get("authorities")).stream().map(mt -> {return new SimpleGrantedAuthority(mt);})
                            .collect(Collectors.toList());*/
                })
                .map(GrantedAuthority::getAuthority)
                /*.map(objj -> {
                    String authority = objj.getAuthority();
                    System.out.println(authority);
                    return authority;
                })*/
                .any(authorities::contains)
                /*.any(roleId -> {
                    // 5. roleId是请求用户的角色(格式:ROLE_{roleId})，authorities是请求资源所需要角色的集合
                    log.info("用户角色roleId：{}", roleId);
                    System.out.println("用户角色roleId：{}" + roleId);
                    log.info("资源需要权限authorities：{}", authorities);
                    System.out.println("资源需要权限authorities：{}" + authorities);
                    return authorities.contains(roleId);
                })*/
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }
}