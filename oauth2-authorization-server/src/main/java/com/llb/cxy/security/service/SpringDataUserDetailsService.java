package com.llb.cxy.security.service;

import java.util.List;
import java.util.stream.Collectors;

import com.llb.cxy.core.convert.MapTrunPojo;
import com.llb.cxy.domain.user.repository.po.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.llb.cxy.core.security.Oauth2Authority;
import com.llb.cxy.core.security.userdetails.WebUserDetail;
import com.llb.cxy.domain.user.repository.mapper.RoleMapper;
import com.llb.cxy.domain.user.repository.mapper.UserMapper;
import com.llb.cxy.domain.user.repository.po.RolePO;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lilubing
 * @version 1.0
 **/
@Service
@Slf4j
public class SpringDataUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    @Lazy
    private RoleMapper roleMapper;

    //根据 账号查询用户信息
    /**
     * 根据用户名登录
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //log.info("loadUserByUsername: {}", username);
        //将来连接数据库根据账号查询用户信息
        List<UserPO> userFormMaps = userMapper.findUserByLoginName(username);

        if(userFormMaps.isEmpty()){
            //如果用户查不到，返回null，由provider来抛出异常
            throw new UsernameNotFoundException("系统用户 " + username + " 不存在!");
        }
        UserPO userPO = userFormMaps.get(0);
        Long userId = userPO.getId();

        WebUserDetail userDetails = new WebUserDetail(MapTrunPojo.object2Map(userPO), getAuthorize(userId));

        return userDetails;
    }

    /**
     * 设置授权信息
     * @param userId
     * @return
     */
    private List<Oauth2Authority> getAuthorize(Long userId) {
        List<RolePO> rolesList = roleMapper.findRolesByUserId(userId);
        // 用户权限列表
        List<Oauth2Authority> authorities = Lists.newArrayList();

        authorities = rolesList.stream().map(role -> {
            // 加入角色标识
            return new Oauth2Authority(role.getName(), role.getId().toString(), null, "role");
        }).collect(Collectors.toList());
        return authorities;
    }
}
