package com.llb.cxy.domain.user.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.llb.cxy.domain.user.entity.User;
import com.llb.cxy.domain.user.repository.facade.RoleUserRepository;
import com.llb.cxy.domain.user.repository.facade.UserRepository;
import com.llb.cxy.interfaces.assembler.UserInfoAssembler;
import com.llb.cxy.interfaces.dto.UserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.llb.cxy.core.SystemContext;
import com.llb.cxy.core.page.Page;
import com.llb.cxy.core.utils.DateUtil;
import com.llb.cxy.domain.user.entity.UserInfo;
import com.llb.cxy.domain.user.repository.facade.UserInfoRepository;
import com.llb.cxy.domain.user.repository.po.UserInfoPO;

import lombok.extern.slf4j.Slf4j;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.domain.user.service
 * @Description: 用户信息服务类
 * @ClassName: UserInfoDomainService
 * @date 2021-01-12 下午9:31
 * @ProjectName cxy
 * @Version V1.0
 */
@Service
@Slf4j
public class UserInfoDomainService {

    /*@Autowired
    EventPublisher eventPublisher;*/
    @Autowired
    UserInfoRepository userInfoRepository;
    @Autowired
    UserInfoFactory userInfoFactory;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserFactory userFactory;
    @Autowired
    private RoleUserRepository roleUserRepository;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 创建用户信息
     * @Author LiLuBing
     * @Date 2021-01-12 21:48
     * @Param  * @param userInfo
     * @return
     **/
    @Transactional(propagation = Propagation.REQUIRED, timeout = 3)
    public Integer create(UserInfoDTO userInfoDTO) throws ParseException {
        UserInfoPO userInfoPO = userInfoRepository.get(userInfoDTO.getId());
        if (null != userInfoPO) {
            throw new RuntimeException("UserInfo already exists");
        }
        // 创建用户信息
        UserInfo userInfo = UserInfoAssembler.toDO(userInfoDTO);
        userInfo.create();
        Integer result = userInfoRepository.save((userInfoFactory.createUserInfoPO(userInfo)));
        // 创建用户登陆信息
        User user = new User();
        user.create(userInfo, passwordEncoder);
        userRepository.save(userFactory.createUserPO(user));
        // 添加角色
        roleUserRepository.saveRoleUserAss(userInfoDTO.getRolesList(), userInfo.getId());
        return result;
    }

    /**
     * userInfoPO
     * @Author LiLuBing
     * @Date 2021-01-12 21:48
     * @Param  * @param userInfoDTO
     * @return 
     **/
    @Transactional(propagation = Propagation.REQUIRED, timeout = 300)
    public Integer update(UserInfoDTO userInfoDTO) throws ParseException {
        userInfoDTO.setLastModifyTime(DateUtil.get10Date());
        userInfoDTO.setLastModifyUserId(SystemContext.getUserInfo().getLong("userId"));
        UserInfo userInfo = UserInfoAssembler.toDO(userInfoDTO);
        //修改用户
        Integer result = userInfoRepository.update((userInfoFactory.createUserInfoPO(userInfo)));
        // 修改角色
        roleUserRepository.saveRoleUserAss(userInfoDTO.getRolesList(), userInfo.getId());
        return result;
    }

    /**
     * 根据用户ID删除用户信息
     * @Author LiLuBing
     * @Date 2021-01-12 21:47
     * @Param  * @param userId
     * @return 
     **/
    @Transactional(propagation = Propagation.REQUIRED, timeout = 3)
    public Integer deleteById(Long userId) {
        Integer result = 0;
        // 删除角色
        roleUserRepository.saveRoleUserAss(new String[0], userId);

        UserInfo userInfo = new UserInfo();
        userInfo.setId(userId);
        userInfo.delete();

        User user = new User();
        user.delete(userInfo);
        // 修改用户登陆
        result = userRepository.updateUserDynamic(userFactory.createUserPO(user));

        // 修改用户信息
        result = userInfoRepository.updateUserInfoDynamic(userInfoFactory.createUserInfoPO(userInfo));
        return result;
    }

    /**
     * 根据用户ID查询用户信息
     * @Author LiLuBing
     * @Date 2021-01-12 21:47
     * @Param  * @param userId
     * @return {@link com.llb.cxy.domain.user.entity.UserInfo}
     **/
    public UserInfo findById(Long userId) {
        UserInfoPO userInfoPO = userInfoRepository.get(userId);
        return userInfoFactory.getPerson(userInfoPO);
    }

    /**
     * 根据用登陆名查询用户信息
     * @Author LiLuBing
     * @Date 2021-01-14 13:35
     * @Param  * @param userName
     * @return {@link com.llb.cxy.domain.user.entity.UserInfo}
     **/
    public UserInfo findUserByLoginName(String userName) {
        return userInfoFactory.createUserInfo(userInfoRepository.findUserByLoginName(userName));
    }

    /**
     * 分页根据条件查询
     * @Author LiLuBing
     * @Date 2021-01-17 15:47
     * @Param  * @param mapConditions
     * @return {@link List< UserInfo>}
     **/
    public Page<UserInfo> findByConditions(String conditions) {
        Map<String, Object> mapConditions = JSONObject.parseObject(conditions);
        if (null == mapConditions) {
            mapConditions = Maps.newHashMap();
            mapConditions.put("sort", "desc");
        }

        // 查询总条数
        Integer total = userInfoRepository.findTotalByConditions(mapConditions);
        // 查询角色
        List<UserInfo> roles = Lists.newArrayList();
        if (total > 0) {
            roles = userInfoRepository.findByConditions(null, mapConditions)
                    .stream().map(userInfo -> userInfoFactory.createUserInfo(userInfo)).collect(Collectors.toList());
        }
        return new Page(SystemContext.getOffset(), total, roles);
    }

    /**
     * 分页根据条件查询
     * @Author LiLuBing
     * @Date 2021-01-17 15:47
     * @Param  * @param mapConditions
     * @return {@link java.lang.Integer}
     **/
    public Integer findTotalByConditions(Map<String, Object> mapConditions) {
        return userInfoRepository.findTotalByConditions(mapConditions);
    }
}
