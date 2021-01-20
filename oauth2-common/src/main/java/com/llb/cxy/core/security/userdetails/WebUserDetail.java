package com.llb.cxy.core.security.userdetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Lists;
import com.llb.cxy.core.convert.LongToStringJsonSerializer;
import com.llb.cxy.core.security.Oauth2Authority;

/**
 * 包名com.llb.cxy.core.security.userdetails:com.czx.springbootwebservice
 * 类名WebUserDetail:扩展默认的 UserDetails 支持更多自定义的字段
 * 当前系统用户lilubing:llb
 * 日期2021/1/3:2020/8/8
 * 项目名cxy:springbootwebservice
 */
public class WebUserDetail implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @JsonSerialize(using = LongToStringJsonSerializer.class)
    private Long id;

    /** 用户登录名 */
    private String userName;

    /** 密码 */
    private String password;

    /** 用户状态：0正常，1锁定，2逾期 */
    private Integer status;

    /** 微信的openid */
    private String otherId;

    /** 认证客户端ID */
    private String clientId;

    /** 认证中心域,适用于区分多用户源,多认证中心域 */
    private String domain;

    /** 是否已锁定 */
    private boolean accountNonLocked;
    /** 是否已过期 */
    private boolean accountNonExpired;
    /** 是否启用 */
    private boolean enabled;
    /** 密码是否已过期 */
    private boolean credentialsNonExpired;
    /** 真实名称 */
    private String realName;
    /** 账户类型 登录类型:password-密码、mobile-手机号、email-邮箱、weixin-微信、weibo-微博、qq-等等 */
    private String accountType;
    @JsonSerialize(using = LongToStringJsonSerializer.class)
    private Long orgId;
    /** 用户附加属性 */
    private Map<String, Object> attrs;

    private Collection<? extends GrantedAuthority> authorities;

    public WebUserDetail() {}

    public WebUserDetail(Map<String, Object> userPO, Collection<Oauth2Authority> authorities) {
        // this.user = user;
        this.id = (Long) userPO.get("id");
        this.userName = (String) userPO.get("userName");
        this.password = (String) userPO.get("password");
        // this.status = (Integer) userPO.get("status");
        this.otherId = (String) userPO.get("otherId");
        this.authorities = authorities;
    }

    public WebUserDetail(Map<String, Object> userPO, List<String> authorities) {
        // this.user = user;
        this.id = (Long) userPO.get("id");
        this.userName = (String) userPO.get("userName");
        this.password = (String) userPO.get("password");
        // this.status = (Integer) userPO.get("status");
        this.otherId = (String) userPO.get("otherId");
        if (!authorities.isEmpty()) {
            List<SimpleGrantedAuthority> finalAuthorities = Lists.newArrayListWithCapacity(authorities.size());
            authorities.forEach(item -> finalAuthorities.add(new SimpleGrantedAuthority(item)));
            this.authorities = finalAuthorities;
        }
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    /**
     * 获取权限信息
     *
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities == null) {
            return Collections.EMPTY_LIST;
        }
        return this.authorities;
    }

    @JsonIgnore
    @JSONField(serialize = false)
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 用户账号是否过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 用户账号是否被锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 用户密码是否过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 用户是否可用
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * get id
     *
     * @return id
     */
    @Id
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    /**
     * set id
     *
     * @param id
     *            id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * set 密码
     *
     * @param password
     *            密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * get 用户状态：0正常，1锁定，2逾期
     *
     * @return 用户状态：0正常，1锁定，2逾期
     */

    public Integer getStatus() {
        return this.status;
    }

    /**
     * set 用户状态：0正常，1锁定，2逾期
     *
     * @param status
     *            用户状态：0正常，1锁定，2逾期
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * get 微信的openid
     *
     * @return 微信的openid
     */

    public String getOtherId() {
        return otherId;
    }

    /**
     * set 微信的openid
     *
     * @param otherId
     *            微信的openid
     */
    public void setOtherId(String otherId) {
        this.otherId = otherId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realname) {
        this.realName = realName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Map<String, Object> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<String, Object> attrs) {
        this.attrs = attrs;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public static WebUserDetail mapToObject(Map map) {
        WebUserDetail user = new WebUserDetail();
        user.setId(Long.valueOf((String)map.get("id")));
        user.setUserName((String)map.get("username"));
        user.setPassword((String)map.get("password"));
        user.setStatus((Integer)map.get("status"));
        user.setOtherId((String)map.get("otherId"));
        user.setClientId((String)map.get("clientId"));
        user.setDomain((String)map.get("domain"));
        user.setAccountNonLocked((Boolean)map.get("accountNonLocked"));
        user.setAccountNonExpired((Boolean)map.get("accountNonExpired"));
        user.setEnabled((Boolean)map.get("enabled"));
        user.setCredentialsNonExpired((Boolean)map.get("credentialsNonExpired"));
        user.setRealName((String)map.get("realName"));
        user.setAccountType((String)map.get("accountType"));
        user.setOrgId(Long.valueOf((String)(map.get("orgId") == null ? "0" : map.get("orgId"))));
        user.setAttrs((Map<String, Object>)map.get("attrs"));
        return user;
    }
}