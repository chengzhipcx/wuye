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
	var page = 20; //每页多少条记录
	var addDialog; //添加窗口

	var _dosageType = [ {
		id : '',
		desc : '不限制'
	}, {
		id : '水',
		desc : '水'
	}, {
		id : '电',
		desc : '电'
	}, {
		id : '燃气',
		desc : '燃气'
	} ];

	$(function() {//页面初始化  开始

		//表table
		tabInfoCol = $('#tabInfo').datagrid({ //列表控件  开始
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
			url : 'dosage!list.action',
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
						title : '业主ID',
						field : 'userId',
						align : 'center'
					},
					{
						title : '用量类型',
						field : 'type',
						align : 'center'
					},
					{
						title : '用量',
						field : 'count',
						align : 'center'
					},
					{
						title : '统计日期',
						field : 'date',
						align : 'center'
					} ] ],
			toolbar : [ '-', {
				iconCls : 'icon-add',
				text : '添加用量',
				handler : function() {
					newDosage();
				}
			}, {
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

		$('#addType').combobox({
			data : _dosageType,
			valueField : 'id',
			textField : 'desc'
		});

		//添加窗口
		addDialog = $('#addDialog').dialog({
			title : '添加用量信息',
			width : 500,
			height : 320,
			buttons : [ {
				text : '确认',
				handler : function() {
					save();
				} //保存
			}, {
				text : '关闭',
				handler : function() {
					addDialog.window('close');
				} //关闭
			} ],
			modal : true,
			minimizable : false,
			maximizable : false,
			shadow : false,
			cache : false,
			closed : true, //打开或关闭
			collapsible : false,
			resizable : false
		});//添加窗口结束

	});

	function clear() {
		$('#addUserName').val('');
		$('#notice').text('');
		$('#addUserId').val('');
		$('#addType').combobox('setValue', '');
		$('#addCount').val('');
		$('#addDate').val('');
	}

	function checkUserName() {

		var userName = $('#addUserName').val();

		$.ajax({
			url : 'user!queryByUserName.action',
			async : false,
			dataType : 'json',
			type : 'POST',
			data : {
				userName : userName
			},
			success : function(data) {
				if (data.status == "OK") {
					$('#addUserId').val(data.detail.id);
					$('#notice').text('业主账号正确');
				} else {
					$.messager.alert('我的消息', data.desc, 'info');
					clear();
				}
			}
		});
	}

	//查询
	function queryPage() {
		tabInfoCol.datagrid('options').queryParams = {
			number : page,
			page : 1
		};
		tabInfoCol.datagrid('reload');
	}

	//保存
	function save() {

		var userId = $('#addUserId').val();

		if (userId == "") {
			$.messager.alert('我的消息', '请先填写正确的业主账号', 'info');
			return;
		}

		var count = $('#addCount').val();

		if (count == "" || isNaN(count)) {
			$.messager.alert('我的消息', '请填写正确的用量数值', 'info');
			return;
		}

		var date = $('#addDate').val();

		if (date == "") {
			$.messager.alert('我的消息', '请填写统计日期,如2016-10', 'info');
			return;
		}

		$.ajax({
			url : 'dosage!save.action',
			dataType : 'json',
			type : 'POST',
			data : {
				userId : userId,
				type : $('#addType').combobox('getValue'),
				count : count,
				date : date
			},
			success : function(data, textStatus, jqXHR) {
				if (data.status == "OK") {
					addDialog.window('close');
					clear();
					tabInfoCol.datagrid("reload");
					$.messager.alert('我的消息', data.desc, 'info');
				} else {
					$.messager.alert('我的消息', data.desc, 'info');
				}
			}
		});
	}

	//添加窗口打开
	function newDosage() {
		clear();
		addDialog.window('open');
	}

	//批量删除
	function remove() {

		var rows = tabInfoCol.datagrid("getSelections");

		if (rows.length == 0) {
			$.messager.alert('我的消息', '请选择要删除的用量信息', 'info');
			return;
		}

		var arr = new Array();
		for (var i = 0; i < rows.length; i++) {
			arr[i] = rows[i].id;
		}

		$.messager.confirm('确认', '确定要删除这 ' + rows.length + ' 条用量信息吗?', function(r) {
			if (!r) {
				return;
			}
			$.ajax({
				url : 'dosage!batchRemove.action',
				dataType : 'json',
				type : 'POST',
				traditional : true,
				data : {
					ids : arr.join(',')
				},
				success : function(data, textStatus, jqXHR) {
					tabInfoCol.datagrid("reload");
					$.messager.alert('我的消息', data.desc, 'info');
				}
			});
		});
	}
</script>
<body>


	<div class="easyui-layout" fit="true" style="border: 0px;">

		<!-- 提示 布局开始 -->
		<div region="north" style="height: 53px;">
			<table style="margin-top: 15px; margin-left: 0px; width: 90%;">
				<tr>
					<td style="text-align: left; font-size: 14px;">住户用量列表(业主端"查看历史用量"展示此数据)</td>
				</tr>
			</table>
		</div>
		<!-- 提示 布局结束 -->

		<div region="center">
			<table id="tabInfo"></table>
		</div>

	</div>

	<div id="addDialog" class="easyui-dialog"
		style="width: 600px; height: 320px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="addFm" method="post">
			<input id="addUserId" name="userId" type="hidden">
			<div class="fitem">
				<label>用量业主账号：</label> <input id="addUserName" name="userName"
					onblur="checkUserName()" type="text"> <span id="notice"></span>
			</div>
			<br />
			<div class="fitem">
				<label>用量类型：</label> <input id="addType" type="text" name="type">
			</div>
			<br />
			<div class="fitem">
				<label>用量数值：</label> <input id="addCount" type="number" name="count">
			</div>
			<br />
			<div class="fitem">
				<label>统计日期：</label> <input id="addDate" type="text" name="date"
					placeholder="如2016-10">
			</div>
		</form>
	</div>

</body>
</html>
