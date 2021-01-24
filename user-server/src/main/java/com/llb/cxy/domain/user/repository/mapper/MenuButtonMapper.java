package com.llb.cxy.domain.user.repository.mapper;

import com.llb.cxy.domain.user.repository.po.MenuButtonPO;
import com.llb.cxy.mybatis.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.domain.user.repository.mapper
 * @Description: 菜单按钮Mapper接口
 * @ClassName: MenuButtonMapper
 * @date 2021-01-13 上午8:44
 * @ProjectName cxy
 * @Version V1.0
 */
@Mapper
public interface MenuButtonMapper extends MyMapper<MenuButtonPO, Long> {

    /**
     * 根据父节点ID查询
     * @Author LiLuBing
     * @Date 2021-01-13 09:02
     * @Param  * @param menuButtonId
     * @return {@link List<  MenuButtonPO >}
     **/
    List<MenuButtonPO> findByParentId(Long menuButtonId);

    /**
     * 根据菜单按钮与角色ID查询
     * @Author LiLuBing
     * @Date 2021-01-13 09:02
     * @Param  * @param menuButtonId
     * @param roleId
     * @return {@link List< Map< String, Object>>}
     **/
    List<Map<String, Object>> findByMenuButtonIdAndRoleId(Long menuButtonId, Long roleId);

    /**
     * 根据用户ID查询菜单按钮
     * @Author LiLuBing
     * @Date 2021-01-13 09:02
     * @Param  * @param userId
     * @return {@link List< Long>}
     **/
    List<Long> findMenuButtonByUserId(
            @Param("userId") Long userId);

    /**
     * 根据条件查询菜单按钮
     * @Author LiLuBing
     * @Date 2021-01-13 09:02
     * @Param  * @param userId
     * @param parentMenuId
     * @param menuButtonType
     * @return {@link List< Map< String, Object>>}
     **/
    List<Map<String, Object>> findMenuButtonByUserIdParentMenuIdMenuButtonType(
            @Param("userId") Long userId, @Param("parentMenuId") Long parentMenuId,
            @Param("menuButtonType") Integer menuButtonType);

    /**
     * 根据上级节点ID查询最大序号
     * @Author LiLuBing
     * @Date 2021-01-13 09:02
     * @Param  * @param parentId
     * @return {@link Integer}
     **/
    Integer getMaxOrders(Long parentId);

    /**
     * 添加角色与菜单按钮
     * @Author LiLuBing
     * @Date 2021-01-13 09:02
     * @Param  * @param id
     * @param roleId
     * @param menuButtonId
     * @return {@link int}
     **/
    int insertRoleMenuButton(long id, Long roleId, Long menuButtonId);

    /**
     * 删除角色与菜单按钮
     * @Author LiLuBing
     * @Date 2021-01-13 09:01
     * @Param  * @param roleId
     * @param menuButtonId
     * @return {@link int}
     **/
    int deleteRoleMenuButton(Long roleId, Long menuButtonId);

    /**
     * 获取所有菜单按钮与角色信息
     * @Author LiLuBing
     * @Date 2021-01-21 19:42
     * @Param  * @param 
     * @return {@link List<Map<String, String>>}
     **/
    List<Map<String, String>> getAllMenuButtonAndRole();
}
