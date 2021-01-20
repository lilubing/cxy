package com.llb.cxy.domain.user.repository.facade;

import com.llb.cxy.domain.user.repository.po.UserPO;
import com.llb.cxy.mybatis.service.GenericManager;

import java.util.List;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.domain.user.repository.facade
 * @Description: 用户登陆表 服务类
 * @ClassName: UserInfoRepository
 * @date 2021-01-12 下午6:14
 * @ProjectName cxy
 * @Version V1.0
 */
public interface UserRepository extends GenericManager<UserPO, Long> {

    /**
     * 根据用户登陆名查询用户信息
     * @Author LiLuBing
     * @Date 2021-01-12 18:26
     * @Param  * @param userName
     * @return {@link UserPO}
     **/
    List<UserPO> findUserByLoginName(String userName);

    /**
     * 动态更新用户登陆
     * @Author LiLuBing
     * @Date 2021-01-18 11:40
     * @Param  * @param userPO
     * @return {@link java.lang.Integer}
     **/
    Integer updateUserDynamic(UserPO userPO);
}
