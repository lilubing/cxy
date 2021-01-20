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
 * @Description: 角色菜单按钮表
 * @ClassName: RoleMenuButtonPO
 * @date 2021-01-13 上午8:42
 * @ProjectName cxy
 * @Version V1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table(name = "sys_role_menu_button")
public class RoleMenuButtonPO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 角色菜单按钮表id
     */
    @Id
    private Long rpId;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 菜单按钮id
     */
    private Long menuButtonId;


}
