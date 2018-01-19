var deal = {
		rightShow : function(src){
			/*var href = $(obj).find("a").attr("href");*/
			$("#right").find("iframe").attr("src",src);
		}
}
$(function(){
	$("#menu").find(".panel.panel-default").each(function(){
		$(this).find(".panel-heading").bind({
			click : function(){
				var parent = $(this).parent(".panel.panel-default");
				parent.find("ul").show(200);
				parent.siblings().find("ul").hide(200);
			},
			mouseover : function(){
				/*$(this).css({"background-color":"#337ab7","color":"white"});*/
			},
			mouseout : function(){
				/*$(this).attr('class','panel-heading');
				$(this).css({"background-color":"#f5f5f5","color":"black"});*/
			}
			
		});
		$(this).find("ul .list-group-item").bind({
			click : function(){
				var parent = $(this).parent(".panel.panel-default");
				parent.find("ul").show(200);
				parent.siblings().find("ul").hide(200);
			},
			mouseover : function(){
				$(this).css({"background-color":"#f5f5f5"});
			},
			mouseout : function(){
				$(this).css({"background-color":"white"});
			}
		});
	});
});