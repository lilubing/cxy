package com.llb.cxy.domain.user.repository.po;

import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.llb.cxy.core.convert.LongToStringJsonSerializer;
import com.llb.cxy.domain.user.entity.valueobject.DeletedEnum;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.domain.user.repository.po
 * @Description: 角色
 * @ClassName: RolePO
 * @date 2021-01-13 上午8:39
 * @ProjectName cxy
 * @Version V1.0
 */
@Data
//@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table(name = "sys_role")
public class RolePO {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 父角色id
     */
    private Long parentId;

    /**
     * 创建用户id
     */
    private Long createUserId;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 修改用户id
     */
    private Long lastModifyUserId;

    /**
     * 修改时间
     */
    private Long lastModifyTime;

    /**
     * 0正常 1删除
     */
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private DeletedEnum deleted;


}

