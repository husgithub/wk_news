//将登录信息存放于localStorage
if(!window.localStorage){
	console.log("浏览暂不支持localStorage");
}

Vue.use(Vuex); //使用Vuex

const  vuex_store = new Vuex.Store({
    state:{
    	login : {
			 islogin : false,
			 loginBoxVisible : false,   //是否显示登录弹出框
			 show : false,        //登录成功后显示下方box
			 userId : '',
			 userName : '',
			 avatar : 'img/user/avatar/tb1.jpg'
		}
    },
    mutations:{
        showUserName:function(state){
        }
    }
});

if(typeof(curUser)!= 'undefined'){
	vuex_store.state.login.islogin = true;
	vuex_store.state.login.userId = curUser.userId;
	vuex_store.state.login.userName = curUser.userName;
	if(curUser.avatar!=''){
		vuex_store.state.login.avatar = curUser.avatar;
	}
	
}

//头部组件
var Header = Vue.extend({
    template :'<div class="header">' +
             '<div class="links">' +
            '<ul class="clearfix" bosszone="mainNav">' +
            '<li><em></em><a href="index/all" target="_blank" title="首页">首页</a></li>' +
            '<li v-for="(type,i) in typeList"><a v-bind:href="\'index/\'+type.code" target="_blank" v-bind:title="type.name">{{type.name}}</a></li></ul></div>' +
            '<div class="user">' +
            '<a class="me" v-on:mouseenter="mouseMove(\'enter\')"   href="javascript:void(0)" v-show="this.$store.state.login.islogin"><img v-bind:src="this.$store.state.login.avatar"/></a>' +
            '<a href="javascript:void(0);" title="登录" id="btn_showLogin" v-on:click="showLoginBox" v-show="this.$store.state.login.islogin==false"><i class="iconfont icon-kehu"></i></a>' +
            '<div v-on:mouseleave="mouseMove(\'leave\')" class="div_mine" v-show="this.$store.state.login.show"><div class="img"><img v-bind:src="this.$store.state.login.avatar"/></div>' +
            '<div class="t"><span class="text">您好，{{this.$store.state.login.userName}}</span><a class="logout" v-on:click="logout" href="javascript:void(0)" title="退出">退出</a></div></div>' +
           '<a href="javascript:void(0)" v-on:click="gotoMine" title="我的"><i class="iconfont icon-comment"></i></a></div></div>',
    
    data : function(){
    	return {
    		typeList : []
    	}
    },
    methods : {
    	getTypes : function(){
			var _self = this;
			tool.ajax.get('type/select',null,function (response) {
				var result = response.data;
			    if(result.success ===false){
			    	alert("系统异常");
			    	return ;
			    }
			    _self.typeList  = result.data;
			});
			
		 },showLoginBox :function(){        //登录相关
			 this.$store.state.login.loginBoxVisible = true;
		 },gotoMine :function(){
			 var _self = this;
			 if(!this.$store.state.login.islogin){
				 _self.showLoginBox();
				 return;
			 }
			 window.open("user/mine");
		 },mouseMove : function(type){
			 if(type == 'enter'){
				 this.$store.state.login.show=true;
			 }else{
				 this.$store.state.login.show=false;
			 }
		 },logout : function(){
			 tool.ajax.get('user/logout',null,function (response) {
				var result = response.data;
			    if(result.success ===false){
			    	alert("系统异常");
			    	return ;
			    }
				window.location.reload();
			}); 
		 }
    },
    filters : {
		 titleHref:function(src){
			 return  "index/"+src;
		 }
	},
    mounted : function(){
    	var _self = this;
    	_self.getTypes();
    }
});
Vue.component('my-header',Header);

//登录组件
var bgid = "user_login";
var Login = Vue.extend({
	template : '<div class="bg" id="user_login" v-show="this.$store.state.login.loginBoxVisible"><div class="well" style="width: 300px;margin: 0 auto;margin-top:180px;position: relative;">' +
    '<h4>用户登录</h4>' +
    '<button id="close" type="button" class="close" style="position:absolute;top:12px;right: 10px;" v-on:click="close">×</button>' +
    '<input type="text" class="inputTxt" placeholder="用户名" aria-describedby="sizing-addon1" name="userName" v-model="data.userName" v-on:keyup.enter="login"/>' +
    '<input type="password" class="inputTxt" placeholder="密码" aria-describedby="sizing-addon1" name="password" v-model="data.password"  v-on:keyup.enter="login" />' +
    '<div class="checkbox">' +
        '<input type="checkbox" name="autoLogin" v-model="data.autoLogin" />下次自动登录<span v-html="msg.error" class="error">{{msg.error}}</span>' +
    '</div>' +
    '<button type="submit" class="btn btn-success btn-block" v-on:click="login">登录</button></div></div>',
    
    data : function(){
		return {
			data : {userName : '', password : '',autoLogin : false},
	        msg : {error : ''}
		}
	},
	methods : {
		login : function(){
			var _self = this;
			if(_self.data.userName == undefined||_self.data.userName==''){
				_self.msg.error = '<span style="color:red;">请补全登录账号！</span>';
				return ;
			}
			if(_self.data.password == undefined||_self.data.password==''){
				_self.msg.error = '<span style="color:red;">请补全密码！</span>';
				return ;
			}
			_self.msg.error = '';
			console.log(JSON.stringify(_self.data))
			tool.ajax.post('user/login',_self.data,function(result){
				result = result.data;
				if(!result.success){
					_self.msg.error = '<span style="color:red;">'+result.error+'</span>';
					return;
				}
				//登录成功!
				_self.msg.error = '';
				//隐藏登录弹出框
				//this.$store.state.login.loginBoxVisible = false;
				window.location.reload();
			});
		},close : function(){
			this.$store.state.login.loginBoxVisible = false;
		}
	},
    mounted : function(){
    	var _self = this;
    }
});
Vue.component('my-login',Login);
