package com.llb.cxy.domain.user.repository.po;

import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.llb.cxy.domain.user.entity.valueobject.DeletedEnum;
import com.llb.cxy.domain.user.entity.valueobject.UserStatusEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.domain.user.repository.po
 * @Description: 用户登陆表
 * @ClassName: UserPo
 * @date 2021-01-12 下午8:03
 * @ProjectName cxy
 * @Version V1.0
 */
@Data
//@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table(name = "sys_user")
public class UserPO {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 用户登录名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 微信的openid
     */
    private String otherId;

    /**
     * 用户状态：0正常，1锁定，2逾期
     */
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private UserStatusEnum status;

    /**
     * 0正常 1删除
     */
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private DeletedEnum deleted;


}
