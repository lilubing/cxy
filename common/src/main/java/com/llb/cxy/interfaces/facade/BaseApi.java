package com.llb.cxy.interfaces.facade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.llb.cxy.core.SystemContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LiLuBing
 * @PackageName: com.llb.cxy.interfaces.facade
 * @Description: 基础Api类
 * @ClassName: BaseApi
 * @date 2021-01-16 下午8:38
 * @ProjectName cxy
 * @Version V1.0
 */
public class BaseApi {
    public JSONObject getUserInfo() {
        //从Header中获取用户信息
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        JSONObject jsonObject = JSON.parseObject(request.getHeader("user"));
        SystemContext.setUserInfo(jsonObject);
        return jsonObject;
    }
}
