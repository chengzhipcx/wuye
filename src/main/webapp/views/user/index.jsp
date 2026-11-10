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
<title>小区物业管理系统--首页</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="Real Site Responsive web template, Bootstrap Web Templates, Flat Web Templates, Android Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyEricsson, Motorola web design" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false);
		function hideURLbar(){ window.scrollTo(0,1); } </script>
<!-- //for-mobile-apps -->
<link href="${pageContext.request.contextPath}/views/user/css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
<link href="${pageContext.request.contextPath}/views/user/css/iconeffects.css" rel='stylesheet' type='text/css' />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/views/user/css/jquery-ui1.css">
<link href="${pageContext.request.contextPath}/views/user/css/style.css" rel="stylesheet" type="text/css" media="all" />
<!-- js -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/views/user/kindeditor/themes/default/default.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/views/user/kindeditor/plugins/code/prettify.css" type="text/css"/>
<script charset="utf-8" src="${pageContext.request.contextPath}/views/user/kindeditor/kindeditor.js"></script>
<script charset="utf-8" src="${pageContext.request.contextPath}/views/user/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="${pageContext.request.contextPath}/views/user/kindeditor/plugins/code/prettify.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/views/user/highcharts1/jquery.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/views/user/highcharts1/highchart.js" charset="UTF-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/views/user/highcharts1/highcharts-3d.js" charset="UTF-8"></script> 
<!-- //js -->
<!--animate-->
<link href="${pageContext.request.contextPath}/views/user/css/animate.css" rel="stylesheet" type="text/css" media="all">
<script src="${pageContext.request.contextPath}/views/user/js/wow.min.js"></script>
	<script>
		 new WOW().init();
	</script>
<!--//end-animate-->

<!-- start-smoth-scrolling -->
<script type="text/javascript" src="${pageContext.request.contextPath}/views/user/js/move-top.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/views/user/js/easing.js"></script>
<script type="text/javascript">
	jQuery(document).ready(function($) {
		$(".scroll").click(function(event){		
			event.preventDefault();
			$('html,body').animate({scrollTop:$(this.hash).offset().top},1000);
		});
	});
</script>
		<script type="text/javascript">
			KindEditor.ready(function(K) {
				var editor1 = K.create('textarea[id="repairContent"]', {
					cssPath : '${pageContext.request.contextPath}/views/user/kindeditor/plugins/code/prettify.css',
					uploadJson : '${pageContext.request.contextPath}/views/user/kindeditor/jsp/upload_json.jsp',
					fileManagerJson : '${pageContext.request.contextPath}/views/user/kindeditor/jsp/file_manager_json.jsp',
					allowFileManager : true,
					 afterBlur: function(){this.sync();},
					afterCreate : function() {
						var self = this;
						K.ctrl(document, 13, function() {
							self.sync();
							document.forms['introduction'].submit();
						});
						K.ctrl(self.edit.doc, 13, function() {
							self.sync();
							document.forms['introduction'].submit();
						});
					}
				});
				prettyPrint();
			});
			KindEditor.ready(function(K) {
				var editor1 = K.create('textarea[id="complainContent"]', {
					cssPath : '${pageContext.request.contextPath}/views/user/kindeditor/plugins/code/prettify.css',
					uploadJson : '${pageContext.request.contextPath}/views/user/kindeditor/jsp/upload_json.jsp',
					fileManagerJson : '${pageContext.request.contextPath}/views/user/kindeditor/jsp/file_manager_json.jsp',
					allowFileManager : true,
					 afterBlur: function(){this.sync();},
					afterCreate : function() {
						var self = this;
						K.ctrl(document, 13, function() {
							self.sync();
							document.forms['introduction'].submit();
						});
						K.ctrl(self.edit.doc, 13, function() {
							self.sync();
							document.forms['introduction'].submit();
						});
					}
				});
				prettyPrint();
			});
	</script>
<script type="text/javascript">
	function logout(){
		window.location.href = "${pageContext.request.contextPath}/user!logout.action";
	}
	function doRepairSubmit() {

 		var repairContent = $('#repairContent').val();
		alert(repairContent);
/* 		if (repairContent == '') {
			alert('报修内容不能为空');
			return;
		} */
 
		$.ajax({
			url : '${pageContext.request.contextPath}/repairAndComplain!save.action',
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
					 location.replace(location.href);
				}
			}
		});
	}

	function doComplainSubmit() {

		var complainContent = $('#complainContent').val();
		alert(complainContent);
		if (complainContent == '') {
			alert('投诉内容不能为空');
			return;
		}

		$.ajax({
			url : '${pageContext.request.contextPath}/repairAndComplain!save.action',
			dataType : 'json',
			type : 'POST',
			data : {
				infoType : '2',
				content : complainContent
			},
			success : function(data) {
				alert(data.desc);
				if (data.status == "OK") {
					$('#complainContent').val('');
					 location.replace(location.href);
				}
			}
		});
	}
</script>
<!-- start-smoth-scrolling -->

</head>
<body style="background:url('images/p3.jpg') repeat;">

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
											<li role="presentation"><a class="hvr-overline-from-center" role="tab" data-toggle="tab"
											href="#dosage">查看历史用量</a></li>
										<li role="presentation"><a class="hvr-overline-from-center" 
										href="javascript:void(0);" onclick="logout();">退出</a></li>
									</ul>
								</nav>
							</div><!-- /navbar-collapse -->
						</nav>
	</div>
