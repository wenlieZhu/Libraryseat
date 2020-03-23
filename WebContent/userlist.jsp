<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户信息</title>
</head>
<body>
	
	<%-- <table border="1">
    	<thead>
    		<tr>
    			<th>用户id</th>
    			<th>姓名</th>
    			<th>学号/工号</th>
    			<th>密码</th>
    		</tr>
    	</thead>
    	<tbody>
    		<c:if test="${list!=null && list.size()>0 }">
    			<c:forEach items="${list }" var="userinfo" varStatus="s">
    				<tr>
		    			<td>${userinfo.uid }</td>
		    			<td>${userinfo.uname }</td>
		    			<td>${userinfo.unumber }</td>
		    			<td>${userinfo.upasswd }</td>
		    		</tr>
    			</c:forEach>
    		</c:if>
    	</tbody>
    </table> --%>
</body>
</html>