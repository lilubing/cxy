package com.llb.cxy.domain.user.entity;

import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.llb.cxy.core.id.Identities;
import com.llb.cxy.core.utils.DateUtil;
import com.llb.cxy.domain.user.entity.valueobject.DeletedEnum;
import com.llb.cxy.mybatis.entity.BaseEntity;

import lombok.Data;

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
public class Role extends BaseEntity {

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

    /**
     * 新建
     * @Author LiLuBing
     * @Date 2021-01-13 13:59
     * @Param  * @param userId
     * @return {@link com.llb.cxy.domain.user.entity.Role}
     **/
    public Role create(){
        this.setId(Identities.getId());
        this.deleted = DeletedEnum.NORMAL;
        this.createTime = DateUtil.get10Date();
        this.lastModifyUserId = this.createUserId;
        this.lastModifyTime = createTime;
        return this;
    }

    /**
     * 修改味删除状态
     * @Author LiLuBing
     * @Date 2021-01-12 20:11
     * @Param  * @param
     * @return {@link com.llb.cxy.domain.user.entity.Role}
     **/
    public Role delete(Long userId){
        this.lastModifyUserId = userId;
        this.lastModifyTime = DateUtil.get10Date();
        this.deleted = DeletedEnum.DELETE;
        return this;
    }

}
