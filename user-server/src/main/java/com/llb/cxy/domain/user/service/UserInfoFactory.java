package com.llb.cxy.domain.user.service;

import com.llb.cxy.core.id.Identities;
import com.llb.cxy.domain.user.entity.UserInfo;
import com.llb.cxy.domain.user.repository.facade.UserInfoRepository;
import com.llb.cxy.domain.user.repository.po.UserInfoPO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.domain.user.service
 * @Description: 用户信息工厂
 * @ClassName: UserInfoFactory
 * @date 2021-01-12 下午9:02
 * @ProjectName cxy
 * @Version V1.0
 */
@Service
public class UserInfoFactory {

    @Autowired
    UserInfoRepository userInfoRepository;

    /**
     * 创建用户信息存储类
     * @Author LiLuBing
     * @Date 2021-01-12 21:17
     * @Param  * @param userInfo
     * @return {@link com.llb.cxy.domain.user.repository.po.UserInfoPO}
     **/
    public UserInfoPO createUserInfoPO(UserInfo userInfo) {
        /**
         * 【强制】避免用ApacheBeanutils进行属性的copy。
         * 说明:Apache BeanUtils 性能较差，可以使用其他方案比如 Spring BeanUtils, Cglib BeanCopier，注意 均是浅拷贝。
         **/
        UserInfoPO userInfoPO = new UserInfoPO();
        BeanUtils.copyProperties(userInfo, userInfoPO);
        return userInfoPO;
    }

    /**
     * 创建用户信息对象
     * @Author LiLuBing
     * @Date 2021-01-12 21:18
     * @Param  * @param userInfo
     * @return {@link com.llb.cxy.domain.user.repository.po.UserInfoPO}
     **/
    public UserInfo createUserInfo(UserInfoPO userInfoPO) {
        /**
         * 【强制】避免用ApacheBeanutils进行属性的copy。
         * 说明:Apache BeanUtils 性能较差，可以使用其他方案比如 Spring BeanUtils, Cglib BeanCopier，注意 均是浅拷贝。
         **/
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userInfoPO, userInfo);
        return userInfo;
    }

    /**
     * 根据用户ID查询
     * @Author LiLuBing
     * @Date 2021-01-12 21:28
     * @Param  * @param userInfo
     * @return {@link com.llb.cxy.domain.user.entity.UserInfo}
     **/
    public UserInfo getPerson(UserInfoPO userInfoPO){
        return createUserInfo(userInfoRepository.get(userInfoPO.getId()));
    }

    /**
     * 存储类转域模型对象
     * @Author LiLuBing
     * @Date 2021-01-12 21:29
     * @Param  * @param userInfoPOList
     * @return {@link java.util.List<com.llb.cxy.domain.user.entity.UserInfo>}
     **/
    private List<UserInfo> getApprovalInfos(List<UserInfoPO> userInfoPOList){
        return userInfoPOList.stream()
                .map(userInfoPO -> createUserInfo(userInfoPO))
                .collect(Collectors.toList());
    }
}
