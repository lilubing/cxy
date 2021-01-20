package com.llb.cxy.domain.user.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.llb.cxy.core.SystemContext;
import com.llb.cxy.core.page.Page;
import com.llb.cxy.core.utils.DateUtil;
import com.llb.cxy.domain.user.entity.Role;
import com.llb.cxy.domain.user.repository.facade.RoleMenuButtonRepository;
import com.llb.cxy.domain.user.repository.facade.RoleRepository;
import com.llb.cxy.domain.user.repository.facade.RoleUserRepository;
import com.llb.cxy.domain.user.repository.po.RolePO;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.domain.user.service
 * @Description: 角色服务类
 * @ClassName: RoleDomainService
 * @date 2021-01-13 上午10:23
 * @ProjectName cxy
 * @Version V1.0
 */
@Service
public class RoleDomainService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleFactory roleFactory;
    @Autowired
    private RoleMenuButtonRepository roleMenuButtonRepository;
    @Autowired
    private RoleUserRepository roleUserRepository;

    /**
     * 添加角色
     * 
     * @Author LiLuBing
     * @Date 2021-01-13 15:02
     * @Param * @param role
     * @return
     **/
    @Transactional(propagation = Propagation.REQUIRED, timeout = 3)
    public Integer saveRole(Role role) {
        RolePO rolePO = roleRepository.get(role.getId());
        if (null != rolePO) {
            rolePO.setName(role.getName());
            rolePO.setDescription(role.getDescription());
            return roleRepository.update(rolePO);
        }
        role.create();
        return roleRepository.save(roleFactory.createRolePO(role));
    }

    /**
     * 修改用户名称
     * 
     * @Author LiLuBing
     * @Date 2021-01-13 15:02
     * @Param * @param role
     * @return {@link Integer}
     **/
    @Transactional(propagation = Propagation.REQUIRED, timeout = 3)
    public Integer updateRoleById(Role role) {
        if (null == role.getId()) {
            return 0;
        }
        return roleRepository.updateRoleById(role.getId(), role.getName(), role.getDescription(), DateUtil.get10Date());
    }

    /**
     * 根据用户Id获取菜单按钮值
     * 
     * @Author LiLuBing
     * @Date 2021-01-13 13:33
     * @Param * @param userId
     * @return {@link java.util.List<java.lang.String>}
     **/
    @Transactional(readOnly = true, timeout = 3)
    public List<String> getMenuButtonsBtnValueByUserId(Long userId) {
        return roleRepository.getMenuButtonsBtnValueByUserId(userId);
    }

    /**
     *
     * @Author LiLuBing
     * @Date 2021-01-13 15:02
     * @Param * @param roleId
     * @param userId
     * @return
     **/
    @Transactional(propagation = Propagation.REQUIRED, timeout = 3)
    public void remove(Long roleId, Long userId) {
        RolePO rolePO = roleRepository.get(roleId);
        Role role = roleFactory.createRole(rolePO);
        // 状态改为删除
        role.delete(userId);
        roleRepository.update(roleFactory.createRolePO(role));
        // 删除角色与用户关联
        roleUserRepository.deleteByRoleId(roleId);
        // 删除角色与菜单关联
        roleMenuButtonRepository.deleteByRoleId(roleId);
    }

    /**
     *
     * @Author LiLuBing
     * @Date 2021-01-13 15:02
     * @Param * @param jsonRow
     * @return {@link Integer}
     **/
    @Transactional(propagation = Propagation.REQUIRED, timeout = 30)
    public Integer saveByJson(String jsonRow) {
        List<JSONObject> list = JSONObject.parseObject(jsonRow, List.class);
        Integer result = 0;
        for (JSONObject json : list) {
            Role role = json.toJavaObject(Role.class);
            Long roleID = role.getId();
            if (roleID == null) {
                role.create();
                return roleRepository.save(roleFactory.createRolePO(role));
            } else {
                RolePO rolePO = roleRepository.get(roleID);
                rolePO.setName(role.getName());
                rolePO.setDescription(role.getDescription());
                result += roleRepository.update(rolePO);
            }
        }
        return result;
    }

    /**
     * 根据条件分页查询
     * 
     * @Author LiLuBing
     * @Date 2021-01-13 15:02
     * @Param * @param conditions
     * @return {@link Page<Role>}
     **/
    @Transactional(readOnly = true, timeout = 3)
    public Page<Role> getByConditions(String conditions) {
        Map<String, Object> mapConditions = JSONObject.parseObject(conditions);
        if (null == mapConditions) {
            mapConditions = Maps.newHashMap();
            mapConditions.put("sort", "desc");
        }

        // 查询总条数
        Integer total = roleRepository.findTotalByConditions(mapConditions);
        // 查询角色
        List<Role> roles = Lists.newArrayList();
        if (total > 0) {
            roles = roleRepository.findByConditions(null, mapConditions).stream()
                .map(rolePO -> roleFactory.createRole(rolePO)).collect(Collectors.toList());
        }
        return new Page(SystemContext.getOffset(), total, roles);
    }

    /**
     * 根据角色ID查询角色
     * 
     * @Author LiLuBing
     * @Date 2021-01-16 08:07
     * @Param * @param roleId
     * @return {@link Role}
     **/
    public Role getRoleById(Long roleId) {
        return roleFactory.createRole(roleRepository.get(roleId));
    }

    /**
     * 查询所有角色
     * @Author LiLuBing
     * @Date 2021-01-17 20:00
     * @Param  * @param 
     * @return {@link java.util.List<com.llb.cxy.domain.user.entity.Role>}
     **/
    public List<Role> getAllRoles() {
        return roleRepository.getAll().stream().map(rolePO -> roleFactory.createRole(rolePO))
            .collect(Collectors.toList());
    }
}
