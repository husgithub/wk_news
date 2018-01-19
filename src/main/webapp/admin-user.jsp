<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String contextPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
    pageContext.setAttribute("ctx", contextPath);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>后台主页</title>
<link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="css/common/common.css" />
<link rel="stylesheet" type="text/css" href="css/admin/news.css" />
<link rel="stylesheet" type="text/css" href="datetimepicker/css/bootstrap-datetimepicker.css" />
<script type="text/javascript" src="vue/vue.js"></script>
<script type="text/javascript" src="vue/vuex.js"></script>
<script type="text/javascript" src="vue/axios.min.js"></script>
<script type="text/javascript" src="js/common/alert.js"></script>
<script type="text/javascript" src="js/common/tool.js"></script>
<script type="text/javascript" src="js/admin/user.js"></script>
<script type="text/javascript" src="jquery/jquery-3.1.1.js"></script>
<script type="text/javascript" src="datetimepicker/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>

</head>
<body>
    <div id="div_comment">
	    <div class="div_table">
		    <fieldset class="fieldset_news">
			    <legend>用户列表</legend>
			    <form role="form">
				  <div class="form-group">
				    <label for="name">账号：</label>
				    <input type="text" class="form-control"  placeholder="请输入标题" v-model="criteria.user_name"/>
				    <!-- <label for="name">编辑：</label>
				    <input type="text" class="form-control"  placeholder="请输入编辑" v-model="criteria.editor"/> -->
				    
				    <label for="name">注册时间：</label>
				    <div class="input-append date" id="startTime" data-date="" data-date-format="yyyy-mm-dd hh:ii" style="display: inline-block;">
					    <input class="span2" size="16" type="text" value="" style="width: 120px;">
					    <span class="add-on"><i class="icon-remove"></i></span>
					    <span class="add-on"><i class="icon-th"></i></span>
					</div>     
					-            
				    <div class="input-append date" id="endTime" data-date="" data-date-format="yyyy-mm-dd hh:ii" style="display: inline-block;">
					    <input class="span2" size="16" type="text" value="" style="width: 120px;">
					    <span class="add-on"><i class="icon-remove"></i></span>
					    <span class="add-on"><i class="icon-th"></i></span>
					</div>     
					<button type="button" class="btn btn-primary btn-sm" v-on:click="query" style="margin-left: 10px;">
			          <span class="glyphicon glyphicon-search"></span> 搜索
			        </button>            
					<button type="button" class="btn btn-sm" v-on:click="reset" style="margin-left: 10px;">
			                    重置
			        </button>            
				    
				  </div>
				</form>
			</fieldset>
	        <div class="table_menu" style="height: 20px;">
	            <!-- <button type="button" class="btn btn-success btn-xs" v-on:click="add" style="margin-left: 2px;">
		          <span class="glyphicon glyphicon-plus"></span> 新增
		        </button> -->
		        <!-- <button type="button" class="btn btn-danger btn-xs" v-on:click="deleteSelect" style="margin-left: 2px;">
		          <span class="glyphicon glyphicon-trash"></span> 删除
		        </button> -->    
		        <!-- <a>
		          <span class="glyphicon glyphicon-trash"></span> 删除
		        </a> -->
			</div>
	        <table class="table table-striped">
				<thead class="fixedThead">
					<tr>
						<th style="width: 20px;"><input type="checkbox":value="check.allChecked" @change="checkAll"></th>
						<th>头像</th>
						<th>账号</th>
						<th>名字</th>
						<th>状态</th>
						<th>注册时间</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody class="scrollTbody">
					<tr v-for="(data,i) in dataList">
		                <td style="width: 20px;"><input type="checkbox":value="data.id" v-model="check.selects"/></td>
		                <td>
		                   <div>
		                      <img alt="" v-bind:src="data.avatar" style="width: 30px;height: 30px;border-radius: 50%;"/>
		                   </div>            
		                </td>
		                <td>{{data.user_name}}</td>
		                <td>{{data.name}}</td>
		                <td>
		                	 <span v-if="data.status == 1" style="color:blue">正常</span>
						     <span v-else style="color:red">禁用</span>
		                </td>
		                <td>{{data.regtime|timeFormat(data.regtime)}}</td>
		                <td align="center">
		                    <button type="button" class="btn btn-danger btn-xs" v-on:click="lock(data.id,0)" v-show="data.status == 1">
		                        <span class="glyphicon glyphicon-remove-sign"></span> 禁用
		                    </button>
		                    <button type="button" class="btn btn-success btn-xs" v-on:click="lock(data.id,1)" v-show="data.status != 1">
		                        <span class="	glyphicon glyphicon-ok-sign"></span> 激活
		                    </button>
							<!-- <button type="button" class="btn btn-danger btn-xs" style="margin-left: 10px;" v-on:click="deleteOne(i)">删除</button> -->
		                </td>
		            </tr>
		            <tr><td colspan="9" v-show="dataList.length==0">{{msg.nodatas}}</td></tr>
				</tbody>
			</table>
	    </div>
		<div class="page">
		   <div class="left">
		     <p>显示{{msg.curRecord}}条，共{{total}}条记录&nbsp;&nbsp;</p>
		     <p>每页显示</p>
	         <select class="form-control" v-model="pageSize" @change="pageSizeChange()">
			      <option value="10">10</option>
			      <option value="20"  selected="selected">20</option>
			      <option value="30">30</option>
			      <option value="50">50</option>
			      <option value="100">100</option>
			 </select>
			 <p>条记录</p>
		   </div>
		   <div class="right">
		     
		     <ul class="pagination pagination-sm">
		        <li v-bind:class="{disabled: pageIndex<=1}" v-on:click="pageIndex-1>=1&&gotoPage(1)"><a href="javascript:void(0)">第一页</a></li>
			    <li v-bind:class="{disabled: pageIndex<=1}" v-on:click="pageIndex-1>=1&&gotoPage(pageIndex-1)"><a href="javascript:void(0)">&laquo;</a></li>
			    
			    <li v-for="index in pag" @click="gotoPage(index)" :class="{'active':index == pageIndex}" :key="index">
	              <a href="javascript:void(0)" >{{index}}</a>
	            </li>
			    <li v-bind:class="{disabled: pageIndex>=totalPage}" v-on:click="pageIndex+1<=totalPage&&gotoPage(pageIndex+1)"><a href="javascript:void(0)">&raquo;</a></li>
			    <li v-bind:class="{disabled: pageIndex>=totalPage}" v-on:click="pageIndex+1<=totalPage&&gotoPage(totalPage)"><a href="javascript:void(0)">最后一页</a></li>
			 </ul>
			 <button type="button" class="btn btn-default btn-sm" style="background: white;" v-on:click="refresh">
	           <span class="glyphicon glyphicon-refresh"></span> 刷新
	         </button> 
	         
			 <div class="goto">
		         <p>跳转到第</p>
				 <input type="text" class="form-control" v-model.number="gtIndex" type="number" v-on:keyup.enter="jump"/>
				 <p>页</p>
		     </div>
           </div>
		</div>
		
		<div class="bg" id="" v-show="bg.show" style="display: block;">
			<div class="panel panel-default" style="position: absolute;  top: 25%;  left: 35%;  width: 500px;">
				<div class="panel-heading">
					<h3 class="panel-title">评论内容</h3><button type="button" class="close" @click="close">×</button>
				</div>
				<div class="panel-body">
					<div v-html="bg.content"></div>
	            </div>
				<!-- <div class="modal-footer">
					<button type="button" class="btn btn-primary btn-sm">确定</button><button type="button" class="btn btn-default btn-sm">取消</button>
				</div> -->
			</div>
		</div>
		
		
	</div>
	
	
	
</body>
</html>