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
    <title>我的</title>
    
    <link rel="stylesheet" href="css/user/iconfont.css"/>
    <link rel="stylesheet" href="css/user/common.css"/>
    <link rel="stylesheet" href="css/user/user-index.css"/>
    <link rel="stylesheet" href="css/user/mine.css"/>
    <style type="text/css">
	    body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,code,form,fieldset,legend,input,textarea,p,blockquote,th,td{
		    margin: 0px;
		    padding:0px
		}
    </style>
    <script type="text/javascript" src="jquery/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="vue/axios.min.js"></script>
    <script type="text/javascript" src="vue/vue.js"></script>
    <script type="text/javascript" src="vue/vuex.js"></script>
    <script type="text/javascript" src="js/common/tool.js"></script>
    <script type="text/javascript">
	    var curUser = eval('(' + '${sessionScope.curUser}' + ')');
	    console.log("curUser:"+JSON.stringify(curUser));  
    </script>
    <script type="text/javascript" src="js/user/mine.js"></script>
    <script type="text/javascript" src="js/user/header.js"></script>
    
</head>
<body>
    <div id="news_mine">
	    <my-header></my-header>
	    <my-login></my-login>
	    <div class="content">
	        <!-- <div class="zuo">
	
	        </div> -->
	        <div class="main" style="width: unset;float: none;margin-right: unset;">
	            <div class="mine">
	            	<div class="left">
	            		<div class="menu">
	            			<a title="我的" v-bind:class="{a_active : div_index == 1}" v-on:click="div_index=1">我的</a>
	            			<a title="我的评论" v-bind:class="{a_active : div_index == 2}" v-on:click="div_index=2">我的评论</a>
	            			<!-- <a title="我的回复" v-bind:class="{a_active : div_index == 3}" v-on:click="div_index=3">我的回复</a> -->
	            		</div>
	            	</div>
	            	<div class="right">
	            		<div class="info" v-show="div_index == 1">
	            			<div class="bigbox">
	            			    <div class="a_box">
	            					<div class="avatar">
	            						<img alt="" v-bind:src="mine.avatar" />
	            					</div>
	            					<div class="upload">
	            						<input type="file" title="浏览" accept="image/gif,image/jpeg,image/png" name="avatar" multiple="multiple" v-on:change="getFile($event)">
								    	<input type="button" value="上传图片" v-on:click="uploadAvatar"/>
								    	<span style="color: red;margin-left: 10px;">{{upload.error}}</span>
		            					<%-- <form name="Form2" action="${ctx}upload/avatar" method="post"  enctype="multipart/form-data" v-on:click="uploadAvatar();">
										</form> --%>
	            					</div>
	            					<a v-on:click="updateMsg" href="javascript:void(0);" class="save" style="float: right;line-height: 80px;" v-show="upload.files.length>0">保存修改 </a>
	            				</div>
	            				<div class="box">
	            					<span class="name">账号：</span>
	            					<span class="content">{{mine.userName}}</span>
	            				</div>
	            				<div class="box">
	            					<span class="name">注册时间：</span>
	            					<span class="content">{{mine.regtime|plTimeFormat(mine.regtime)}}</span>
	            				</div>
	            			</div>
	            			<div class="bigbox">
	            			    <div class="box" style="height: 80px;line-height: 80px;color:#9e9e9e">
	            					<span class="name"><span style="margin-right: 12px;">修改密码</span></span>
	            				</div>
	            			    <div class="box">
	            					<span class="name">原始密码：</span>
	            					<span class="content"><input type="password" v-model="pwd.old" @change="pwd.error.old='' "/></span>
	            					<span class="error">{{pwd.error.old}}</span>
	            				</div>
	            				<div class="box">
	            					<span class="name">新密码：</span>
	            					<span class="content"><input type="password" v-model="pwd.news"/></span>
	            					<span class="error">{{pwd.error.news}}</span>
	            				</div>
	            				<div class="box">
	            					<a v-on:click="updatePwd" href="javascript:void(0);" class="save" style="float: right;line-height: 50px;" >确认修改 </a>
	            				</div>
	            			</div>
	            		</div>
	            		<div class="comment" v-show="div_index == 2">
		            		<ul class="list">
		            			<li v-for="(data,i) in pl.commentList">
		            				<div class="userinfo"><span v-show="data.parentid==-1" class="gray">评论</span><span v-show="data.parentid!=-1" class="gray">对&nbsp;&nbsp;{{data.to_name}}&nbsp;&nbsp;的回复</span></div>
		            				<div class="message" v-html="data.content">
		            				</div>
		            				
		            				<!-- <div class="title">
		            					来自<a href="">asdfasdf</a>
		            				</div> -->
		            				<div class="bottom">
		            					<span class="time">{{data.time|plTimeFormat(data.time)}} &nbsp;&nbsp;&nbsp;&nbsp; </span>
		            					<span class="title">来自<a v-bind:href=' "detail/" + data.newsid' target="black">{{data.title}}</a></span>
		            					<span class="options"> 
		            						<!-- <a hidefocus="true" href="javascript:void(0);" class="center-btn-upvote"> (<em>1</em>) </a> 
		            						<a hidefocus="true" href="javascript:;" class="np-btn-reply reply"> 回复(<em>0</em>) </a>  -->
		            						<a hidefocus="true" href="javascript:;" class="np-btn-delete delete" id="" v-on:click="deleteComment(data.id,data.parentid)"> 删除 </a> 
		            					</span>
		            					 
		            				</div>
		            			</li>
		            			<li style="color:red;text-align: center;line-height: 100px;" v-show="pl.total<=0">暂无数据！</li>
		            		</ul>
		            		<!-- 分页 -->
				            <div class="pagination"  v-show="pl.total>0">
				            	<span class="pagination-wrap-w">
				            		<a class="wrap-prev-w"  id="prevPage" v-on:click="pl.pageIndex-1>=1&&gotoPage(pl.pageIndex-1)">上一页</a>
				            		<!-- <a class="wrap-current">1</a><a data-pagenum="2">2</a><a data-pagenum="3">3</a><a data-pagenum="4">4</a><a data-pagenum="5">5</a><a data-pagenum="6">6</a><a data-pagenum="7">7</a><a data-pagenum="8">8</a><a data-pagenum="9">9</a> -->
				            		<a v-for="(c_page , i) in pl.pag" v-bind:class="{wrap_current:c_page == pl.pageIndex}" v-on:click="gotoPage(c_page)">{{c_page}}</a>
				            		<a class="wrap-next-w" id="nextPage" v-on:click="pl.pageIndex+1<=pl.totalPage&&gotoPage(pl.pageIndex+1)">下一页</a>
				            	</span>
				            </div>
	            		</div>
	            		<div class="reply" v-show="div_index == 3">
	            		我的回复
	            		</div>
	            	</div>
	            </div>
	            
	        <%-- <div class="you">
	        
	            <div class="search">
	                <input type="text" v-model="criteria.title" v-on:keyup.enter="search"/><a v-on:click="search" class="s_btn" href="javascript:void(0)">搜索</a>
	            </div>
	            
	            <div class="hotTop">
	                <div class="lh">
	                    <h2><em></em> 热点排行</h2>
	                </div>
	                <ul>
	                    <!-- <li class="top ">
	                        <em>1</em>
	                        <a href="http://news.163.com/17/1206/14/D4VSIONN0001875P.html">记者被关太平间 涉案人员:再遇见弄死他 甘愿坐牢</a>
	                        <span>136880</span>
	                    </li> -->
	                    <li v-bind:class="{top:i<3}" v-for="(news,i) in hotList">
	                        <em>{{i+1}}</em>
	                        <a href="http://news.163.com/17/1206/14/D4VSIONN0001875P.html" v-bind:title="news.title">{{news.title}}</a>
	                        <span>{{news.browsenum}}</span>
	                    </li>
	                </ul>
	
	            </div>
	            
	            <div class="picture">
	                <div class="lh">
	                    <h2><em></em> 精彩图片</h2>
	                    <a class="change" v-on:click="changeImgs" href="javascript:void(0)">换一批</a>
	                </div>
	                <div class="picShowBox" v-on:mouseenter="showPicNextBtn" v-on:mouseleave="showPicNextBtn">
	                    <div class="inner">
	                    
	                        <!-- <a target="_blank" href="http://news.qq.com/original/oneday/2900.html" style="display: none;">
	                            <img name="page_cnt_1" src="http://img1.gtimg.com/ninja/2/2017/12/ninja151279215798472.jpg" alt="">
	                            <span class="txt">他拉着架子车徒步旅行 从陕西到西藏历时4个月</span>
	                        </a> -->
	                        
	                        <a  v-for="(news,i) in imgTop.imgList" target="_blank" href=""  v-show="i == imgTop.cIndex">
	                            <img name="page_cnt_1" v-bind:src="news.imgsrc|singleImgSrc(news.imgsrc)" v-bind:alt="news.title">
	                            <span class="txt">{{news.title}}</span>
	                        </a>

	                    </div>
	                    <div class="toolBox"><em v-for="(news,i) in imgTop.imgList" v-bind:class="{current:i == imgTop.cIndex}" v-on:click="showNextPic(i)">{{i}}</em></div>
	                    <span class="arrow arrow_left" v-on:click="showNextPic(imgTop.cIndex-1)"></span>
	                    <span class="arrow arrow_right" v-on:click="showNextPic(imgTop.cIndex+1)"></span>
	                </div>
	            </div>
	            
	            <div class="like">
	                <div class="lh">
	                    <h2><em></em> 随机推荐</h2>
	                    <a v-on:click="getRandomNews" class="change" href="javascript:void(0)">换一批</a>
	                </div>
	                <ul>
	                    <!-- <li>
	                        <a href="" class="txt">asdfasdf</a>
	                    </li>
	                    <li>
	                        <a target="_blank" class="pic" href="http://news.qq.com/a/20171206/003878.htm"><img src="http://inews.gtimg.com/newsapp_bt/0/2423364259/640" alt="西成高铁今日开通 动车组列车整装待发"></a>
	                        <a href="" class="txt">asdfasdf</a>
	                    </li> -->
	                    <li v-for="(news,i) in randomList">
	                        <a v-show="news.imgsrc!='' && news.imgsrc!='0'"   target="_blank" class="pic" href=""><img v-bind:src="news.imgsrc|singleImgSrc(news.imgsrc)" v-bind:alt="news.title"></a>
	                        <a href="" class="txt" v-bind:title="news.title">{{news.title}}</a>
	                    </li>
	                    
	                </ul>
	            </div>
	            
	        </div> --%>
	    </div>
	    <%@ include file="/WEB-INF/views/user/user-foot.jsp" %>
    </div>
</body>
</html>