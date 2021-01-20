package com.llb.cxy.domain.user.repository.mapper;

import com.llb.cxy.domain.user.repository.po.RoleMenuButtonPO;
import com.llb.cxy.mybatis.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.domain.user.repository.mapper
 * @Description: 角色菜单按钮Mapper接口
 * @ClassName: RoleMenuButtonMapper
 * @date 2021-01-13 上午8:45
 * @ProjectName cxy
 * @Version V1.0
 */
@Mapper
public interface RoleMenuButtonMapper extends MyMapper<RoleMenuButtonPO, Long> {

    /**
     * 根据菜单按钮ID删除
     * @Author LiLuBing
     * @Date 2021-01-13 09:08
     * @Param  * @param menuButtonId
     * @return {@link int}
     **/
    Integer deleteByMenuButtonId(Long menuButtonId);

    /**
     * 根据角色ID删除
     * @Author LiLuBing
     * @Date 2021-01-13 08:58
     * @Param  * @param roleId
     * @return 
     **/
    Integer deleteByRoleId(Long roleId);

    /**
     * 根据角色ID菜单按钮菜单ID删除角色菜单按钮
     * @Author LiLuBing
     * @Date 2021-01-14 08:43
     * @Param  * @param roleId
     * @param menuButtonId
     * @return {@link Integer}
     **/
    Integer deleteRoleMenuButtonByRoleIdAndMenuButtonId(Long roleId, Long menuButtonId);

    /**
     * 根据菜单按钮Id与角色id查询
     * @Author LiLuBing
     * @Date 2021-01-17 13:29
     * @Param  * @param menuButtonId
     * @param roleId
     * @return {@link List< RoleMenuButtonPO>}
     **/
    List<RoleMenuButtonPO> findByMenuButtonIdAndRoleId(@Param("menuButtonId") Long menuButtonId, @Param("roleId") Long roleId);
}