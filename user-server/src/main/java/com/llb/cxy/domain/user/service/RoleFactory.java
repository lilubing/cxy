package com.llb.cxy.domain.user.service;

import com.llb.cxy.domain.user.entity.Role;
import com.llb.cxy.domain.user.entity.RoleUser;
import com.llb.cxy.domain.user.repository.facade.RoleRepository;
import com.llb.cxy.domain.user.repository.po.RolePO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.domain.user.service
 * @Description: 角色工厂
 * @ClassName: RoleFactory
 * @date 2021-01-13 上午10:14
 * @ProjectName cxy
 * @Version V1.0
 */
@Service
public class RoleFactory {
    @Autowired
    RoleRepository roleRepository;

    /**
     * 创建角色存储类
     * @Author LiLuBing
     * @Date 2021-01-12 21:17
     * @Param  * @param Role
     * @return {@link RolePO}
     **/
    public RolePO createRolePO(Role role) {
        /**
         * 【强制】避免用ApacheBeanutils进行属性的copy。
         * 说明:Apache BeanUtils 性能较差，可以使用其他方案比如 Spring BeanUtils, Cglib BeanCopier，注意 均是浅拷贝。
         **/
        RolePO rolePO = new RolePO();
        BeanUtils.copyProperties(role, rolePO);
        return rolePO;
    }

    /**
     * 创建角色对象
     * @Author LiLuBing
     * @Date 2021-01-12 21:18
     * @Param  * @param Role
     * @return {@link RolePO}
     **/
    public Role createRole(RolePO rolePO) {
        /**
         * 【强制】避免用ApacheBeanutils进行属性的copy。
         * 说明:Apache BeanUtils 性能较差，可以使用其他方案比如 Spring BeanUtils, Cglib BeanCopier，注意 均是浅拷贝。
         **/
        Role role = new Role();
        BeanUtils.copyProperties(rolePO, role);
        return role;
    }

    /**
     * 根据用户Id所有角色
     * @Author LiLuBing
     * @Date 2021-01-13 14:59
     * @Param  * @param userId
     * @return {@link List< Role>}
     **/
    public List<Role> findRolesByUserId(Long userId) {
        return roleRepository.findRolesByUserId(userId).stream().map(rolePO -> createRole(rolePO))
                .collect(Collectors.toList());
    }
}
