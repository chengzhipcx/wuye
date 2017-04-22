<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.onlymvp.entity.*, java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<jsp:include page="../common/adminHead.jsp"></jsp:include><!-- 引入js,css -->


<link rel="stylesheet" type="text/css"
	href="js/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="js/easyui/themes/icon.css" />
<script type="text/javascript" src="js/easyui/jquery.easyui.min.js"
	charset="utf-8"></script>

<style type="text/css">
body {
	margin: 0;
	padding: 0;
}

div {
	width: 80%;
	height: 450px;
	margin: 0 auto;
}

table {
	height: 80%;
	width: 100%;
	text-align: right;
}
</style>
<body>
	<div>
		<table>
			<tr>
				<td><img src="images/adminIcon.png"></img></td>
			</tr>
		</table>
	</div>
</body>
</html>