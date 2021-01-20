package com.llb.cxy.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.llb.cxy.core.convert.LongToStringJsonSerializer;
import com.llb.cxy.domain.user.entity.valueobject.DeletedEnum;
import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.interfaces.dto
 * @Description: 【角色】数据传输的载体，内部不存在任何业务逻辑，可以通过DTO把内部的领域对象与外界隔离。
 * @ClassName: RoleDTO
 * @date 2021-01-14 上午8:59
 * @ProjectName cxy
 * @Version V1.0
 */
@Data
public class RoleDTO  implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    @JsonSerialize(using = LongToStringJsonSerializer.class)
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
    @JsonSerialize(using = LongToStringJsonSerializer.class)
    private Long parentId;

    /**
     * 创建用户id
     */
    @JsonSerialize(using = LongToStringJsonSerializer.class)
    private Long createUserId;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 修改用户id
     */
    @JsonSerialize(using = LongToStringJsonSerializer.class)
    private Long lastModifyUserId;

    /**
     * 修改时间
     */
    private Long lastModifyTime;

    /**
     * 0正常 1删除
     * @JsonFormat(shape = JsonFormat.Shape.NUMBER) Json格式化时返回数字
     */
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private DeletedEnum deleted;
}
