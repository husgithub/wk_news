window.onload = function(){
	
	 var div_comment = new Vue({
		 el : "#div_comment",
		 store:vuex_store, //注入到vue
		 data : {
			 check : {selects:[],allChecked:false},
			 criteria : {title:'',editor:'',source:'',type:'',startTime:'',endTime:''},
			 msg : {nodatas:''},
			 commentList : []
		 },
		 filters : {
			 timeFormat:function(timeObj){
				 return  tool.dateUtil.format(timeObj);
			 },
			 singleImgSrc: function(imgsrc) {
				 if(imgsrc.indexOf(",")==-1){
					 return imgsrc;
				 }
                 return imgsrc.substring(0,imgsrc.indexOf(","));
             }
		 },
		 methods: {
			 getComment : function(){
				 var _self = this;
				 console.log(_self.criteria.startTime)
				 var c_str = '';
				 
				 var param = {page: this.$store.state.pageIndex,size:this.$store.state.pageSize};
				 for (var key in _self.criteria){
					 if(_self.criteria[key] !=undefined&&_self.criteria[key] !=''){
						 param[key] = _self.criteria[key];
					 }
			     }
				 console.log(JSON.stringify(param))
				 axios.get('comment/select', {params: param})
				  .then(function (response) {
				    var result = response.data;
				    if(result.success ===false){
				    	alert("系统异常");
				    	return ;
				    }
				    var array = result.data;
				    if(array.length ==0){
				    	_self.msg.nodatas = "暂无数据！";
				    }
				    _self.commentList = array;
				    _self.total = result.total;
				    _self.totalPage = Math.ceil(_self.total*1.0/_self.pageSize);
				    //当前记录
				    var start_record = 1 + (_self.pageIndex-1)*_self.pageSize;
				    var end_record = _self.pageIndex*_self.pageSize;
				    _self.msg.curRecord = start_record+"-"+end_record;
				    _self.returnPage();
				  })
				  .catch(function (error) {
					  alert("系统异常");
				      console.log(error);
				 });
			 },
			 query: function(){
				 this.pageIndex = 1;
				 this.getNews();
			 },
			 reset: function(){
				 this.criteria = {};
				 //清除时间选择
				 $("#startTime").find("input[type='text']").val('');
				 $("#endTime").find("input[type='text']").val('');
			 },
			 deleteOne : function(i){
				 my.confirm('警告','确定删除此条新闻吗？',function(){
					 div_news.newsList.splice(i, 1);
					 my.show('删除成功!');
				 });
			 },
			 checkAll : function(){
				 var _this = this;
				 var checked = this.check.allChecked;
				 if(!checked){
					 _this.newsList.forEach(function(item) {
				        _this.check.selects.push(item.id);
				     });
				 }else{
					 _this.check.selects = [];
				 }
				 _this.check.allChecked = !checked;
			 },
			 add : function(){
				 window.location.href = "admin-newsAdd.jsp";
			 },
			 edit : function(id){
				 window.location.href = "admin-newsAdd.jsp?pageType=2&id="+id;
			 },
			 showDetail : function(id){
				 window.open("detail/"+id);
			 },
			 deleteSelect : function(){
				 var _self = this;
				 if(_self.check.selects.length<=0){
					 alert("请选择");
					 return ;
				 }
				 
				 my.confirm('警告','确定删除选中的新闻吗？',function(){
					 let param = new URLSearchParams();
					 param.append("ids", _self.check.selects.join(","));
					 axios.post('news/delete',param)
					 .then(function(res){
					     var result = res.data;
					     if(result.success == false){
					    	 alert("失败！");
					    	 return ;
					     }
					     for(var i=0;i<_self.check.selects.length;i=0){
							 for(var j=0;j<_self.newsList.length;j++){
					        	if(_self.newsList[j].id==_self.check.selects[i]){
					        		_self.newsList.splice(j, 1);
					        		_self.check.selects.splice(i,1);
					        		break;
					        	}
					         }
						 }
						 my.show('删除成功!');
					 })
					 .catch(function(err){
					   console.log(err);
					 });
				 });
			 }
         },
         mounted : function(){
        	 //改变滚动table scrollTbody 的大小
        	 //console.log($("body").height()+" \n"+$(".div_table fieldset").height()+"\n"+$(".div_table .table_menu").height()+"\n"+$(".div_table .fixedThead").height()+"\n"+$("#div_news .page").height());
        	 var scrollHeight = $("body").height()-$(".div_table fieldset").height()-$(".div_table .table_menu").height()-$(".div_table .fixedThead").height()-$("#div_news .page").height()-40;//预留40px
        	 $(".scrollTbody").height(scrollHeight);
        	 this.getComment();
         }
	});
	
	  
	  
	//时间相关
	$('#startTime').datetimepicker({
        language: 'zh-CN',
        autoclose: true,
        todayHighlight: true
    }).on('hide', function (ev) {
	     let value = $("#startTime").find("input[type='text']").val();
	     div_news.criteria.startTime = value;
	});
	$('#endTime').datetimepicker({
		language: 'zh-CN',
		autoclose: true,
		todayHighlight: true
	}).on('hide', function (ev) {
		let value = $("#endTime").find("input[type='text']").val();
		div_news.criteria.endTime = value;
	});
	  
	  
	  
	  
};
