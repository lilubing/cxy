package com.llb.cxy.domain.myBatis.entity.valueobject.enumConvert;

import com.llb.cxy.domain.myBatis.entity.valueobject.BaseCodeEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.mybatis.util
 * @Description: 定义需要处理的类
 * @ClassName: CodeEnumTypeHandler
 * @date 2021-01-14 下午3:26
 * @ProjectName cxy
 * @Version V1.0
 */
@MappedTypes({BaseCodeEnum.class})
public class CodeEnumTypeHandler<E extends Enum<?> & BaseCodeEnum> extends BaseTypeHandler<BaseCodeEnum> {

    private Class<E> type;

    public CodeEnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null.");
        }
        this.type = type;
    }

    //用于定义设置参数时，该如何把Java类型的参数转换为对应的数据库类型
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, BaseCodeEnum parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    //用于定义通过字段名称获取字段数据时，如何把数据库类型转换为对应的Java类型
    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);
        return rs.wasNull() ? null : codeOf(code);
    }

    //用于定义通过字段索引获取字段数据时，如何把数据库类型转换为对应的Java类型
    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int code = rs.getInt(columnIndex);
        return rs.wasNull() ? null : codeOf(code);
    }

    //用定义调用存储过程后，如何把数据库类型转换为对应的Java类型
    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        return cs.wasNull() ? null : codeOf(code);
    }

    private E codeOf(int code){
        try {
            return CodeEnumUtil.codeOf(type, code);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Cannot convert " + code + " to " + type.getSimpleName() + " by code value.", ex);
        }
    }
}