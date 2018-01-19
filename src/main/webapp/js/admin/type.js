window.onload = function(){
	  var div_type = new Vue({
		 el : "#div_type",
		 data : {
			 list : [],
			 message : '',
			 single : {id:'',code:'',name:'',parentid:''},        //新增或修改的单个类型
			 alert_panel :{
				 type : 'add' ,
				 msg : [{key:'add',value:'新增'},{key:'edit',value:'编辑'}],
				 c_msg :''     //当前显示的文字
			 },                             //模态框类型，新增或编辑
			 id :''
		 },
		 methods: {
			 getTypes : function(){
				var _self = this;
				tool.ajax.get('type/select',null,function (response) {
					var result = response.data;
				    if(result.success ===false){
				    	alert("系统异常");
				    	return ;
				    }
				    _self.list  = result.data;
				    if(_self.list.length == 0){
				    	_self.message = '暂无数据！';
				    }
				});
				
			 },
			 deleteOne : function(id){
				 var _self = this;
			     _self.id = id;
				 my.confirm('提示','确定删除该新闻类别吗？',function(){
					 tool.ajax.post('type/deleteOne',{'id' : _self.id},function (response) {
						var result = response.data;
					    if(result.success ===false){
					    	alert(result.error);
					    	return ;
					    }
					    _self.getTypes();
					    alert("删除成功！");
					    
					 });
				 });
			},saveOrUpdate : function(){
				var _self = this;
				if(_self.single.code==undefined||_self.single.code==''||_self.single.name==undefined||_self.single.name==''){
					alert('请补全内容！');
					return ;
				}
				var param={};
				$("#type_add form input").each(function(){
					param[$(this).attr('name')] = $(this).val();
				});
				tool.ajax.post('type/saveOrUpdate',param,function (response) {
					var result = response.data;
				    if(result.success ===false){
				    	alert(result.error);
				    	return ;
				    }
				    alert(_self.alert_panel.c_msg+"成功！");
				    _self.getTypes();
				    _self.single = {};    //成功后清除临时数据
				    $("#type_add").hide();
				    
				 });
				
			},edit : function(data){
				var _self = this;
				_self.single = data;
				 view.alert_type.show('edit');
			}
         },mounted : function(){
        	 var _self = this;
        	 _self.getTypes();
         }
	  });
	  
	  var view = {
			  alert_type : {
				  show : function(type){
					  div_type.alert_panel.type = type;
					  var id = '#type_add';
					  for(i in div_type.alert_panel.msg){
						  if(type == div_type.alert_panel.msg[i].key){
							  div_type.alert_panel.c_msg = div_type.alert_panel.msg[i].value;
						  }
					  }
					  $(id).slideDown('fast');
				  }
			  }
	  }
	  
	  $("#btn_add").bind({
		  'click' : function(){
			  view.alert_type.show('add');
		  }
	  });
	  $("#type_add .close").bind({
		  'click' : function(){
			  $("#type_add").hide();
			  div_type.single = {};    //成功后清除临时数据
		  }
	  })
	  
	  
};
