package com.llb.cxy.domain.user.repository.persistence;

import com.llb.cxy.core.id.Identities;
import com.llb.cxy.core.utils.MyStringUtils;
import com.llb.cxy.domain.user.entity.UserInfo;
import com.llb.cxy.domain.user.repository.facade.RoleUserRepository;
import com.llb.cxy.domain.user.repository.mapper.RoleUserMapper;
import com.llb.cxy.domain.user.repository.po.RoleUserPO;
import com.llb.cxy.mybatis.service.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.domain.user.repository.persistence
 * @Description: 角色用户
 * @ClassName: RoleUserRepositoryImpl
 * @date 2021-01-13 下午1:45
 * @ProjectName cxy
 * @Version V1.0
 */
@Service
public class RoleUserRepositoryImpl extends GenericManagerImpl<RoleUserPO, Long> implements RoleUserRepository {

    private RoleUserMapper roleUserMapper;

    @Autowired
    public RoleUserRepositoryImpl(RoleUserMapper roleUserMapper) {
        super(roleUserMapper);
        this.roleUserMapper = roleUserMapper;
    }

    /**
     * 根据角色ID删除
     * 
     * @Author LiLuBing
     * @Date 2021-01-13 14:39
     * @Param * @param roleId
     * @return
     **/
    @Override
    public Integer deleteByRoleId(Long roleId) {
        return roleUserMapper.deleteByRoleId(roleId);
    }

    /**
     * 根据用户与角色ID查询
     * 
     * @Author LiLuBing
     * @Date 2021-01-13 14:38
     * @Param * @param userId
     * @param roleId
     * @return {@link List< Long>}
     **/
    @Override
    public List<Long> selectByUserIdAndRoleId(Long userId, Long roleId) {
        return roleUserMapper.selectByUserIdAndRoleId(userId, roleId);
    }

    /**
     * 根据用户与角色ID集合删除
     * 
     * @Author LiLuBing
     * @Date 2021-01-13 14:38
     * @Param * @param userId
     * @param listIds
     * @return {@link int}
     **/
    @Override
    public Integer deleteByUserIdAndRoleId(Long userId, List<Long> listIds) {
        return roleUserMapper.deleteByUserIdAndRoleId(userId, listIds);
    }

    /**
     * 根据用户ID查询所有角色
     * 
     * @Author LiLuBing
     * @Date 2021-01-17 19:42
     * @Param * @param userId
     * @return {@link java.util.List<java.lang.String>}
     **/
    @Override
    public List<String> findRoleIdsByUserId(Long userId) {
        return roleUserMapper.findRoleIdsByUserId(userId);
    }

    /**
     * 存储角色用户关联表
     * 
     * @Author LiLuBing
     * @Date 2021-01-18 07:48
     * @Param * @param rolesList
     * @param userId
     * @return
     **/
    @Override
    public Integer saveRoleUserAss(String[] rolesList, Long userId) {
        List<String> oldRoles = roleUserMapper.findRoleIdsByUserId(userId);
        Integer result = 0;
        for (String tmpRoleId : rolesList) {
            if (MyStringUtils.isBlank(tmpRoleId)) {
                continue;
            }
            // 删除本次添加的
            oldRoles.remove(tmpRoleId);
            Long roleId = Long.valueOf(tmpRoleId);
            if (roleUserMapper.selectByUserIdAndRoleId(userId, roleId).isEmpty()) {
                RoleUserPO roleUserPO = new RoleUserPO();
                roleUserPO.setRuId(Identities.getId());
                roleUserPO.setRoleId(roleId);
                roleUserPO.setUserId(userId);
                result += roleUserMapper.insert(roleUserPO);
            }
        }
        //判断是否没有带删除的
        if(oldRoles.isEmpty()) {
            return result;
        }
        // 删除本次未提交的
        roleUserMapper.deleteByUserIdAndRoleId(userId,
            oldRoles.stream().map(roleId -> Long.valueOf(roleId)).collect(Collectors.toList()));
        return result;
    }
}
