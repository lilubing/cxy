package com.llb.cxy.core.page;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.JSONObject;
import com.llb.cxy.core.SystemContext;
import com.llb.cxy.core.utils.IpUtils;

/**
 * 分页控件
 *
 * @author lilubing
 *
 */
@Configuration
@WebFilter(filterName="pagerFilter", urlPatterns = {
        "/*"})
public class PagerFilter implements Filter {

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        SystemContext.setOffset(getOffset(httpRequest));
        SystemContext.setPageSize(getPagesize(httpRequest));
        SystemContext.setOrder(getOrder(httpRequest));
        SystemContext.setSort(getSort(httpRequest));
        SystemContext.setUserInfo(getUserInfo());

        SystemContext.setModuleId(getModulId(httpRequest));
        SystemContext.setColumnNodeId(getColumnNodeId(httpRequest));
        SystemContext.setTableName(getTableName(httpRequest));
        SystemContext.setIp(getIp(httpRequest));

        try {
            chain.doFilter(request, response);
        } finally {
            SystemContext.removeOffset();
            SystemContext.removePageSize();
            SystemContext.removeUserInfo();
            SystemContext.removeOrder();
            SystemContext.removeSort();
            SystemContext.removeModuleId();
            SystemContext.removeColumnNodeId();
            SystemContext.removeTableName();
            SystemContext.removeIp();
        }
    }

    private String getIp(HttpServletRequest httpRequest) {

        return IpUtils.getIpAddr(httpRequest);
    }

    private String getSort(HttpServletRequest request) {
        String psvalue = "";
        try {
            psvalue = request.getParameter("sort");
        } catch (Exception ignore) {
        }
        return psvalue;
    }

    private String getOrder(HttpServletRequest request) {
        String psvalue = "";
        try {
            psvalue = request.getParameter("order");
        } catch (Exception ignore) {
        }
        return psvalue;
    }

    private int getOffset(HttpServletRequest request) {
        int offset = 0;
        try {
            offset = Integer.parseInt(request.getParameter("page"));
        } catch (Exception ignore) {
        }
        return offset;
    }

    private int getPagesize(HttpServletRequest httpRequest) {
        // 首先判断request中是否有pagesize参数，如果有这个参数，证明客户端正在请求改变每页显示的行数
        String psvalue = httpRequest.getParameter("limit");
        Integer pagesize = 20;
        if (psvalue != null && !psvalue.trim().equals("")) {
            try {
                pagesize = Integer.parseInt(psvalue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return pagesize;
    }

    private JSONObject getUserInfo() {
        JSONObject user = null;
        try {
            //无法使用类
            //从Header中获取用户信息
            // ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            // HttpServletRequest request = servletRequestAttributes.getRequest();
            // user = JSON.parseObject(request.getHeader("user"));

            // user = Oauth2Helper.getUser();
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
        return user;
    }

    private String getColumnNodeId(HttpServletRequest request){
        String psvalue = "";
        try {
            psvalue = request.getParameter("columnNodeId");
        } catch (Exception ignore) {
        }
        return psvalue;
    }

    private String getModulId(HttpServletRequest request){
        String psvalue = "";
        try {
            psvalue = request.getParameter("moduleId");
        } catch (Exception ignore) {
        }
        return psvalue;
    }

    private String getTableName(HttpServletRequest request) {
        String _tableName = "";
        try {
            _tableName = request.getParameter("tableName");
        } catch (Exception ignore) {
        }
        return _tableName;
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {}

}