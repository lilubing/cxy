package com.llb.cxy.mybatis.entity;

import javax.persistence.Transient;

/**
 * description: 基础信息 <br>
 * date: 2020/3/25 16:13 <br>
 * author: Administrator <br>
 * version: 1.0 <br>
 */
public class BaseEntity {

    @Transient
    private Integer page = 1;

    @Transient
    private Integer rows = 20;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }
}