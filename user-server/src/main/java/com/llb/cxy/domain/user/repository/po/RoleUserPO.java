package com.llb.cxy.domain.user.repository.po;

import com.llb.cxy.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.domain.user.repository.po
 * @Description: 角色用户表
 * @ClassName: RoleUserPO
 * @date 2021-01-13 上午8:49
 * @ProjectName cxy
 * @Version V1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table(name = "sys_role_user")
public class RoleUserPO extends BaseEntity {

    private static final long serialVersionUID = 1L;

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


}
