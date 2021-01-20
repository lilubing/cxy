package com.llb.cxy.user.service;

import java.util.List;

import com.llb.cxy.domain.user.entity.RoleUser;
import com.llb.cxy.domain.user.repository.mapper.RoleUserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.llb.cxy.domain.user.repository.mapper.RoleMapper;
import com.llb.cxy.domain.user.repository.po.RolePO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RoleUserDomainServiceTest {

    @Autowired
    private RoleUserMapper roleUserMapper;

    @Test
    public void getByConditionsTest() {
        List<String> list = roleUserMapper.findRoleIdsByUserId(11011L);
        System.out.println(list);
        /*ResultBody<Page<Role>> resultBody = roleMapper.getByConditions("");
        List<Role> list = resultBody.getData().getRows();
        for (Role sysRole : list) {
            System.out.println(JSON.toJSONString(sysRole));
        }*/
    }

    /*

    @Test
    public void saveByJsonTest() {
        List<SysRole> list = Lists.newArrayList();
        SysRole role = new SysRole();
        //role.setId(123L);
        role.setName("test123");
        role.setDescription("testDescription");
        role.setParentId(0L);
        role.setDFlag(0);
        role.setCId(11011L);
        role.setDId(0L);
        Long time = DateUtil.get10Date();
        role.setCId(time);
        role.setDTime(time);
        list.add(role);
        Assert.assertTrue("创建失败", sysRoleService.saveByJson(JSON.toJSONString(list)) > 0);
    }

    @Test
    public void removeTest() {
        Assert.assertTrue("删除失败", sysRoleService.deletes("741579605600763904") > 0);
    }

    @Test
    public void getByConditionsTest() {
        ResultBody<Page<SysRole>> resultBody = sysRoleService.getByConditions("");
        List<SysRole> list = resultBody.getData().getRows();
        for (SysRole sysRole : list) {
            System.out.println(JSON.toJSONString(sysRole));
        }
    }

    @Test
    public void saveRoleAndUserAssTest() {
        Assert.assertTrue("角色用户关联失败", sysRoleService.saveRoleAndUserAss(11011L, "111") > 0);
    }

    @Test
    public void deleteRoleAndUserAssTest() {
        Assert.assertTrue("删除角色用户关联失败", sysRoleService.deleteRoleAndUserAss(11011L, "111") > 0);
    }

    @Test
    public void findRolesByUserTest() {
        List<SysRole> list = sysRoleService.findRolesByUser(11011L);
        for (SysRole sysRole : list) {
            System.out.println(JSON.toJSONString(sysRole));
        }
    }

    @Test
    public void getMenuButtonsBtnValueByUserIdTest() {
        List<String> list = sysRoleService.getMenuButtonsBtnValueByUserId(11011L);
        for (String btnValue : list) {
            System.out.println(btnValue);
        }
    }*/
}

