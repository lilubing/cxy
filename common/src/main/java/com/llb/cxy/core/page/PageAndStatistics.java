package com.llb.cxy.core.page;

import java.io.Serializable;
import java.util.List;

public final class PageAndStatistics implements Serializable {

    private static final long serialVersionUID = -4392289858660025690L;

    // 当前页数
    private long page;

    // 总条数
    private long total;

    // 当前页记录
    private List<Object> rows;

    private Object statistics;

    public PageAndStatistics(long page, long total) {
        this.page = page;
        this.total = total;
    }

    public PageAndStatistics(long page, long total, List<Object> rows) {
        this.page = page;
        this.total = total;
        this.rows = rows;
    }

    public PageAndStatistics(long page, long total, List<Object> rows, Object statistics) {
        this.page = page;
        this.total = total;
        this.rows = rows;
        this.statistics = statistics;
    }

    /**
     * 取得当前页号
     *
     * @return
     */
    public long getPage() {
        return page;
    }

    /**
     * 设置当前页号
     *
     * @param page
     */
    public void setPage(long page) {
        this.page = page;
    }

    /**
     * 取得总条数
     *
     * @return
     */
    public long getTotal() {
        return total;
    }

    /**
     * 设置总条数
     *
     * @param total
     */
    public void setTotal(long total) {
        this.total = total;
    }

    /**
     * 取得结果
     *
     * @return
     */
    public List<Object> getRows() {
        return rows;
    }

    /**
     * 设置结果
     *
     * @param rows
     */
    public void setRows(List<Object> rows) {
        this.rows = rows;
    }

    /**
     * 取得统计结果
     *
     * @return
     */
    public Object getStatistics() {
        return this.statistics;
    }

    /**
     * 设置统计结果
     *
     * @param statistics
     */
    public void setStatistics(Object statistics) {
        this.statistics = statistics;
    }
}