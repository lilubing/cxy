package com.llb.cxy.domain.user.repository.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import com.llb.cxy.domain.user.repository.facade.RoleRepository;
import com.llb.cxy.domain.user.repository.mapper.RoleMapper;
import com.llb.cxy.domain.user.repository.po.RolePO;
import com.llb.cxy.mybatis.service.GenericManagerImpl;
import org.springframework.stereotype.Service;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.domain.user.repository.persistence
 * @Description: 角色存储类
 * @ClassName: RoleRepositoryImpl
 * @date 2021-01-13 上午9:14
 * @ProjectName cxy
 * @Version V1.0
 */
@Service
public class RoleRepositoryImpl extends GenericManagerImpl<RolePO, Long> implements RoleRepository {

    private RoleMapper roleMapper;

    @Autowired
    public RoleRepositoryImpl(RoleMapper roleMapper) {
        super(roleMapper);
        this.roleMapper = roleMapper;
    }

    /**
     * 根据角色ID修改角色信息
     * @Author LiLuBing
     * @Date 2021-01-14 09:59
     * @Param  * @param roleId
     * @param name
     * @param description
     * @param lastModifyTime
     * @return {@link Integer}
     **/
    @Override
    public Integer updateRoleById(Long roleId, String name, String description, Long lastModifyTime) {
        return roleMapper.updateRoleName(roleId, name, description, lastModifyTime);
    }

    /**
     * 分页根据菜单按钮查询
     * @Author LiLuBing
     * @Date 2021-01-13 09:00
     * @Param  * @param mapPageInfo
     * @param mapConditions
     * @return {@link List < RolePO>}
     **/
    @Override
    public List<RolePO> findByConditions(@Param("page") Map<String, Object> mapPageInfo,
                                         @Param("params") Map<String, Object> mapConditions) {
        return roleMapper.findByConditions(getPageInfoMap(), mapConditions);
    }

    /**
     * 分页根据条件查询总数
     * @Author LiLuBing
     * @Date 2021-01-13 09:00
     * @Param  * @param mapConditions
     * @return {@link int}
     **/
    @Override
    public Integer findTotalByConditions(Map<String, Object> mapConditions) {
        return roleMapper.findTotalByConditions(mapConditions);
    }

    /**
     * 根据用户Id所有角色
     * @Author LiLuBing
     * @Date 2021-01-13 09:35
     * @Param  * @param userId
     * @return {@link java.util.List<com.llb.cxy.domain.user.repository.po.RolePO>}
     **/
    @Override
    public List<RolePO> findRolesByUserId(Long userId) {
        return roleMapper.findRolesByUserId(userId);
    }

    /**
     * 根据用户Id获取菜单按钮值
     * @Author LiLuBing
     * @Date 2021-01-13 09:36
     * @Param  * @param userId
     * @return {@link java.util.List<java.lang.String>}
     **/
    @Override
    public List<String> getMenuButtonsBtnValueByUserId(Long userId) {
        return roleMapper.selectBtnValuesByUserId(userId);
    }
}
