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
<link rel="stylesheet" type="text/css" href="css/admin/news.css" />
<link rel="stylesheet" type="text/css" href="datetimepicker/css/bootstrap-datetimepicker.css" />
<script type="text/javascript" src="vue/vue.js"></script>
<script type="text/javascript" src="vue/axios.min.js"></script>
<script type="text/javascript" src="js/common/alert.js"></script>
<script type="text/javascript" src="js/common/tool.js"></script>
<script type="text/javascript" src="js/admin/news.js"></script>
<script type="text/javascript" src="jquery/jquery-3.1.1.js"></script>
<script type="text/javascript" src="datetimepicker/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>

</head>
<body>
    <div id="div_news">
	    <div class="div_table">
		    <fieldset class="fieldset_news">
			    <legend>新闻列表</legend>
			    <form role="form">
				  <div class="form-group">
				    <label for="name">标题：</label>
				    <input type="text" class="form-control"  placeholder="请输入标题" v-model="criteria.title"/>
				    <label for="name">编辑：</label>
				    <input type="text" class="form-control"  placeholder="请输入编辑" v-model="criteria.editor"/>
				    <label for="name">来源：</label>
				    <select class="form-control" v-model="criteria.source" style="display:inline-block;width: 120px;">
					    <option v-for="source in sourceList"  v-bind:value="source">{{source}}</option>
					</select> 
				    <label for="name">类型：</label>
					<select class="form-control" v-model="criteria.type" style="display:inline-block;width: 120px;">
					    <option v-for="type in typeList"  v-bind:value="type.id">{{type.name}}</option>
					</select>
				    <label for="name">起止时间：</label>
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
			          <!-- <span class="glyphicon glyphicon-search"></span> --> 重置
			        </button>            
				    
				  </div>
				</form>
			</fieldset>
	        <div class="table_menu">
	            <button type="button" class="btn btn-success btn-xs" v-on:click="add" style="margin-left: 2px;">
		          <span class="glyphicon glyphicon-plus"></span> 新增
		        </button>
		        <button type="button" class="btn btn-danger btn-xs" v-on:click="deleteSelect" style="margin-left: 2px;">
		          <span class="glyphicon glyphicon-trash"></span> 删除
		        </button>    
		        <!-- <a>
		          <span class="glyphicon glyphicon-trash"></span> 删除
		        </a> -->
			</div>
	        <table class="table table-striped">
				<thead class="fixedThead">
					<tr>
						<th style="width: 20px;"><input type="checkbox":value="check.allChecked" @change="checkAll"></th>
						<th style="width: 290px;">标题</th>
						<th>编辑</th>
						<th>来源</th>
						<th>浏览量</th>
						<th>时间</th>
						<th>最后修改时间</th>
						<th>类型</th>
						<th style="width: 238px;">操作</th>
					</tr>
				</thead>
				<tbody class="scrollTbody">
					<tr v-for="(news,i) in newsList">
		                <td style="width: 20px;"><input type="checkbox":value="news.id" v-model="check.selects"/></td>
		                <td style="width: 300px;">
		                   <!-- <div class="row imgfloat" v-show="news.imgsrc!='' && news.imgsrc!='0'">
							    <div class="col-sm-6 col-md-3">
							        <a href="#" class="thumbnail">
							            <img v-bind:src="news.imgsrc|singleImgSrc(news.imgsrc)"  v-bind:alt="news.imgsrc|singleImgSrc(news.imgsrc)">
							        </a>
							    </div>
							</div> -->	 
							
							<span class="pull-left" v-show="news.imgsrc!='' && news.imgsrc!='0'" style="color:#5BC0DE"><span class="glyphicon glyphicon-picture"></span></span>
		                   {{news.title}}            
		                </td>
		                <td>{{news.editor}}</td>
		                <td>{{news.source}}</td>
		                <td>{{news.browsenum}}</td>
		                <td>{{news.time | timeFormat(news.time)}}</td>
		                <td>{{news.modifiedtime | timeFormat(news.modifiedtime)}}</td>
		                <td>{{news.newsType.name}}</td>
		                <td align="center">
		                    <button type="button" class="btn btn-warning btn-xs" v-on:click="edit(news.id)">
		                        <span class="glyphicon glyphicon-edit"></span> 编辑
		                    </button>
		                    <button type="button" class="btn btn-success btn-xs" v-on:click="showDetail(news.id)" style="margin-left: 8px;">
		                        <span class="glyphicon glyphicon-eye-open"></span> 详情
		                    </button>
							<!-- <button type="button" class="btn btn-danger btn-xs" style="margin-left: 10px;" v-on:click="deleteOne(i)">删除</button> -->
		                </td>
		            </tr>
		            <tr><td colspan="9" v-show="newsList.length==0">{{msg.nodatas}}</td></tr>
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
			    <!-- <li class="active"><a href="#">1</a></li>
			    <li><a href="#">2</a></li>
			    <li><a href="#">3</a></li>
			    <li><a href="#">4</a></li>
			    <li><a href="#">5</a></li> -->
			    <li v-for="index in pag" @click="gotoPage(index)" :class="{'active':index == pageIndex}" :key="index">
	              <a href="javascript:void(0)" >{{index}}</a>
	            </li>
			    <li v-bind:class="{disabled: pageIndex>=totalPage}" v-on:click="pageIndex+1<=totalPage&&gotoPage(pageIndex+1)"><a href="javascript:void(0)">&raquo;</a></li>
			    <li v-bind:class="{disabled: pageIndex>=totalPage}" v-on:click="pageIndex+1<=totalPage&&gotoPage(totalPage)"><a href="javascript:void(0)">最后一页</a></li>
			 </ul>
			 <button type="button" class="btn btn-default btn-sm" style="background: white;" v-on:click="refresh">
	           <span class="glyphicon glyphicon-refresh"></span> 刷新
	         </button> 
	         <!-- <a href="#" class="btn btn-default btn-sm" style="background: white;">
	           <span class="glyphicon glyphicon-refresh"></span> 刷新
	         </a> -->
			 <div class="goto">
		         <p>跳转到第</p>
				 <input type="text" class="form-control" v-model.number="gtIndex" type="number" v-on:keyup.enter="jump"/>
				 <p>页</p>
		     </div>
           </div>
		</div>
		<!-- {{criteria.startTime}}
		{{criteria.endTime}} -->
	</div>
	
	<!-- <div class="bg" v-show="a_show" id="bg1">
	    <div class="panel panel-default show">
		    <div class="panel-heading">
		        <h3 class="panel-title">{{a_title}}</h3>
		        <button type="button" class="close" v-on:click="cancel">
					&times;
				</button>
		    </div>
		    <div class="panel-body">
		       <p>{{a_content}}</p>{{a_show}}
		    </div>
		    <div class="modal-footer">
				 <button type="button" class="btn btn-primary btn-sm" v-on:click="">确定</button>
		        <button type="button" class="btn btn-default btn-sm" v-on:click="cancel">取消</button>
			</div>
		</div>
	</div> -->
	
	<!-- <div class="my_show">
	    <div class="alert alert-success">成功！很好地完成了提交。</div>
	</div> -->
	<script type="text/javascript">
	
	</script>
	
</body>
</html>