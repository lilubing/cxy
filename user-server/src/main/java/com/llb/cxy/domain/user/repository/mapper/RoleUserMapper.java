package com.llb.cxy.domain.user.repository.mapper;

import com.llb.cxy.domain.user.repository.po.RoleUserPO;
import com.llb.cxy.mybatis.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.domain.user.repository.mapper
 * @Description: 角色用户Mapper接口
 * @ClassName: RoleUserMapper
 * @date 2021-01-13 上午8:46
 * @ProjectName cxy
 * @Version V1.0
 */
@Mapper
public interface RoleUserMapper extends MyMapper<RoleUserPO, Long> {

    /**
     * 根据角色ID删除
     * @Author LiLuBing
     * @Date 2021-01-13 08:56
     * @Param  * @param roleId
     * @return
     **/
    Integer deleteByRoleId(Long roleId);

    /**
     * 根据用户ID与角色ID查询
     * @Author LiLuBing
     * @Date 2021-01-13 08:57
     * @Param  * @param userId
     * @param roleId
     * @return {@link java.util.List<java.lang.Long>}
     **/
    List<Long> selectByUserIdAndRoleId(Long userId, Long roleId);

    /**
     * 根据用户ID与角色ID集合删除
     * @Author LiLuBing
     * @Date 2021-01-13 08:57
     * @Param  * @param userId
     * @param listIds
     * @return {@link int}
     **/
    int deleteByUserIdAndRoleId(@Param("userId") Long userId, @Param("listIds") List<Long> listIds);

    /**
     * 根据用户ID查询所有角色
     * @Author LiLuBing
     * @Date 2021-01-17 19:44
     * @Param  * @param userId
     * @return {@link java.util.List<java.lang.String>}
     **/
    List<String> findRoleIdsByUserId(Long userId);
}
