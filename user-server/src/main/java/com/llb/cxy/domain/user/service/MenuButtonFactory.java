package com.llb.cxy.domain.user.service;

import com.llb.cxy.domain.user.entity.MenuButton;
import com.llb.cxy.domain.user.repository.facade.MenuButtonRepository;
import com.llb.cxy.domain.user.repository.po.MenuButtonPO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.domain.user.service
 * @Description: 菜单按钮菜单工厂
 * @ClassName: MenuButtonFactory
 * @date 2021-01-13 上午10:15
 * @ProjectName cxy
 * @Version V1.0
 */
@Service
public class MenuButtonFactory {
    @Autowired
    MenuButtonRepository menuButtonRepository;

    /**
     * 创建菜单按钮菜单存储类
     * @Author LiLuBing
     * @Date 2021-01-12 21:17
     * @Param  * @param menuButton
     * @return {@link MenuButtonPO}
     **/
    public MenuButtonPO createMenuButtonPO(MenuButton menuButton) {
        /**
         * 【强制】避免用ApacheBeanutils进行属性的copy。
         * 说明:Apache BeanUtils 性能较差，可以使用其他方案比如 Spring BeanUtils, Cglib BeanCopier，注意 均是浅拷贝。
         **/
        MenuButtonPO menuButtonPO = new MenuButtonPO();
        BeanUtils.copyProperties(menuButton, menuButtonPO);
        return menuButtonPO;
    }

    /**
     * 创建菜单按钮菜单对象
     * @Author LiLuBing
     * @Date 2021-01-12 21:18
     * @Param  * @param menuButtonPO
     * @return {@link MenuButton}
     **/
    public MenuButton createMenuButton(MenuButtonPO menuButtonPO) {
        /**
         * 【强制】避免用ApacheBeanutils进行属性的copy。
         * 说明:Apache BeanUtils 性能较差，可以使用其他方案比如 Spring BeanUtils, Cglib BeanCopier，注意 均是浅拷贝。
         **/
        MenuButton menuButton = new MenuButton();
        BeanUtils.copyProperties(menuButtonPO, menuButton);
        return menuButton;
    }
}
