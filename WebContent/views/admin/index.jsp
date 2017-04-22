<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.onlymvp.entity.*, java.util.*"
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
<jsp:include page="../common/adminHead.jsp"></jsp:include><!-- 引入js,css -->


<link rel="stylesheet" type="text/css" href="js/easyui/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="js/easyui/themes/icon.css"/>
<script type="text/javascript" src="js/easyui/jquery.easyui.min.js" charset="utf-8"></script>
<%
			
Map<MenuInfoEntity, List<MenuInfoEntity>> menuMap = (Map<MenuInfoEntity, List<MenuInfoEntity>>)request.getAttribute("menuMap");

Set<MenuInfoEntity> parentMenuSet = menuMap.keySet();

%>
<script type="text/javascript">
		
		
		//载入左树
		$(function(){	//页面加载完成执行	开始
			
			 $('#mainTree').tree({
				   onClick:function(node){
				 	   addTabs(node);
				   },
				   onDblClick:function(node){
					   if(node.state == 'closed'){
						   $(this).tree('expand', node.target);
					   }else{
						   $(this).tree('collapse', node.target);
					   }
				   }
				});
		
				$('#centerTabs').tabs({
					onContextMenu:function(e, title, index){
						e.preventDefault();
					}
				});
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
			
		function logout(){
				window.location.href = "adminIndex!logout.action";
		}
		
		
		//添加选项卡 
		function addTabs(node){
					
			if($('#centerTabs').tabs('exists', node.text)){
				$('#centerTabs').tabs('select', node.text);//可接index
			}else{
				
				<%
				for(Iterator<MenuInfoEntity> it = parentMenuSet.iterator(); it.hasNext(); ){
					MenuInfoEntity parentMenu = it.next();
				%>
				
				
				if(node.text == "<%=parentMenu.getName()%>"){
					return;
				}
				<%}%>
				
				$('#centerTabs').tabs('add',{
					title:node.text,
					closable:true,
					content:getTabStr(node)
				});
			} 
		};
		
		
		function closeTab(tabName){
			$('#centerTabs').tabs('close',tabName);
		};


	function getTabStr(node) {
		
		<%
		for(Iterator<MenuInfoEntity> it = parentMenuSet.iterator(); it.hasNext(); ){
			MenuInfoEntity parentMenu = it.next();
		%>
		
		<% for (MenuInfoEntity childMenu : menuMap.get(parentMenu)) {%>
		
		if(node.text == '<%=childMenu.getName()%>'){
			return '<iframe src="<%=childMenu.getUrl()%>" frameborder="0px;" style="border:0px; width:100%;height:100%;"></iframe>';
		}
		
		<%}%>

		<%}%>
		

	}
		
		
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>后台管理</title>
</head>
<body>

	<div class="easyui-layout" border="false" fit="true">
		<div region="north" id="general_north" border="false"
			style="height: 60px; margin-top: 0px; overflow: hidden;">
			<div
				style="float: right; padding: 20px 0px 0px 0px; font-size: 16px; line-height: 50px;">
				<label id="lbUserName"
					style="padding: 0 10px; font-style: normal; color: blue;"></label>欢迎您登录本系统<label
					id="lbUserName"
					style="padding: 0 10px; font-style: normal; color: blue;"></label>
				<label><a href="javascript:void(0);" onclick="logout();"
					style="color: red; text-decoration: none;">[退出系统]</a></label>
			</div>
		</div>
		<div region="west" border="true" title="导航菜单" style="width: 220px;">
			<ul id="mainTree" class="easyui-tree" lines="true">
				<!-- animate="true" -->

			

			<%
			for(Iterator<MenuInfoEntity> it = parentMenuSet.iterator(); it.hasNext(); ){
				MenuInfoEntity parentMenu = it.next();
			%>
			
			<li state="<%=parentMenu.getSwitchStatus() == 0 ? "closed" : "opened" %>">
			
				<span><%=parentMenu.getName() %></span>
				
				<ul>
					<% for(MenuInfoEntity childMenu : menuMap.get(parentMenu)){ %>
					
					<li id="<%=childMenu.getId() %>"><span><%=childMenu.getName() %></span></li>
					
					<%} %>
				</ul>
			
			</li>
			
			<%} %>

			</ul>
		</div>
		<div region="center" id="centerTabs" class="easyui-tabs" border="true"
			style="border: 1px;">
			<div title="首页" border="false" style="overflow-y: hidden;">
				<iframe src="views/admin/adminIcon.jsp" frameborder="0"
					style="border: 0; width: 100%; height: 100%;"></iframe>
			</div>
		</div>
		<div region="south" border="false"
			style="height: auto; overflow: hidden;">
			<div id="footer"
				style="height: 60px; line-height: 60px; text-align: center; clear: both; background: #ddd; font-size: 16px; color: #666; overflow: hidden;">
				<span>管理系统 （<label id="lbFootDate">2013年10月01日</label>）
				</span>
			</div>
		</div>
	</div>

</body>
</html>