</div>
<!-- //navigation -->
	<div class="inner cover tab-content"  style="height:500px;align:center;" >
					<br><br><br><br><br><br>
					<div role="tabpanel" class="tab-pane active" id="repair">
						<div class="form-horizontal">
							<div class="form-group" style="margin-top:-100px;">
								<label for="repairContent" style="margin-left:300px" class="col-sm-2 control-label">请填写报修内容</label><br><br>
								<div class="col-sm-9" style="margin-left:300px">
										<textarea id="repairContent"  name="content" placeholder="报修内容" style="width: 600px;height: 300px"></textarea>
								</div><br><br>
								<button class="btn btn-default" style="margin-top:20px;margin-left:600px" onclick="doRepairSubmit()">提交报修</button>
							</div>
						</div>
					</div>

					<div role="tabpanel" class="tab-pane" id="complain">
						<div class="form-horizontal">
							<div class="form-group" style="margin-top:-100px;">
								<label for="repairContent" style="margin-left:300px" class="col-sm-2 control-label">请填写投诉内容</label><br><br>
								<div class="col-sm-9" style="margin-left:300px">
										<textarea id="complainContent"  name="content" placeholder="投诉内容" style="width: 600px;height: 300px"></textarea>
								</div><br><br>
								<button class="btn btn-default" style="margin-top:20px;margin-left:600px" onclick="doComplainSubmit()">提交投诉</button>
							</div>
						</div>
					</div>

					<div role="tabpanel" class="tab-pane" id="charge" >
						<div class="form-horizontal" >
							<div class="form-group" style="margin:0 200px;">
							<div class="col-sm-9">
								<ul class="list-group" id="chargeList">
									
								</ul>
								<ul class="list-group" id="weichargeList">
									
								</ul>
							</div>
							</div>
						</div>
					</div>
					<div role="tabpanel" class="tab-pane" id="dosage">
						<div class="form-horizontal" style="margin-left:200px;width: 40%; float:left;">
						<div class="form-group" >
							<div class="col-sm-9" >
								<ul class="list-group" id="dosageList">
									
								</ul>
							</div>
							</div>
						</div>
						<div id="containertubiao" class="panel panel-primary"	style="width: 30%; height: 330px;margin-left:2px;float:left">
							<!-- <div class="panel-heading">
								<h3 class="panel-title">各月份各类别用量情况饼图表统计</h3>
							</div>
							<div class="panel-body">
								<div id="containertubiao" style="width: 100%; height: 240px;"></div>
							</div> -->
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
								if(data[i].status==1){
									var li = '<li class="list-group-item list-group-item-success">备注 '+data[i].remark+ ' 金额 ' + data[i].payment +' </li>';
									$('#chargeList').append(li);
								}else{
									var li = '<form action="<%=path%>/charge!updateStatus.action" method="post"><li class="list-group-item list-group-item-success">备注 '+data[i].remark+ ' 金额 ' + data[i].payment +' </li>'
									+'<input type="hidden" name="chargeInfoId" value="'+data[i].id+'"/><input type="submit" value="缴费"/></form>';
									$('#weichargeList').append(li);
								}
								
							}
						}
					});
			
			$.ajax({
				url : 'dosage!listByUserId.action',
				type : 'POST',
				dataType : 'json',
				data : {
					user_id : '<%=((UserInfoEntity) request.getSession().getAttribute("userBean")).getId()%>'
						},
						success : function(data) {
							var labs=new Array();
							var nums=new Array();
							var arr=new Array();
							for (var i = 0; i < data.length; i++) {
								var li = '<li class="list-group-item list-group-item-success">类型：   '+data[i].type+ ' 用量： ' + data[i].count +'  日期： '+data[i].date+'</li>';
								$('#dosageList').append(li);
								var tmp = new Array();
				                tmp[0] = data[i].date+"-"+data[i].type;
				                tmp[1] = Number(data[i].count);
				                labs[i]=tmp[0];
				                nums[i]=tmp[1];
				                arr[i] = tmp;
							}
				                
				          
							var chart = {      
								      type: 'pie',     
								      options3d: {
								         enabled: true,
								         alpha: 45,
								         beta: 0
								      }
								   };
								   var credits={
								      	enabled:false
									};
								   var title = {
								      text: '各月份各类别用量比例显示'   
								   };   
								   var tooltip = {
								      pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
								   };

								   var plotOptions = {
								      pie: {
								          allowPointSelect: true,
								          cursor: 'pointer',
								          depth: 35,
								          dataLabels: {
								          	distance:3,
								             enabled: true,
								             format: '{point.name}'
								          },
								          showInLegend: true
								      }
								   };   
								   var series= [{
								         type: 'pie',
								         name: '所占百分比',
								         data: arr,
										     events: {
											        click: function(e) {
											               alert("单击点")
												    	}
												}
								         
								   }];     
								   
								    
								      
								   var json = {};   
								   json.chart = chart; 
								   json.title = title;       
								   json.tooltip = tooltip; 
								   json.plotOptions = plotOptions; 
								   json.credits=credits;
								   json.series = series;   

								   $('#containertubiao').highcharts(json);
								  
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
		var now = new Date();
		var year = now.getFullYear(); //年
		var month = now.getMonth() + 1; //月
		var day = now.getDate(); //日

		var date = year + "年";
		if (month < 10)
			date += "0";
		date += month + "月";
		if (day < 10)
			date += "0";
		date += day + "日";
		$("#lbFootDate").text(date);
		});
	</script>
	<a href="#" id="toTop" style="display: block;"> <span id="toTopHover" style="opacity: 1;"> </span></a>
<!-- //smooth scrolling -->
<script type="text/javascript" src="js/bootstrap-3.1.1.min.js"></script>
<div region="south" border="false"
			style="height: auto; overflow: hidden;">
			<div id="footer"
				style="height: 60px; line-height: 60px; text-align: center; clear: both; background: #16A9EF; font-size: 16px; color: #666; overflow: hidden;">
				<span>小区物业管理系统 （<label id="lbFootDate">2013年10月01日</label>）
				</span>
			</div>
		</div>
</body>
</html>