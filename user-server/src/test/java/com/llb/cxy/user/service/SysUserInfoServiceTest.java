package com.llb.cxy.user.service;

import com.llb.cxy.domain.user.entity.UserInfo;
import com.llb.cxy.domain.user.repository.mapper.UserInfoMapper;
import com.llb.cxy.domain.user.repository.po.UserInfoPO;
import com.llb.cxy.domain.user.service.UserInfoFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.llb.cxy.domain.user.service.UserInfoDomainService;

/**
 * description: SysUserInfoService <br>
 * date: 2020/7/29 8:18 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SysUserInfoServiceTest {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Test
    public void getUserByIdTest() {
        UserInfoPO po = userInfoMapper.findUserByLoginName("admin");
        UserInfo userInfo = new UserInfoFactory().createUserInfo(po);
        System.out.println(userInfo.getUserName());
    }

    // @Test
    // public void manualSaveTest() {
    //     SysUserInfo sysUserInfo = new SysUserInfo();
    //     sysUserInfo.setId(2L);
    //     /**
    //      * 用户登录名
    //      */
    //     sysUserInfo.setUserName("test1");
    //     /**
    //      * 真实名称
    //      */
    //     sysUserInfo.setRealName("测试1");
    //     /**
    //      * 机构id
    //      */
    //     sysUserInfo.setOrgId(1L);
    //     /**
    //      * 机构名称
    //      */
    //     sysUserInfo.setOrgName("测试机构");
    //     /**
    //      * 电子邮箱
    //      */
    //     sysUserInfo.setEmail("8366419@qq.com");
    //     /**
    //      * 个人电话
    //      */
    //     sysUserInfo.setMobilePhone("13606480548");
    //     /**
    //      * 办公电话
    //      */
    //     sysUserInfo.setOfficePhone("");
    //     /**
    //      * qq
    //      */
    //     sysUserInfo.setQq(0L);
    //     /**
    //      * 微信号
    //      */
    //     sysUserInfo.setWeChat("");
    //     /**
    //      * 性别0：男,1：女
    //      */
    //     sysUserInfo.setSex(0);
    //     /**
    //      * 家庭地址
    //      */
    //     sysUserInfo.setHomeAddress("");
    //     /**
    //      * 办公地址
    //      */
    //     sysUserInfo.setOfficeAddress("");
    //     /**
    //      * 默认地址:0办公地址，1家庭地址
    //      */
    //     sysUserInfo.setDefaultAddress(0);
    //     /**
    //      * 姓名拼音
    //      */
    //     sysUserInfo.setPyFull("LiLuBing");
    //     /**
    //      * 名:首字母,字:首字母
    //      */
    //     sysUserInfo.setPyShort("llb");
    //     /**
    //      * 职位
    //      */
    //     sysUserInfo.setPosition("测试");
    //     /**
    //      * 职工号
    //      */
    //     sysUserInfo.setJobNumber("123");
    //     /**
    //      * 国籍
    //      */
    //     sysUserInfo.setNationality("中国");
    //     /**
    //      * 出生年月日
    //      */
    //     sysUserInfo.setBirthDate(new Date());
    //     /**
    //      * 最后登录时间
    //      */
    //     sysUserInfo.setLastLoginTime(0);
    //     /**
    //      * 用户状态：0正常，1锁定，2逾期
    //      */
    //     sysUserInfo.setStatus(0);
    //     /**
    //      * 激活状态:0未激活 1激活
    //      */
    //     sysUserInfo.setActivateStatus(0);
    //     /**
    //      * 创建用户id
    //      */
    //     sysUserInfo.setCUserId(0L);
    //     /**
    //      * 创建时间
    //      */
    //     sysUserInfo.setCTime(DateUtil.get10Date());
    //     /**
    //      * 修改用户id
    //      */
    //     sysUserInfo.setUUserId(0L);
    //     /**
    //      * 修改时间
    //      */
    //     sysUserInfo.setUTime(DateUtil.get10Date());
    //     /**
    //      * 0正常 1删除
    //      */
    //     sysUserInfo.setDeleted(0);
    //
    //     Assert.assertTrue("添加用户失败", sysUserInfoService.manualSave(sysUserInfo));
    // }
    //
    // @Test
    // public void updateUserInfoTest() {
    //     SysUserInfo sysUserInfo = new SysUserInfo();
    //     sysUserInfo.setId(2L);
    //     /**
    //      * 用户登录名
    //      */
    //     sysUserInfo.setUserName("test1");
    //     /**
    //      * 真实名称
    //      */
    //     sysUserInfo.setRealName("测试11");
    //     /**
    //      * 机构id
    //      */
    //     sysUserInfo.setOrgId(1L);
    //     /**
    //      * 机构名称
    //      */
    //     sysUserInfo.setOrgName("测试机构1");
    //
    //     Assert.assertTrue("修改用户失败", sysUserInfoService.updateUserInfo(sysUserInfo) == 1);
    // }
}