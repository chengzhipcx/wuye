<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.onlymvp.entity.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title>Real Site a Real Estate Category Flat Bootstrap Responsive Website Template | Home :: w3layouts</title>
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
	function doRepairSubmit() {

		var repairContent = $('#repairContent').val();

		if (repairContent == '') {
			alert('报修内容不能为空');
			return;
		}

		$.ajax({
			url : 'repairAndComplain!save.action',
			dataType : 'json',
			type : 'POST',
			data : {
				infoType : '1',
				content : repairContent
			},
			success : function(data) {
				alert(data.desc);
				if (data.status == "OK") {
					$('#repairContent').val('');
				}
			}
		});
	}

	function doComplainSubmit() {

		var repairContent = $('#repairContent').val();

		if (complainContent == '') {
			alert('投诉内容不能为空');
			return;
		}

		$.ajax({
			url : 'repairAndComplain!save.action',
			dataType : 'json',
			type : 'POST',
			data : {
				infoType : '2',
				content : repairContent
			},
			success : function(data) {
				alert(data.desc);
				if (data.status == "OK") {
					$('#complainContent').val('');
				}
			}
		});
	}
</script>
<!-- start-smoth-scrolling -->

</head>
<body style="background-image:url('images/p3.jpg');">

<!-- navigation -->
<div class="header w3ls wow bounceInUp" data-wow-duration="1s" data-wow-delay="0s" >
	<div class="container" >
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
										<li role="presentation" class="active"><a class="hvr-overline-from-center active" role="tab"
											data-toggle="tab" href="#repair">报修</a></li>
										<li role="presentation"><a class="hvr-overline-from-center" role="tab" data-toggle="tab"
											href="#complain">投诉</a></li>
										<li role="presentation"><a class="hvr-overline-from-center" role="tab" data-toggle="tab"
											href="#charge">缴费查询</a></li>
									</ul>
								</nav>
							</div><!-- /navbar-collapse -->
						</nav>
	</div>
</div>
<!-- //navigation -->
	<div class="inner cover tab-content"  style="height:500px">
					<br><br><br><br><br><br>
					<div role="tabpanel" class="tab-pane active" id="repair">
						<div class="form-horizontal">
							<div class="form-group">
								<label for="repairContent" class="col-sm-2 control-label">报修</label>
								<div class="col-sm-9">
									<input type="text" class="form-control text-center"
										id="repairContent" name="content" placeholder="报修内容">
								</div>
								<button class="btn btn-default" onclick="doRepairSubmit()">提交</button>
							</div>
						</div>
					</div>

					<div role="tabpanel" class="tab-pane" id="complain">
						<div class="form-horizontal">
							<div class="form-group">
								<label for="complainContent" class="col-sm-2 control-label">投诉</label>
								<div class="col-sm-9">
									<input type="text" class="form-control text-center"
										id="complainContent" name="content" placeholder="投诉内容">
								</div>
								<button class="btn btn-default" onclick="doComplainSubmit()">提交</button>
							</div>
						</div>
					</div>

					<div role="tabpanel" class="tab-pane" id="charge">
						<div class="form-horizontal">
							<div class="form-group" >
								<ul class="list-group" id="chargeList">
									
								</ul>
							</div>
						</div>
					</div>

				</div>
				<div class="mastfoot">
					<div class="inner">
						<p>
							
						</p>
					</div>
				</div>



	<script type="text/javascript">
		$(function(){
			$.ajax({
				url : 'charge!listByUserId.action',
				type : 'POST',
				dataType : 'json',
				data : {
					userId : '<%=((UserInfoEntity) request.getSession().getAttribute("userBean")).getId()%>'
						},
						success : function(data) {
							for (var i = 0; i < data.length; i++) {
								var li = '<li class="list-group-item list-group-item-success">备注 '+data[i].remark+ ' 金额 ' + data[i].payment +'</li>';
								$('#chargeList').append(li);
							}
						}
					});
		});
	</script>


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