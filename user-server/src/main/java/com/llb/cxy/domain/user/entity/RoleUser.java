package com.llb.cxy.domain.user.entity;

import com.llb.cxy.mybatis.entity.BaseEntity;

import javax.persistence.Id;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.domain.user.entity
 * @Description: 角色用户域对象
 * @ClassName: RoleUser
 * @date 2021-01-13 下午2:49
 * @ProjectName cxy
 * @Version V1.0
 */
public class RoleUser extends BaseEntity {
    /**
     * 角色用户表id
     */
    @Id
    private Long ruId;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 用户id
     */
    private Long userId;

    public RoleUser create(Long ruId, Long roleId, Long userId) {
        this.ruId = ruId;
        this.roleId = roleId;
        this.userId = userId;
        return this;
    }
}
