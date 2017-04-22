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

	var _chargeType = [ {
		id : '',
		desc : '不限制'
	}, {
		id : '1',
		desc : '水费'
	}, {
		id : '2',
		desc : '电费'
	}, {
		id : '3',
		desc : '宽带费'
	}, {
		id : '4',
		desc : '物业费'
	} ];

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
							url : 'charge!list.action',
							loadMsg : '数据装载中......',
							columns : [ [
									{
										title : 'ID',
										field : 'chargeInfoId',
										align : 'center',
										hidden : true
									},
									{
										checkbox : true
									},
									{
										title : '业主姓名',
										field : 'realName',
										align : 'center'
									},
									{
										title : '地址',
										field : 'address',
										align : 'center'
									},
									{
										title : '联系方式',
										field : 'phone',
										align : 'center'
									},
									{
										title : '缴费类型',
										field : 'chargeType',
										align : 'center',
										formatter : function(value, row, index) {
											var desc = '';
											for (var i = 0; i < _chargeType.length; i++) {
												if (value == _chargeType[i].id) {
													desc = _chargeType[i].desc;
												}
											}
											return desc;
										}
									},
									{
										title : '金额',
										field : 'payment',
										align : 'center'
									},
									{
										title : '备注',
										field : 'remark',
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

											var edit = '<a href="javascript:void(0);" onclick="editInfo('
													+ index
													+ ')" style="color:#0000FF">修改</a>';

											return edit;

										}
									} ] ],
							toolbar : [ '-', {
								iconCls : 'icon-add',
								text : '缴费',
								handler : function() {
									newCharge();
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
					realName : $('#realName').val(),
					phone : $('#phone').val(),
					address : $('#address').val(),
					chargeType : $('#chargeType').combobox('getValue'),
					number : pageSize,
					page : $(this).pageNumber,
				};
			}
		});//分页控件结束

		$('#chargeType').combobox({
			data : _chargeType,
			valueField : 'id',
			textField : 'desc'
		});

		$('#editChargeType').combobox({
			data : _chargeType,
			valueField : 'id',
			textField : 'desc'
		});

		$('#addChargeType').combobox({
			data : _chargeType,
			valueField : 'id',
			textField : 'desc'
		});

		//修改窗口
		addDialog = $('#addDialog').dialog({
			title : '添加缴费信息',
			width : 500,
			height : 300,
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

		//修改窗口
		editDialog = $('#editDialog').dialog({
			title : '修改缴费信息',
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

	});

	function clear() {
		$('#addUserName').val('');
		$('#notice').text('');
		$('#addUserId').val('');
		$('#addPropertyId').val('');
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
				} else {
					$.messager.alert('我的消息', data.desc, 'info');
					clear();
				}
			}
		});

		$.ajax({
			url : 'property!queryByUserName.action',
			dataType : 'json',
			async : false,
			type : 'POST',
			data : {
				userName : userName
			},
			success : function(data) {
				if (data.status == "OK") {
					$('#notice').text('核对房产地址：' + data.detail.address);
					$('#addPropertyId').val(data.detail.id);
				} else {
					$.messager.alert('我的消息', data.desc, 'info');
					clear();
				}
			}
		});
	}

	function newCharge() {
		addDialog.window('open');
		addDialog.window('setTitle', '新增缴费');
		$('addFm').form('clear');
	}

	function save() {
		
		if($('#addPropertyId').val() == '' || $('#addUserId').val() == '' || $('#addUserName').val() == ''){
			$.messager.alert('我的消息', "无业主数据", 'info');
			return;
		}
		
		if($('#addChargeType').combobox('getValue') == ''){
			$.messager.alert('我的消息', "缴费类型不能为空", 'info');
			return;
		}
		if($('#addPayment').val() == ''){
			$.messager.alert('我的消息', "缴费金额不能为空", 'info');
			return;
		}
		if($('#addRemark').val() == ''){
			$.messager.alert('我的消息', "备注不能为空", 'info');
			return;
		}
		
		$('#addFm').form('submit', {
			url : 'charge!save.action',
			success : function(data) {
				var _data = $.parseJSON(data);
				if (_data.status == "OK") {
					$.messager.alert('我的消息', _data.desc, 'info');
					tabInfoCol.datagrid("reload");
				} else {
					$.messager.alert('我的消息', _data.desc, 'info');
				}
				addDialog.window('close');
			}
		});
	}

	function editInfo(index) {

		var _rows = tabInfoCol.datagrid("getRows");
		var _row = _rows[index];
		var chargeInfoId = _row.chargeInfoId;

		$('#chargeInfoId').val(chargeInfoId);
		$('#editPayment').val(_row.payment);
		$('#editRemark').val(_row.remark);

		$('#editChargeType').combobox('select', _row.chargeType);

		editDialog.window('open');
		editDialog.window("setTitle", "修改缴费信息");
		$('editFm').form('clear');

	}

	function update() {
		var chargeType = $('#editChargeType').combobox('getValue');
		var payment = $('#editPayment').val();
		var remark = $('#editRemark').val();

		if (chargeType == "") {
			$.messager.alert('我的消息', "缴费类型不能为空", 'info');
			return;
		}
		if (payment == "") {
			$.messager.alert('我的消息', "缴费金额不能为空", 'info');
			return;
		}
		if (remark == "") {
			$.messager.alert('我的消息', "备注不能为空", 'info');
			return;
		}

		$('#editFm').form('submit', {
			url : 'charge!update.action',
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
			realName : $('#realName').val(),
			phone : $('#phone').val(),
			address : $('#address').val(),
			chargeType : $('#chargeType').combobox('getValue'),
			number : page

		});
	}// 分页条件查询结束

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
					arr = arr + "," + row.chargeInfoId;
				}
				arr = arr.substring(1, arr.length);
			}

			$.ajax({
				url : "charge!batchRemove.action",
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
						缴费类型：<input type="text" id="chargeType" name="chargeType"
						style="margin-left: 10px; width: 150px" />
					</td>
					<td style="text-align: right; width: 300px; font-size: 14px;">
						房产地址：<input type="text" id="address" name="address"
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

	<div id="editDialog" class="easyui-dialog"
		style="width: 600px; height: 280px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="editFm" method="post">
			<!-- ID隐藏域 -->
			<input id="chargeInfoId" type="hidden" name="chargeInfoId">
			<div class="fitem">
				<label>缴费类型：</label> <input id="editChargeType" type="text"
					name="chargeType">
			</div>
			<br />
			<div class="fitem">
				<label>金额：</label> <input id="editPayment" type="number"
					name="payment">
			</div>
			<br />
			<div class="fitem">
				<label>备注：</label> <input id="editRemark" type="text" name="remark">
			</div>


		</form>
	</div>


	<div id="addDialog" class="easyui-dialog"
		style="width: 600px; height: 280px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="addFm" method="post">
			<input id="addPropertyId" name="propertyId" type="hidden"> <input
				id="addUserId" name="userId" type="hidden">
			<div class="fitem">
				<label>缴费业主账号：</label> <input id="addUserName" name="userName"
					onblur="checkUserName()" type="text"> <span id="notice"></span>
			</div>
			<br />
			<div class="fitem">
				<label>缴费类型：</label> <input id="addChargeType" type="text"
					name="chargeType">
			</div>
			<br />
			<div class="fitem">
				<label>金额：</label> <input id="addPayment" type="number"
					name="payment">
			</div>
			<br />
			<div class="fitem">
				<label>备注：</label> <input id="addRemark" type="text" name="remark">
			</div>


		</form>
	</div>



</body>
</html>