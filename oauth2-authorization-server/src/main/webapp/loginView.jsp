<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html lang="en"
	class="app js no-touch no-android chrome no-firefox no-iemobile no-ie no-ie10 no-ie11 no-ios no-ios7 ipad">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Google Chrome Frame也可以让IE用上Chrome的引擎: -->
<meta http-equiv="X-UA-Compatible" content="IE=edge;chrome=1">
<meta name="renderer" content="webkit">
<title>登录－管理系统</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script type="application/javascript">
//根路径
var global_rootPath = "${ctx}";
</script>
<link rel="stylesheet"
	href="${ctx}/css/login/login.css">
<script src="${ctx}/resource/jquery/jquery-1.11.3.min.js" type="application/javascript"></script>	
<script src="${ctx}/js/login/login.js" type="application/javascript"></script>	
<!--[if lt IE 9]> 
	<script src="${ctx}/resource/jquery/ie/html5shiv.js" type="application/javascript"></script>
	<script src="${ctx}/resource/jquery/ie/respond.min.js" type="application/javascript"></script>
<![endif]-->
</head>
<body>
	<div class="top_div"></div>
	<div class="content_div">
		<div class="longin_div_1">
			<div class="tou"></div>
			<div class="initial_left_hand" id="left_hand"></div>
			<div class="initial_right_hand" id="right_hand"></div>
		</div>
	<form id="form1" onsubmit="return false" action="##" method="post">
		<p class="longin_p_1">
			<span class="u_logo"></span> 
			<input class="ipt" name="username" id="username" type="text" placeholder="请输入用户名或邮箱" value="">
		</p>
		<P class="longin_p_2">
			<span class="p_logo"></span>
			<input class="ipt" name="password" id="password" type="password" placeholder="请输入密码" value="">
		</P>
		<div class="button_div">
			<p class="longin_p_3">
				<span class="longin_span_1">
<!-- 					<a style="color: rgb(204, 204, 204);" href="#">忘记密码?</a> -->
				</span>
				<span class="longin_span_2">
<!-- 					<a style="color: rgb(204, 204, 204); margin-right: 10px;" href="#">注册</a> -->
					<a class="longin_a_1" href="#">登录</a> 
				</span>
			</p>
		</div>
	</form>
	</div>
	<input type="hidden" id="userSessionName" value="${userSessionName}">
</body>