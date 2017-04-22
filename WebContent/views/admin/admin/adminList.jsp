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
	var tabInfoCol; //列表控件
	var pageCol; //分页控件
	var page = 20; //第页多少条记录

	$(function() {//页面初始化  开始

		//表table
		tabInfoCol = $('#tabInfo')
				.datagrid(
						{ //列表控件  开始
							fit : true,
							nowrap : true,
							striped : false,
							fitColumns : true,
							rownumbers : true,
							pageSize : page,
							pageList : [ 5, 10, 15, 20 ],
							pagination : true,
							queryParams : {
								number : page
							},
							url : 'admin!adminList.action',
							loadMsg : '数据装载中......',
							columns : [ [
									{
										title : 'ID',
										field : 'id',
										align : 'center',
										hidden : true
									},
									{
										checkbox : true
									},
									{
										title : '管理员账号',
										field : 'userName',
										align : 'center'
									},
									{
										title : '创建时间',
										field : 'createTime',
										align : 'center',
										formatter : function(value, row, index) {
											return value.substring(0,
													value.length - 2);
										}
									},
									{
										title : '操作',
										field : 'OP',
										align : 'center',
										formatter : function(value, row, index) {

											var queryRole = '<a href="javascript:void(0);" onclick="queryRole('
													+ index
													+ ')" style="color:#0000FF">查看权限</a>';

											var edit = ' | <a href="javascript:void(0);" onclick="editAdmin('
													+ index
													+ ')" style="color:#0000FF">修改</a>';

											return queryRole + edit;

										}
									} ] ],
							toolbar : [ '-', {
								iconCls : 'icon-add',
								text : '添加管理员',
								handler : function() {
									newAdmin();
								}
							}, '-', {
								iconCls : 'icon-cancel',
								text : '删除',
								handler : function() {
									remove();
								}
							}, '-' ]

						});//列表控件  结束

		//获取分页控件
		pageCol = tabInfoCol.datagrid('getPager');
		pageCol.pagination({
			onChangePageSize : function(pageSize) {
				var _obj = tabInfoCol.datagrid('options');
				_obj.queryParams = {
					number : pageSize,
					page : $(this).pageNumber,
				};
			}
		});//分页控件结束

		//添加窗口
		addDialog = $('#addDialog').dialog({
			title : '添加管理员',
			width : 500,
			height : 300,
			buttons : [ {
				text : '保存',
				handler : function() {
					save();
				} //保存
			}, {
				text : '关闭',
				handler : function() {
					addDialog.window('close');
				} //关闭
			} ],
			//href: '',
			modal : true,
			minimizable : false,
			maximizable : false,
			shadow : false,
			cache : false,
			closed : true, //打开或关闭
			collapsible : false,
			resizable : false
		//loadingMessage: '正在加载数据，请稍等片刻......'
		});//添加窗口结束

		//查看管理员权限窗口
		queryRoleDialog = $('#queryRoleDialog').dialog({
			title : '查看管理员权限',
			width : 500,
			height : 300,
			buttons : [ {
				text : '关闭',
				handler : function() {
					queryRoleDialog.window('close');
				} //关闭
			} ],
			//href: '',
			modal : true,
			minimizable : false,
			maximizable : false,
			shadow : false,
			cache : false,
			closed : true, //打开或关闭
			collapsible : false,
			resizable : false
		//loadingMessage: '正在加载数据，请稍等片刻......'
		});//查看管理员权限窗口结束

	});

	function newAdmin() {
		addDialog.window('open');
		addDialog.window("setTitle", "添加管理员");
		$('#fm').form('clear');
	}

	function editAdmin(index) {

		var _rows = tabInfoCol.datagrid("getRows");
		var _row = _rows[index];
		var id = _row.id;

		window
				.open(
						"adminIndex!toEditAdmin.action?adminId=" + id,
						'管理员信息修改',
						'height=260,width=600,toolbar=no,menubar=no,'
								+ 'scrollbars=yes,top=100,resizable=no,location=no,status=no');
	}
	function save() {

		var userName = $('#userName').val();
		var userPwd = $('#userPwd').val();
		var menuIds = $('input[name="menuIds"]:checked').val();

		if (userName == "") {
			$.messager.alert('我的消息', '管理员账号不能为空', 'info');
			return;
		}
		if (userPwd == "") {
			$.messager.alert('我的消息', "管理员密码不能为空", 'info');
			return;
		}
		if (menuIds == undefined) {
			$.messager.alert('我的消息', "权限不能为空", 'info');
			return;
		}

		$('#fm').form('submit', {
			url : 'admin!saveAdmin.action',
			success : function(data) {
				var _data = $.parseJSON(data);
				if (_data.status == "OK") {
					$.messager.alert('我的消息', _data.desc, 'info');
					tabInfoCol.datagrid("reload");
				} else {
					$.messager.alert('我的消息', _data.desc, 'info');
				}
				tabInfoCol.datagrid("reload");
				addDialog.window('close');
			}
		});
	}

	function queryRole(index) {

		var _rows = tabInfoCol.datagrid("getRows");
		var _row = _rows[index];
		var id = _row.id;

		$.ajax({
			type : 'POST',
			dataType : 'json',
			url : 'admin!queryAdminParentMenu.action',
			data : {
				id : id
			},
			success : function(data) {

				$('#adminRole li').remove();// 清空 id为adminRole下的li节点

				for (var i = 0; i < data.length; i++) {

					console.log(data[i].name);

					$('#adminRole').append('<li>' + data[i].name + '</li>');

				}

				queryRoleDialog.window('open');
				queryRoleDialog.window("setTitle", "查看管理员权限");

			}
		});

	}

	function remove() {
		$.messager.confirm('确认对话框', '确认要删除吗？', function(flag) {
			if (!flag)
				return;
			var index = tabInfoCol.datagrid('getSelections');
			var arr = "";
			if (index.length > 0) {
				var row;
				for (var i = 0; i < index.length; i++) {
					row = index[i];
					arr = arr + "," + row.id;
				}
				arr = arr.substring(1, arr.length);
			}

			$.ajax({
				url : "admin!batchRemoveAdmin.action",
				type : "POST",
				async : false,
				dataType : "json",
				data : {
					ids : arr
				},
				success : function(data, textStatus, jqXHR) {
					tabInfoCol.datagrid("reload");
					if (data.status == "OK") {
						tabInfoCol.datagrid("reload");
						$.messager.alert('我的消息', data.desc, 'info');
					} else {
						$.messager.alert('我的消息', data.desc, 'info');
					}
				}
			});

		});
	}
</script>
<body>


	<div class="easyui-layout" fit="true" style="border: 0px;">
		<div region="center">
			<table id="tabInfo"></table>
		</div>
	</div>
	<div id="addDialog" class="easyui-dialog"
		style="width: 600px; height: 280px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="fm" method="post">
			<div class="fitem">
				<label>管理员账号：</label> <input id="userName" name="userName">
			</div>
			<br />
			<div class="fitem">
				<label>登录密码：&nbsp;&nbsp;&nbsp;&nbsp;</label> <input id="userPwd"
					type="password" name="userPwd">
			</div>
			<br />
			<div class="fitem">
				<label>管理员权限：</label>
				<c:forEach items="${normalMenuInfoList }" var="menu">
					<input type="checkbox" class="addMenuIds" name="menuIds"
						value="${menu.id }">${menu.name }
				</c:forEach>
			</div>
		</form>
	</div>

	<div id="editDialog" class="easyui-dialog"
		style="width: 600px; height: 280px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="editFm" method="post">
			<div class="fitem">
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


		</form>
	</div>

	<div id="queryRoleDialog" class="easyui-dialog"
		style="width: 600px; height: 280px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">

		<ul id="adminRole"></ul>

	</div>

</body>
</html>