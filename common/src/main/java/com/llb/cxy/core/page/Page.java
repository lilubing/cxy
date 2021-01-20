package com.llb.cxy.core.page;

import java.io.Serializable;
import java.util.List;

public class Page<T> implements Serializable {

    private static final long serialVersionUID = -4392289858660025690L;

    // 当前页数
    private int page;

    private int limit;

    // 总条数
    private int total;

    // 当前页记录
    private List<T> rows;

    public Page() {
    }

    public Page(int page, int total) {
        this.page = page;
        this.total = total;
    }

    public Page(int page, int total, List<T> rows) {
        this.page = page;
        this.total = total;
        this.rows = rows;
    }

    /**
     * 取得当前页号
     *
     * @return
     */
    public int getPage() {
        return page;
    }

    /**
     * 设置当前页号
     *
     * @param page
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * 取得总条数
     *
     * @return
     */
    public int getTotal() {
        return total;
    }

    /**
     * 设置总条数
     *
     * @param total
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * 取得结果
     *
     * @return
     */
    public List<T> getRows() {
        return rows;
    }

    /**
     * 设置结果
     *
     * @param rows
     */
    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    /**
     * 取得显示条数
     *
     * @return
     */
    public int getLimit() {
        return limit;
    }

    /**
     * 设置显示条数
     *
     * @param limit
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }
}