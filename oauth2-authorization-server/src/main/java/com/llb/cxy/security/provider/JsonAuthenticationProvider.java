package com.llb.cxy.security.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.llb.cxy.core.security.userdetails.WebUserDetail;
import com.llb.cxy.domain.user.repository.mapper.UserMapper;
import com.llb.cxy.security.token.JsonAuthenticationToken;

/**
 * description: JsonAuthenticationProvider <br>
 * date: 2019/12/2 10:59 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
public class JsonAuthenticationProvider implements AuthenticationProvider {

    private static final Logger log = LoggerFactory.getLogger(JsonAuthenticationProvider.class);

    private UserDetailsService userDetailsService;
    private UserMapper userMapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JsonAuthenticationToken authenticationToken = (JsonAuthenticationToken) authentication;

        String loginUsername = authenticationToken.getName();
        String loginPassword = (String) authenticationToken.getCredentials();
        log.info("用户登录，用户名 [{}]，密码 [{}]", loginUsername, loginPassword);

        WebUserDetail webUserDetail = (WebUserDetail) userDetailsService.loadUserByUsername(loginUsername);
        String password = webUserDetail.getPassword();

        if (null == webUserDetail) {
            throw new UsernameNotFoundException("用户名/密码无效");
        } else if (!webUserDetail.isEnabled()) {
            log.info("{}用户已被禁用", loginUsername);
            throw new DisabledException("用户已被禁用");
        } else if (!webUserDetail.isAccountNonExpired()) {
            log.info("{}账号已过期", loginUsername);
            throw new LockedException(loginUsername + "账号已过期");
        } else if (!webUserDetail.isAccountNonLocked()) {
            log.info("{}账号已被锁定", loginUsername);
            throw new LockedException(loginUsername + "账号已被锁定");
        } else if (!webUserDetail.isCredentialsNonExpired()) {
            log.info("{}凭证已过期", loginUsername);
            throw new LockedException(loginUsername + "凭证已过期");
        }

        //与authentication里面的credentials相比较
        // 此处自定义密码加密处理规则
        //if (!loginPassword.equals(password)) {
        if (bCryptPasswordEncoder.matches(loginPassword, password)) {
            //throw new DisabledException("用户登录，密码错误");
            throw new BadCredentialsException("Invalid username/password");
        }
        //userInfoService.getUserInfo(webUserDetail.getUsername())

        //授权
        //return authenticationResult;
        return new UsernamePasswordAuthenticationToken(
                userMapper.findUserByLoginName(webUserDetail.getUsername()),
                password, webUserDetail.getAuthorities());
    }

    /** 支持使用此方法验证
     *
     * @param authentication
     * @return 没有特殊处理，返回true，否则不会用这个配置进行验证
     */
    @Override
    public boolean supports(Class<?> authentication) {
        //返回true后才会执行上面的authenticate方法,这步能确保authentication能正确转换类型
        return JsonAuthenticationToken.class.equals(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public UserMapper getUserMapper() {
        return userMapper;
    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
