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
							url : 'user!list.action',
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
										title : '业主用户名',
										field : 'userName',
										align : 'center'
									},
									{
										title : '业主姓名',
										field : 'realName',
										align : 'center'
									},
									{
										title : '身份证号',
										field : 'idCard',
										align : 'center'
									},
									{
										title : '联系方式',
										field : 'phone',
										align : 'center'
									},
									{
										title : 'QQ',
										field : 'qq',
										align : 'center'
									},
									{
										title : 'email',
										field : 'email',
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

											queryProperty = ' | <a href="javascript:void(0);" onclick="queryPropertyInfo('
													+ index
													+ ')" style="color:#0000FF">查看房产</a>';

											var edit = '<a href="javascript:void(0);" onclick="editUserInfo('
													+ index
													+ ')" style="color:#0000FF">修改</a>';

											return edit + queryProperty;

										}
									},
									{
										title : 'qq通知',
										field : 'OP1',
										align : 'center',
										formatter : function(value, row, index) {

											var edit = '<a href="tencent://message/?uin='+row.qq+'&amp;Site=有事Q我&amp;Menu=yes" class="soc-icon icon-qq"><img style="border:0px;" src="<%=path%>/images/pa.gif"></a>';

											return edit ;

										}
									},
									{
										title : 'email通知',
										field : 'OP2',
										align : 'center',
										formatter : function(value, row, index) {
											var _rows = tabInfoCol.datagrid("getRows");
											var _row = _rows[index];
											var edit = '<a href="mailto:'
												+ _row.email
												+ '">发送email</a>';

											return edit ;

										}
									}
									] ],
							toolbar : [ '-', {
								iconCls : 'icon-cancel',
								text : '删除',
								handler : function() {
									remove();
								}
							},'-' ]

						});//列表控件  结束

		//获取分页控件
		pageCol = tabInfoCol.datagrid('getPager');
		pageCol.pagination({
			onChangePageSize : function(pageSize) {
				var _obj = tabInfoCol.datagrid('options');
				_obj.queryParams = {
					userName : $('#userName').val(),
					realName : $('#realName').val(),
					phone : $('#phone').val(),
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
		
		//修改窗口
		editDialog = $('#editDialog').dialog({
			title : '修改业主信息',
			width : 500,
			height : 300,
			buttons : [ {
				text : '确认修改',
				handler : function() {
					update();
				} //保存
			}, {
				text : '关闭',
				handler : function() {
					editDialog.window('close');
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
		});//修改窗口结束
		
		
		//查询房产信息窗口
		queryPropertyDialog = $('#queryPropertyDialog').dialog({
			title : '查询房产信息',
			width : 500,
			height : 300,
			buttons : [  {
				text : '关闭',
				handler : function() {
					queryPropertyDialog.window('close');
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
		});//查询房产信息窗口结束

		//发送邮件窗口
		sendmailDialog = $('#sendmailDialog').dialog({
			title : '发送邮件',
			width : 500,
			height : 300,
			buttons : [ {
				text : '确认发送',
				handler : function() {
					tosendmail();
				} //保存
			}, {
				text : '关闭',
				handler : function() {
					editDialog.window('close');
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
		});//发送邮件窗口结束
	});

	function queryPropertyInfo(index) {

		var _rows = tabInfoCol.datagrid("getRows");
		var _row = _rows[index];
		var propertyId = _row.propertyId;// 房产ID

		console.log(propertyId);
		
		
		$.ajax({
			url : 'property!queryById.action',
			dataType : 'json',
			type : 'POST',
			data : {
				id : propertyId
			},
			success : function(data){
				if(data.status == "OK"){
					$('#propertyAddress').val(data.detail.address);
					$('#propertyArea').val(data.detail.area);
					$('#propertyLayout').val(data.detail.layout);
				}else{
					$.messager.alert('我的消息', _data.desc, 'info');
				}
			}
		});
		
		
		
		
		queryPropertyDialog.window('open');
		queryPropertyDialog.window('setTitle', '查询房产信息');
		
	}

	function editUserInfo(index) {

		var _rows = tabInfoCol.datagrid("getRows");
		var _row = _rows[index];
		var id = _row.id;

		$('#id').val(id);
		$('#editRealName').val(_row.realName);
		$('#editUserPwd').val(_row.userPwd);
		$('#editPhone').val(_row.phone);
		$('#editIdCard').val(_row.idCard);

		editDialog.window('open');
		editDialog.window("setTitle", "修改业主信息");
//		$('editFm').form('clear');

	}
	// TODO  发送邮件
	function sendemail(index){
		var _rows = tabInfoCol.datagrid("getRows");
		var _row = _rows[index];
		var _email = _row.email;// 房产ID
		$('#tomail').val(_email);
		sendmailDialog.window('open');
		sendmailDialog.window('setTitle', '输入要发送的消息');
		$('sendmailFm').form('clear');
	}
	function tosendmail() {
		var tomail = $('#tomail').val();
		var content = $('#mailcontent').val();
		if (tomail == "") {
			$.messager.alert('我的消息', "邮箱地址不能为空", 'info');
			return;
		}
		if (content == "") {
			$.messager.alert('我的消息', "内容不能为空", 'info');
			return;
		}

		$('#sendmailFm').form('submit', {
			url : 'user!sendMail.action',
			success : function(data) {
				var _data = $.parseJSON(data);
				if (_data.status == "OK") {
					$.messager.alert('我的消息', _data.desc, 'info');
					tabInfoCol.datagrid("reload");
				} else {
					$.messager.alert('我的消息', _data.desc, 'info');
				}
				editDialog.window('close');
			}
		});
	}
	function update() {
		var realName = $('#editRealName').val();
		var idCard = $('#editIdCard').val();
		var phone = $('#editPhone').val();
		var userPwd = $('#editUserPwd').val();

		if (realName == "") {
			$.messager.alert('我的消息', "业主姓名不能为空", 'info');
			return;
		}
		if (idCard == "") {
			$.messager.alert('我的消息', "身份证号不能为空", 'info');
			return;
		}
		if (phone == "") {
			$.messager.alert('我的消息', "联系方式不能为空", 'info');
			return;
		}
		if (userPwd == "") {
			$.messager.alert('我的消息', "登录密码不能为空", 'info');
			return;
		}

		$('#editFm').form('submit', {
			url : 'user!update.action',
			success : function(data) {
				var _data = $.parseJSON(data);
				if (_data.status == "OK") {
					$.messager.alert('我的消息', _data.desc, 'info');
					tabInfoCol.datagrid("reload");
				} else {
					$.messager.alert('我的消息', _data.desc, 'info');
				}
				editDialog.window('close');
			}
		});
	}
	
	function save() {
		var realName = $('#editRealName').val();
		var idCard = $('#editIdCard').val();
		var phone = $('#editPhone').val();
		var userPwd = $('#editUserPwd').val();
		
		if (realName == "") {
			$.messager.alert('我的消息', "业主姓名不能为空", 'info');
			return;
		}
		if (idCard == "") {
			$.messager.alert('我的消息', "身份证号不能为空", 'info');
			return;
		}
		if (phone == "") {
			$.messager.alert('我的消息', "联系方式不能为空", 'info');
			return;
		}
		if (userPwd == "") {
			$.messager.alert('我的消息', "登录密码不能为空", 'info');
			return;
		}

		$('#addfm').form('submit', {
			url : 'user!save.action',
			success : function(data) {
				var _data = $.parseJSON(data);
				if (_data.status == "OK") {
					$.messager.alert('我的消息', _data.desc, 'info');
					tabInfoCol.datagrid("reload");
				} else {
					$.messager.alert('我的消息', _data.desc, 'info');
				}
				editDialog.window('close');
			}
		});
	}
	// 分页条件查询开始
	function queryPage() {

		$('#tabInfo').datagrid('load', {
			userName : $('#userName').val(),
			realName : $('#realName').val(),
			phone : $('#phone').val(),
			number : page

		});
	}// 分页条件查询结束

	function add() {
		addDialog.window('open');
		addDialog.window("setTitle", "添加住户");
		$('#fm').form('clear');
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
				url : "user!batchRemove.action",
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

		<!-- 模糊查询条件 布局开始 -->
		<div region="north" style="height: 73px;">
			<table style="margin-top: 15px; margin-left: 0px; width: 90%;">
				<tr>
					<td style="text-align: right; width: 300px; font-size: 14px;">
						业主用户名：<input type="text" id="userName" name="userName"
						style="margin-left: 10px; width: 150px" />
					</td>
					<td style="text-align: right; width: 300px; font-size: 14px;">
						业主姓名：<input type="text" id="realName" name="realName"
						style="margin-left: 10px; width: 150px" />
					</td>
					<td style="text-align: right; width: 300px; font-size: 14px;">
						联系方式：<input type="number" id="phone" name="phone"
						style="margin-left: 10px; width: 150px" />
					</td>
					<td style="padding-left: 50px;"><input type="button"
						value="查找" style="width: 70px;" onclick="queryPage();" /></td>
				</tr>
			</table>
		</div>
		<!-- 模糊查询条件 布局结束 -->

		<div region="center">
			<table id="tabInfo"></table>
		</div>


	</div>

<div id="addDialog" class="easyui-dialog"
		style="width: 600px; height: 280px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="addfm" method="post">
				<div class="fitem">
			<label>业主姓名：</label> <input id="editRealName" type="text"
					name="realName">
			</div>
			<div class="fitem">
			<label>业主用户名：</label> <input id="userName" type="text"
					name="userName">
			</div>
			<br />
			<div class="fitem">
				<label>身份证号：</label> <input id="editIdCard" type="number"
					name="idCard">
			</div>
			<br />
			<div class="fitem">
				<label>联系方式：</label> <input id="editPhone" type="number"
					name="phone">
			</div>
			<br />
			<div class="fitem">
				<label>登录密码：</label> <input id="editUserPwd" type="password"
					name="userPwd">
			</div>
			
		</form>
	</div>

	<div id="editDialog" class="easyui-dialog"
		style="width: 600px; height: 280px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="editFm" method="post">
			<!-- ID隐藏域 -->
			<input id="id" type="hidden" name="id">
			<div class="fitem">
				<label>业主姓名：</label> <input id="editRealName" type="text"
					name="realName">
			</div>
			<br />
			<div class="fitem">
				<label>身份证号：</label> <input id="editIdCard" type="number"
					name="idCard">
			</div>
			<br />
			<div class="fitem">
				<label>联系方式：</label> <input id="editPhone" type="number"
					name="phone">
			</div>
			<br />
			<div class="fitem">
				<label>登录密码：</label> <input id="editUserPwd" type="password"
					name="userPwd">
			</div><br>
			<div class="fitem">
				<label>QQ：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label> <input id="qq" type="text" name="qq">
			</div><br>
			<div class="fitem">
				<label>email：&nbsp;&nbsp;&nbsp;&nbsp;</label> <input id="email" type="text" name="email">
			</div>

		</form>
	</div>


	<div id="queryPropertyDialog" class="easyui-dialog"
		style="width: 600px; height: 280px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<div class="fitem">
			<label>房产地址：</label> <input id="propertyAddress" type="text" disabled="true">
		</div>
		<br />
		<div class="fitem">
			<label>房产面积：</label> <input id="propertyArea" type="number" disabled="true">
			(平方米)
		</div>
		<br />
		<div class="fitem">
			<label>房产结构：</label> <input id="propertyLayout" type="text" disabled="true">
		</div>
	</div>

	<div id="sendmailDialog" class="easyui-dialog"
		style="width: 600px; height: 280px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="sendmailFm" method="post">
		<div class="fitem">
			<label>接收邮箱：</label> <input id="tomail" type="text" name="tomail" >
		</div>
		<br />
		<div class="fitem">
			<label>邮件内容：</label> <textarea rows="5" cols="40" name="mailcontent" id="mailcontent"></textarea>
		</div>
		</form>
	</div>
</body>
</html>