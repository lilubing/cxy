package com.llb.cxy.interfaces.facade;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.llb.cxy.application.service.MenuButtonApplicationService;
import com.llb.cxy.core.model.ResultBody;
import com.llb.cxy.domain.user.entity.MenuButton;
import com.llb.cxy.interfaces.assembler.MenuButtonAssembler;
import com.llb.cxy.interfaces.dto.MenuButtonDTO;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.interfaces.facade
 * @Description: 用户提供较粗粒度的调用接口，将用户请求委派给一个或多个应用服务进行处理。
 * @ClassName: MenuButtonApi
 * @date 2021-01-14 上午8:50
 * @ProjectName cxy
 * @Version V1.0
 */
@RestController
@RequestMapping("/menuButtons")
@Slf4j
public class MenuButtonApi extends BaseApi {
    @Autowired
    MenuButtonApplicationService menuButtonApplicationService;

    /**
     * 常用的HTTP动词有下面五个（括号里是对应的SQL命令）。
     * GET（SELECT）：从服务器取出资源（一项或多项）。
     * POST（CREATE）：在服务器新建一个资源。
     * PUT（UPDATE）：在服务器更新资源（客户端提供改变后的完整资源）。
     * PATCH（UPDATE）：在服务器更新资源（客户端提供改变的属性）。
     * DELETE（DELETE）：从服务器删除资源。
     *
     * 下面是一些例子。
     * GET /zoos：列出所有动物园
     * POST /zoos：新建一个动物园
     * GET /zoos/ID：获取某个指定动物园的信息
     * PUT /zoos/ID：更新某个指定动物园的信息（提供该动物园的全部信息）
     * PATCH /zoos/ID：更新某个指定动物园的信息（提供该动物园的部分信息）
     * DELETE /zoos/ID：删除某个动物园
     * GET /zoos/ID/animals：列出某个指定动物园的所有动物
     * DELETE /zoos/ID/animals/ID：删除某个指定动物园的指定动物
     **/

    /**
     * 保存按钮集合
     * @Title: saveMenuButtons
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @author LiLuBing
     * @param menuButton
     * @throws
     */
    @PostMapping
    @ResponseBody
    public MenuButtonDTO create(@RequestBody MenuButton menuButton) {
        return MenuButtonAssembler.toDTO(menuButtonApplicationService.saveMenuButtons(menuButton));
    }

    /**
     * 删除按钮集合
     * @Title: deleteMenuButtons
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @author LiLuBing
     * @param menuButtonId
     * @throws
     */
    @DeleteMapping
    public ResultBody deleteMenuButtons(@RequestParam Long menuButtonId) {
        try {
            menuButtonApplicationService.delete(menuButtonId);
            return ResultBody.ok();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
            return ResultBody.failed().msg(e.getMessage());
        }
    }

    /**
     * 根据菜单按钮Id查询
     * @Author LiLuBing
     * @Date 2021-01-16 15:56
     * @Param  * @param menuButtonId
     * @return {@link com.llb.cxy.core.model.ResultBody}
     **/
    @GetMapping
    public ResultBody getMenuButton(@RequestParam Long menuButtonId) {
        try {
            MenuButtonDTO dto = menuButtonApplicationService.findByMenuButtonId(menuButtonId);
            String str = JSON.toJSONString(dto).toString();
            return ResultBody.ok().data(dto);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
            return ResultBody.failed().msg(e.getMessage());
        }
    }

    /**
     * 保存按钮与角色关联
     * @Author LiLuBing
     * @Date 2021-01-14 09:11
     * @Param  * @param menuButtonId
     * @param checked
     * @param roleId
     * @return {@link ResultBody}
     **/
    @PostMapping(value = "saveMenuButtonAndRoleAss")
    @ResponseBody
    public ResultBody saveMenuButtonAndRoleAss(@RequestParam(value = "menuButtonId") Long menuButtonId,
                       @RequestParam(value = "checked") boolean checked, @RequestParam(value = "roleId") Long roleId) {
        try {
            menuButtonApplicationService.saveMenuButtonAndRoleAss(menuButtonId, checked, roleId);
            return ResultBody.ok();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
            return ResultBody.failed().msg(e.getMessage());
        }
    }

    /**
     * 根据角色查询菜单按钮
     * @Author LiLuBing
     * @Date 2021-01-14 09:10
     * @Param  * @param roleId
     * @return {@link java.util.List<java.util.Map<java.lang.String,java.lang.Object>>}
     **/
    @GetMapping(value = "/menuButtonsTreeByRoleId")
    public List<Map<String, Object>> menuButtonsTreeByRoleId(@RequestParam Long roleId) {
        return menuButtonApplicationService.getMenuButtonsTreeByRoleId(0L, roleId);
    }

    /**
     * 查询所有菜单按钮
     * @Author LiLuBing
     * @Date 2021-01-14 09:10
     * @Param  * @param
     * @return {@link java.util.List<java.util.Map<java.lang.String,java.lang.Object>>}
     **/
    @GetMapping(value = "getAllMenuButtonTree")
    public ResultBody getAllMenuButtonTree() {
        try {
            JSONObject jsonObject = this.getUserInfo();
            return ResultBody.ok().data(menuButtonApplicationService.getAllMenuButtonByParentId(0L));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
            return ResultBody.failed().msg(e.getMessage());
        }
    }

    /**
     * 根据用户查与菜单按钮的父节点ID询菜单按钮
     * @Author LiLuBing
     * @Date 2021-01-14 09:10
     * @Param  * @param parentMenuId
     * @param userId
     * @return {@link List< Map< String, Object>>}
     **/
    @ApiOperation(value="根据用户查与菜单按钮的父节点ID询菜单按钮", notes="menuButton/user_menu")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentMenuId", value = "父节点ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "Long")
    })
    //@RequiresMenuButtons("upms:menuButton:read")
    @RequestMapping(value = "/user_menu", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> user_menu(Long parentMenuId, Long userId) {
        return menuButtonApplicationService.findMenuButtonByUserId(userId, parentMenuId, 0);
    }

    /**
     * 根据用户查与菜单按钮的父节点ID询按钮菜单按钮
     * @Author LiLuBing
     * @Date 2021-01-14 09:10
     * @Param  * @param parentMenuId
     * @param userId
     * @return {@link List< Map< String, Object>>}
     **/
    @ApiOperation(value="根据用户查与菜单按钮的父节点ID询按钮菜单按钮", notes="menuButton/user_button")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentMenuId", value = "父节点ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "Long")
    })
    //@RequiresMenuButtons("upms:menuButton:read")
    @RequestMapping(value = "/user_button", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> user_button(Long parentMenuId, Long userId) {
        return menuButtonApplicationService.findMenuButtonByUserId(userId, parentMenuId, 1);
    }
}
