window.onload =  function(){
	var news_detail = new Vue({
		el : '#news_detail',
		store:vuex_store, //注入到vue
		data : {
			news : {id:'',time:{},newsType:{name:''},commentCount:0},
			emojs : [],           //表情包
			emoj_index: -1,       //当前展示的表情包下标
			comment : {
				content : '',          //评论内容
				c_emoj : '',           //评论表情
				img : '',
				newsid : '',
				ufrom : '',
				parentid : -1
			},
			pl : {                      //评论相关
				commentList:[],          
				pageIndex : 1,
				pageSize : 5,
				total : 0,
				totalPage : 0,
				showBtnSize : 5 ,       //每次显示按钮 
				pag : []                //显示的分页
				
			} ,
			zan : {
				show : false
			},
			reply : {                  //以下为回复相关
				emoj_index: -1,
				commentId: '',         //被回复的评论id
				placeholder : '说两句吧...',
				content : '',          //评论内容
				c_emoj : '',           //评论表情
				img : '',
				newsid : '',
				ufrom : '',
				uto : '',
				parentid : -1
			}
			
			
		},
		methods : {
			getSingleNews : function(id){
				var _self = this;
				tool.ajax.get('news/selectOne',{id : id},function (response) {
					var result = response.data;
				    if(result.success ===false){
				    	alert("系统异常");
				    	return ;
				    }
				    _self.news = result.data;
				    document.title = _self.news.title;
				    
				});
			},
			getComment : function(){
				 var _self = this;
				 var c_str = '';
				 var param = {page: _self.pl.pageIndex,size:_self.pl.pageSize,newsid : _self.news.id,parentid:-1};
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
					    if(result.total ===0){
					    	return ;
					    }
					    var array = result.data;
					    _self.pl.commentList = array;
					    //console.log(JSON.stringify(_self.commentList))
					    _self.pl.total = result.total;
					    _self.pl.totalPage = Math.ceil(_self.pl.total*1.0/_self.pl.pageSize);
					    _self.createCommentPageUI();
					    console.log(_self.pl.totalPage)
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
			getEmojs : function(){
				var _self = this;
				tool.ajax.get('sys/getEmotion',null,function (response) {
					var result = response.data;
				    if(result.success ===false){
				    	alert("系统异常");
				    	return ;
				    } 
				    _self.emojs = result.data;
				});
			},
			showEmoj : function(path){
				var _self = this;
				//未登录
				if(!this.$store.state.login.islogin){
					//显示登录框
					this.$store.state.login.loginBoxVisible = true;
					return;
				}
				var img = '['+path+']';
				_self.comment.content += img;
			},
			showEmoj2 : function(path){
				var _self = this;
				var img = '['+path+']';
				_self.reply.content += img;
			},
			publish : function(){     //发表评论
				var _self = this;
				if(!this.$store.state.login.islogin){
					//显示登录框
					this.$store.state.login.loginBoxVisible = true;
					return;
				}
				if(_self.comment.content==undefined||_self.comment.content==''){
					alert("请输入评论！");
					return ;
				}
				//将评论中包含的emoj转换成富文本
				var content = _self.comment.content;
				console.log("原内容："+content);
				var r = /\[(.+?)\]/g;
				var arr = content.split(r);
				var new_content = "";
				for(var i=0;i<arr.length;i++){
					console.log(arr[i]);
					var s_str = arr[i];
					if(s_str.indexOf("img")!=-1){
						new_content +=  '<img src="'+s_str+'"/>';
					}else{
						new_content +=  s_str;
					}
				}
				console.log(new_content)
				_self.comment.content = new_content;
				_self.comment.newsid = _self.news.id;
				_self.comment.ufrom = this.$store.state.login.userId;
				tool.ajax.post('comment/save',_self.comment,function (response) {
					var result = response.data;
				    if(result.success ===false){
				    	alert("系统异常");
				    	return ;
				    }
				    _self.comment.content = "";   //清空评论
				    _self.getComment();
				    _self.news.commentCount++;   //评论数+1
				});
			},
			dianzan : function(commentId){
				alert(commentId);
			},
			showReply :function(commentId,uto,to_name){              //以下为回复相关
				console.log(commentId+"  "+uto+"  "+to_name);
				var _self = this;
				if(!this.$store.state.login.islogin){
					//显示登录框
					this.$store.state.login.loginBoxVisible = true;
					return;
				}
				if(this.$store.state.login.userId == uto){
					alert("不可回复自己的评论！");
					return ;
				}
				_self.reply.commentId = commentId;
				_self.reply.parentid = commentId;
				_self.reply.uto = uto;
				if(to_name!=undefined){
					_self.reply.placeholder = "回复："+to_name;
				}
			},
			replyComment : function(){
				var _self = this;
				
				if(_self.reply.content==undefined||_self.reply.content==''){
					alert("请输入回复内容！");
					return ;
				}
				//将评论中包含的emoj转换成富文本
				var content = _self.reply.content;
				console.log("原内容："+content);
				var r = /\[(.+?)\]/g;
				var arr = content.split(r);
				var new_content = "";
				for(var i=0;i<arr.length;i++){
					console.log(arr[i]);
					var s_str = arr[i];
					if(s_str.indexOf("img")!=-1){
						new_content +=  '<img src="'+s_str+'"/>';
					}else{
						new_content +=  s_str;
					}
				}
				console.log(new_content)
				_self.reply.content = new_content;
				_self.reply.newsid = _self.news.id;
				_self.reply.ufrom = this.$store.state.login.userId;
				tool.ajax.post('comment/save',_self.reply,function (response) {
					var result = response.data;
				    if(result.success ===false){
				    	alert("系统异常");
				    	return ;
				    }
				    _self.reply.content = "";   //清空评论
				    _self.getComment();
				    _self.reply.commentId=-1;
				    _self.news.commentCount++;   //评论数+1
				});
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
			var href = window.location.href;
			var id = href.substring(href.lastIndexOf("/")+1,href.length);
			_self.news.id = id;
			_self.getSingleNews(id);
			_self.getComment();
			_self.getEmojs();
			
			/*var obj = { "date": 26, "day": 2, "hours": 22, "minutes": 59, "month": 11, "nanos": 0, "seconds": 15, "time": 1314300355000, "timezoneOffset": -480, "year": 117 };
			var str = tool.dateUtil.dynamicFormat(obj);
			console.log(str)*/
		}
	
	});
}