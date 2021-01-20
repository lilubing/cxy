package com.llb.cxy.user.service;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 包名com.llb.cxy.user.service:com.czx.springbootwebservice
 * 类名SysMenuButtonServiceTest:CodeTemplateTest
 * 当前系统用户lilubing:llb
 * 日期2020/8/10:2020/8/8
 * 项目名cxy:springbootwebservice
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SysMenuButtonServiceTest {

    /*@Autowired
    private SysRoleMenuButtonMapper sysRoleMenuButtonMapper;
    @Autowired
    private SysMenuButtonService sysMenuButtonService;
    @Test
    public void deletesTest() {
        //SysRoleMenuButtonMapper.deleteByMenuButtonId()
        sysMenuButtonService.deletes("1596682349040");
    }

    @Test
    public void saveMenuButtonsTest() {
        SysMenuButton entity = new SysMenuButton();
        entity.setBtnId("0");
        entity.setParentId(0l);
        entity.setName("test1");
        entity.setType(0);
        entity.setMenuButtonValue("test:test");
        entity.setStatus(0);
        List<SysMenuButton> list = Lists.newArrayList();
        list.add(entity);
        TestCase.assertEquals(1, sysMenuButtonService.saveMenuButtons(JSON.toJSONString(list)));
    }


    @Test
    public void getAllMenuButtonByParentIdTest() {
        List<Map<String, Object>> maps = sysMenuButtonService.getAllMenuButtonByParentId(0L);
        for (Map<String, Object> map : maps) {
            System.out.println(map);
        }
    }

    @Test
    public void getAllMenuButtonTreeByRoleIdTest() {
        List<Map<String, Object>> maps = sysMenuButtonService.getAllMenuButtonTreeByRoleId(0L,110L);
        for (Map<String, Object> map : maps) {
            System.out.println(map);
        }
    }

    @Test
    public void findMenuButtonByUserIdTest() {
        List<Map<String, Object>> maps = sysMenuButtonService.findMenuButtonByUserId(11011L,0L, 0);
        for (Map<String, Object> map : maps) {
            System.out.println(map);
        }
    }*/

}