<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head lang="zh_cn">
    <meta charset="UTF-8">
    <title>user-head</title>
    
    <style type="text/css">
	    body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,code,form,fieldset,legend,input,textarea,p,blockquote,th,td{
		    margin: 0px;
		    padding:0px
		}
    </style>
    <script type="text/javascript" src="js/user/header.js"></script>
</head>
<body>
    <div class="header" id="userHeader">
        <div class="links">
            <ul class="clearfix" bosszone="mainNav">
                <!-- <li bosszone="qqcom"><em></em><a href="http://www.qq.com" target="_blank" title="腾讯网首页">首页</a></li> -->
                
                <li><em></em><a href="http://www.qq.com" target="_blank" title="首页">首页</a></li>
                <li v-for="(type,i) in typeList"><a v-bind:href=' "index/" + type.code' target="_blank" v-bind:title="type.name">{{type.name}}</a></li>
                
                <!-- <li bosszone="more" class="moreNav">
                    <a class="moreLink">更多<span class="moreNav1"></span></a>
                    <div class="navmenu">
                        <a href="http://comic.qq.com/" target="_blank">动漫</a>
                        <a href="http://book.qq.com/" target="_blank">读书</a>
                        <a href="http://kid.qq.com/" target="_blank">儿童</a>
                        <a href="http://astro.lady.qq.com/" target="_blank">星座</a>
                        <a href="http://class.qq.com/" target="_blank">精品课</a>
                        <a href="http://www.qq.com/map/" target="_blank">全部频道</a>
                    </div>
                </li> -->
            </ul>
        </div>
        <div class="user">

            <a href="javascript:void(0)" title="登录">
                <i class="iconfont icon-login"></i>
            </a>
            <a href="javascript:void(0)" title="我的评论">
                <i class="iconfont icon-comment"></i>
            </a>
        </div>
    </div>
</body>
</html>