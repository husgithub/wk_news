Vue.use(Vuex); //使用Vuex

const  vuex_store = new Vuex.Store({
    state:{
    	msg : {curRecord:"1-10",pageHtml:'',nodatas:''},
		pag : [],
		uipage : 10,             //每页展示的分页按钮数量
		gtIndex : '',
		pageIndex : 1,
		pageSize : 20,
		total : 0,
		totalPage : 0
    },
    mutations:{
        showUserName:function(state){
        }
    }
});

//分页组件
var Pager = Vue.extend({
    template :'<div class="page">' +
                '<div class="left">' +
                '<p>显示{{msg.curRecord}}条，共{{total}}条记录&nbsp;&nbsp;</p>' +
                '<p>每页显示</p>' +
                '<select class="form-control" v-model="pageSize" @change="pageSizeChange()">' +
                    '<option value="10">10</option>' +
                    '<option value="20"  selected="selected">20</option>' +
                    '<option value="30">30</option>' +
                    '<option value="50">50</option>' +
                    '<option value="100">100</option>' +
                '</select>' +
               '<p>条记录</p>' +
              '</div>' +
              '<div class="right">' +
                '<ul class="pagination pagination-sm">' +
                    '<li v-bind:class="{disabled: pageIndex<=1}" v-on:click="pageIndex-1>=1&&gotoPage(1)"><a href="javascript:void(0)">第一页</a></li>' +
                    '<li v-bind:class="{disabled: pageIndex<=1}" v-on:click="pageIndex-1>=1&&gotoPage(pageIndex-1)"><a href="javascript:void(0)">&laquo;</a></li>' +
                    '<li v-for="index in pag" @click="gotoPage(index)" :class="{active:index == pageIndex}" :key="index">' +
                        '<a href="javascript:void(0)" >{{index}}</a>' +
                    '</li>' +
                    '<li v-bind:class="{disabled: pageIndex>=totalPage}" v-on:click="pageIndex+1<=totalPage&&gotoPage(pageIndex+1)"><a href="javascript:void(0)">&raquo;</a></li>' +
                    '<li v-bind:class="{disabled: pageIndex>=totalPage}" v-on:click="pageIndex+1<=totalPage&&gotoPage(totalPage)"><a href="javascript:void(0)">最后一页</a></li>' +
                '</ul>' +
                ' <button type="button" class="btn btn-default btn-sm" style="background: white;" v-on:click="refresh"><span class="glyphicon glyphicon-refresh"></span> 刷新</button>' +
                '<div class="goto">' +
                    '<p>跳转到第</p>' +
                    '<input  class="form-control" v-model.number="gtIndex" type="number" v-on:keyup.enter="jump"/>' +
                    '<p>页</p>' +
                '</div></div></div>',
                props : ['aa'],
    
    data : function(){
    	return {
    	}
    },
    computed : {
    	msg : function(){
    		return vuex_store.state.msg;
    	},
    	pageIndex : function(){
    		return vuex_store.state.pageIndex;
    	},
    	pageSize : function(){
    		return vuex_store.state.pageSize;
    	},
    	pag : function(){
    		return vuex_store.state.pag;
    	},
    	uipage : function(){
    		return vuex_store.state.uipage;
    	},
    	gtIndex : function(){
    		return vuex_store.state.gtIndex;
    	},
    	total : function(){
    		return vuex_store.state.total;
    	},
    	totalPage : function(){
    		return vuex_store.state.totalPage;
    	}
    },
    methods : {
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
		 
    },
    filters : {
		 titleHref:function(src){
			 return  "index/"+src;
		 }
	},
    mounted : function(){
    	var _self = this;
    }
});
Vue.component('my-pager',Pager);
