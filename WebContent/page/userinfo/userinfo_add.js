//window.onload = function(){
//	function UrlSearch() 
//{
// var name,value; 
// var str=location.href; //取得整个地址栏
// var num=str.indexOf("?") 
// str=str.substr(num+1); //取得所有参数   stringvar.substr(start [, length ]
//
// var arr=str.split("&"); //各个参数放到数组里
// for(var i=0;i < arr.length;i++){ 
//  num=arr[i].indexOf("="); 
//  if(num>0){ 
//   name=arr[i].substring(0,num);
//   value=arr[i].substr(num+1);
//   this[name]=value;
//   } 
//  } 
//} 
//var Request=new UrlSearch(); //实例化
////alert("传入参数id："+Request.id);
//	 
//　　$.ajax({
//				url : "../../json/userinfo_one.json",
//				type : "get",
//				dataType : "json",
//				success : function(data){
//					console.log(data[0]);
//					$(".uname").val(data[0].uname);
//					$(".unumber").val(data[0].unumber);
//					$(".uphone").val(data[0].uphone);
//					
//					
//					
//					$(".did").val(data[0].did);
//					$(".utid").val(data[0].utid);
//					$(".uemail").val(data[0].uemail);
//					$(".utphone").val(data[0].utphone);
//					$(".ucard").val(data[0].ucard);
//					$(".uqq").val(data[0].uqq);
//					$(".uwechat").val(data[0].uwechat);
//					$(".uremark").val(data[0].uremark);
//					
//				}
//			});
//
//　
//	
//}


layui.config({
	base : "js/"
}).use(['form','layer','jquery','layedit','laydate'],function(){
	
	
	
	var form = layui.form(),
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		laypage = layui.laypage,
		layedit = layui.layedit,
		laydate = layui.laydate,
		$ = layui.jquery;
		
		

	//创建一个编辑器
// 	var editIndex = layedit.build('news_content');
// 	var addNewsArray = [],addNews;
 	form.on("submit(add_userinfo)",function(data){
 		console.log($(".uname").val());
 		$.ajax({
			type:"get",
			dataType: "json",
		    url: "https://www.zhuwenlie.cn/Libraryseat/adminServlet?opr=userinfo_add",
		   	data:{
		        uname: $(".uname").val(),
		        unumber: $(".unumber").val(),
		        upasswd: $(".upasswd").val(),
		        urex: $('input:radio:checked').val(),
		        uphone: $(".uphone").val(),
		        utid: $(".utid").val(),
		        did: $(".did").val(),
		        uemail: $(".uemail").val(),
		        utphone: $(".utphone").val(),
		        ucard: $(".ucard").val(),
		        uqq: $(".uqq").val(),
		        uwechat: $(".uwechat").val(),
		        uremark: $(".uremark").val(),
		        
		        
		   	},
		    success: function (result) {
		        console.log(result);
		        
		    },
		    error : function() {
		        alert("异常！");
		    }
		});
 		//是否添加过信息
//	 	if(window.sessionStorage.getItem("addNews")){
//	 		addNewsArray = JSON.parse(window.sessionStorage.getItem("addNews"));
//	 	}
	 	//显示、审核状态
// 		var isShow = data.field.show=="on" ? "checked" : "",
// 			newsStatus = data.field.shenhe=="on" ? "审核通过" : "待审核";
//
// 		addNews = '{"newsName":"'+$(".newsName").val()+'",';  //文章名称
// 		addNews += '"newsId":"'+new Date().getTime()+'",';	 //文章id
// 		addNews += '"newsLook":"'+$(".newsLook option").eq($(".newsLook").val()).text()+'",'; //开放浏览
// 		addNews += '"newsTime":"'+$(".newsTime").val()+'",'; //发布时间
// 		addNews += '"newsAuthor":"'+$(".newsAuthor").val()+'",'; //文章作者
// 		addNews += '"isShow":"'+ isShow +'",';  //是否展示
// 		addNews += '"newsStatus":"'+ newsStatus +'"}'; //审核状态
// 		addNewsArray.unshift(JSON.parse(addNews));
// 		window.sessionStorage.setItem("addNews",JSON.stringify(addNewsArray));
 		//弹出loading
 		var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            top.layer.close(index);
			top.layer.msg("文章添加成功！");
 			layer.closeAll("iframe");
	 		//刷新父页面
	 		parent.location.reload();
        },2000);
 		return false;
 	})
 	
 	
	
})
