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
    <script type="text/javascript" src="js/user/detail.js"></script>
    <script type="text/javascript" src="js/user/header.js"></script>
    
</head>
<body>
    <div id="news_detail">
	    <my-header></my-header>
	    <my-login></my-login>
	    <div class="content">
	        <div class="zuo">
	
	        </div>
	        <div class="main">
	            <div class="detail">
	            	<h1>{{news.title}}</h1>
	            	<div class="bar">
	            		<div class="left">
	            			<span class="tag">{{news.newsType.name}}</span>
	            			<span class="a_time">{{news.time | timeFormat(news.time)}}</span>
	            		</div>
	            		<span class="box">
						    <em id="cmtNum">{{news.commentCount}}</em>评论
						</span>
	            		<span class="box">
						    <em id="">{{news.browsenum}}</em>点击量
						</span>
	            	</div>
	            	<div class="article" v-html="news.detail"></div>
	            	
	            	<div class="art_foot">
	            	      责任编辑：{{news.editor}}
	            	</div>
	            </div>
	            <div class="comment">
		            <div class="head">
		            	<p class="head-number"><i>{{news.commentCount}}</i>条评论</p>
		            </div>
		            
		            <div class="box">
		            	<div class="common-avatar">
		            		<img v-show="this.$store.state.login.islogin" alt="" v-bind:src="this.$store.state.login.avatar" style="width:50px;height: 50px;"/>
		            	</div>
		            	<div class="box-content box-logout"> 
			            	<div class="box-textarea-block"> 
			            		<textarea id="textarea" class="box-textarea"  placeholder="说两句吧..." v-model="comment.content">{{comment.content}}</textarea> 
			            		<!-- <div id="textarea" class="box-textarea"  placeholder="说两句吧..." ></div>  -->
			            	</div> 
			            	<div class="box-img" v-html="comment.c_emoj">
				            				    
				            </div>
			            	<div class="box-info"> 
			            		<div class="box-commentBtn" v-bind:class="{box_commentBtn_login : this.$store.state.login.islogin}" v-on:click="publish">发表评论</div>
			            		<div class="emotion">
			            			<ul class="e_ul">
			            				<li v-for="(emoj , i) in emojs ">
				            				<a href="javascript:void(0)" v-bind:title="emoj.name" class="li_a" v-on:mouseenter="emoj_index=i" v-on:mouseleave="emoj_index=i">
				            					<img alt="" v-bind:src="emoj.rootPath+emoj.value[0]">
				            				</a>
				            				<div class="e_list" v-show="i==emoj_index" v-on:mouseleave="emoj_index=-1">
				            				    <a href="javascript:void(0)" v-for="(single , i) in emoj.value" v-on:click="showEmoj(emoj.rootPath+single)">
					            					<img alt="" v-bind:src="emoj.rootPath+single" />
					            				</a>
				            				</div>
			            				</li>
			            			</ul>
			            		</div>
			            	</div>
		            	</div>
		            </div>
		            <!-- 评论列表 -->
		            
		            <ul class="comment_ul" v-show="pl.total>0">
		            	<!-- <li>
		            		<div class="top-box">
		            			<div class="avatar">
				            		<img v-show="this.$store.state.login.islogin" alt="" v-bind:src="this.$store.state.login.avatar"/>
				            	</div>
				            	<div class="qh-user-nt"><span class="qh-u-name">蚁穴溃堤</span><span class="qh-u-time">2小时前</span></div>
				            	<div class="qh-cmt-lr">
				            		<div class="qh-cmt-ups"><a href="javascript:void(0)" class="qh-cmt-icon"></a><span class="qh-ups-num">3</span></div>
				            		<div class="qh-cmt-reply" ><a href="javascript:void(0)" class="qh-cmt-icon">回复</a></div>
				            	</div>
		            		</div>
		            		<div class="bottom-box">
		            			<div class="qh-cmt-cont">
		            				<div class="qh-cmt-cont-t" >傻逼小编，，真当一堆人，零智商啊，，唉，每天都写这种开头一张图，内容全考编的，文章，有什么意思傻逼小编，，真当一堆人，零智商啊，，唉，每天都写这种开头一张图，内容全考编的，文章，有什么意思</div>
		            			</div>
		            		</div>
		            	</li> -->
		            	<li v-for="(data,i) in pl.commentList">
		            		<div class="top-box">
		            			<div class="avatar">
				            		<img v-show="data.f_avatar!=''" alt="" v-bind:src="data.f_avatar"/>
				            	</div>
				            	<div class="qh-user-nt"><span class="qh-u-name">{{data.from_name}}</span><span class="qh-u-time">{{data.time|plTimeFormat(data.time)}}</span></div>
				            	<div class="qh-cmt-lr">
				            		<div class="qh-cmt-ups" v-show="false"><a href="javascript:void(0)" class="qh-cmt-icon" v-on:click="dianzan(data.id)" v-bind:class=""></a><span class="qh-ups-num">{{data.upvote}}</span></div>
				            		<div class="qh-cmt-reply" ><a href="javascript:void(0)" class="qh-cmt-icon" v-on:click="showReply(data.id,data.ufrom,data.from_name)">回复</a></div>
				            	</div>
		            		</div>
		            		<div class="bottom-box">
		            			<div class="qh-cmt-cont">
		            				<div class="qh-cmt-cont-t" v-html="data.content"></div>
		            			</div>
		            		</div>
		            		
		            		<div class="comment_reply">
			            		<div class="my_reply" v-for="(data,i) in data.children">
				            		<div class="top-box">
				            			<div class="avatar">
						            		<img v-show="data.f_avatar!=''" alt="" v-bind:src="data.f_avatar"/>
						            	</div>
						            	<div class="qh-user-nt"><span class="qh-u-name">{{data.from_name}}&nbsp;&nbsp;回复&nbsp;&nbsp;{{data.to_name}}</span><span class="qh-u-time">{{data.time|plTimeFormat(data.time)}}</span></div>
						            	<div class="qh-cmt-lr">
						            		<div class="qh-cmt-ups"><a href="javascript:void(0)" class="qh-cmt-icon" v-on:click="dianzan(data.id)" v-bind:class=""></a><span class="qh-ups-num">{{data.upvote}}</span></div>
						            		<div class="qh-cmt-reply" ><a href="javascript:void(0)" class="qh-cmt-icon" v-on:click="showReply(data.parentid,data.ufrom,data.from_name)">回复</a></div>
						            	</div>
				            		</div>
				            		<div class="bottom-box">
				            			<div class="qh-cmt-cont">
				            				<div class="qh-cmt-cont-t" v-html="data.content"></div>
				            			</div>
				            		</div>
			            		</div>
		            		</div>
		            		
		            		
		            		<div class="replybox" v-show="data.id == reply.commentId">
				            	<div class="common-avatar">
				            		<img   alt=""  style="width:50px;height: 50px;"/>
				            	</div>
				            	<div class="box-content box-logout"> 
					            	<div class="box-textarea-block"> 
					            		<textarea id="textarea" class="box-textarea"  v-bind:placeholder="reply.placeholder"  v-model="reply.content"></textarea> 
					            		
					            	</div> 
					            	<div class="box-info"> 
					            		<div class="box-commentBtn box_commentBtn_login"  v-on:click="replyComment">发表评论</div>
					            		<div class="emotion">
					            			<ul class="e_ul">
					            				<li v-for="(emoj , i) in emojs ">
						            				<a href="javascript:void(0)" v-bind:title="emoj.name" class="li_a" v-on:mouseenter="reply.emoj_index=i" v-on:mouseleave="reply.emoj_index=i">
						            					<img alt="" v-bind:src="emoj.rootPath+emoj.value[0]">
						            				</a>
						            				<div class="e_list" v-show="i==reply.emoj_index" v-on:mouseleave="reply.emoj_index=-1">
						            				    <a href="javascript:void(0)" v-for="(single , i) in emoj.value" v-on:click="showEmoj2(emoj.rootPath+single)">
							            					<img alt="" v-bind:src="emoj.rootPath+single" />
							            				</a>
						            				</div>
					            				</li>
					            			</ul>
					            		</div>
					            	</div> 
				            	</div>
				            </div>
				            
				            
		            	</li>
		            	
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