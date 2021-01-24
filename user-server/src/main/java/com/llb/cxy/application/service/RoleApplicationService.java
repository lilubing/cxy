package com.llb.cxy.application.service;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.llb.cxy.core.model.ResultBody;
import com.llb.cxy.core.page.Page;
import com.llb.cxy.domain.user.entity.Role;
import com.llb.cxy.domain.user.service.RoleDomainService;
import com.llb.cxy.domain.user.service.RoleUserDomainService;
import com.llb.cxy.interfaces.assembler.RoleAssembler;
import com.llb.cxy.interfaces.dto.RoleDTO;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.application.service
 * @Description: 角色应用层
 * @ClassName: RoleApplicationService
 * @date 2021-01-13 下午3:08
 * @ProjectName cxy
 * @Version V1.0
 */
@Service
public class RoleApplicationService {

    @Autowired
    RoleDomainService roleDomainService;
    @Autowired
    RoleUserDomainService roleUserDomainService;

    /**
     * 添加角色
     * @Author LiLuBing
     * @Date 2021-01-13 15:02
     * @Param  * @param role
     * @return
     **/
    public Integer saveRole(RoleDTO roleDTO) throws ParseException {
        return roleDomainService.saveRole(RoleAssembler.toDO(roleDTO));
    }

    /**
     * 修改用户名称
     * @Author LiLuBing
     * @Date 2021-01-13 15:02
     * @Param  * @param role
     * @return {@link Integer}
     **/
    public Integer updateRoleById(Role role) {
        return roleDomainService.updateRoleById(role);
    }

    /**
     * 根据用户Id获取菜单按钮值
     * @Author LiLuBing
     * @Date 2021-01-13 13:33
     * @Param  * @param userId
     * @return {@link java.util.List<java.lang.String>}
     **/
    public List<String> getMenuButtonsBtnValueByUserId(Long userId) {
        return roleDomainService.getMenuButtonsBtnValueByUserId(userId);
    }

    /**
     *
     * @Author LiLuBing
     * @Date 2021-01-13 15:02
     * @Param  * @param roleId
     * @return
     **/
    public void remove(Long roleId) {
        roleDomainService.remove(roleId);
    }

    /**
     *
     * @Author LiLuBing
     * @Date 2021-01-13 15:02
     * @Param  * @param jsonRow
     * @return {@link Integer}
     **/
    public Integer saveByJson(String jsonRow) {
        return roleDomainService.saveByJson(jsonRow);
    }

    /**
     *
     * @Author LiLuBing
     * @Date 2021-01-13 15:02
     * @Param  * @param conditions
     * @return {@link ResultBody <  Page <  SysRole >>}
     **/
    public Page<RoleDTO> getByConditions(String conditions) {
        Page<Role> page = roleDomainService.getByConditions(conditions);
        return new Page(page.getPage(), page.getTotal(),
                page.getRows().stream().map(role -> RoleAssembler.toDTO(role)).collect(Collectors.toList()));
    }

    /**
     *
     * @Author LiLuBing
     * @Date 2021-01-13 15:02
     * @Param  * @param userId
     * @param roleIds
     * @return {@link Integer}
     **/
    public Integer saveRoleAndUserAss(Long userId, String roleIds) {
        return roleUserDomainService.saveRoleAndUserAss(userId, roleIds);
    }

    /**
     * 删除角色与用户关联
     * @Author LiLuBing
     * @Date 2021-01-13 15:02
     * @Param  * @param userId
     * @param roleIds
     * @return {@link Integer}
     **/
    public Integer deleteRoleAndUserAss(Long userId, String roleIds) {
        return roleUserDomainService.deleteRoleAndUserAss(userId, roleIds);
    }

    /**
     * 根据角色ID查询角色
     * @Author LiLuBing
     * @Date 2021-01-16 08:05
     * @Param  * @param roleId
     * @return {@link RoleDTO}
     **/
    public RoleDTO getRoleById(Long roleId) {
        return RoleAssembler.toDTO(roleDomainService.getRoleById(roleId));
    }
}
