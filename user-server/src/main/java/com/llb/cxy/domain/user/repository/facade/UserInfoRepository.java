package com.llb.cxy.domain.user.repository.facade;

import com.llb.cxy.domain.user.repository.po.UserInfoPO;
import com.llb.cxy.mybatis.service.GenericManager;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.domain.user.repository.facade
 * @Description: 用户信息表 服务类
 * @ClassName: UserInfoRepository
 * @date 2021-01-12 下午6:14
 * @ProjectName cxy
 * @Version V1.0
 */
public interface UserInfoRepository extends GenericManager<UserInfoPO, Long> {

    /**
     * 根据用户登陆名查询用户信息
     * @Author LiLuBing
     * @Date 2021-01-12 18:26
     * @Param  * @param userName
     * @return {@link UserInfoPO}
     **/
    UserInfoPO findUserByLoginName(String userName);

    /**
     * 动态更新用户信息
     * @Author LiLuBing
     * @Date 2021-01-18 11:32
     * @Param  * @param userInfoPO
     * @return {@link java.lang.Integer}
     **/
    Integer updateUserInfoDynamic(UserInfoPO userInfoPO);
}
