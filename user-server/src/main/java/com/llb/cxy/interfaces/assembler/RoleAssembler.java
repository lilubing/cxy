package com.llb.cxy.interfaces.assembler;

import java.text.ParseException;

import org.springframework.beans.BeanUtils;

import com.llb.cxy.domain.user.entity.Role;
import com.llb.cxy.interfaces.dto.RoleDTO;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.interfaces.assembler
 * @Description: 实现【角色】DTO与领域对象之间的相互转换和数据交换。（DTO接收前端传递的参数）
 * @ClassName: RoleAssembler
 * @date 2021-01-14 上午8:57
 * @ProjectName cxy
 * @Version V1.0
 */
public class RoleAssembler {
    /**
     * 领域对象转角色DTO
     * @Author LiLuBing
     * @Date 2021-01-12 22:14
     * @Param  * @param role
     * @return {@link com.llb.cxy.interfaces.dto.RoleDTO}
     **/
    public static RoleDTO toDTO(Role role){
        RoleDTO roleDTO = new RoleDTO();
        BeanUtils.copyProperties(role, roleDTO);
        return roleDTO;
    }

    /**
     * 角色DTO转领域对象
     * @Author LiLuBing
     * @Date 2021-01-12 22:14
     * @Param  * @param dto
     * @return {@link Role}
     **/
    public static Role toDO(RoleDTO roleDTO) throws ParseException {
        Role role = new Role();
        BeanUtils.copyProperties(roleDTO, role);
        return role;
    }
}
