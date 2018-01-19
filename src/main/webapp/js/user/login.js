$(function(){
	
});

var bgid = "user_login";
/*$("#btn_showLogin").click(function(){alert()
	$("#"+bgid).show();
});*/
var user_login = new Vue({
	el : "#user_login",
	data : {
		data : {userName : '', password : '',autoLogin : false},
        msg : {error : ''}
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
				_self.msg.error = '';
				$("#"+bgid).hide();
			});
		},close : function(){
			$("#"+bgid).hide();
		}
	},
	mounted : function(){
		
	}
});