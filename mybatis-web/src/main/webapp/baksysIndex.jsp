<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/WEB-INF/page/common/common_easyui.jspf"%>
    <title>权限管理</title>
	<link rel="stylesheet" href="${ctx}/resource/easyui/themes/default/easyui.css" type="text/css"></link>
	<link rel="stylesheet" href="${ctx}/resource/easyui/themes/icon.css" type="text/css"></link>
	
	<script type="text/javascript" src="${ctx}/resource/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${ctx}/resource/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${ctx}/resource/jquery/ajaxSetupExtension.js"></script>
	<!--[if lt IE 9]>
	    <script type="text/javascript" src="${ctx}/resource/jquery/json.js"></script>
	<![endif]-->
	<script type="text/javascript" src="${ctx}/js/baksysIndex.js"></script>
</head>
<body>
    <div id="index_easyui-layout" style="height:700px;">
        <div data-options="region:'north'" style="height:50px">
        	<%--Hello, <shiro:principal/> <a href="${ctx}/login/logout.action" >退出登录</a>--%>
        </div>
        <div data-options="region:'south'" style="height:50px;"></div>
        <div data-options="region:'west'" style="width:150px;">
<%-- 	        <shiro:hasRole name="autmgt-usermgt"> --%>
	        	<a href="#" class="easyui-linkbutton" data-options="plain:true" style="width:80px" 
		    		onclick="index_addTab('用户管理', '/jsp/system/user/user_List.jsp');">用户管理</a>
<%-- 		    </shiro:hasRole> --%>
<%-- 		    <shiro:hasRole name="autmgt-rolemgt"> --%>
		    	<a href="#" class="easyui-linkbutton" data-options="plain:true" style="width:80px" 
		    		onclick="index_addTab('角色管理', '/jsp/system/user/role_List.jsp');">角色管理</a>
<%-- 		    </shiro:hasRole> --%>
<%-- 		    <shiro:hasRole name="autmgt-menumgt"> --%>
		    	<a href="#" class="easyui-linkbutton" data-options="plain:true" style="width:80px" 
		    		onclick="index_addTab('菜单管理', '/jsp/system/user/menu_List.jsp');">菜单管理</a>
<%-- 		    </shiro:hasRole> --%>
<%-- 		    <shiro:hasRole name="autmgt-buttonmgt"> --%>
		    	<a href="#" class="easyui-linkbutton" data-options="plain:true" style="width:80px" 
		    		onclick="index_addTab('按钮管理', '/jsp/system/user/button_List.jsp');">按钮管理</a>
<%-- 		    </shiro:hasRole> --%>
        </div>
        <div data-options="region:'center'">
            <div id="index_tabs" class="easyui-tabs" data-options="tools:'#tab-tools'" 
	    		style="height: 600px;">
	    	</div>
        </div>
    </div>
    
    <div id="dialog_1" class="easyui-window" title="dialog" 
    	data-options="closed:true,modal:true,collapsible:false" 
    	style="width: 670px; padding: 10px;">
		<iframe id="iframe_1" name="iframe_1" src="" width="100%" height="100%" scrolling="no" frameborder="0"></iframe>
	</div>
</body>
</html>