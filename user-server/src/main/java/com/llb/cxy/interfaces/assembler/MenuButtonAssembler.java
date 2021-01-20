package com.llb.cxy.interfaces.assembler;

import java.text.ParseException;

import org.springframework.beans.BeanUtils;

import com.llb.cxy.domain.user.entity.MenuButton;
import com.llb.cxy.interfaces.dto.MenuButtonDTO;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.interfaces.assembler
 * @Description: 实现【角色菜单按钮】DTO与领域对象之间的相互转换和数据交换。（DTO接收前端传递的参数）
 * @ClassName: MenuButtonAssembler
 * @date 2021-01-14 上午8:57
 * @ProjectName cxy
 * @Version V1.0
 */
public class MenuButtonAssembler {
    /**
     * 领域对象转角色菜单按钮DTO
     * @Author LiLuBing
     * @Date 2021-01-12 22:14
     * @Param  * @param MenuButton
     * @return {@link MenuButtonDTO}
     **/
    public static MenuButtonDTO toDTO(MenuButton menuButton){
        MenuButtonDTO dto = new MenuButtonDTO();
        BeanUtils.copyProperties(menuButton, dto);
        return dto;
    }

    /**
     * 角色菜单按钮DTO转领域对象
     * @Author LiLuBing
     * @Date 2021-01-12 22:14
     * @Param  * @param dto
     * @return {@link MenuButton}
     **/
    public static MenuButton toDO(MenuButtonDTO dto) throws ParseException {
        MenuButton menuButton = new MenuButton();
        BeanUtils.copyProperties(dto, menuButton);
        return menuButton;
    }
}
