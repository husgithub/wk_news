<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String contextPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
    pageContext.setAttribute("ctx", contextPath);
%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传测试</title>

</head>
<body>
	<form name="Form2" action="${ctx}upload/test" method="post"  enctype="multipart/form-data">
	<h1>使用spring mvc提供的类的方法上传文件</h1>
	<input type="file" name="file">
	<input type="submit" value="upload"/>
	</form>
</body>
</html>