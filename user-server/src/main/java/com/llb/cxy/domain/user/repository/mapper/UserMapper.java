package com.llb.cxy.domain.user.repository.mapper;

import com.llb.cxy.domain.user.repository.po.UserPO;
import org.apache.ibatis.annotations.Mapper;

import com.llb.cxy.mybatis.MyMapper;

import java.util.List;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.domain.user.repository.mapper
 * @Description: 用户表 Mapper 接口
 * @ClassName: UserDao
 * @date 2021-01-12 下午6:14
 * @ProjectName cxy
 * @Version V1.0
 */
@Mapper
public interface UserMapper extends MyMapper<UserPO, Long> {

    /**
     * 根据登陆名查询用户登陆表
     * @Author LiLuBing
     * @Date 2021-01-12 18:19
     * @Param  * @param userName
     * @return {@link UserPO}
     **/
    List<UserPO> findUserByLoginName(String userName);

    /**
     * 动态更新用户登陆
     * @Author LiLuBing
     * @Date 2021-01-18 11:43
     * @Param  * @param userPO
     * @return {@link java.lang.Integer}
     **/
    Integer updateUserDynamic(UserPO userPO);
}
