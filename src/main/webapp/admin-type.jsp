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
<link rel="stylesheet" type="text/css" href="css/admin/type.css" />
<link rel="stylesheet" type="text/css" href="datetimepicker/css/bootstrap-datetimepicker.css" />
<script type="text/javascript" src="vue/vue.js"></script>
<script type="text/javascript" src="jquery/jquery-3.1.1.js"></script>
<script type="text/javascript" src="vue/axios.min.js"></script>
<script type="text/javascript" src="js/common/alert.js"></script>
<script type="text/javascript" src="js/common/tool.js"></script>
<script type="text/javascript" src="js/admin/type.js"></script>
<script type="text/javascript" src="datetimepicker/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>

</head>
<body>
    <div  id="div_type">
    <div>
	    <div class="div_table">
		    <fieldset class="fieldset_news">
			    <legend>类别管理</legend>
			    <form role="form">
				  <div class="form-group">
				    <button type="button" class="btn btn-success btn-sm" v-on:click="" style="margin-left: 10px;" id="btn_add">
			          <span class="glyphicon glyphicon-plus"></span> 新增
			        </button>
				    <!-- <button type="button" class="btn btn-primary btn-sm" v-on:click="" style="margin-left: 10px;">
			          <span class="glyphicon glyphicon-search"></span> 搜索
			        </button> -->   
				  </div>
				</form>
			</fieldset>
	        
	        <table class="table table-bordered">
				<thead>
					<tr>
						<th>编号</th>
						<th>名称</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<tr v-for="single in list">
		                <td>{{single.code}}</td>
		                <td>{{single.name}}</td>
		                <td align="center">
		                    <button type="button" class="btn btn-warning btn-sm btn-xs" v-on:click="edit(single)">编辑</button>
							<button type="button" class="btn btn-danger btn-sm btn-xs" style="margin-left: 10px;" v-on:click="deleteOne(single.id)">删除</button>
		                </td>
		            </tr>
		            <tr><td colspan="7" v-show="list.length==0">{{message}}</td></tr>
				</tbody>
			</table>
	    </div>
		
	</div>
	
	<div class="bg" id="type_add">
		<div class="panel panel-default" style="position: absolute;  top: 25%;  left: 35%;  width: 500px;">
			<div class="panel-heading">
				<h3 class="panel-title">{{alert_panel.c_msg}}</h3><button type="button" class="close">×</button>
			</div>
			<div class="panel-body">
				<form role="form">
					 <table class="table table-bordered">
						  <caption>{{alert_panel.c_msg}}类型</caption>
						 <!--  <thead>
						    <tr>
						      <th>名称</th>
						      <th>城市</th>
						    </tr>
						  </thead> -->
						  <tbody>
						    <tr>
						      <td>编号：<input type="hidden" v-model="single.id" name="id"/></td>
						      <td><input type="text" class="form-control" placeholder="输入编号" v-model="single.code" name="code"></td>
						    </tr>
						    <tr>
						      <td>名称：</td>
						      <td><input type="text" class="form-control" placeholder="输入名称" v-model="single.name" name="name"></td>
						    </tr>
						    <!-- <tr>
						      <td>来源：</td>
						      <td><input type="text" class="form-control" placeholder="输入来源" v-model="news.source"></td>
						    </tr> -->
						    <tr>
						      <td colspan="2">
						            <button type="button" class="btn btn-primary btn-sm" v-on:click="saveOrUpdate">
				                        <span class="glyphicon glyphicon-floppy-disk"></span> 保存
				                    </button>
						      </td>
						    </tr>
						  </tbody>
				   </table>
				</form>
            </div>
			<!-- <div class="modal-footer">
				<button type="button" class="btn btn-primary btn-sm">确定</button><button type="button" class="btn btn-default btn-sm">取消</button>
			</div> -->
		</div>
	</div>
	</div>
</body>
</html>