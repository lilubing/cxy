package com.llb.cxy.interfaces.assembler;

import com.llb.cxy.domain.user.entity.UserInfo;
import com.llb.cxy.interfaces.dto.UserInfoDTO;
import org.springframework.beans.BeanUtils;

import java.text.ParseException;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.interfaces.assembler
 * @Description: 实现【用户】DTO与领域对象之间的相互转换和数据交换。（DTO接收前端传递的参数）
 * @ClassName: UserAssembler
 * @date 2021-01-12 下午9:59
 * @ProjectName cxy
 * @Version V1.0
 */
public class UserInfoAssembler {
    /**
     * 领域对象转用户信息DTO
     * @Author LiLuBing
     * @Date 2021-01-12 22:14
     * @Param  * @param userInfo
     * @return {@link com.llb.cxy.interfaces.dto.UserInfoDTO}
     **/
    public static UserInfoDTO toDTO(UserInfo userInfo){
        UserInfoDTO dto = new UserInfoDTO();
        BeanUtils.copyProperties(userInfo, dto);
        return dto;
    }

    /**
     * 用户信息DTO转领域对象
     * @Author LiLuBing
     * @Date 2021-01-12 22:14
     * @Param  * @param dto
     * @return {@link UserInfo}
     **/
    public static UserInfo toDO(UserInfoDTO dto) throws ParseException {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(dto, userInfo);
        return userInfo;
    }
}
