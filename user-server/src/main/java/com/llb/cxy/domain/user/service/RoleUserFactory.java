package com.llb.cxy.domain.user.service;

import org.springframework.beans.BeanUtils;

import com.llb.cxy.domain.user.entity.RoleUser;
import com.llb.cxy.domain.user.repository.po.RoleUserPO;
import org.springframework.stereotype.Service;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.domain.user.service
 * @Description: 角色用户工厂方法
 * @ClassName: RoleUserFactory
 * @date 2021-01-13 下午2:53
 * @ProjectName cxy
 * @Version V1.0
 */
@Service
public class RoleUserFactory {
    /**
     * 创建角色用户存储类
     * @Author LiLuBing
     * @Date 2021-01-12 21:17
     * @Param  * @param userInfo
     * @return {@link com.llb.cxy.domain.user.repository.po.UserInfoPO}
     **/
    public RoleUserPO createRoleUserPO(RoleUser roleUser) {
        /**
         * 【强制】避免用ApacheBeanutils进行属性的copy。
         * 说明:Apache BeanUtils 性能较差，可以使用其他方案比如 Spring BeanUtils, Cglib BeanCopier，注意 均是浅拷贝。
         **/
        RoleUserPO roleUserPO = new RoleUserPO();
        BeanUtils.copyProperties(roleUser, roleUserPO);
        return roleUserPO;
    }
}
