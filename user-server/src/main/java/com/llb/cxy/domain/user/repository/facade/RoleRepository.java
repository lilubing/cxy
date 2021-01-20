package com.llb.cxy.domain.user.repository.facade;

import java.util.List;

import com.llb.cxy.domain.user.repository.po.RolePO;
import com.llb.cxy.mybatis.service.GenericManager;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.domain.user.repository.facade
 * @Description: 角色存储类
 * @ClassName: RoleRepository
 * @date 2021-01-13 上午9:08
 * @ProjectName cxy
 * @Version V1.0
 */
public interface RoleRepository extends GenericManager<RolePO, Long> {

    /**
     * 根据角色ID修改角色信息
     * @Author LiLuBing
     * @Date 2021-01-13 15:03
     * @Param  * @param roleId
     * @param name
     * @param description
     * @param lastModifyTime
     * @return {@link Integer}
     **/
    Integer updateRoleById(Long roleId, String name, String description, Long lastModifyTime);

    /**
     * 根据用户Id所有角色
     * @Author LiLuBing
     * @Date 2021-01-13 09:35
     * @Param  * @param userId
     * @return {@link java.util.List<com.llb.cxy.domain.user.repository.po.RolePO>}
     **/
    List<RolePO> findRolesByUserId(Long userId);

    /**
     * 根据用户Id获取菜单按钮值
     * @Author LiLuBing
     * @Date 2021-01-13 09:36
     * @Param  * @param userId
     * @return {@link java.util.List<java.lang.String>}
     **/
    List<String> getMenuButtonsBtnValueByUserId(Long userId);

}
