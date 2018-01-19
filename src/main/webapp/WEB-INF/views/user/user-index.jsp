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
    <title>新闻首页</title>
    <style>
        body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,code,form,fieldset,legend,input,textarea,p,blockquote,th,td{
            margin: 0px;
            padding:0px
        }
        html, body {height:100%;}
    </style>
    <link rel="stylesheet" href="css/user/iconfont.css"/>
    <link rel="stylesheet" href="css/user/common.css"/>
    <link rel="stylesheet" href="css/user/user-index.css"/>
    <script type="text/javascript" src="vue/axios.min.js"></script>
    <script type="text/javascript" src="vue/vue.js"></script>
    <script type="text/javascript" src="vue/vuex.js"></script>
    <script type="text/javascript" src="jquery/jquery-3.1.1.js"></script>
    <script type="text/javascript" src="js/common/tool.js"></script>
    <script type="text/javascript">
       var curUser = eval('(' + '${sessionScope.curUser}' + ')');
       console.log("curUser:"+JSON.stringify(curUser)); 
    </script>
    <script type="text/javascript" src="js/user/index.js"></script>
    <script type="text/javascript" src="js/user/header.js"></script>
    
</head>
<body>
    <div id="news_index">
	    <my-header></my-header>
	    <my-login></my-login>
	    <div class="content">
	        <div class="zuo">
	
	        </div>
	        <div class="main">
	            <h1><span id="date"><!-- 2017年12月06日 --></span>滚动新闻</h1>
	            <div class="querymode">
	                <span>浏览方式：</span>
	                <ul class="rows">
	                    <li><a v-on:click="change('all')" href="javascript:void(0);" v-bind:class="{changeActive:changeTypes.all}">全部</a></li>
	                    <li><a v-on:click="change('znew')" href="javascript:void(0);"  v-bind:class="{changeActive:changeTypes.znew}">最新</a></li>
	                    <li><a v-on:click="change('hot')" href="javascript:void(0);" v-bind:class="{changeActive:changeTypes.hot}">最热门</a></li>
	                    <li><a v-on:click="change('img')" href="javascript:void(0);" v-bind:class="{changeActive:changeTypes.img}">图片</a></li>
	                </ul>
	                <div class="r">
	
	                    <div class="autoRefresh" id="autoRefresh"><input type="checkbox" class="check" v-model="autoRefresh.yes" @change="refreshAuto"/><strong v-bind:class="{secondsRed:autoRefresh.yes}">{{autoRefresh.seconds}}</strong>秒后自动刷新</div>
	                    <a v-on:click="refresh" href="javascript:void(0);" class="refresh" id="refresh">刷新 <i class="iconfont icon-refresh" v-show="true"></i></a>
	                </div>
	            </div>
	            <ul class="newslist">
	                <!-- <li>
	                    <div class="info">
	                        <h3><a target="_blank" href="http://news.qq.com/a/20171206/005840.htm">平静只是在表面上 耶路撒冷缠绕多少禁忌与哀愁？</a></h3>
	                        <div class="hot"><img src="img/hot.png" /><span>30</span></div>
	                        <div class="mark"><span>国际</span></div>
	                        <p class="time">12月06日08:38:33</p>
	                    </div>
	                </li>
	                <li>
	                    <a target="_blank" class="pic" href="http://news.qq.com/a/20171206/003878.htm"><img src="http://inews.gtimg.com/newsapp_bt/0/2423364259/640" alt="西成高铁今日开通 动车组列车整装待发"></a>
	                    <div class="info">
	                        <h3><a target="_blank" href="http://news.qq.com/a/20171206/003878.htm">西成高铁今日开通 动车组列车整装待发</a></h3>
	                        <div class="hot"><img src="img/hot.png" /><span>30</span></div>
	                        <div class="mark"><span>图片</span></div>
	                        <p class="time">12月06日08:05:55</p>
	                    </div>
	                </li> -->
	                <li v-for="(news,i) in newsList">
	                    <a v-show="news.imgsrc!='' && news.imgsrc!='0'" target="_blank" class="pic" v-bind:href=' "detail/" + news.id'><img v-bind:src="news.imgsrc|singleImgSrc(news.imgsrc)" v-bind:alt="news.title"></a>
	                    <div class="info">
	                        <h3><a target="_blank" v-bind:href=' "detail/" + news.id'>{{news.title}}</a></h3>
	                        <div class="hot"><img src="img/hot.png" /><span>{{news.browsenum}}</span></div>
	                        <div class="mark"><span>{{news.source}}</span><span v-show="news.imgsrc!='' && news.imgsrc!='0'">图片</span></div>
	                        <p class="time">{{news.time | timeFormat(news.time)}}</p>
	                    </div>
	                </li>
	            </ul>
	            <div v-show="msg.nodatas!=''" style="height:40px;line-height :40px; text-align: center" v-html="msg.nodatas">
	            {{msg.nodatas}}
	            </div>
	            <div v-show="nextStatus" class="next" v-on:click="nextPage">
	                查看更多
	            </div>
	        </div>
	        <div class="you">
	        
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
	                        <a target="_blank" v-bind:href=' "detail/" + news.id' v-bind:title="news.title">{{news.title}}</a>
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
	                        
	                        <a  v-for="(news,i) in imgTop.imgList" target="_blank" v-bind:href=' "detail/" + news.id'  v-show="i == imgTop.cIndex">
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
	                        <a v-show="news.imgsrc!='' && news.imgsrc!='0'"   target="_blank" v-bind:href=' "detail/" + news.id' class="pic" ><img v-bind:src="news.imgsrc|singleImgSrc(news.imgsrc)" v-bind:alt="news.title"></a>
	                        <a target="_blank" v-bind:href=' "detail/" + news.id' class="txt" v-bind:title="news.title">{{news.title}}</a>
	                    </li>
	                    
	                </ul>
	            </div>
	            
	        </div>
	    </div>
	    <div class="footer">Copyright © 2017</div>
    </div>
    
</body>
</html>