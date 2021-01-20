package com.llb.cxy.interfaces.facade;

import com.llb.cxy.application.service.UserApplicationService;
import com.llb.cxy.core.model.ResultBody;
import com.llb.cxy.domain.user.entity.UserInfo;
import com.llb.cxy.interfaces.assembler.UserInfoAssembler;
import com.llb.cxy.interfaces.dto.UserInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Map;

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
@RequestMapping("/users")
@Slf4j
public class UserInfoApi extends BaseApi {

    @Autowired
    UserApplicationService userApplicationService;

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
     * 创建用户信息
     * 
     * @Author LiLuBing
     * @Date 2021-01-12 22:17
     * @Param * @param userInfoDTO
     * @return {@link com.llb.cxy.core.model.ResultBody}
     **/
    @PostMapping
    public ResultBody create(@RequestBody UserInfoDTO userInfoDTO) {
        try {
            getUserInfo();
            userApplicationService.create(userInfoDTO);
            return ResultBody.ok();
        } catch (ParseException e) {
            log.error("", e);
            return ResultBody.failed().msg(e.getMessage());
        }
    }

    /**
     * 修改用户信息
     * 
     * @Author LiLuBing
     * @Date 2021-01-12 22:17
     * @Param * @param userInfoDTO
     * @return {@link ResultBody}
     **/
    @PutMapping
    public ResultBody update(@RequestBody UserInfoDTO userInfoDTO) {
        try {
            getUserInfo();
            userApplicationService.update(userInfoDTO);
            return ResultBody.ok();
        } catch (Exception e) {
            log.error("", e);
            return ResultBody.failed().msg(e.getMessage());
        }
    }

    /**
     * 根据用户ID删除用户信息
     * 
     * @Author LiLuBing
     * @Date 2021-01-12 22:17
     * @Param * @param personId
     * @return {@link com.llb.cxy.core.model.ResultBody}
     **/
    @DeleteMapping
    public ResultBody delete(@RequestParam Long userId) {
        getUserInfo();
        userApplicationService.deleteById(userId);
        return ResultBody.ok();
    }

    /**
     * 根据用户ID查询用户信息
     * 
     * @Author LiLuBing
     * @Date 2021-01-12 22:15
     * @Param * @param personId
     * @return {@link com.llb.cxy.core.model.ResultBody}
     **/
    @GetMapping(value = "id")
    public ResultBody findById(@RequestParam Long userId) {
        return ResultBody.ok().data((userApplicationService.findById(userId)));
    }

    /**
     * 根据条件分页查询
     * @Author LiLuBing
     * @Date 2021-01-17 15:37
     * @Param  * @param conditions
     * @return {@link ResultBody}
     **/
    @GetMapping
    public ResultBody getUserByConditions(String conditions) {
        return ResultBody.ok().data(userApplicationService.findByConditions(conditions));
    }
}
