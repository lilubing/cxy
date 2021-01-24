package com.llb.cxy.domain.user.service;

import java.util.List;
import java.util.Map;

import com.llb.cxy.domain.user.entity.MenuButton;
import com.llb.cxy.domain.user.repository.po.RoleMenuButtonPO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.llb.cxy.core.id.Identities;
import com.llb.cxy.core.utils.MyStringUtils;
import com.llb.cxy.domain.user.entity.RoleMenuButton;
import com.llb.cxy.domain.user.repository.facade.MenuButtonRepository;
import com.llb.cxy.domain.user.repository.facade.RoleMenuButtonRepository;
import com.llb.cxy.domain.user.repository.po.MenuButtonPO;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.domain.user.service
 * @Description: 菜单按钮服务类
 * @ClassName: MenuButtonDomainService
 * @date 2021-01-13 上午10:24
 * @ProjectName cxy
 * @Version V1.0
 */
@Service
public class MenuButtonDomainService {
    @Autowired
    MenuButtonRepository menuButtonRepository;
    @Autowired
    MenuButtonFactory menuButtonFactory;
    @Autowired
    RoleMenuButtonRepository roleMenuButtonRepository;
    @Autowired
    RoleMenuButtonFactory roleMenuButtonFactory;

    /**
     * 删除菜单按钮
     * @Author LiLuBing
     * @Date 2021-01-14 10:34
     * @Param  * @param ids
     * @return {@link java.lang.Integer}
     **/
    @Transactional(propagation = Propagation.REQUIRED, timeout = 3)
    public Integer delete(Long menuButtonId) {
        Integer result = menuButtonRepository.delete(menuButtonId);
        roleMenuButtonRepository.deleteByMenuButtonId(menuButtonId);
        return result;
    }

    /**
     * 保存树菜单按钮菜单按钮
     * @Author LiLuBing
     * @Date 2021-01-14 10:32
     * @Param  * @param menuButton
     * @return {@link MenuButton}
     **/
    @Transactional(propagation = Propagation.REQUIRED, timeout = 3)
    public MenuButton saveMenuButtons(MenuButton menuButton) {
        if (null == menuButton) {
            return null;
        }
        Long menuButtonId = menuButton.getMenuButtonId();
        if (menuButtonId == null) {
            menuButton.create();
            menuButton.setOrders(menuButtonRepository.getMaxOrders(menuButton.getParentId()) + 1);
            menuButtonRepository.save(menuButtonFactory.createMenuButtonPO(menuButton));
            //给超级管理员添加菜单按钮
            saveMenuButtonAndRoleAss(menuButton.getMenuButtonId(), true, 110L);
        } else {
            /**
             * 【强制】避免用ApacheBeanutils进行属性的copy。
             * 说明:Apache BeanUtils 性能较差，可以使用其他方案比如 Spring BeanUtils, Cglib BeanCopier，注意 均是浅拷贝。
             **/
            MenuButtonPO menuButtonPO = menuButtonRepository.get(menuButtonId);
            //如果上级ID修改类需要修改对应的顺序
            if(!menuButton.getParentId().equals(menuButtonPO.getParentId())) {
                menuButton.setOrders(menuButtonRepository.getMaxOrders(menuButton.getParentId()) + 1);
            }
            BeanUtils.copyProperties(menuButton, menuButtonPO);
            menuButtonRepository.update(menuButtonPO);
        }
        return menuButton;
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
    @Transactional(propagation = Propagation.REQUIRED, timeout = 3)
    public void saveMenuButtonAndRoleAss(Long menuButtonId, boolean checked, Long roleId) {
        if (checked) {
            List<RoleMenuButtonPO> list = roleMenuButtonRepository.findByMenuButtonIdAndRoleId(menuButtonId, roleId);
            // 判断是否已经存在
            if(list.isEmpty()) {
                //选中添加
                roleMenuButtonRepository.save(roleMenuButtonFactory.createRolePO(
                        new RoleMenuButton().create(Identities.getId(), roleId, menuButtonId)));
            }
        } else {
            //取消选中删除
            roleMenuButtonRepository.deleteRoleMenuButtonByRoleIdAndMenuButtonId(roleId, menuButtonId);
        }
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
        return menuButtonRepository.getMaxOrders(pid);
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
        return menuButtonRepository.getAllMenuButtonByParentId(parentId);
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
        return menuButtonRepository.getMenuButtonsTreeByRoleId(menuButtonId, roleId);
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
        return menuButtonRepository.findMenuButtonByUserId(userId, parentMenuId, menuButtonType);
    }

    /**
     * 根据菜单按钮Id查询
     * @Author LiLuBing
     * @Date 2021-01-16 15:57
     * @Param  * @param menuButtonId
     * @return {@link com.llb.cxy.domain.user.entity.MenuButton}
     **/
    public MenuButton findByMenuButtonId(Long menuButtonId) {
        return menuButtonFactory.createMenuButton(menuButtonRepository.get(menuButtonId));
    }

    /**
     * 获取所有菜单按钮与角色信息
     * @Author LiLuBing
     * @Date 2021-01-21 09:35
     * @Param  * @param
     * @return {@link List<Map<String, String>>}
     **/
    public List<Map<String, String>> getAllMenuButtonAndRole() {
        return menuButtonRepository.getAllMenuButtonAndRole();
    }
}
