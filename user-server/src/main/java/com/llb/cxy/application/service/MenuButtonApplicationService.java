package com.llb.cxy.application.service;

import java.util.List;
import java.util.Map;

import com.llb.cxy.interfaces.assembler.MenuButtonAssembler;
import com.llb.cxy.interfaces.dto.MenuButtonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.llb.cxy.domain.user.entity.MenuButton;
import com.llb.cxy.domain.user.service.MenuButtonDomainService;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.application.service
 * @Description: 菜单按钮应用层
 * @ClassName: MenuButtonApplicationService
 * @date 2021-01-14 上午8:45
 * @ProjectName cxy
 * @Version V1.0
 */
@Service
public class MenuButtonApplicationService {
    @Autowired
    MenuButtonDomainService menuButtonDomainService;

    /**
     * 删除菜单按钮
     * @Author LiLuBing
     * @Date 2021-01-14 10:33
     * @Param  * @param ids
     * @return {@link java.lang.Integer}
     **/
    public Integer delete(Long menuButtonId) {
        return menuButtonDomainService.delete(menuButtonId);
    }

    /**
     * 保存树菜单按钮菜单按钮
     * @Author LiLuBing
     * @Date 2021-01-14 10:32
     * @Param  * @param menuButton
     * @return {@link MenuButton}
     **/
    public MenuButton saveMenuButtons(MenuButton menuButton) {
        return menuButtonDomainService.saveMenuButtons(menuButton);
    }

    /**
     * 添加菜单按钮与角色关联
     * @Author LiLuBing
     * @Date 2021-01-14 10:32
     * @Param  * @param menuButtonId
     * @param checked
     * @param roleId
     * @return
     **/
    public void saveMenuButtonAndRoleAss(Long menuButtonId, boolean checked, Long roleId) {
        menuButtonDomainService.saveMenuButtonAndRoleAss(menuButtonId, checked, roleId);
    }

    /**
     * 根据上级ID获取最大序号
     *
     * @Author LiLuBing
     * @Date 2021-01-13 09:29
     * @Param * @param pid
     * @return {@link int}
     **/
    public Integer getMaxOrders(Long pid) {
        return menuButtonDomainService.getMaxOrders(pid);
    }

    /**
     * 根据上级节点ID查询菜单按钮树
     *
     * @Author LiLuBing
     * @Date 2021-01-14 10:26
     * @Param * @param parentId
     * @return {@link java.util.List<java.util.Map<java.lang.String,java.lang.Object>>}
     **/
    public List<Map<String, Object>> getAllMenuButtonByParentId(Long parentId) {
        return menuButtonDomainService.getAllMenuButtonByParentId(parentId);
    }

    /**
     * 根据角色ID查询菜单按钮
     *
     * @Author LiLuBing
     * @Date 2021-01-13 09:13
     * @Param * @param menuButtonId
     * @param roleId
     * @return {@link List< Map< String, Object>>}
     **/
    public List<Map<String, Object>> getMenuButtonsTreeByRoleId(Long menuButtonId, Long roleId) {
        return menuButtonDomainService.getMenuButtonsTreeByRoleId(menuButtonId, roleId);
    }

    /**
     * 根据用户与上级菜单ID查询查询菜单
     *
     * @Author LiLuBing
     * @Date 2021-01-14 10:29
     * @Param * @param userId
     * @param parentMenuId
     * @param menuButtonType
     * @return {@link List< Map< String, Object>>}
     **/
    public List<Map<String, Object>> findMenuButtonByUserId(Long userId, Long parentMenuId, Integer menuButtonType) {
        return menuButtonDomainService.findMenuButtonByUserId(userId, parentMenuId, menuButtonType);
    }

    /**
     * 根据菜单按钮Id查询
     * @Author LiLuBing
     * @Date 2021-01-16 15:57
     * @Param  * @param menuButtonId
     * @return {@link com.llb.cxy.interfaces.dto.MenuButtonDTO}
     **/
    public MenuButtonDTO findByMenuButtonId(Long menuButtonId) {
        return MenuButtonAssembler.toDTO(menuButtonDomainService.findByMenuButtonId(menuButtonId));
    }
}
