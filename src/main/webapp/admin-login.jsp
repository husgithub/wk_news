<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String contextPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
    pageContext.setAttribute("ctx", contextPath);
%>    

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="${ctx }"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员登录</title>
<link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.css" />
<script type="text/javascript" src="vue/vue.js"></script>
<script type="text/javascript" src="vue/axios.min.js"></script>
<script type="text/javascript" src="jquery/jquery-3.1.1.js"></script>
<script type="text/javascript" src="js/common/alert.js"></script>
<script type="text/javascript" src="js/common/tool.js"></script>
<script type="text/javascript" src="js/admin/login.js"></script>

<style type="text/css">
html, body {height:100%;}
.content{
    text-align: center;
    height:100%;
    background: url("${ctx }/img/admin/admin_login_bg.jpg") no-repeat;
    background-position: center;
}
.head{
    height: 120px;
}
.head h3{
    line-height: 120px;
    margin-top: 0px;
}
</style>
</head>
<body>
    <div class="content" id="admin_login">
	    <div class="head">
	        <h3>
			     欢迎使用新闻管理系统
			</h3>
	    </div>
	    <div class="well" style="width: 440px;margin: 0 auto;margin-top:50px;">
			<h4 style="margin-bottom: 20px;">管理员登录</h4>
			<div class="input-group input-group-md">
			<span class="input-group-addon" id="sizing-addon1"><i class="glyphicon glyphicon-user" aria-hidden="true"></i></span>
			<input type="text" class="form-control" placeholder="用户名" aria-describedby="sizing-addon1" name="userName" v-model="data.userName" v-on:keyup.enter="login"/>
			</div>
			<div class="input-group input-group-md">
			<span class="input-group-addon" id="sizing-addon1"><i class="glyphicon glyphicon-lock"></i></span>
			<input type="password" class="form-control" placeholder="密码" aria-describedby="sizing-addon1" name="password" v-model="data.password"  v-on:keyup.enter="login" />
			</div>
			<div class="well well-sm" style="text-align:center;">
			  <div class="checkbox"  style="margin-top: 0px; margin-bottom: 0px;text-align: left;padding-left: 20px;">
			    <input type="checkbox" name="autoLogin" v-model="data.autoLogin" />下次自动登录<span v-html="msg.error" style="position: absolute;right: 0px;">{{msg.error}}</span>
			  </div>
			</div>
			<button type="submit" class="btn btn-success btn-block" v-on:click="login">
			登录
			</button>
		</div>
    </div>
    
</body>
</html>