package com.llb.cxy.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.llb.cxy.core.convert.LongToStringJsonSerializer;
import com.llb.cxy.domain.user.entity.valueobject.DeletedEnum;
import com.llb.cxy.domain.user.entity.valueobject.UserActiveStatusEnum;
import com.llb.cxy.domain.user.entity.valueobject.UserSexEnum;
import com.llb.cxy.domain.user.entity.valueobject.UserStatusEnum;
import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.interfaces.dto
 * @Description: 【用户】数据传输的载体，内部不存在任何业务逻辑，可以通过DTO把内部的领域对象与外界隔离。
 * @ClassName: UserDTO
 * @date 2021-01-12 下午10:00
 * @ProjectName cxy
 * @Version V1.0
 */
@Data
public class UserInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    @JsonSerialize(using = LongToStringJsonSerializer.class)
    private Long id;

    /**
     * 用户登录名
     */
    private String userName;

    /**
     * 真实名称
     */
    private String realName;

    /**
     * 机构id
     */
    private Long orgId;

    /**
     * 机构名称
     */
    private String orgName;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 个人电话
     */
    private String mobilePhone;

    /**
     * 办公电话
     */
    private String officePhone;

    /**
     * qq
     */
    private Long qq;

    /**
     * 微信号
     */
    private String weChat;

    /**
     * 性别0：男,1：女
     */
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private UserSexEnum sex;

    /**
     * 家庭地址
     */
    private String homeAddress;

    /**
     * 办公地址
     */
    private String officeAddress;

    /**
     * 默认地址:0办公地址，1家庭地址
     */
    private Integer defaultAddress;

    /**
     * 姓名拼音
     */
    private String pyFull;

    /**
     * 名:首字母,字:首字母
     */
    private String pyShort;

    /**
     * 职位
     */
    private String position;

    /**
     * 职工号
     */
    private String jobNumber;

    /**
     * 国籍
     */
    private String nationality;

    /**
     * 出生年月日
     */
    private Date birthDate;

    /**
     * 最后登录时间
     */
    private Long lastLoginTime;

    /**
     * 用户状态：0正常，1锁定，2逾期
     */
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private UserStatusEnum status;

    /**
     * 激活状态:0未激活 1激活
     */
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private UserActiveStatusEnum activateStatus;

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

    /**
     * 角色数组
     **/
    private String[] rolesList;
}
