var uuid;
function child(d) {
	uuid=d;
}
//window.onload = function(){
//	window.uuid;
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
//window.uuid=Request.id;
//console.log("用户id"+window.uuid);
	//console.log(Request.id);
//alert("传入参数id："+Request.id);
	 
//　　$.ajax({
//				url : "../../json/userinfo_one.json",
//				type : "get",
//				dataType : "json",
//				success : function(data){
//					console.log(data[0].urex);
//					$(".uname").val(data[0].uname);
//					$(".unumber").val(data[0].unumber);
//					$(".uphone").val(data[0].uphone);
//					$(".urex").val(data[0].urex);
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
//			
//			});
//			}



 


layui.config({
	base : "js/"
}).use(['form','layer','jquery','layedit','laydate'],function(){
	
	
	
	var form = layui.form(),
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		laypage = layui.laypage,
		layedit = layui.layedit,
		laydate = layui.laydate,
		$ = layui.jquery;
		
//		var editNews;
//		var userinfoArray = [],editNews;
		form.on("submit(edit_userinfo)",function(data){
			$.ajax({
				type:"get",
				dataType: "json",
        url: "https://www.zhuwenlie.cn/Libraryseat/adminServlet?opr=userinfo_edit",
       	data:{
       		uid: uuid,
            uname: $(".uname").val(),
            unumber: $(".unumber").val(),
            upasswd: $(".upasswd").val(),
            uphone: $(".uphone").val(),
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
//			editNews = '{"uname":"'+$(".uname").val()+'",';
//			editNews += '"unumber":"'+$(".unumber").val()+'",';	
//			editNews += '"uphone":"'+$(".uphone").val()+'",';
//			editNews += '"urex":"'+$(".urex").val()+'",';
//			editNews += '"did":"'+$(".did").val()+'",';
//			editNews += '"utid":"'+$(".utid").val()+'",';
//			editNews += '"uemail":"'+$(".uemail").val()+'",';
//			editNews += '"utphone":"'+$(".utphone").val()+'",';
//			editNews += '"ucard":"'+$(".ucard").val()+'",';
//			editNews += '"uqq":"'+$(".uqq").val()+'",';
//			editNews += '"uwechat":"'+$(".uwechat").val()+'",';
//			editNews += '"uremark":"'+$(".uremark").val()+'"}';
//			
//			
//			userinfoArray.unshift(JSON.parse(editNews));
// 		window.sessionStorage.setItem("editNews",JSON.stringify(userinfoArray));
			
			
			var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
	        setTimeout(function(){
	            top.layer.close(index);
				top.layer.msg("保存成功！");
	 			layer.closeAll("iframe");
		 		//刷新父页面
		 		parent.location.reload();
	        },2000);
	 		return false;
		})

	
 
	//加载默认数据
 	
 		console.log(uuid);
 		$.ajax({
			url : "https://www.zhuwenlie.cn/Libraryseat/adminServlet?opr=userinfo_loadedit&uid="+uuid,
			type : "get",
			dataType : "json",
			success : function(data){
				//console.log(data.djson);
				fillData(data);
				
			}
		})
 	
 	
 	//填充数据方法
 	function fillData(data){
 		$(".uname").val(data.ujson[0].uname); 		
		$(".unumber").val(data.ujson[0].unumber);
		$(".uphone").val(data.ujson[0].uphone);
		$(".upasswd").val(data.ujson[0].upasswd);
		$(".did").val(data.ujson[0].did);
		$(".uemail").val(data.ujson[0].uemail);
		$(".utphone").val(data.ujson[0].utphone);
		$(".ucard").val(data.ujson[0].ucard);
		$(".uqq").val(data.ujson[0].uqq);
		$(".uwechat").val(data.ujson[0].uwechat);
		$(".uremark").val(data.ujson[0].uremark);
 	}
})
