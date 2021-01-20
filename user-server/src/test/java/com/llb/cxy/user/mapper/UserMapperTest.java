package com.llb.cxy.user.mapper;

import com.llb.cxy.domain.user.repository.po.UserPO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.llb.cxy.domain.user.entity.User;
import com.llb.cxy.domain.user.repository.mapper.UserMapper;

import java.util.List;

/**
 * description: UserMapperTest <br>
 * date: 2020/7/29 15:25 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void getUserById() {
        List<UserPO> list = userMapper.findUserByLoginName("1");
        User user = createUser(list.get(0));
        System.out.println(user.getDeleted().getCode());
    }

    @Test
    public void create() {
        User user = new User();
        user.create();
        user.setId(1L);
        user.setUserName("1");
        user.setPassword("1");

        UserPO userPO = createUserPO(user);
        Integer result = userMapper.insert(userPO);
        System.out.println(result + "===" + user.getDeleted().getCode());
    }

    /**
     * 创建用户信息存储类
     * @Author LiLuBing
     * @Date 2021-01-12 21:17
     * @Param  * @param user
     * @return {@link com.llb.cxy.domain.user.repository.po.UserPO}
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
}
