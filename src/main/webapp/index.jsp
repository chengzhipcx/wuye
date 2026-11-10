<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title>小区客房管理系统前端登陆</title>
<!-- for-mobile-apps -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="Real Site Responsive web template, Bootstrap Web Templates, Flat Web Templates, Android Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyEricsson, Motorola web design" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false);
		function hideURLbar(){ window.scrollTo(0,1); } </script>
<!-- //for-mobile-apps -->
<link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/iconeffects.css" rel='stylesheet' type='text/css' />
<link rel="stylesheet" type="text/css" href="css/jquery-ui1.css">
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<!-- js -->
<script type="text/javascript" src="js/jquery-2.1.4.min.js"></script>
<!-- //js -->
<!--animate-->
<link href="css/animate.css" rel="stylesheet" type="text/css" media="all">
<script src="js/wow.min.js"></script>
	<script>
		 new WOW().init();
	</script>
<!--//end-animate-->

<!-- start-smoth-scrolling -->
<script type="text/javascript" src="js/move-top.js"></script>
<script type="text/javascript" src="js/easing.js"></script>
<script type="text/javascript">
	jQuery(document).ready(function($) {
		$(".scroll").click(function(event){		
			event.preventDefault();
			$('html,body').animate({scrollTop:$(this.hash).offset().top},1000);
		});
	});
</script>
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
	
	
	var url = 'user!login.action';// 登录url
	var data = {"userName" : userName, "userPwd" : userPwd};// 登录参数
	$.ajax({
		url : url,
		type : 'POST',
		dataType : 'json',
		async : false,
		data : data,
		success : function(obj){
			if(obj.status == "OK"){// 登录成功
				/* window.open("adminIndex!index.action?adminId=" + obj.messageInfoEntity.detail.id);
				window.close(); */
				var timestamp = Date.parse(new Date());
				window.location.href="views/user/index.jsp";
			}else{
				alert(obj.desc);
			}
		}
	});
}

</script>
<!-- start-smoth-scrolling -->

</head>
<body>
<!-- navigation -->
<div class="header w3ls wow bounceInUp" data-wow-duration="1s" data-wow-delay="0s">
	<div class="container">
						<nav class="navbar navbar-default">
							<!-- Brand and toggle get grouped for better mobile display -->
							<div class="navbar-header logo">
								<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
									<span class="sr-only">小区物业管理系统</span>
									<span class="icon-bar"></span>
									<span class="icon-bar"></span>
									<span class="icon-bar"></span>
								</button>
								<h1>
									<a class="navbar-brand link link--yaku" href="index.html">小区物业管理系统</a>
								</h1>
								
							</div>
							<!-- Collect the nav links, forms, and other content for toggling -->
							<div class="collapse navbar-collapse nav-wil" id="bs-example-navbar-collapse-1">
								<nav class="cl-effect-1">
									<ul class="nav navbar-nav ">
										<!-- <li><a class="hvr-overline-from-center active" href="index.html">首页</a></li>
										<li><a class="hvr-overline-from-center" href="about.html"></a></li>
										<li><a class="hvr-overline-from-center" href="gallery.html">Properties</a></li>
										<li><a class="hvr-overline-from-center" href="services.html">Services</a></li>
										<li><a class="hvr-overline-from-center" href="codes.html">Short Codes</a></li>
										<li><a class="hvr-overline-from-center" href="contact.html">Contact</a></li> -->
									</ul>
								</nav>
							</div><!-- /navbar-collapse -->
						</nav>
	</div>
</div>
<!-- //navigation -->
<!-- header -->
<div class="banner w3l">
	
	<div class="banner-info">
		<div class="container">
			<div class="profile-left wow flipInY" data-wow-duration="1.5s" data-wow-delay="0s">
					<div align="center" class="login">
					<input type="text" class="form-control text-center"	id="userName" name="userName" placeholder="请输入用户名">
					<br><br>
					<input type="password" class="form-control text-center" id="userPwd" name="userPwd" placeholder="请输入密码">
					<br><br>
					<button  class="btn btn-default" onclick="doLogin()">点击登陆</button>
					</div>
			</div>
		</div>
	</div>
</div>
<!-- //header -->
<div class="footer w3agile wow bounceInDown" data-wow-duration="1.5s" data-wow-delay="0s">
	<div class="container">
		<!-- <div class="foo-arr text-center"><img src="images/arrows.png" alt=" "/></div> -->
		<div class="col-md-4 footer-left">
			<i class="glyphicon glyphicon-map-marker" aria-hidden="true"></i><p>山东省济南市长清区<span>大学路3501号</span></p>
		</div>
		<div class="col-md-4 footer-left">
			<i class="glyphicon glyphicon-envelope" aria-hidden="true"></i><p><a href="mailto:example@mail.com">mail@example1.com</a><span><a href="mailto:example@mail.com">mail@example2.com</a></span></p>
		</div>
		<div class="col-md-4 footer-left">
			<i class="glyphicon glyphicon-earphone" aria-hidden="true"></i><p>+123 2222 222<span>+123 456 7890</span><a href="<%=path %>/adminIndex!index.action"><span>进入后台系统</span></a></p>
		</div>
		<div class="clearfix"></div>
		<div class="copyrights w3">
			<div class="copy-left">
			</div>
			<div class="copy-right agileinfo">
				<ul class="fb_icons">
					<li class="hvr-rectangle-out"><a class="fb" href="#"></a></li>
					<li class="hvr-rectangle-out"><a class="twit" href="#"></a></li>
					<li class="hvr-rectangle-out"><a class="goog" href="#"></a></li>
					<li class="hvr-rectangle-out"><a class="pin" href="#"></a></li>
					<li class="hvr-rectangle-out"><a class="drib" href="#"></a></li>
				</ul>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>
<!-- //footer -->
<!-- smooth scrolling -->
	<script type="text/javascript">
		$(document).ready(function() {
		/*
			var defaults = {
			containerID: 'toTop', // fading element id
			containerHoverID: 'toTopHover', // fading element hover id
			scrollSpeed: 1200,
			easingType: 'linear' 
			};
		*/								
		$().UItoTop({ easingType: 'easeOutQuart' });
		});
	</script>
	<a href="#" id="toTop" style="display: block;"> <span id="toTopHover" style="opacity: 1;"> </span></a>
<!-- //smooth scrolling -->
<script type="text/javascript" src="js/bootstrap-3.1.1.min.js"></script>
</body>
</html>