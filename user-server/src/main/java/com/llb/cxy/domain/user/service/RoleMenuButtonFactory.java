package com.llb.cxy.domain.user.service;

import com.llb.cxy.domain.user.entity.RoleMenuButton;
import com.llb.cxy.domain.user.repository.po.RolePO;
import com.llb.cxy.domain.user.repository.po.RoleMenuButtonPO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.domain.user.service
 * @Description: 角色菜单按钮工厂类
 * @ClassName: RoleMenuButtonFactory
 * @date 2021-01-14 上午8:32
 * @ProjectName cxy
 * @Version V1.0
 */
@Service
public class RoleMenuButtonFactory {

    /**
     * 创建角色存储类
     * @Author LiLuBing
     * @Date 2021-01-12 21:17
     * @Param  * @param Role
     * @return {@link RolePO}
     **/
    public RoleMenuButtonPO createRolePO(RoleMenuButton roleMenuButton) {
        /**
         * 【强制】避免用ApacheBeanutils进行属性的copy。
         * 说明:Apache BeanUtils 性能较差，可以使用其他方案比如 Spring BeanUtils, Cglib BeanCopier，注意 均是浅拷贝。
         **/
        RoleMenuButtonPO roleMenuButtonPO = new RoleMenuButtonPO();
        BeanUtils.copyProperties(roleMenuButton, roleMenuButtonPO);
        return roleMenuButtonPO;
    }
}
