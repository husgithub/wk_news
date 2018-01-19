var id_of_settimeout;
var img_settimeout;        //图片滚动定时器
window.onload = function(){
	
	var news_index = new Vue({
		el : '#news_index',
		store:vuex_store, //注入到vue
		data : {
			 criteria : {title:'',editor:'',source:'',type:'',imgsrc:'',startTime:'',endTime:'',orderby:''},
			 msg : {nodatas:''},
			 pageIndex : 1,
			 pageSize : 10,
			 total : 0,
			 totalPage : 0,
			 newsList : [],
			 typeList : [],
			 hotList : [],
			 randomList : [],
			 imgTop : {imgList : [],index:1,size : 5,totalPage:0,cIndex:0},                     //精选图片默认显示下标为0
			 nextStatus : true,     //是否显示查看更多
			 changeTypes:{all:true,znew:false,hot:false,img:false},   //浏览方式
			 autoRefresh :  {yes:true,seconds:60},      //自动刷新
			 login : {
				 islogin : false,
				 show : false
			 }
		},
		methods : {
			reset : function(){
				var _self = this;
				for (var key in _self.criteria){
					if(key !='type'){
						_self.criteria[key] =''
					}
					
			    }
			},
			getNews : function(){
				 var _self = this;
				 _self.msg.nodatas = '<span style="color:blue;">数据查询中...</span>';
				 _self.nextStatus = true;
				 
				 var c_str = '';
				 
				 var param = {page: _self.pageIndex,size:_self.pageSize};
				 for (var key in _self.criteria){
					 if(_self.criteria[key] !=undefined&&_self.criteria[key] !=''){
						 param[key] = _self.criteria[key];
					 }
			     }
				 tool.ajax.get('news/select',param,function (response) {
				    var result = response.data;
				    console.log(result);
				    if(result.success ===false){
				    	alert("系统异常");
				    	return ;
				    }
				    _self.msg.nodatas = '';
				    var array = result.data;
				    if(array.length ==0){
				    	_self.msg.nodatas = "暂无数据！";
				    	_self.msg.nodatas = '<span style="color:red;">没有数据!</span>';
				    	_self.nextStatus =false;
				    	return ;
				    }
				    _self.newsList = _self.newsList.concat(array);
				    _self.total = result.total;
				    _self.totalPage = Math.ceil(_self.total*1.0/_self.pageSize);
				    if(_self.pageIndex>=_self.totalPage){
				    	_self.msg.nodatas = '<span style="color:gray;">到底了哦~</span>';
				    	_self.nextStatus =false;
				    }
				    
				  });
				 
			 },
			 nextPage : function(){
				 var _self = this;
				 _self.pageIndex ++;
				 
				 _self.getNews();
			 },
			 search : function(){
				 var _self = this;
				 _self.newsList = [];   //清空
				 _self.pageIndex =1;
				 _self.getNews();
			 },refresh : function(){
				 var _self = this;
				// _self.reset();
				 _self.newsList = [];   //清空
				 _self.pageIndex =1;
				 _self.getNews();
				 _self.getHotTop();
				 _self.getImgTop();
				 _self.getRandomNews();
			 },change : function(type){
				 var _self = this;
				 for(var key in _self.changeTypes){
					 if(key == type){
						 _self.changeTypes[key] = true;
					 }else{
						 _self.changeTypes[key] = false;
					 }
				 }
				  _self.reset();
				 if(type == 'img'){
					 _self.criteria.imgsrc='1';
				 }else if(type == 'hot'){
					 _self.criteria.orderby ='browsenum desc';
				 }
				 _self.newsList = [];   //清空
				 _self.pageIndex =1;
				 _self.getNews();
			 },
			 refreshAuto : function(){
				 var _self = this;
				 if(_self.autoRefresh.yes){
					 _self.countdown();
					 return ;
				 }
				 if(typeof(id_of_settimeout) == 'number'){
					 _self.autoRefresh.seconds = 60;
					 clearTimeout(id_of_settimeout);
				 }
			 },countdown : function(){    //倒计时
				 var _self = this;
				 if(_self.autoRefresh.seconds>0){
					 id_of_settimeout = setTimeout(function(){_self.autoRefresh.seconds--;_self.countdown();},1000);
					 
				 }else{ 
					 //clearTimeout(id_of_settimeout);
					 _self.refresh();
					 _self.autoRefresh.seconds = 60;
					 _self.countdown();
				 }
				
			 },getRandomNews : function(){
				 var _self = this;
				 
				 var c_str = '';
				 
				 var param = {page: 1,size:10,orderby:'RAND() ASC'};
				 
				 tool.ajax.get('news/select',param,function (response) {
				    var result = response.data;
				    console.log(result);
				    if(result.success ===false){
				    	alert("系统异常");
				    	return ;
				    }
				   
				    var array = result.data;
				    if(array.length ==0){
				    	return ;
				    }
				    _self.randomList = array;
				   
				  });
			 },getHotTop : function(){
				 var _self = this;
				 
				 var c_str = '';
				 
				 var param = {page: 1,size:10,orderby:'browsenum desc'};
				 
				 tool.ajax.get('news/select',param,function (response) {
				    var result = response.data;
				    console.log(result);
				    if(result.success ===false){
				    	alert("系统异常");
				    	return ;
				    }
				   
				    var array = result.data;
				    if(array.length ==0){
				    	return ;
				    }
				    _self.hotList = array;
				   
				  });
			 },getImgTop : function(){
				 var _self = this;
				 
				 var c_str = '';
				 
				 if(_self.imgTop.totalPage>0&&_self.imgTop.index>_self.imgTop.totalPage){
					 _self.imgTop.index = 1;
				 }
				 
				 var param = {page: _self.imgTop.index,size:_self.imgTop.size,imgsrc:'1'};
				 
				 tool.ajax.get('news/select',param,function (response) {
				    var result = response.data;
				    console.log(result);
				    if(result.success ===false){
				    	alert("系统异常");
				    	return ;
				    }
				   
				    var array = result.data;
				    if(array.length ==0){
				    	return ;
				    }
				    _self.imgTop.imgList = array;
				    _self.imgTop.totalPage = Math.ceil(result.total/_self.imgTop.size);
				    _self.imgTop.index++;
				   
				  });
			 },showPicNextBtn : function(){                 //鼠标移入显示上下一张图片按钮
				 var _self = this;
				 var obj = document.getElementsByClassName("arrow");
				 var dis_str = obj[0].style.display;
				 if(dis_str==''||dis_str == 'none'){
					 dis_str = 'block';
					 //鼠标移入时停止定时任务
					 if(img_settimeout!=undefined){
						 clearTimeout(img_settimeout);
					 }
				 }else{
					 dis_str = 'none';
					 img_settimeout = setTimeout(function(){_self.changeImgDiv();},2000);
				 }
				 for(var i=0;i<obj.length;i++){
					 obj[i].style.display = dis_str;
				 }
			 },showNextPic : function(i){                        //展示下一张图片
				 if(i<0){
					 i=4
				 }else if(i>4){
					 i=0;
				 }
				 var _self = this;
				 _self.imgTop.cIndex = i;
			 },changeImgDiv :  function(){      //定时滚动div
				 var _self = this;
				 _self.showNextPic(_self.imgTop.cIndex+1);
				 img_settimeout = setTimeout(function(){_self.changeImgDiv();},2000);
			 },changeImgs : function(){      //切换下一批图片
				 var _self = this;
				 _self.imgTop.cIndex = 0;
				 _self.getImgTop();
			 },getTypes : function(){
				var _self = this;
				tool.ajax.get('type/select',null,function (response) {
					var result = response.data;
				    if(result.success ===false){
				    	alert("系统异常");
				    	return ;
				    }
				    _self.typeList  = result.data;
				});
				
			 }
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
		mounted : function(){
			var _self = this;
			
			//首先获取新闻类型
			var href = window.location.href;
			var type = '';
			var typecode  = href.substring(href.lastIndexOf("/")+1,href.length);
			if(typecode=='all'){
				_self.criteria.type = type;
			    _self.getNews();
				_self.getHotTop();
				_self.getImgTop();
				_self.getRandomNews();
				img_settimeout = setTimeout(function(){_self.changeImgDiv();},2000);
				_self.getTypes();
				_self.refreshAuto();
				return;
			}
			tool.ajax.get('type/select',{'code':typecode},function (response) {
			    var result = response.data;
			    console.log(result);
			    if(result.success ===false){
			    	alert("系统异常");
			    	return ;
			    }
			    var array = result.data;
			    if(array.length ==0){     //没有找到该类型
			    	window.location.href = "index/all";
			    	return ;
			    }
			    //设置网页title
			    document.title = result.data[0].name;
			    _self.criteria.type = result.data[0].id;
			    _self.getNews();
				_self.getHotTop();
				_self.getImgTop();
				_self.getRandomNews();
				img_settimeout = setTimeout(function(){_self.changeImgDiv();},2000);
				_self.getTypes();
				_self.refreshAuto();
		   
		    });
        }
	});
};
