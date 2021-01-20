package com.llb.cxy.domain.myBatis.entity.valueobject.enumConvert;

import com.llb.cxy.domain.myBatis.entity.valueobject.BaseCodeEnum;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.mybatis.util
 * @Description: Enum的转换工具类
 * @ClassName: CodeEnumUtil
 * @date 2021-01-14 下午3:25
 * @ProjectName cxy
 * @Version V1.0
 */
public class CodeEnumUtil {
    public static <E extends Enum<?> & BaseCodeEnum> E codeOf(Class<E> enumClass, int code) {
        E[] enumConstants = enumClass.getEnumConstants();
        for (E e : enumConstants) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }
}
