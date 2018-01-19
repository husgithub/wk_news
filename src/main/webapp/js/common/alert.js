var my = {
		confirm : function(title,content,fnOk,fnCancel){
			if($("#my_confirm").length>0){
				//$(".bg").show();
				$("#my_confirm").slideDown("fast");
				return;
			}
			var c_html = '<div class="bg" id="my_confirm">'+
						    '<div class="panel panel-default show" style="display:none">'+
							    '<div class="panel-heading">'+
							        '<h3 class="panel-title">'+title+'</h3>'+
							        '<button type="button" class="close">&times;</button>'+
							    '</div>'+
							    '<div class="panel-body">'+
							      ' <p>'+content+'</p>'+
							    '</div>'+
							    '<div class="modal-footer">'+
									'<button type="button" class="btn btn-primary btn-sm">确定</button>'+
							        '<button type="button" class="btn btn-default btn-sm">取消</button>'+
								'</div>'+
							'</div>'+
						'</div>';
			$("body").append(c_html); 
			var win_width = $("body").width();
			var c_width = $("#my_confirm div").width();
			//$(".bg div").css({"top": "25%","left": (win_width-c_width)/2});
			$("#my_confirm").slideDown("fast");
			$("#my_confirm .modal-footer button:eq(0)").click(function(){     //确认
				if (fnOk && typeof fnOk == "function")  
					fnOk(true);  
				$("#my_confirm").hide();
			});
			$("#my_confirm .close , #my_confirm .modal-footer button:eq(1)").click(function(){  //取消
				if (fnCancel && typeof fnCancel == "function")  
					fnCancel(true);  
				$("#my_confirm").hide();
			});
		},
		alert : function(title,content,fn){
			if($("#my_alert").length>0){
				//$(".bg").show();
				$("#my_alert").slideDown("fast");
				return;
			}
			var c_html = '<div class="bg" id="my_alert">'+
						    '<div class="panel panel-default show" style="display:none">'+
							    '<div class="panel-heading">'+
							        '<h3 class="panel-title">'+title+'</h3>'+
							        '<button type="button" class="close">&times;</button>'+
							    '</div>'+
							    '<div class="panel-body">'+
							      ' <p>'+content+'</p>'+
							    '</div>'+
							    '<div class="modal-footer" style="text-align: center;">'+
									'<button type="button" class="btn btn-primary btn-sm">确定</button>'+
								'</div>'+
							'</div>'+
						'</div>';
			$("body").append(c_html); 
			var win_width = $("body").width();
			var c_width = $("#my_alert div").width();
			//$(".bg div").css({"top": "25%","left": (win_width-c_width)/2});
			$("#my_alert").slideDown("fast");
			$("#my_alert .modal-footer button:eq(0)").click(function(){     //确认
				if (fn && typeof fn == "function")  
					fn(true);  
				$("#my_alert").hide();
			});
			$("#my_alert .close , #my_alert .modal-footer button:eq(1)").click(function(){  //取消
				$("#my_alert").hide();
			});
		},
		show : function(content,speed){
			if(speed==undefined||speed==null){
				speed = 2000;
			}
			if($(".my_show").length>0){
				$(".my_show").show().animate({
				   top: '100px'
				}, 500).delay(speed);
				$(".my_show").animate({opacity:0.2},200,function(){
					$(".my_show").hide();
					$(".my_show").css({top:"0px",opacity:'1'});
				});
				return;
			}
			var show_html = '<div class="my_show" style="display:none;">'+
	                        '<div class="alert alert-success">'+content+'</div>'+
	                        '</div> ';
			$("body").append(show_html); 
			$(".my_show").show().animate({
			   top: '100px'
			}, 500).delay(speed);
			$(".my_show").animate({opacity:0.2},200,function(){
				$(".my_show").hide();
				$(".my_show").css({top:"0px",opacity:'1'});
			});
			
		}
}