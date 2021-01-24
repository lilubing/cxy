package com.llb.cxy.domain.user.repository.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.llb.cxy.domain.user.repository.facade.MenuButtonRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.llb.cxy.core.convert.MapTrunPojo;
import com.llb.cxy.domain.user.repository.mapper.MenuButtonMapper;
import com.llb.cxy.domain.user.repository.po.MenuButtonPO;
import com.llb.cxy.mybatis.service.GenericManagerImpl;
import org.springframework.stereotype.Service;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.domain.user.repository.persistence
 * @Description: 菜单按钮存储类
 * @ClassName: MenuButtonRepositoryImpl
 * @date 2021-01-13 上午9:14
 * @ProjectName cxy
 * @Version V1.0
 */
@Service
public class MenuButtonRepositoryImpl extends GenericManagerImpl<MenuButtonPO, Long> implements MenuButtonRepository {

    private MenuButtonMapper menuButtonMapper;

    @Autowired
    public MenuButtonRepositoryImpl(MenuButtonMapper menuButtonMapper) {
        super(menuButtonMapper);
        this.menuButtonMapper = menuButtonMapper;
    }

    /**
     * 根据上级ID获取最大序号
     * 
     * @Author LiLuBing
     * @Date 2021-01-13 09:29
     * @Param * @param pid
     * @return {@link int}
     **/
    @Override
    public Integer getMaxOrders(Long parentId) {
        return Optional.ofNullable(menuButtonMapper.getMaxOrders(parentId)).orElse(1);
    }

