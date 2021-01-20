package com.llb.cxy.domain.user.repository.persistence;

import com.llb.cxy.domain.user.repository.facade.RoleMenuButtonRepository;
import com.llb.cxy.domain.user.repository.mapper.RoleMenuButtonMapper;
import com.llb.cxy.domain.user.repository.po.RoleMenuButtonPO;
import com.llb.cxy.mybatis.service.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.domain.user.repository.persistence
 * @Description: 角色菜单按钮
 * @ClassName: RoleMenuButtonRepositoryImpl
 * @date 2021-01-13 下午1:45
 * @ProjectName cxy
 * @Version V1.0
 */
@Service
public class RoleMenuButtonRepositoryImpl extends GenericManagerImpl<RoleMenuButtonPO, Long>
    implements RoleMenuButtonRepository {

    private RoleMenuButtonMapper roleMenuButtonMapper;

    @Autowired
    public RoleMenuButtonRepositoryImpl(RoleMenuButtonMapper roleMenuButtonMapper) {
        super(roleMenuButtonMapper);
        this.roleMenuButtonMapper = roleMenuButtonMapper;
    }

    /**
     * 根据菜单按钮ID删除
     * @Author LiLuBing
     * @Date 2021-01-13 14:37
     * @Param  * @param menuButtonId
     * @return {@link int}
     **/
    @Override
    public Integer deleteByMenuButtonId(Long menuButtonId) {
        return roleMenuButtonMapper.deleteByMenuButtonId(menuButtonId);
    }

    /**
     * 根据角色ID删除
     * @Author LiLuBing
     * @Date 2021-01-13 14:37
     * @Param  * @param roleId
     * @return
     **/
    @Override
    public Integer deleteByRoleId(Long roleId) {
        return roleMenuButtonMapper.deleteByRoleId(roleId);
    }

    /**
     * 根据角色ID菜单按钮菜单ID删除角色菜单按钮
     * @Author LiLuBing
     * @Date 2021-01-14 08:40
     * @Param  * @param roleId
     * @param menuButtonId
     * @return
     **/
    @Override
    public Integer deleteRoleMenuButtonByRoleIdAndMenuButtonId(Long roleId, Long menuButtonId) {
        return roleMenuButtonMapper.deleteRoleMenuButtonByRoleIdAndMenuButtonId(roleId, menuButtonId);
    }

    /**
     * 根据菜单按钮Id与角色id查询
     * @Author LiLuBing
     * @Date 2021-01-17 13:28
     * @Param  * @param menuButtonId
     * @param roleId
     * @return {@link List< RoleMenuButtonPO>}
     **/
    @Override
    public List<RoleMenuButtonPO> findByMenuButtonIdAndRoleId(Long menuButtonId, Long roleId) {
        return roleMenuButtonMapper.findByMenuButtonIdAndRoleId(menuButtonId, roleId);
    }
}
