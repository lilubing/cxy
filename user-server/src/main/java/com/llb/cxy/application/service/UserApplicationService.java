package com.llb.cxy.application.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.Maps;
import com.llb.cxy.domain.user.entity.Role;
import com.llb.cxy.domain.user.repository.po.RolePO;
import com.llb.cxy.domain.user.service.RoleDomainService;
import com.llb.cxy.domain.user.service.RoleUserDomainService;
import com.llb.cxy.interfaces.assembler.RoleAssembler;
import com.llb.cxy.interfaces.dto.RoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.llb.cxy.core.page.Page;
import com.llb.cxy.domain.user.entity.UserInfo;
import com.llb.cxy.domain.user.service.UserInfoDomainService;
import com.llb.cxy.domain.user.service.UserInfoFactory;
import com.llb.cxy.interfaces.assembler.UserInfoAssembler;
import com.llb.cxy.interfaces.dto.UserInfoDTO;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.application.service
 * @Description: 用户应用层
 * @ClassName: UserApplicationService
 * @date 2021-01-12 下午9:52
 * @ProjectName cxy
 * @Version V1.0
 */
@Service
public class UserApplicationService {

    @Autowired
    UserInfoDomainService userInfoDomainService;
    @Autowired
    UserInfoFactory userInfoFactory;
    @Autowired
    RoleDomainService roleDomainService;
    @Autowired
    RoleUserDomainService roleUserDomainService;

    /**
     * 创建用户信息
     * 
     * @Author LiLuBing
     * @Date 2021-01-12 22:16
     * @Param * @param userInfo
     * @return
     **/
    public void create(UserInfoDTO userInfoDTO) throws ParseException {
        userInfoDomainService.create(userInfoDTO);
    }

    /**
     * 修改用户信息
     * 
     * @Author LiLuBing
     * @Date 2021-01-12 22:16
     * @Param * @param userInfoDTO
     * @return
     **/
    public Integer update(UserInfoDTO userInfoDTO) throws ParseException {
        return userInfoDomainService.update(userInfoDTO);
    }

    /**
     * 根据用户ID删除用户信息
     * 
     * @Author LiLuBing
     * @Date 2021-01-12 22:16
     * @Param * @param userId
     * @return
     **/
    public void deleteById(Long userId) {
        userInfoDomainService.deleteById(userId);
    }

    /**
     * 根据用户ID查询用户信息
     * 
     * @Author LiLuBing
     * @Date 2021-01-12 22:16
     * @Param * @param userId
     * @return {@link UserInfo}
     **/
    public Map<String, Object> findById(Long userId) {
        Map<String, Object> result = Maps.newHashMapWithExpectedSize(3);
        UserInfoDTO userInfo = UserInfoAssembler.toDTO(userInfoDomainService.findById(userId));
        userInfo.setRolesList(roleUserDomainService.findRoleIdsByUserId(userId).toArray(new String[0]));
        result.put("user", userInfo);
        result.put("roles", roleDomainService.getAllRoles().stream().map(role -> RoleAssembler.toDTO(role)).collect(Collectors.toList()));
        return result;
    }

    /**
     * 根据用登陆名查询用户信息
     * 
     * @Author LiLuBing
     * @Date 2021-01-14 13:30
     * @Param * @param user_name
     * @return {@link com.llb.cxy.interfaces.dto.UserInfoDTO}
     **/
    public UserInfoDTO findUserByLoginName(String userName) {
        return UserInfoAssembler.toDTO(userInfoDomainService.findUserByLoginName(userName));
    }

    /**
     * 根据条件分页查询
     * 
     * @Author LiLuBing
     * @Date 2021-01-17 15:40
     * @Param * @param conditions
     * @return {@link com.llb.cxy.core.page.Page<com.llb.cxy.interfaces.dto.UserInfoDTO>}
     **/
    @Transactional(readOnly = true, timeout = 3)
    public Page<UserInfoDTO> findByConditions(String conditions) {
        Page<UserInfo> page = userInfoDomainService.findByConditions(conditions);
        return new Page(page.getPage(), page.getTotal(),
            page.getRows().stream().map(userInfo -> UserInfoAssembler.toDTO(userInfo)).collect(Collectors.toList()));
    }
}
