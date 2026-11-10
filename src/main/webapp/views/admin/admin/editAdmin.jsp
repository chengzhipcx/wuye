<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.onlymvp.entity.*, java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<jsp:include page="../../common/adminHead.jsp"></jsp:include><!-- 引入js,css -->
<script type="text/javascript">
	$(function() {

		$.ajax({
			type : 'POST',
			dataType : 'json',
			data : {
				'id' : $('#id').val()
			},
			url : 'admin!findMenuAndRoleList',
			success : function(data) {
				for (var i = 0; i < data.length; i++) {

					$('input[value="' + data[i].menuId + '"]:checkbox').prop(
							'checked', 'true');
				}
			}
		});

	});

	function save() {
		
		var userPwd = $('#editUserPwd').val();
		var menuIds = $('input[name="menuIds"]:checked').val();

		if (userPwd == "") {
			$.messager.alert('我的消息', "管理员密码不能为空", 'info');
			return;
		}
		if (menuIds == undefined) {
			$.messager.alert('我的消息', "权限不能为空", 'info');
			return;
		}

		$('#editFm').form('submit', {
			url : 'admin!updateAdmin.action',
			success : function(data){
				var _data = $.parseJSON(data);
				if (_data.status == "OK") {
					//$.messager.alert('我的消息', _data.desc, 'info');
					alert(_data.desc);
					window.opener.location.reload();
					window.close();
				} else {
					$.messager.alert('我的消息', _data.desc, 'info');
				}
			}
		});
		
	}
</script>
</head>
<body>
	<form id="editFm" method="post">
		<input type="hidden" id="id" name="id" value="${entity.id }">
		<table>
			<tr>
				<td>登录密码：</td>
				<td><input id="editUserPwd" type="password" name="userPwd"
					value="${entity.userPwd }"></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>管理员权限：</td>
				<td><c:forEach items="${menuInfoList }" var="menu">
						<input type="checkbox" name="menuIds" class="editMenuIds"
							value="${menu.id }">${menu.name }
				</c:forEach></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="button" onclick="save()" value="确认修改" /></td>
			</tr>

		</table>
		<%-- <div class="fitem">
			<label>登录密码：&nbsp;&nbsp;&nbsp;&nbsp;</label> <input id="editUserPwd"
				type="password" name="userPwd">
		</div>
		<br />
		<div class="fitem">
			<label>管理员权限：</label>
			<c:forEach items="${normalMenuInfoList }" var="menu">
				<input type="checkbox" name="menuIds" class="editMenuIds"
					value="${menu.id }">${menu.name }
				</c:forEach>
		</div>
		<br />

		<div>
			<div class="fitem">
				<input type="button" onclick="save()"  value="确认修改" />
			</div>
		</div> --%>


	</form>
</body>
</html>