package com.llb.cxy.domain.user.service;

import com.llb.cxy.core.id.Identities;
import com.llb.cxy.core.utils.MyStringUtils;
import com.llb.cxy.domain.user.entity.RoleUser;
import com.llb.cxy.domain.user.repository.facade.RoleUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.domain.user.service
 * @Description: 角色用户服务类
 * @ClassName: RoleUserService
 * @date 2021-01-13 下午3:15
 * @ProjectName cxy
 * @Version V1.0
 */
@Service
public class RoleUserDomainService {

    @Autowired
    private RoleUserRepository roleUserRepository;
    @Autowired
    private RoleUserFactory roleUserFactory;

    /**
     *
     * @Author LiLuBing
     * @Date 2021-01-13 15:02
     * @Param  * @param userId
     * @param roleIds
     * @return {@link Integer}
     **/
    @Transactional(propagation = Propagation.REQUIRED, timeout = 30)
    public Integer saveRoleAndUserAss(Long userId, String roleIds) {
        Integer result = 0;
        String[] arr = roleIds.split(",");
        for (String temp_id : arr) {
            if (MyStringUtils.isBlank(temp_id)) {
                continue;
            }
            Long id = Long.valueOf(temp_id);
            //查收是否已经存在
            List<Long> list = roleUserRepository.selectByUserIdAndRoleId(userId, id);
            //判断不存在则添加
            if(list.isEmpty()) {
                RoleUser roleUser = new RoleUser();
                roleUser.create(Identities.getId(), userId, id);
                result += roleUserRepository.save(roleUserFactory.createRoleUserPO(roleUser));
            }
        }
        return result;
    }

    /**
     * 删除角色与用户关联
     * @Author LiLuBing
     * @Date 2021-01-13 15:02
     * @Param  * @param userId
     * @param roleIds
     * @return {@link Integer}
     **/
    @Transactional(propagation = Propagation.REQUIRED, timeout = 30)
    public Integer deleteRoleAndUserAss(Long userId, String roleIds) {
        List<Long> listIds = Arrays.asList(roleIds.split(",")).stream()
                .map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());

        return roleUserRepository.deleteByUserIdAndRoleId(userId, listIds);
    }

    /**
     * 根据用户ID查询所有角色
     * @Author LiLuBing
     * @Date 2021-01-17 19:39
     * @Param  * @param userId
     * @return {@link java.util.List<java.lang.String>}
     **/
    public List<String> findRoleIdsByUserId(Long userId) {
        return roleUserRepository.findRoleIdsByUserId(userId);
    }
}
