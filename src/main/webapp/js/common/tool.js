var tool = {
		ajax : {
			get :  function(url,param,fn){
				axios.get(url, {params: param})
				  .then(fn)
				  .catch(function (error) {
					  alert("系统异常");
				      console.log(error);
				});
			},post : function(url,param,fn){
				let params = new URLSearchParams();
				for(key in param){
					params.append(key, param[key]);
				}
				axios.post(url,params)
				 .then(fn)
				 .catch(function(err){
				   console.log(err);
				 });
			}
		
		},
		dateUtil : {
				format : function(obj,pattern){
					if(obj==undefined||obj==null||obj==''){
						return "";
					}
					if(pattern==undefined||pattern==null){
						pattern = "yyyy-MM-dd HH:mm:ss";
					}
					var str = JSON.stringify(obj);
					var time = str.substring(str.indexOf("\"time\"")+7,str.indexOf("\"timezoneOffset\"")-1);
					var date = new Date(Number (time));
					var year =  date.getFullYear() ;
					var month = this.formatNum(date.getMonth()+1);
					var day = this.formatNum(date.getDate());
					var hour = this.formatNum(date.getHours());
					var minutes = this.formatNum(date.getMinutes());
					var seconds = this.formatNum(date.getSeconds());
					var time_str = pattern.replace("yyyy",year).replace("MM",month).replace("dd",day).replace("HH",hour).replace("mm",minutes).replace("ss",seconds);
					return time_str;
				},
				formatNum : function(num){
					if(num>=10){
						return num;
					}
					return "0"+num;
				},
				formatWithMillisecond : function(time,pattern){                     //根据毫秒和pattern格式化时间
					if(pattern==undefined||pattern==null){
						pattern = "yyyy-MM-dd HH:mm:ss";
					}
					var date = new Date(Number (time));
					var year =  date.getFullYear() ;
					var month = this.formatNum(date.getMonth()+1);
					var day = this.formatNum(date.getDate());
					var hour = this.formatNum(date.getHours());
					var minutes = this.formatNum(date.getMinutes());
					var seconds = this.formatNum(date.getSeconds());
					var time_str = pattern.replace("yyyy",year).replace("MM",month).replace("dd",day).replace("HH",hour).replace("mm",minutes).replace("ss",seconds);
					return time_str;
				},
				dynamicFormat : function(obj){                          //与当前时间比对动态显示时间
					if(obj==undefined){
						return "";
					}
					var time_str = "";
					var pattern = "yyyy年MM月dd日 HH时mm分";
					//var str = JSON.stringify(obj);
					//var time = str.substring(str.indexOf("\"time\"")+7,str.indexOf("\"timezoneOffset\"")-1);
					var time = obj.time;
					var nowTime = new Date().getTime();
					var cha = nowTime-time;
					if( cha <= 3600000){            //以分钟计数
						var minutes = Math.floor(cha/60000);
						if(minutes == 0){
							time_str = "刚刚";
						}else{
							time_str = minutes+"分钟前";
						}
					}else if(cha <= 86400000){  //以小时计数
						var hour = Math.floor(cha/3600000);
						time_str = hour+"小时前";
					}else{    //以天计数
						var day = Math.ceil(cha/3600000);
						if(day<=15){
							time_str = day+"天前";
						}else{      //直接显示时间
							time_str = this.formatWithMillisecond(time+"",pattern);
						}
						
					}
					return time_str;
				}
		},
		array : {
			remove : function(original,needRemove){
				if(original instanceof Array == false||needRemove instanceof Array == false){
					throw "tool.array.remove:形参格式有误!";
					return null;
				}
				for(var i=0;i<needRemove.length;i++){
					for(var j=0;j<original.length;j++){
						
					}
				}
				
			}
		},
		textUtil : {
			getImgs : function(text){
				console.log(text);
			}
		}
}