package com.llb.cxy.core;

import com.alibaba.fastjson.JSONObject;

/**
 * @author LiLuBing
 * @ClassName: SystemContext
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2015年4月3日 下午2:05:20
 */
public class SystemContext {
    private static ThreadLocal<Integer> offset = new ThreadLocal<Integer>();
    private static ThreadLocal<Integer> pageSize = new ThreadLocal<Integer>();
//    private static ThreadLocal<WebUserDetail> userInfo = new ThreadLocal<WebUserDetail>();
    private static ThreadLocal<JSONObject> userInfo = new ThreadLocal<JSONObject>();
    private static ThreadLocal<String> sort = new ThreadLocal<String>();
    private static ThreadLocal<String> order = new ThreadLocal<String>();
    private static ThreadLocal<String> moduleId = new ThreadLocal<String>();
    private static ThreadLocal<String> columnNodeId = new ThreadLocal<String>();
    private static ThreadLocal<String> tableName = new ThreadLocal<String>();
    private static ThreadLocal<String> ip = new ThreadLocal<String>();

    public static void setUserInfo(JSONObject v) {
        userInfo.set(v);
    }

    public static JSONObject getUserInfo() {
        return userInfo.get();
    }

    public static void removeUserInfo() {
        userInfo.remove();
    }

    public static void setOffset(int _offset) {
        offset.set(_offset);
    }

    public static int getOffset() {
        Integer os = (Integer) offset.get();
        if (os == null) {
            return 1;
        }
        return os;
    }

    public static void removeOffset() {
        offset.remove();
    }

    public static void setPageSize(int _pageSize) {
        pageSize.set(_pageSize);
    }

    public static int getPageSize() {
        Integer ps = (Integer) pageSize.get();
        if (ps == null) {
            return 20;
        }
        return ps;
    }

    public static void removePageSize() {
        pageSize.remove();
    }

    public static void setSort(String _sort) {
        sort.set(_sort);
    }

    public static String getSort() {
        String os = (String) sort.get();
        return os;
    }

    public static void removeSort() {
        sort.remove();
    }

    public static String getOrder() {
        String os = (String) order.get();
        return os;
    }

    public static void setOrder(String _order) {
        order.set(_order);
    }

    public static void removeOrder() {
        order.remove();
    }

    public static void setModuleId(String _moduleId) {
        moduleId.set(_moduleId);
    }

    public static String getModuleId() {
        String ps = (String) moduleId.get();
        return ps;
    }

    public static void removeModuleId() {
        moduleId.remove();
    }

    public static String getColumnNodeId() {
        String _columnNodeId = String.valueOf(columnNodeId.get());
        return _columnNodeId;
    }

    public static void setColumnNodeId(String _columnNodeId) {
        columnNodeId.set(_columnNodeId);
    }

    public static void removeColumnNodeId() {
        columnNodeId.remove();
    }

    public static String getTableName() {
        String _tableName = String.valueOf(tableName.get());
        return _tableName;
    }

    public static void setTableName(String _tableName) {
        tableName.set(_tableName);
    }

    public static void removeTableName() {
        tableName.remove();
    }

    public static String getIp() {
        return ip.get();
    }

    public static void setIp(String _ip) {
        ip.set(_ip);
    }

    public static void removeIp() {
        ip.remove();
    }
}