package com.llb.cxy.interfaces.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.llb.cxy.application.service.RoleApplicationService;
import com.llb.cxy.core.model.ResultBody;
import com.llb.cxy.core.page.Page;
import com.llb.cxy.interfaces.assembler.RoleAssembler;
import com.llb.cxy.interfaces.dto.RoleDTO;

import lombok.extern.slf4j.Slf4j;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.interfaces.facade
 * @Description: 用户提供较粗粒度的调用接口，将用户请求委派给一个或多个应用服务进行处理。
 * @ClassName: UserApi
 * @date 2021-01-12 下午10:06
 * @ProjectName cxy
 * @Version V1.0
 */
@RestController
@RequestMapping("/roles")
@Slf4j
public class RoleApi extends BaseApi {

    @Autowired
    RoleApplicationService roleApplicationService;

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
     * 添加角色
     * @Author LiLuBing
     * @Date 2021-01-13 15:02
     * @Param  * @param role
     * @return
     **/
    @PostMapping
    public ResultBody saveRole(@RequestBody RoleDTO roleDTO) {
        try {
            roleApplicationService.saveRole(roleDTO);
            return ResultBody.ok();
        } catch (Exception e) {
            log.error("", e);
            return ResultBody.failed().msg(e.getMessage());
        }
    }

    /**
     * 删除角色
     * @Author LiLuBing
     * @Date 2021-01-13 15:02
     * @Param  * @param ids
     * @return
     **/
    @DeleteMapping
    public ResultBody remove(@RequestParam Long roleId) {
        this.getUserInfo();
        roleApplicationService.remove(roleId);
        return ResultBody.ok();
    }

    /**
     * 修改用户名称
     * @Author LiLuBing
     * @Date 2021-01-13 15:02
     * @Param  * @param role
     * @return {@link Integer}
     **/
    @PutMapping
    public ResultBody updateRoleById(@RequestBody RoleDTO role) {
        try {
            roleApplicationService.updateRoleById(RoleAssembler.toDO(role));
            return ResultBody.ok();
        } catch (Exception e) {
            log.error("", e);
            return ResultBody.failed().msg(e.getMessage());
        }
    }

    /**
     * 根据角色ID查询角色
     * @Author LiLuBing
     * @Date 2021-01-16 08:05
     * @Param  * @param roleId
     * @return {@link com.llb.cxy.core.model.ResultBody}
     **/
    @GetMapping(value = "id")
    public ResultBody getRoleById(@RequestParam Long roleId) {
        return ResultBody.ok().data(roleApplicationService.getRoleById(roleId));
    }

    /**
     * 根据条件分页查询
     * @Author LiLuBing
     * @Date 2021-01-13 15:02
     * @Param  * @param conditions
     * @return {@link ResultBody <  Page <  SysRole >>}
     **/
    @GetMapping
    public ResultBody<Page<RoleDTO>> getByConditions(String conditions) {
        return ResultBody.ok().data(roleApplicationService.getByConditions(conditions));
    }
    
    /**
     * 保存角色集合
     * @Author LiLuBing
     * @Date 2021-01-13 15:02
     * @Param  * @param jsonRow
     * @return {@link Integer}
     **/
    @PostMapping(value = "json")
    public ResultBody saveByJson(@RequestBody String jsonRow) {
        return ResultBody.ok().data(roleApplicationService.saveByJson(jsonRow));
    }

    /**
     * 根据用户Id获取菜单按钮值
     * @Author LiLuBing
     * @Date 2021-01-13 13:33
     * @Param  * @param userId
     * @return {@link java.util.List<java.lang.String>}
     **/
    @GetMapping(value = "getMenuButtonsBtnValueByUserId")
    public ResultBody getMenuButtonsBtnValueByUserId(Long userId) {
        return ResultBody.ok().data(roleApplicationService.getMenuButtonsBtnValueByUserId(userId));
    }

    /**
     *
     * @Author LiLuBing
     * @Date 2021-01-13 15:02
     * @Param  * @param userId
     * @param roleIds
     * @return {@link Integer}
     **/
    public ResultBody saveRoleAndUserAss(Long userId, String roleIds) {
        return ResultBody.ok().data(roleApplicationService.saveRoleAndUserAss(userId, roleIds));
    }

    /**
     * 删除角色与用户关联
     * @Author LiLuBing
     * @Date 2021-01-13 15:02
     * @Param  * @param userId
     * @param roleIds
     * @return {@link Integer}
     **/
    public ResultBody deleteRoleAndUserAss(Long userId, String roleIds) {
        return ResultBody.ok().data(roleApplicationService.deleteRoleAndUserAss(userId, roleIds));
    }
}
