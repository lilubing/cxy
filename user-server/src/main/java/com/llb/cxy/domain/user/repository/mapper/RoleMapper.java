package com.llb.cxy.domain.user.repository.mapper;

import com.llb.cxy.domain.user.repository.po.RolePO;
import com.llb.cxy.mybatis.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.domain.user.repository.mapper
 * @Description: 角色Mapper接口
 * @ClassName: RoleMapper
 * @date 2021-01-13 上午8:43
 * @ProjectName cxy
 * @Version V1.0
 */
@Mapper
public interface RoleMapper extends MyMapper<RolePO, Long> {

    /**
     * 分页根据菜单按钮查询
     * @Author LiLuBing
     * @Date 2021-01-13 09:00
     * @Param  * @param mapPageInfo
     * @param mapConditions
     * @return {@link List< RolePO>}
     **/
    List<RolePO> findByConditions(@Param("page") Map<String, Object> mapPageInfo,
                                   @Param("params") Map<String, Object> mapConditions);

    /**
     * 分页根据条件查询总数
     * @Author LiLuBing
     * @Date 2021-01-13 09:00
     * @Param  * @param mapConditions
     * @return {@link int}
     **/
    Integer findTotalByConditions(@Param("params") Map<String, Object> mapConditions);

    /**
     * 根据用户ID查询角色
     * @Author LiLuBing
     * @Date 2021-01-13 09:00
     * @Param  * @param userId
     * @return {@link java.util.List<com.llb.cxy.domain.user.repository.po.RolePO>}
     **/
    List<RolePO> findRolesByUserId(Long userId);

    /**
     * 根据角色ID修改角色信息
     * @Author LiLuBing
     * @Date 2021-01-13 15:05
     * @Param  * @param roleId
     * @param name
     * @param description
     * @param lastModifyTime
     * @return {@link Integer}
     **/
    Integer updateRoleName(Long roleId, String name, String description, Long lastModifyTime);

    /**
     * 根据用户查询菜单
     * @Author LiLuBing
     * @Date 2021-01-13 08:59
     * @Param  * @param userId
     * @return {@link java.util.List<java.lang.String>}
     **/
    List<String> selectBtnValuesByUserId(Long userId);

}