package com.llb.cxy.interfaces.dto;

import java.io.Serializable;

import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.llb.cxy.core.convert.LongToStringJsonSerializer;
import com.llb.cxy.domain.user.entity.valueobject.MenuButtonStateEnum;
import com.llb.cxy.domain.user.entity.valueobject.MenuButtonTypeEnum;

import lombok.Data;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.interfaces.dto
 * @Description: 【角色菜单按钮】数据传输的载体，内部不存在任何业务逻辑，可以通过DTO把内部的领域对象与外界隔离。
 * @ClassName: MenuButtonDTO
 * @date 2021-01-14 上午8:59
 * @ProjectName cxy
 * @Version V1.0
 */
@Data
public class MenuButtonDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 菜单按钮id
     */
    @Id
    @JsonSerialize(using = LongToStringJsonSerializer.class)
    private Long menuButtonId;

    /**
     * 上级菜单按钮id
     */
    @JsonSerialize(using = LongToStringJsonSerializer.class)
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
     * @JsonFormat(shape = JsonFormat.Shape.NUMBER) Json格式化时返回数字
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
     * @JsonFormat(shape = JsonFormat.Shape.NUMBER) Json格式化时返回数字
     */
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private MenuButtonStateEnum state;

    /**
     * 创建时间
     */
    private Long createTime;

}
