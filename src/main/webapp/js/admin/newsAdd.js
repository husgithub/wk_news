window.onload = function(){
	var ue = UE.getEditor('editor');
	var div_newsAdd = new Vue({
		el : '#div_newsAdd',
		data : {
			pageType : '1',     /*页面类型 1：新增，2,：编辑*/
			news : {
				id : '',
				title : '',
				editor : '',
				source : '',
				type : '',
				detail :'',
				imgsrc :'',
				videosrc : ''
			},
			types : []
		},
		methods : {
			getTypes : function(){
				var _self = this;
				axios.get('type/select', {params: {}})
				  .then(function (response) {
				    console.log(response);
				    var result = response.data;
				    console.log(result);
				    if(result.success ===false){
				    	alert("系统异常");
				    	return ;
				    }
				    _self.types  = result.data;
				  })
				  .catch(function (error) {
					  alert("系统异常");
				      console.log(error);
				 });
			},
			save : function(){
				var _self = this;
				_self.news.detail = ue.getContent();
				if(_self.news.title==''||_self.news.editor==''||_self.news.source==''||_self.news.type==''||_self.news.detail==''){
					my.alert("警告","请补全内容！",function(){});
					return ;
				}
				var arr = ['title','editor','source','type','detail'] ;
				console.log(JSON.stringify(_self.news));
				let param = new URLSearchParams();
				for(var key in _self.news){
				  console.log(key + ":" + _self.news[key]);
				  for(var i=0;i<arr.length;i++){
					 if(key == arr[i]){
						 param.append(key, _self.news[key]+"");
						 continue;
					 } 
				  }
				  
				}
				if(_self.pageType =="1"){
					axios.post('news/add',param)
					.then(function(res){
					     var result = res.data;
					     if(result.success == false){
					    	 alert("失败！");
					    	 return ;
					     }
						 my.confirm('提示','保存成功!是否继续添加？',function(){
							 _self.news.title='';
							 _self.news.editor='';
							 _self.news.source='';
							 _self.news.type='';
							 ue.setContent('');
							 return;
						 },function(){
							 setTimeout(function(){window.location.href= "admin-news.jsp";},300);
						 });
					})
					.catch(function(err){
					   console.log(err);
					});
				}else{
					param.append("id", _self.news.id);
					axios.post('news/update',param)
					.then(function(res){
					     var result = res.data;
					     if(result.success == false){
					    	 alert("失败！");
					    	 return ;
					     }
						 my.confirm('提示','保存成功!是否继续添加？',function(){
							 for(var key in _self.news){
								 _self.news[key] = '';
							 }
							 ue.setContent('');
							 return;
						 },function(){
							 setTimeout(function(){window.location.href= "admin-news.jsp";},300);
						 });
					})
					.catch(function(err){
					   console.log(err);
					});
				}
				
			}
		},
		mounted : function() {
	        this.getTypes();
	        var str = window.location.search;
	        if(str==''||str.indexOf("pageType=1")!=-1){
	        	this.pageType = 1;
	        }else if(str.indexOf("pageType=2")!=-1){
	        	this.pageType = 2;
	        	var id = str.substring(str.indexOf("id=")+3,str.length);
	        	if(id==''){
	        		alert("有误！");
	        		return;
	        	}
	        	axios.get('news/selectOne', {params: {id:id}})
				  .then(function (response) {
				    var result = response.data;
				    console.log(result);
				    if(result.success ===false){
				    	alert("系统异常");
				    	return ;
				    }
				    var detail = result.data.detail;
				    div_newsAdd.news = result.data;
				    ue.ready(function(){
				    	ue.setContent(detail);
				    });
				  })
				  .catch(function (error) {
					  alert("系统异常");
				      console.log(error);
				 });
	        }
	    }

	});
}