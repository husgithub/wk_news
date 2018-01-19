<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String contextPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
    pageContext.setAttribute("ctx", contextPath);
%>
<!DOCTYPE html>
<html>
<head lang="zh_cn">
    <meta charset="UTF-8">
    <base href="${ctx }"/>
    <title>{{news.title}}</title>
    
    <link rel="stylesheet" href="css/user/iconfont.css"/>
    <link rel="stylesheet" href="css/user/common.css"/>
    <link rel="stylesheet" href="css/user/user-index.css"/>
    <link rel="stylesheet" href="css/user/news.css"/>
    <script type="text/javascript" src="jquery/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="vue/axios.min.js"></script>
    <script type="text/javascript" src="vue/vue.js"></script>
    <script type="text/javascript" src="vue/vuex.js"></script>
    <script type="text/javascript" src="js/common/tool.js"></script>
    <script type="text/javascript" src="js/user/header.js"></script>
    <script type="text/javascript" src="js/user/detail.js"></script>
    
</head>
<body>
    <div id="news_detail">
	    <div class="content">
	        
	    <my-login></my-login>
    </div>
</body>
</html>