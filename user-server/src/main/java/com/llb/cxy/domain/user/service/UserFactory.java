package com.llb.cxy.domain.user.service;

import java.util.List;
import java.util.stream.Collectors;

import com.llb.cxy.domain.user.entity.User;
import com.llb.cxy.domain.user.repository.facade.UserRepository;
import com.llb.cxy.domain.user.repository.po.UserPO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.domain.user.service
 * @Description: 用户信息工厂
 * @ClassName: UserFactory
 * @date 2021-01-12 下午9:02
 * @ProjectName cxy
 * @Version V1.0
 */
@Service
public class UserFactory {

    @Autowired
    UserRepository userRepository;

    /**
     * 创建用户信息存储类
     * @Author LiLuBing
     * @Date 2021-01-12 21:17
     * @Param  * @param User
     * @return {@link UserPO}
     **/
    public UserPO createUserPO(User user) {
        /**
         * 【强制】避免用ApacheBeanutils进行属性的copy。
         * 说明:Apache BeanUtils 性能较差，可以使用其他方案比如 Spring BeanUtils, Cglib BeanCopier，注意 均是浅拷贝。
         **/
        UserPO userPO = new UserPO();
        BeanUtils.copyProperties(user, userPO);
        return userPO;
    }

    /**
     * 创建用户信息对象
     * @Author LiLuBing
     * @Date 2021-01-12 21:18
     * @Param  * @param User
     * @return {@link UserPO}
     **/
    public User createUser(UserPO userPO) {
        /**
         * 【强制】避免用ApacheBeanutils进行属性的copy。
         * 说明:Apache BeanUtils 性能较差，可以使用其他方案比如 Spring BeanUtils, Cglib BeanCopier，注意 均是浅拷贝。
         **/
        User user = new User();
        BeanUtils.copyProperties(userPO, user);
        return user;
    }

    /**
     * 根据用户ID查询
     * @Author LiLuBing
     * @Date 2021-01-12 21:28
     * @Param  * @param User
     * @return {@link User}
     **/
    public User getPerson(UserPO userPO){
        return createUser(userRepository.get(userPO.getId()));
    }

    /**
     * 存储类转域模型对象
     * @Author LiLuBing
     * @Date 2021-01-12 21:29
     * @Param  * @param UserPOList
     * @return {@link List<User>}
     **/
    private List<User> getApprovalInfos(List<UserPO> userPOList){
        return userPOList.stream()
                .map(userPO -> createUser(userPO))
                .collect(Collectors.toList());
    }
}
