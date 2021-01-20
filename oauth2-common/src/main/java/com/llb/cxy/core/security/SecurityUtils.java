package com.llb.cxy.core.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 包名com.llb.cxy.core.security:com.czx.springbootwebservice
 * 类名SecurityUtils:权限获取工具类
 * 当前系统用户lilubing:llb
 * 日期2020/8/18:2020/8/8
 * 项目名cxy:springbootwebservice
 */
public class SecurityUtils {
    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication()
    {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取用户
     */
    /*public static String getUsername()
    {
        return getLoginUser().getUserName();
    }*/

    /**
     * 获取用户
     */
    /*public static LoginUser getLoginUser(Authentication authentication)
    {
        Object principal = authentication.getPrincipal();
        if (principal instanceof LoginUser)
        {
            return (LoginUser) principal;
        }
        return null;
    }*/

    /**
     * 获取用户
     */
    /*public static LoginUser getLoginUser()
    {
        Authentication authentication = getAuthentication();
        if (authentication == null)
        {
            return null;
        }
        return getLoginUser(authentication);
    }*/

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword 真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 是否为管理员
     *
     * @param userId 用户ID
     * @return 结果
     */
    public static boolean isAdmin(Long userId)
    {
        return userId != null && 1L == userId;
    }
}
