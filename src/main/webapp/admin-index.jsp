<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String contextPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
    pageContext.setAttribute("ctx", contextPath);
%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>后台主页</title>
<link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="css/admin/index.css" />
</head>
<body>
    <div class="main">
	   <div class="row" style="margin-bottom: -15px;">
	      <div class="col-md-*">
	         <nav class="navbar navbar-default" role="navigation"> 
			    <div class="container-fluid"> 
			        <div class="navbar-header"> 
			            <a class="navbar-brand" href="#">新闻发布系统</a> 
			        </div> 
			        <ul class="nav navbar-nav navbar-right"> 
			            <li><a href="javascript:void(0)"><span class="glyphicon glyphicon-user"></span> <span id="userName">${sessionScope.curManager}</span><!-- <img src="/wp-content/uploads/2014/06/download.png" class="img-circle"> --></a></li> 
			            <li><a href="manager/logout"><span class="glyphicon glyphicon-log-in"></span> 退出</a></li> 
			        </ul> 
			    </div> 
			</nav>
	      </div>      
	   </div>
	   <div class="row">
	      <div id="left" class="col-md-2">
	          <div id="menu" class="panel-group">
	              <div class="panel panel-default">
				    <div class="panel-heading">新闻管理</div>
				    <ul class="list-group">
				        <li class="list-group-item" title="类别管理" onclick="deal.rightShow('admin-type.jsp')">类别管理</li>
				        <li class="list-group-item" title="新闻管理" onclick="deal.rightShow('admin-news.jsp')">新闻管理</li>
				        <li class="list-group-item" title="评论管理" onclick="deal.rightShow('admin-comment.jsp')">评论管理</li>
				    </ul>
	              </div>
	              <div class="panel panel-default">
				    <div class="panel-heading">系统管理</div>
				    <ul class="list-group" style="display: none;">
				        <!-- <li class="list-group-item">管理员账号管理</li> -->
				        <li class="list-group-item" title="用户管理" onclick="deal.rightShow('admin-user.jsp')">用户管理</li>
				    </ul>
	              </div>
	          </div>
	          
	      </div>  
	      <div id="right" class="col-md-2 right">
	         <iframe src="admin-news.jsp"> 和 </iframe>
	      </div>  
       </div>
	</div>
	

<script type="text/javascript" src="jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="vue/vue.js"></script>
<script type="text/javascript" src="js/admin/index.js"></script>
<script type="text/javascript">
    var curManager = eval('(' + '${sessionScope.curManager}' + ')');
    console.log("curManager:"+JSON.stringify(curManager)); 
    $("#userName").text(curManager.userName);
 </script>
</body>
</html>