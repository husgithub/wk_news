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
<link rel="stylesheet" type="text/css" href="css/common/common.css" />
<link rel="stylesheet" type="text/css" href="css/admin/newsAdd.css" />
<link rel="stylesheet" type="text/css" href="datetimepicker/css/bootstrap-datetimepicker.css" />
<script type="text/javascript" src="vue/vue.js"></script>
<script type="text/javascript" src="vue/axios.min.js"></script>
<script type="text/javascript" src="jquery/jquery-3.1.1.js"></script>
<script type="text/javascript" src="js/common/alert.js"></script>
<script type="text/javascript" src="js/common/tool.js"></script>
<script type="text/javascript" src="datetimepicker/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<!-- ueditor -->
<script type="text/javascript" charset="utf-8" src="ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="ueditor/lang/zh-cn/zh-cn.js"></script>

<%
    String pageType = request.getParameter("pageType");
    pageContext.setAttribute("pageType", pageType);
%>
</head>
<body>
    <div id="div_newsAdd">
	    <form role="form">
			 <table class="table table-bordered">
				 <caption>详情</caption>
				 <!--  <thead>
				    <tr>
				      <th>名称</th>
				      <th>城市</th>
				    </tr>
				  </thead> -->
				  <tbody>
				    <tr>
				      <td>标题：</td>
				      <td><input type="text" class="form-control" placeholder="输入标题" v-model="news.title"></td>
				    </tr>
				    <tr>
				      <td>编辑：</td>
				      <td><input type="text" class="form-control" placeholder="输入编辑" v-model="news.editor"></td>
				    </tr>
				    <tr>
				      <td>来源：</td>
				      <td><input type="text" class="form-control" placeholder="输入来源" v-model="news.source"></td>
				    </tr>
				    <tr>
				      <td>类型：</td>
				      <td>
				          <select class="form-control" v-model="news.type">
							<option v-for="type in types"  v-bind:value="type.id">{{type.name}}</option>
						  </select> 
				      </td>
				    </tr>
				    <tr>
				      <td>内容：</td>
				      <td>
				          <div v-model="news.detail"></div>
				      </td>
				    </tr>
				    
				  </tbody>
		   </table>
		</form>
	</div>
	<script type="text/javascript" src="js/admin/newsAdd.js"></script>
	
</body>
</html>