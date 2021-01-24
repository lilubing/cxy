package com.llb.cxy.boot.config.afterStart;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.llb.cxy.core.constants.UserConstants;
import com.llb.cxy.domain.user.repository.mapper.MenuButtonMapper;
import com.llb.cxy.domain.user.service.MenuButtonDomainService;
import com.llb.cxy.infrastructure.util.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.boot.config.afterStart
 * @Description: 缓存预热用户权限
 * @ClassName: CacheUserPermission
 * @date 2021-01-21 上午8:34
 * @ProjectName cxy
 * @Version V1.0
 */
@Component
public class PreheatUserPrivileges implements ApplicationRunner {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    MenuButtonDomainService menuButtonDomainService;

    private Map<String, Set<String>> menuButtonAndRoleMap;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 获取所有菜单按钮与角色联合信息
        List<Map<String, String>> menuButtonAndRoleList = menuButtonDomainService.getAllMenuButtonAndRole();
        menuButtonAndRoleMap = Maps.newHashMapWithExpectedSize(menuButtonAndRoleList.size());
        //循环组装Map<String, List<String>>
        menuButtonAndRoleList.forEach(menuButtonAndRole -> {
            String uri = menuButtonAndRole.get("uri");
            //从无到有，所以这里是先创建后追加
            if(!menuButtonAndRoleMap.containsKey(uri)) {
                menuButtonAndRoleMap.put(uri, Sets.newHashSet(String.valueOf(menuButtonAndRole.get("role_id"))));
            } else {
                menuButtonAndRoleMap.get(uri).add(String.valueOf(menuButtonAndRole.get("role_id")));
            }
        });
        redisUtil.hPutAllMapValueList(UserConstants.REDIS_USER_PRIVILEGES_KEY_PREFIX,
                menuButtonAndRoleMap);
    }
}
