package com.llb.cxy.domain.user.repository.mapper;

import com.llb.cxy.domain.user.repository.po.UserInfoPO;
import org.apache.ibatis.annotations.Mapper;

import com.llb.cxy.mybatis.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.domain.user.repository.mapper
 * @Description: 用户信息表 Mapper 接口
 * @ClassName: UserInfoDao
 * @date 2021-01-12 下午6:14
 * @ProjectName cxy
 * @Version V1.0
 */
@Mapper
public interface UserInfoMapper extends MyMapper<UserInfoPO, Long> {

    /**
     * 根据登陆名查询用户信息表
     * @Author LiLuBing
     * @Date 2021-01-12 18:25
     * @Param  * @param userName
     * @return {@link UserInfoPO}
     **/
    UserInfoPO findUserByLoginName(String userName);

    /**
     * 分页根据条件查询
     * @Author LiLuBing
     * @Date 2021-01-17 15:57
     * @Param  * @param mapPageInfo
     * @param mapConditions
     * @return {@link List< UserInfoPO>}
     **/
    List<UserInfoPO> findByConditions(@Param("page") Map<String, Object> mapPageInfo,
                                      @Param("params") Map<String, Object> mapConditions);

    /**
     * 分页根据条件查询总数
     * @Author LiLuBing
     * @Date 2021-01-17 15:57
     * @Param  * @param mapConditions
     * @return {@link Integer}
     **/
    Integer findTotalByConditions(Map<String, Object> mapConditions);

    /**
     * 动态更新用户信息
     * @Author LiLuBing
     * @Date 2021-01-18 11:32
     * @Param  * @param userInfoPO
     * @return {@link java.lang.Integer}
     **/
    Integer updateUserInfoDynamic(UserInfoPO userInfoPO);
}
