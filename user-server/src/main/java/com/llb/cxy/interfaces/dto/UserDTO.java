package com.llb.cxy.interfaces.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.llb.cxy.core.convert.LongToStringJsonSerializer;
import com.llb.cxy.domain.user.entity.valueobject.DeletedEnum;
import com.llb.cxy.domain.user.entity.valueobject.UserActiveStatusEnum;
import com.llb.cxy.domain.user.entity.valueobject.UserSexEnum;
import com.llb.cxy.domain.user.entity.valueobject.UserStatusEnum;

import lombok.Data;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.interfaces.dto
 * @Description: 【用户登陆】数据传输的载体，内部不存在任何业务逻辑，可以通过DTO把内部的领域对象与外界隔离。
 * @ClassName: UserDTO
 * @date 2021-01-12 下午10:00
 * @ProjectName cxy
 * @Version V1.0
 */
@Data
public class UserDTO implements Serializable {
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
