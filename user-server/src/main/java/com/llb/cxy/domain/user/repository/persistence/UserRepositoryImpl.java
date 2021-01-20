package com.llb.cxy.domain.user.repository.persistence;

import com.llb.cxy.domain.user.repository.facade.UserRepository;
import com.llb.cxy.domain.user.repository.mapper.UserMapper;
import com.llb.cxy.domain.user.repository.po.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.llb.cxy.mybatis.service.GenericManagerImpl;

import java.util.List;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.domain.user.repository.persistence
 * @Description: 用户登陆表 持久化服务实现类
 * @ClassName: UserRepositoryImpl
 * @date 2021-01-12 下午6:14
 * @ProjectName cxy
 * @Version V1.0
 */
@Service
public class UserRepositoryImpl extends GenericManagerImpl<UserPO, Long> implements UserRepository {

    private UserMapper userMapper;

    @Autowired
    public UserRepositoryImpl(UserMapper userMapper) {
        super(userMapper);
        this.userMapper = userMapper;
    }

    /**
     * 根据登陆名查询用户登陆表
     * @Author LiLuBing
     * @Date 2021-01-12 18:29
     * @Param  * @param userName
     * @return {@link UserPO}
     **/
    @Override
    public List<UserPO> findUserByLoginName(String userName) {
        return userMapper.findUserByLoginName(userName);
    }

    /**
     * 动态更新用户登陆
     * @Author LiLuBing
     * @Date 2021-01-18 11:41
     * @Param  * @param userPO
     * @return {@link java.lang.Integer}
     **/
    @Override
    public Integer updateUserDynamic(UserPO userPO) {
        return userMapper.updateUserDynamic(userPO);
    }
}
