<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../common/head.jsp"></jsp:include><!-- 引入js,css -->
<title>管理员登陆界面</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="application/x-javascript">
	
addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } 
</script>
<link href="css/style1.css" rel='stylesheet' type='text/css' />
<!--webfonts-->
<!--//webfonts-->
</head>
<body>
	<script>
		$(document).ready(function(c) {
			$('.close').on('click', function(c) {
				$('.login-form').fadeOut('slow', function(c) {
					$('.login-form').remove();
				});
			});
		});
		
		function doLogin(){
			var userName = $('#userName').val();
			var userPwd = $('#userPwd').val();
			
			if(userName == ""){
				alert("登录名不能为空!");
				return;
			}
			
			if(userPwd == ""){
				alert("登录密码不能为空!");
				return;
			}
			
			
			var url = '<%=basePath%>admin!login.action';// 登录url
			var data = {"userName" : userName, "userPwd" : userPwd};// 登录参数
			$.ajax({
				url : url,
				type : 'POST',
				dataType : 'json',
				async : false,
				data : data,
				success : function(obj){
					if(obj.messageInfoEntity.status == "OK"){// 登录成功
						/* window.open("adminIndex!index.action?adminId=" + obj.messageInfoEntity.detail.id);
						window.close(); */
						var timestamp = Date.parse(new Date());
						window.location.href="adminIndex!index.action?adminId=" + obj.messageInfoEntity.detail.id+"&timestamp=" + timestamp;
					}else{
						alert(obj.messageInfoEntity.desc);
					}
				}
			});
		}
	</script>
	<!--SIGN UP-->
	<h1>物业管理系统--管理员登陆界面</h1>
	<div class="login-form">
		<div class="close"></div>
		<div class="head-info">
			<label class="lbl-1"> </label> <label class="lbl-2"> </label> <label
				class="lbl-3"> </label>
		</div>
		<div class="clear"></div>
		<div class="avtar">
			<img src="images/avtar.png" />
		</div>
		<form>
			<input type="text" id="userName" name="userName" class="text" value="用户名"
				onfocus="this.value = '';"
				onblur="if (this.value == '') {this.value = 'Username';}">
			<div class="key">
				<input type="password" id="userPwd" name="userPwd" value="Password" onfocus="this.value = '';"
					onblur="if (this.value == '') {this.value = 'Password';}">
			</div>
		</form>
		<div class="signin">
			<input type="submit" onclick="doLogin()" value="登陆">
		</div>
	</div>
	<div class="copy-rights">
		<p>
			Copyright &copy; 2017.Company name All rights reserved.<a
				target="_blank" href="<%=path %>/">前端首页</a>
		</p>
	</div>

</body>
</html>