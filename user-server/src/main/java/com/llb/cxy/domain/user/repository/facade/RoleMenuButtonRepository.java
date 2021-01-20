package com.llb.cxy.domain.user.repository.facade;

import com.llb.cxy.domain.user.repository.po.RoleMenuButtonPO;
import com.llb.cxy.mybatis.service.GenericManager;

import java.util.List;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.domain.user.repository.facade
 * @Description: 角色菜单按钮
 * @ClassName: RoleMenuButtonRepository
 * @date 2021-01-13 下午1:42
 * @ProjectName cxy
 * @Version V1.0
 */
public interface RoleMenuButtonRepository extends GenericManager<RoleMenuButtonPO, Long> {
    /**
     * 根据菜单按钮ID删除
     * @Author LiLuBing
     * @Date 2021-01-13 14:37
     * @Param  * @param menuButtonId
     * @return {@link int}
     **/
    Integer deleteByMenuButtonId(Long menuButtonId);

    /**
     * 根据角色ID删除
     * @Author LiLuBing
     * @Date 2021-01-13 14:37
     * @Param  * @param roleId
     * @return 
     **/
    Integer deleteByRoleId(Long roleId);

    /**
     * 根据角色ID菜单按钮菜单ID删除角色菜单按钮
     * @Author LiLuBing
     * @Date 2021-01-14 08:40
     * @Param  * @param roleId
     * @param menuButtonId
     * @return
     **/
    Integer deleteRoleMenuButtonByRoleIdAndMenuButtonId(Long roleId, Long menuButtonId);

    /**
     * 根据菜单按钮Id与角色id查询
     * @Author LiLuBing
     * @Date 2021-01-17 13:26
     * @Param  * @param roleId
     * @param menuButtonId
     * @return {@link List< RoleMenuButtonPO>}
     **/
    List<RoleMenuButtonPO> findByMenuButtonIdAndRoleId(Long menuButtonId, Long roleId);
}
