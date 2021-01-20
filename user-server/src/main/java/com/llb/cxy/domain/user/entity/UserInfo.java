package com.llb.cxy.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.llb.cxy.core.SystemContext;
import com.llb.cxy.core.id.Identities;
import com.llb.cxy.core.utils.DateUtil;
import com.llb.cxy.domain.user.entity.valueobject.UserActiveStatusEnum;
import com.llb.cxy.domain.user.entity.valueobject.DeletedEnum;
import com.llb.cxy.domain.user.entity.valueobject.UserSexEnum;
import com.llb.cxy.domain.user.entity.valueobject.UserStatusEnum;
import com.llb.cxy.mybatis.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.domain.user.entity
 * @Description: 用户登陆实体
 * Entity：实体，存放聚合根、实体、值对象以及工厂模式相关代码。
 * 实体类采用充血模型，统一实体相关的业务逻辑都在实体类代码中实现。
 * 跨实体的业务逻辑代码在领域服务中实现。
 *
 * @ClassName: UserInfo
 * @date 2021-01-12 下午6:42
 * @ProjectName cxy
 * @Version V1.0
 */
@Data
public class UserInfo extends BaseEntity {
    /**
     * 在 DDD 中有这样一类对象，它们拥有唯一标识符，且标识符在历经各种状态变更后仍能保持一致。对这些对象而言，重要的不是其属性，而是其延续性和标识，对象的延续性和标识会跨越甚至超出软件的生命周期。我们把这样的对象称为实体。
     *
     * 实体的业务形态：领域模型中的实体是多个属性、操作或行为的载体。实体和值对象是组成领域模型的基础单元。
     *
     * 实体的代码形态：在代码模型中，实体的表现形式是实体类，这个类包含了实体的属性和方法，通过这些方法实现实体自身的业务逻辑。在 DDD 里，这些实体类通常采用充血模型，与这个实体相关的所有业务逻辑都在实体类的方法中实现，跨多个实体的领域逻辑则在领域服务中实现。
     *
     * 实体的运行形态：实体以 DO（领域对象）的形式存在，每个实体对象都有唯一的 ID。可以对一个实体对象进行多次修改，修改后的数据和原来的数据可能会大不相同。但是，由于它们拥有相同的 ID，它们依然是同一个实体。
     *
     * 实体的数据库形态：，DDD 是先构建领域模型，针对实际业务场景构建实体对象和行为，再将实体对象映射到数据持久化对象。一个实体可能对应 0 个、1 个或者多个数据库持久化对象。
     **/

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
     * @Date 2021-01-12 20:01
     * @Param  * @param
     * @return {@link com.llb.cxy.domain.user.entity.UserInfo}
     **/
    public UserInfo create(){
        this.id = Identities.getId();
        this.createUserId = SystemContext.getUserInfo().getLong("userId");
        this.createTime = DateUtil.get10Date();
        this.lastModifyUserId = this.createUserId;
        this.lastModifyTime = this.createTime;
        this.lastLoginTime = this.createTime;
        this.status = UserStatusEnum.ENABLE;
        this.activateStatus = UserActiveStatusEnum.ACTIVE;
        this.deleted = DeletedEnum.NORMAL;
        return this;
    }

    /**
     * 启用
     * @Author LiLuBing
     * @Date 2021-01-12 20:01
     * @Param  * @param
     * @return {@link com.llb.cxy.domain.user.entity.UserInfo}
     **/
    public UserInfo enable(){
        this.lastModifyTime = DateUtil.get10Date();
        this.status = UserStatusEnum.ENABLE;
        return this;
    }

    /**
     * 锁定
     * @Author LiLuBing
     * @Date 2021-01-12 20:01
     * @Param  * @param
     * @return {@link com.llb.cxy.domain.user.entity.UserInfo}
     **/
    public UserInfo lock(){
        this.lastModifyTime = DateUtil.get10Date();
        this.status = UserStatusEnum.LOCK;
        return this;
    }

    /**
     * 逾期
     * @Author LiLuBing
     * @Date 2021-01-12 20:01
     * @Param  * @param
     * @return {@link com.llb.cxy.domain.user.entity.UserInfo}
     **/
    public UserInfo overdue(){
        this.lastModifyTime = DateUtil.get10Date();
        this.status = UserStatusEnum.OVERDUE;
        return this;
    }

    /**
     * 激活
     * @Author LiLuBing
     * @Date 2021-01-12 20:15
     * @Param  * @param
     * @return {@link com.llb.cxy.domain.user.entity.UserInfo}
     **/
    public UserInfo active() {
        this.activateStatus = UserActiveStatusEnum.ACTIVE;
        this.lastModifyTime = DateUtil.get10Date();
        return this;
    }

    /**
     * 未激活
     * @Author LiLuBing
     * @Date 2021-01-12 20:15
     * @Param  * @param
     * @return {@link com.llb.cxy.domain.user.entity.UserInfo}
     **/
    public UserInfo unactivated() {
        this.activateStatus = UserActiveStatusEnum.UNACTIVATED;
        this.lastModifyTime = DateUtil.get10Date();
        return this;
    }

    /**
     * 修改味删除状态
     * @Author LiLuBing
     * @Date 2021-01-12 20:11
     * @Param  * @param
     * @return {@link com.llb.cxy.domain.user.entity.UserInfo}
     **/
    public UserInfo delete(){
        this.lastModifyUserId = SystemContext.getUserInfo().getLong("userId");
        this.lastModifyTime = DateUtil.get10Date();
        this.deleted = DeletedEnum.DELETE;
        return this;
    }
}
