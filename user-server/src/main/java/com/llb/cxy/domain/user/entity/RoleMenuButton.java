package com.llb.cxy.domain.user.entity;

import lombok.Data;

import javax.persistence.Id;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.domain.user.entity
 * @Description: 角色菜单按钮域对象
 * @ClassName: RoleMenuButton
 * @date 2021-01-14 上午8:30
 * @ProjectName cxy
 * @Version V1.0
 */
@Data
public class RoleMenuButton {
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

    public RoleMenuButton create(Long rpId, Long roleId, Long menuButtonId) {
        this.rpId = rpId;
        this.roleId = roleId;
        this.menuButtonId = menuButtonId;
        return this;
    }
}