    /**
     * 根据上级节点ID查询菜单按钮树
     * 
     * @Author LiLuBing
     * @Date 2021-01-14 10:26
     * @Param * @param parentId
     * @return {@link java.util.List<java.util.Map<java.lang.String,java.lang.Object>>}
     **/
    @Override
    public List<Map<String, Object>> getAllMenuButtonByParentId(Long parentId) {
        // 返回集
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        // 根据父节点ID查询
        List<MenuButtonPO> menuButtons = menuButtonMapper.findByParentId(parentId);
        for (MenuButtonPO menuButtonPO : menuButtons) {
            Long menuButtonId = menuButtonPO.getMenuButtonId();
            Map<String, Object> map = getMenuButtonToMap(menuButtonPO, menuButtonId);
            // 根据父节点ID查询
            List<MenuButtonPO> sub_btns = menuButtonMapper.findByParentId(menuButtonId);
            if (!sub_btns.isEmpty()) {
                // 变成可展开的状态
                map.put("state", "open");
                List<Object> nextList = new ArrayList<Object>();
                map.put("children", nextList);
                // 获取子节点按钮
                getChildrenTree(nextList, sub_btns);
            }
            maps.add(map);
        }
        return maps;
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
    @Override
    public List<Map<String, Object>> getMenuButtonsTreeByRoleId(Long menuButtonId, Long roleId) {
        // 返回集
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        // 根据父节点ID查询
        List<Map<String, Object>> menuButtons = menuButtonMapper.findByMenuButtonIdAndRoleId(menuButtonId, roleId);
        for (Map<String, Object> perm : menuButtons) {
            // p.menu_button_id,p.pid,p.name,p.type,p.menuButton_value,p.uri,p.icon,p.status,p.ctime,p.orders,rp.menu_button_id
            Long loopMenuButtonId = (Long)perm.get("menu_button_id");
            getMenuButtonTreeByParentIdIdExtractMethod(roleId, result, perm, loopMenuButtonId);
        }
        return result;
    }

    /**
     * 根据查询的树节点数组返回map 抽象方法
     * 
     * @Author LiLuBing
     * @Date 2021-01-17 09:51
     * @Param * @param roleId
     * @param result
     * @param perm
     * @param loopMenuButtonId
     * @return
     **/
    private void getMenuButtonTreeByParentIdIdExtractMethod(Long roleId, List<Map<String, Object>> result,
        Map<String, Object> perm, Long loopMenuButtonId) {
        // 根据查询的树节点数组返回map
        Map<String, Object> map = getMapByObjects(perm, loopMenuButtonId);
        // 根据父节点ID查询
        List<Map<String, Object>> sub_btns = menuButtonMapper.findByMenuButtonIdAndRoleId(loopMenuButtonId, roleId);
        if (!sub_btns.isEmpty()) {
            // 变成可展开的状态
            map.put("state", "open");
            List<Object> nextList = new ArrayList<Object>();
            map.put("children", nextList);
            // 获取子节点按钮
            getChildrenByRoleId(nextList, sub_btns, roleId);
        }
        result.add(map);
    }

    /**
     * 根据父节点ID查询
     * 
     * @Author LiLuBing
     * @Date 2021-01-14 10:26
     * @Param * @param parentList
     * @param btn_List
     * @return
     **/
    public void getChildrenTree(List<Object> parentList, List<MenuButtonPO> btn_List) {
        // 当前父节点的所有子节点 返回集合
        List<Object> subList = new ArrayList<Object>();
        // 循环所有的子节点
        for (MenuButtonPO menuButtonPO : btn_List) {
            Long menuButtonId = menuButtonPO.getMenuButtonId();
            Map<String, Object> map = getMenuButtonToMap(menuButtonPO, menuButtonId);
            // 根据父节点ID查询
            List<MenuButtonPO> sub_btns = menuButtonMapper.findByParentId(menuButtonId);
            if (!sub_btns.isEmpty()) {
                List<Object> nextList = new ArrayList<Object>();
                map.put("children", nextList);
                // 获取子节点按钮
                getChildrenTree(nextList, sub_btns);
            }
            subList.add(map);
        }
        // 将当前节点数据添加到 传递的根对象中
        parentList.addAll(subList);
    }

    /**
     * 对象转Map
     * 
     * @Author LiLuBing
     * @Date 2021-01-16 19:29
     * @Param * @param menuButtonPO
     * @param menuButtonId
     * @return {@link Map< String, Object>}
     **/
    private Map<String, Object> getMenuButtonToMap(MenuButtonPO menuButtonPO, Long menuButtonId) {
        Map<String, Object> map = MapTrunPojo.object2Map(menuButtonPO);
        // 范型问题无法转换，只能手动转
        map.put("state", menuButtonPO.getState().getCode());
        map.put("type", menuButtonPO.getType().getCode());
        // 前端的Id长度问题需要手动转
        map.put("menuButtonId", String.valueOf(menuButtonId));
        map.put("parentId", menuButtonPO.getParentId());
        return map;
    }

    /**
     * 根据查询的树节点数组返回map
     * 
     * @Author LiLuBing
     * @Date 2021-01-14 10:27
     * @Param * @param menuButton
     * @param menuButtonId
     * @return {@link Map< String, Object>}
     **/
    private Map<String, Object> getMapByObjects(Map<String, Object> menuButton, Long menuButtonId) {
        /*p.menu_button_id, p.parent_id, p.name, p.type, p.menu_button_value , p.uri, p.icon, p.state,
                p.create_time, p.orders , rp.menu_button_id AS as_id*/
        menuButton.put("menuButtonId", menuButtonId.toString());
        menuButton.put("parentId", menuButton.get("parent_id").toString());
        menuButton.put("menuButtonValue", menuButton.get("menu_button_value"));
        menuButton.put("createTime", menuButton.get("create_time"));
        menuButton.remove("menu_button_id");
        menuButton.remove("parent_id");
        menuButton.remove("menu_button_value");
        menuButton.remove("create_time");

        if (null != menuButton.get("as_id")) {
            menuButton.put("checked", true);
        }
        return menuButton;
    }

    /**
     * 根据角色ID查询
     *
     * @Author LiLuBing
     * @Date 2021-01-14 10:27
     * @Param * @param parentList
     * @param btn_List
     * @param roleId
     * @return
     **/
    public void getChildrenByRoleId(List<Object> parentList, List<Map<String, Object>> btn_List, Long roleId) {
        // 当前父节点的所有子节点 返回集合
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        // 循环所有的子节点
        for (Map<String, Object> perm : btn_List) {
            // Object[] menuButton = (Object[]) obj;
            // p.menu_button_id,p.pid,p.name,p.type,p.menuButton_value,p.uri,p.icon,p.status,p.ctime,p.orders,rp.menu_button_id
            Long loopMenuButtonId = (Long)perm.get("menu_button_id");
            getMenuButtonTreeByParentIdIdExtractMethod(roleId, result, perm, loopMenuButtonId);
        }
        // 将当前节点数据添加到 传递的根对象中
        parentList.addAll(result);
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
    @Override
    public List<Map<String, Object>> findMenuButtonByUserId(Long userId, Long parentMenuId, Integer menuButtonType) {
        List<Map<String, Object>> result =
            menuButtonMapper.findMenuButtonByUserIdParentMenuIdMenuButtonType(userId, parentMenuId, menuButtonType);
        result = result.stream().map((map) -> {
            map.put("menu_button_id", String.valueOf(map.get("menu_button_id")));
            return map;
        }).collect(Collectors.toList());
        try {
            for (Map<String, Object> map : result) {
                findMenuButtonByUserIdChildren(map, menuButtonType, userId, result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据用户与上级菜单ID查询查询菜单 获取子节点方法
     * 
     * @Author LiLuBing
     * @Date 2021-01-14 10:29
     * @Param * @param parentMap
     * @param menuButtonType
     * @param userId
     * @param result
     * @return
     **/
    private void findMenuButtonByUserIdChildren(Map<String, Object> parentMap, Integer menuButtonType, Long userId,
        List<Map<String, Object>> result) {
        List<Map<String, Object>> list = menuButtonMapper.findMenuButtonByUserIdParentMenuIdMenuButtonType(userId,
            Long.parseLong(parentMap.get("menu_button_id").toString()), menuButtonType);
        if (list.isEmpty()) {
            return;
        }
        list = list.stream().map((map) -> {
            map.put("menu_button_id", String.valueOf(map.get("menu_button_id")));
            return map;
        }).collect(Collectors.toList());
        parentMap.put("children", list);
        for (Map<String, Object> map : list) {
            findMenuButtonByUserIdChildren(map, menuButtonType, userId, result);
        }
    }

    /**
     * 获取所有菜单按钮与角色信息
     * @Author LiLuBing
     * @Date 2021-01-21 09:37
     * @Param  * @param
     * @return {@link List<Map<String, String>>}
     **/
    @Override
    public List<Map<String, String>> getAllMenuButtonAndRole() {
        return menuButtonMapper.getAllMenuButtonAndRole();
    }
}
