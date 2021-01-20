package com.llb.cxy.domain.user.repository.persistence;

import com.llb.cxy.domain.user.repository.facade.UserInfoRepository;
import com.llb.cxy.domain.user.repository.mapper.UserInfoMapper;
import com.llb.cxy.domain.user.repository.po.RolePO;
import com.llb.cxy.domain.user.repository.po.UserInfoPO;
import com.llb.cxy.mybatis.service.GenericManagerImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.domain.user.repository.persistence
 * @Description: 用户信息表 持久化服务实现类
 * @ClassName: UserInfoRepositoryImpl
 * @date 2021-01-12 下午6:14
 * @ProjectName cxy
 * @Version V1.0
 */
@Service
public class UserInfoRepositoryImpl extends GenericManagerImpl<UserInfoPO, Long> implements UserInfoRepository {

    private UserInfoMapper userInfoMapper;

    @Autowired
    public UserInfoRepositoryImpl(UserInfoMapper userInfoMapper) {
        super(userInfoMapper);
        this.userInfoMapper = userInfoMapper;
    }

    /**
     * 根据登陆名查询用户信息表
     * @Author LiLuBing
     * @Date 2021-01-12 18:29
     * @Param  * @param userName
     * @return {@link UserInfoPO}
     **/
    @Override
    public UserInfoPO findUserByLoginName(String userName) {
        return userInfoMapper.findUserByLoginName(userName);
    }

    /**
     * 动态更新用户信息
     * @Author LiLuBing
     * @Date 2021-01-18 11:32
     * @Param  * @param userInfoPO
     * @return {@link java.lang.Integer}
     **/
    @Override
    public Integer updateUserInfoDynamic(UserInfoPO userInfoPO) {
        return userInfoMapper.updateUserInfoDynamic(userInfoPO);
    }

    /**
     * 分页根据条件查询
     * @Author LiLuBing
     * @Date 2021-01-17 15:56
     * @Param  * @param mapPageInfo
     * @param mapConditions
     * @return {@link List< UserInfoPO>}
     **/
    @Override
    public List<UserInfoPO> findByConditions(@Param("page") Map<String, Object> mapPageInfo,
                                         @Param("params") Map<String, Object> mapConditions) {
        return userInfoMapper.findByConditions(getPageInfoMap(), mapConditions);
    }

    /**
     * 分页根据条件查询总数
     * @Author LiLuBing
     * @Date 2021-01-17 15:56
     * @Param  * @param mapConditions
     * @return {@link Integer}
     **/
    @Override
    public Integer findTotalByConditions(Map<String, Object> mapConditions) {
        return userInfoMapper.findTotalByConditions(mapConditions);
    }
}
