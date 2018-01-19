window.onload =  function(){
	var news_detail = new Vue({
		el : '#news_mine',
		store:vuex_store, //注入到vue
		data : {
			div_index : 1,
			pl : {                      //评论相关
				commentList:[],          
				pageIndex : 1,
				pageSize : 5,
				total : 0,
				totalPage : 0,
				showBtnSize : 5 ,       //每次显示按钮 
				pag : []                //显示的分页
				
			},
			mine : {},
			upload : {
				files : [],
				error : ''
			},
			pwd : {
				old : '',
				news : '',
				error : {
					old:'',
					news:''
				}
			}
			
		},
		methods : {
			getComment : function(){
				 var _self = this;
				 var c_str = '';
				 var param = {page: _self.pl.pageIndex,size:_self.pl.pageSize,ufrom:this.$store.state.login.userId};
				 /*for (var key in _self.criteria){
					 if(_self.criteria[key] !=undefined&&_self.criteria[key] !=''){
						 param[key] = _self.criteria[key];
					 }
			     }*/
				 tool.ajax.get('comment/select',param,function (response) {
					    var result = response.data;
					    if(result.success ===false){
					    	alert("系统异常");
					    	return ;
					    }
					    _self.pl.total = result.total;
					    if(result.total ===0){
					    	_self.pl.commentList = [];
					    	return ;
					    }
					    var array = result.data;
					    _self.pl.commentList = array;
					    //console.log(JSON.stringify(_self.commentList))
					    
					    _self.pl.totalPage = Math.ceil(_self.pl.total*1.0/_self.pl.pageSize);
					    _self.createCommentPageUI();
					    console.log(_self.pl.totalPage);
				 });
				 
			},
			createCommentPageUI : function(){
				var _self = this;
				var pageIndex = _self.pl.pageIndex;
				var totalPage = _self.pl.totalPage;
				var showBtnSize = _self.pl.showBtnSize;
				_self.pl.pag = [];
				let pag = _self.pl.pag;
               if(pageIndex < showBtnSize ){ //如果当前的激活的项 小于要显示的条数
                    //总页数和要显示的条数那个大就显示多少条
                    var i = Math.min(totalPage,showBtnSize);
                    while(i){
                        pag.unshift(i--);
                    }
               }else{ //当前页数大于显示页数了
                    var middle =pageIndex - Math.floor(showBtnSize / 2 ),//从哪里开始
                        i = showBtnSize;
                    if( middle >  (totalPage - showBtnSize)  ){
                        middle = (totalPage - showBtnSize) + 1
                    }
                    while(i--){
                        pag.push( middle++ );
                    }
              }
              _self.pl.pag = pag;
              return pag;
			},
			gotoPage : function(pageIndex){
				var _self = this;
				 _self.pl.pageIndex = pageIndex;
				_self.getComment();
			},
			deleteComment : function(commentId,parentid){
				var _self = this;
				console.log(commentId+" "+parentid);
				var str = "确定删除这条回复吗？";
				if(parentid==-1){
					str = "确定删除这条评论及其回复信息吗？";
				}
				var r=confirm(str);
				if (r==true){
					var param = {"ids" : commentId};
					tool.ajax.post('comment/deleteWithChild',param,function(res){
					     var result = res.data;
					     if(result.success == false){
					    	 alert("失败！");
					    	 return ;
					     }
					     _self.getComment();
					});
				}else{
					
				}
			},
			getLoginedMessage : function(){
				var _self = this;
				tool.ajax.get('user/getLoginedMessage',{userName:this.$store.state.login.userName},function (response) {
				    var result = response.data;
				    if(result.success ===false){
				    	alert("系统异常");
				    	return ;
				    }
				    _self.mine = result.data;
			    });
			},
			getFile: function (even) {                      //文件上传相关
				var _self = this;
				_self.upload.files = event.target.files;
		    },
			uploadAvatar  : function(){
				var _self = this;
				_self.upload.error = '';
			    if(_self.upload.files == undefined||_self.upload.files.length<=0){
			    	_self.upload.error = '请上传图片！';
			    	return ;
			    }
			    _self.upload.error = '';
			    var formData=new FormData();
			    var files= _self.upload.files;
			    var i=0;
			    $.each(files,function(i,file){
			       formData.append("files["+i+"]",files[i]);
			    });
			    $.ajax({
			         url:"upload/avatar",
			         type:"post",
			         data:formData,
			         dataType:"json",
			         processData: false,
                     contentType: false
			    }).done(function(result) {
			         console.log(JSON.stringify(result));
			         if(!result.success){
			        	 _self.upload.error = '上传失败！';
			        	 return;
			         }
			         _self.mine.avatar = result.data;
                }).fail(function(error) {
                     alert(error);
                });
			},
			updateMsg  : function(){
				var _self = this;
				var mine = _self.mine;
				var user = {id: mine.userId,avatar : mine.avatar};
				_self.updateUser(user);
				this.$store.state.login.avatar = mine.avatar;
			},
			updateUser  : function(user){         //更新用户信息
				if(user==undefined||user.id == undefined||user.id==null||user.id==''){
					throw "参数错误！";
				}
				tool.ajax.post('user/update',user,function (response) {
					    var result = response.data;
					    if(result.success ===false){
					    	alert("系统异常");
					    	return ;
					    }
					    alert("保存成功！");
				});
			},
			updatePwd : function(){
				var _self = this;
				if(_self.pwd.old == ''){
					_self.pwd.error.old = "请输入原密码！";
					return ;
				}
				if(_self.pwd.news == ''){
					_self.pwd.error.news = "请输入新密码！";
					return ;
				}
				if(_self.pwd.old == _self.pwd.news){
					_self.pwd.error.news = "新密码不能与原密码一致！";
					return ;
				}
				var user = {id: _self.mine.userId ,old : _self.pwd.old , news : _self.pwd.news};
				tool.ajax.post('user/changePwd',user,function (response) {
				    var result = response.data;
				    if(result.success ===false){
				    	_self.pwd.error.news = result.error;
				    	return ;
				    }
				    _self.clearPwd();
				    alert("密码修改成功，下次登录时生效！");
			    });
			},
			clearPwd : function(){
				var _self = this;
				_self.pwd.old = '';
				_self.pwd.news = '';
				_self.pwd.error.old = '';
				_self.pwd.error.news = '';
			}
		},
		filters : {
			 timeFormat:function(timeObj){
				 return  tool.dateUtil.format(timeObj);
			 },
			 plTimeFormat:function(timeObj){
				 return  tool.dateUtil.dynamicFormat(timeObj);
			 }
		},
		mounted : function(){
			var _self = this;
			_self.getLoginedMessage();
			_self.getComment();
		}
	
	});
}