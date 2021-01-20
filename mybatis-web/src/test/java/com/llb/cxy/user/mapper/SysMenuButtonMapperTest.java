package com.llb.cxy.user.mapper;

import com.llb.cxy.domain.user.repository.mapper.MenuButtonMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * description: SysUserMapperTest <br>
 * date: 2020/7/29 15:25 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SysMenuButtonMapperTest {
    @Autowired
    private MenuButtonMapper menuButtonMapper;

//    @Test
    /*public void getUserById() {
        System.out.println(sysMenuButtonMapper.findMenuButtonByUserId().get(0));
    }*/
}
