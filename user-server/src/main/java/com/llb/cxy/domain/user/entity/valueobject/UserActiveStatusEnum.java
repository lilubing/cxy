package com.llb.cxy.domain.user.entity.valueobject;

import com.llb.cxy.domain.myBatis.entity.valueobject.BaseCodeEnum;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.domain.user.entity.valueobject
 * @Description: 用户激活状态值对象
 * @ClassName: UserStatus
 * @date 2021-01-12 下午7:15
 * @ProjectName cxy
 * @Version V1.0
 */
public enum UserActiveStatusEnum implements BaseCodeEnum {
    /**
     * 在 DDD 中用来描述领域的特定方面，并且是一个没有标识符的对象，叫作值对象。值对象描述了领域中的一件东西，这个东西是不可变的，它将不同的相关属性组合成了一个概念整体。
     *
     * 值对象的业务形态：实体是看得到、摸得着的实实在在的业务对象，实体具有业务属性、业务行为和业务逻辑。而值对象只是若干个属性的集合，只有数据初始化操作和有限的不涉及修改数据的行为，基本不包含业务逻辑。
     *
     * 值对象的代码形态：如果值对象是单一属性，则直接定义为实体类的属性；如果值对象是属性集合，则把它设计为 Class 类，Class 将具有整体概念的多个属性归集到属性集合，这样的值对象没有 ID，会被实体整体引用。
     *
     * 值对象的运行形态：值对象嵌入到实体的话，有这样两种不同的数据格式，也可以说是两种方式，分别是属性嵌入的方式和序列化大对象的方式。
     *
     * 值对象的数据库形态：在领域建模时，我们可以将部分对象设计为值对象，保留对象的业务涵义，同时又减少了实体的数量；在数据建模时，我们可以将值对象嵌入实体，减少实体表的数量，简化数据库设计。
     **/
    // 激活状态:0未激活 1激活
    UNACTIVATED(0),
    ACTIVE(1);

    /**
     * 激活状态:0未激活 1激活
     **/
    // private Integer activateStatus;
    private Integer value;

    UserActiveStatusEnum(Integer value) {
        this.value = value;
    }

    /**
     * 改造枚举, 需要转换的枚举类实现BaseCodeEnum接口
     * @Author LiLuBing
     * @Date 2021-01-14 15:47
     * @Param  * @param
     * @return {@link java.lang.Integer}
     **/
    @Override
    public Integer getCode() {
        return value;
    }
}
