window.onload = function(){
	
	 var div_news = new Vue({
		 el : "#div_news",
		 data : {
			 check : {selects:[],allChecked:false},
			 criteria : {title:'',editor:'',source:'',type:'',startTime:'',endTime:''},
			 msg : {curRecord:"1-10",pageHtml:'',nodatas:''},
			 pag : [],
			 uipage : 10,             //每页展示的分页按钮数量
			 gtIndex : '',
			 pageIndex : 1,
			 pageSize : 20,
			 total : 0,
			 totalPage : 0,
			 newsList : [],
			 sourceList:[],
			 typeList : []
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
			 getSource : function(){
				 var _self = this;
				 
				 axios.get('news/selectResource', {params: {}})
				  .then(function (response) {
				    var result = response.data;
				    if(result.success ===false){
				    	alert("系统异常");
				    	return ;
				    }
				    var array = result.data;
				    if(array.length ==0){
				    	return ;
				    }
				    _self.sourceList = array;
				  })
				  .catch(function (error) {
					  alert("系统异常");
				      console.log(error);
				 });
			 },
			 getTypes : function(){
				var _self = this;
				axios.get('type/select', {params: {}})
				  .then(function (response) {
				    var result = response.data;
				    if(result.success ===false){
				    	alert("系统异常");
				    	return ;
				    }
				    _self.typeList  = result.data;
				  })
				  .catch(function (error) {
					  alert("系统异常");
				      console.log(error);
				 });
			 },
			 getNews : function(){
				 var _self = this;
				 console.log(_self.criteria.startTime)
				 var c_str = '';
				 
				 var param = {page: _self.pageIndex,size:_self.pageSize};
				 for (var key in _self.criteria){
					 if(_self.criteria[key] !=undefined&&_self.criteria[key] !=''){
						 param[key] = _self.criteria[key];
					 }
			     }
				 console.log(JSON.stringify(param))
				 axios.get('news/select', {params: param})
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
				    _self.newsList = array;
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
			 pageSizeChange : function(){
				 this.pageIndex = 1;
				 this.getNews();
			 },
			 returnPage : function(){    //改变分页样式
				 
				 this.pag = [];
				 let pag = this.pag;
                 if( this.pageIndex < this.uipage ){ //如果当前的激活的项 小于要显示的条数
                      //总页数和要显示的条数那个大就显示多少条
                      var i = Math.min(this.totalPage,this.uipage);
                      while(i){
                          pag.unshift(i--);
                      }
                  }else{ //当前页数大于显示页数了
                      var middle = this.pageIndex - Math.floor(this.uipage / 2 ),//从哪里开始
                          i = this.uipage;
                      if( middle >  (this.totalPage - this.uipage)  ){
                          middle = (this.totalPage - this.uipage) + 1
                      }
                      while(i--){
                          pag.push( middle++ );
                      }
                  }
                 this.pag = pag;
                return pag;
			 },
			 gotoPage : function(index){
				 if(index==null){
					 return;
				 }
				 console.log(index);
				 this.pageIndex = index;
				 this.getNews(); 
			 },
			 jump : function(){
				 let gtIndex = this.gtIndex;
				 let totalPage = this.totalPage;
				 console.log(gtIndex);
				 var ptn=new RegExp("^(0|[1-9][0-9]*)$");
				 if(!ptn.test(gtIndex)||gtIndex<1||gtIndex>totalPage){
					 this.gtIndex = '' ;
					 return;
				 }
				 this.gotoPage(gtIndex);
			 },
			 refresh : function(){
				 this.getNews();
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
        	 this.getSource();
        	 this.getTypes();
        	 this.getNews();
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
