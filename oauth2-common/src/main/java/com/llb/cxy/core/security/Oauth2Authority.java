package com.llb.cxy.core.security;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Date;

/**
 * 包名com.llb.cxy.core.security:com.czx.springbootwebservice
 * 类名Oauth2Authority:自定义已授权权限标识
 * 当前系统用户lilubing:llb
 * 日期2021/1/3:2020/8/8
 * 项目名cxy:springbootwebservice
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Oauth2Authority implements GrantedAuthority, Serializable {

    private static final long serialVersionUID = -4682269499506460314L;

    /**
     * 权限Id
     */
    private String authorityId;
    /**
     * 权限标识
     */
    private String authority;
    /**
     * 过期时间,用于判断权限是否已过期
     */
    private Date expireTime;

    /**
     * 权限所有者
     */
    private String owner;

    @JsonProperty("isExpired")
    public Boolean getIsExpired() {
        if (expireTime != null && System.currentTimeMillis() > expireTime.getTime()) {
            return true;
        }
        return false;
    }

    public Oauth2Authority() {
    }

    public Oauth2Authority(String authority) {
        Assert.hasText(authority, "A granted authority textual representation is required");
        this.authority = authority;
    }

    public Oauth2Authority(String authority, Date expireTime) {
        Assert.hasText(authority, "A granted authority textual representation is required");
        this.authority = authority;
        this.expireTime = expireTime;
    }

    public Oauth2Authority(String authorityId, String authority, Date expireTime, String owner) {
        this.authorityId = authorityId;
        this.authority = authority;
        this.expireTime = expireTime;
        this.owner = owner;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else {
            return obj instanceof Oauth2Authority ? this.authority.equals(((Oauth2Authority) obj).authority) : false;
        }
    }

    @Override
    public int hashCode() {
        return this.authority.hashCode();
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("authorityId", this.authorityId);
        jsonObject.put("authority", this.authority);
        return jsonObject.toString();
    }

    public String getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(String authorityId) {
        this.authorityId = authorityId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
