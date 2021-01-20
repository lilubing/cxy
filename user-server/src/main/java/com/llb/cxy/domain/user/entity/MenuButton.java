package com.llb.cxy.domain.user.entity;

import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.llb.cxy.core.id.Identities;
import com.llb.cxy.core.utils.DateUtil;
import com.llb.cxy.domain.user.entity.valueobject.MenuButtonStateEnum;
import com.llb.cxy.domain.user.entity.valueobject.MenuButtonTypeEnum;
import com.llb.cxy.mybatis.entity.BaseEntity;

import lombok.Data;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.domain.user.repository.po
 * @Description: 菜单按钮菜单按钮
 * @ClassName: MenuButtonPO
 * @date 2021-01-13 上午8:40
 * @ProjectName cxy
 * @Version V1.0
 */
@Data
public class MenuButton extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单按钮id
     */
    @Id
    private Long menuButtonId;

    /**
     * 上级菜单按钮id
     */
    private Long parentId;

    /**
     * 按钮id
     */
    private String btnId;

    /**
     * 菜单按钮名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer orders;

    /**
     * 类型(0.菜单1.按钮)
     */
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private MenuButtonTypeEnum type;

    /**
     * 菜单按钮值
     */
    private String menuButtonValue;

    /**
     * 路径
     */
    private String uri;

    /**
     * 图标
     */
    private String icon;

    /**
     * 状态(0:禁用,1:正常) true、false和1、0转化原理
     */
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private MenuButtonStateEnum state;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 新建
     * @Author LiLuBing
     * @Date 2021-01-12 20:01
     * @Param  * @param
     * @return {@link MenuButton}
     **/
    public MenuButton create(){
        this.menuButtonId = Identities.getId();
        this.createTime = DateUtil.get10Date();
        return this;
    }

    /**
     * 修改味删除状态
     * @Author LiLuBing
     * @Date 2021-01-12 20:11
     * @Param  * @param
     * @return {@link MenuButton}
     **/
    public MenuButton delete(){
        this.state = MenuButtonStateEnum.DISABLE;
        return this;
    }
}
