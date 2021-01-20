package com.llb.cxy.domain.user.repository.facade;

import com.llb.cxy.domain.user.entity.UserInfo;
import com.llb.cxy.domain.user.repository.po.RoleUserPO;
import com.llb.cxy.mybatis.service.GenericManager;

import java.util.List;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.domain.user.repository.facade
 * @Description: 角色用户
 * @ClassName: RoleUserRepository
 * @date 2021-01-13 下午1:43
 * @ProjectName cxy
 * @Version V1.0
 */
public interface RoleUserRepository extends GenericManager<RoleUserPO, Long> {
    /**
     * 根据角色ID删除
     * @Author LiLuBing
     * @Date 2021-01-13 14:39
     * @Param  * @param roleId
     * @return 
     **/
    Integer deleteByRoleId(Long roleId);

    /**
     * 根据用户与角色ID删除
     * @Author LiLuBing
     * @Date 2021-01-13 14:38
     * @Param  * @param userId
     * @param roleId
     * @return {@link List< Long>}
     **/
    List<Long> selectByUserIdAndRoleId(Long userId, Long roleId);

    /**
     * 根据用户与角色ID集合删除
     * @Author LiLuBing
     * @Date 2021-01-13 14:38
     * @Param  * @param userId
     * @param listIds
     * @return {@link int}
     **/
    Integer deleteByUserIdAndRoleId(Long userId, List<Long> listIds);

    /**
     * 根据用户ID查询所有角色
     * @Author LiLuBing
     * @Date 2021-01-17 19:52
     * @Param  * @param userId
     * @return {@link java.util.List<java.lang.String>}
     **/
    List<String> findRoleIdsByUserId(Long userId);

    /**
     * 存储角色用户关联表
     * @Author LiLuBing
     * @Date 2021-01-18 07:48
     * @Param  * @param rolesList
     * @param userId
     * @return
     **/
    Integer saveRoleUserAss(String[] rolesList, Long userId);
}
