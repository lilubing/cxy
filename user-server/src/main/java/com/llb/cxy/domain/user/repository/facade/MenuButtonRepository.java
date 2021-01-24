package com.llb.cxy.domain.user.repository.facade;

import com.llb.cxy.domain.user.repository.po.MenuButtonPO;
import com.llb.cxy.mybatis.service.GenericManager;

import java.util.List;
import java.util.Map;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.domain.user.repository.facade
 * @Description: 菜单按钮存储类
 * @ClassName: MenuButtonRepository
 * @date 2021-01-13 上午9:07
 * @ProjectName cxy
 * @Version V1.0
 */
public interface MenuButtonRepository extends GenericManager<MenuButtonPO, Long> {

    /**
     * 根据上级ID获取最大序号
     * @Author LiLuBing
     * @Date 2021-01-13 09:29
     * @Param  * @param parentId
     * @return {@link int}
     **/
    Integer getMaxOrders(Long parentId);

    /**
     * 根据上级节点ID查询菜单按钮树
     * @Author LiLuBing
     * @Date 2021-01-14 10:27
     * @Param  * @param parentId
     * @return {@link java.util.List<java.util.Map<java.lang.String,java.lang.Object>>}
     **/
    List<Map<String, Object>> getAllMenuButtonByParentId(Long parentId);

    /**
     * 根据角色ID查询菜单按钮
     * @Author LiLuBing
     * @Date 2021-01-13 09:13
     * @Param  * @param menuButtonId
     * @param roleId
     * @return {@link List< Map< String, Object>>}
     **/
    List<Map<String, Object>> getMenuButtonsTreeByRoleId(Long menuButtonId, Long roleId);

    /**
     * 根据用户与上级菜单ID查询查询菜单
     * @Author LiLuBing
     * @Date 2021-01-14 10:29
     * @Param  * @param userId
     * @param parentMenuId
     * @param menuButtonType
     * @return {@link List< Map< String, Object>>}
     **/
    List<Map<String, Object>> findMenuButtonByUserId(Long userId, Long parentMenuId, Integer menuButtonType);

    /**
     * 获取所有菜单按钮与角色信息
     * @Author LiLuBing
     * @Date 2021-01-21 09:37
     * @Param  * @param 
     * @return
     **/
    List<Map<String, String>> getAllMenuButtonAndRole();
}
