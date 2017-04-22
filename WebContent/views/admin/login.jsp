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
<base href="<%=basePath%>">
<jsp:include page="../common/head.jsp"></jsp:include><!-- 引入js,css -->

<script type="text/javascript">

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

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员登录</title>
</head>
<body>
	<div class="site-wrapper">

		<div class="site-wrapper-inner">

			<div class="cover-container">

				<div class="masthead clearfix">
					<div class="inner">
						<h3 class="masthead-brand">物业管理系统——管理员登录</h3>

					</div>

				</div>


				<div class="inner cover">
					<div class="form-horizontal">
							<div class="form-group">
								<label for="userName" class="col-sm-2 control-label">User</label>
								<div class="col-sm-6">
									<input type="text" class="form-control text-center"
										id="userName" name="userName" placeholder="Enter User">
								</div>
							</div>
							<div class="form-group">
								<label for="userPwd" class="col-sm-2 control-label">Password</label>
								<div class="col-sm-6">
									<input type="password" class="form-control text-center"
										id="userPwd" name="userPwd" placeholder="Password">
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-6">
									<button  class="btn btn-default"
										onclick="doLogin()">Login</button>
								</div>
							</div>
					</div>


					<div class="mastfoot">
						<div class="inner">
							<p>
								<a href="http://onlymvp.com">Onlymvp.com</a> by <a
									target="_blank"
									href="http://wpa.qq.com/msgrd?v=3&uin=53383633&site=qq&menu=yes">ZH</a>.
							</p>
						</div>
					</div>

				</div>

			</div>

		</div>
	</div>
</body>
</html>