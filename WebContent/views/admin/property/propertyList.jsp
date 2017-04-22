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

	var _saleStatus = [ {
		id : '',
		desc : '不限制'
	}, {
		id : '0',
		desc : '未出售'
	}, {
		id : '1',
		desc : '已出售'
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
							url : 'property!propertyList.action',
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
										title : '房产地址',
										field : 'address',
										align : 'center'
									},
									{
										title : '房产面积(m2)',
										field : 'area',
										align : 'center'
									},
									{
										title : '房产结构',
										field : 'layout',
										align : 'center'
									},
									{
										title : '房产状态',
										field : 'saleStatus',
										align : 'center',
										formatter : function(value, row, index) {

											var t = '';

											for (var i = 0; i < _saleStatus.length; i++) {
												if (value == _saleStatus[i].id) {
													t = _saleStatus[i].desc;
												}
											}

											return t;

										}
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

											var buy = '';

											var saleStatus = row.saleStatus;

											if (saleStatus == '0') {

												buy = ' | <a href="javascript:void(0);" onclick="openBuyDialog('
														+ index
														+ ')" style="color:#0000FF">用户购买</a>';
											}

											var edit = '<a href="javascript:void(0);" onclick="editProperty('
													+ index
													+ ')" style="color:#0000FF">修改</a>';

											return edit + buy;

										}
									} ] ],
							toolbar : [ '-', {
								iconCls : 'icon-add',
								text : '添加房产',
								handler : function() {
									newProperty();
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
					saleStatus : $('#saleStatus').combobox('getValue'),
					address : $('#address').val(),
					layout : $('#layout').val(),
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
			title : '修改房产信息',
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
		
		//购买窗口
		buyDialog = $('#buyDialog').dialog({
			title : '修改房产信息',
			width : 500,
			height : 300,
			buttons : [ {
				text : '确认',
				handler : function() {
					saveBuy();
				} //保存
			}, {
				text : '关闭',
				handler : function() {
					buyDialog.window('close');
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
		});//购买窗口结束

		$('#saleStatus').combobox({
			data : _saleStatus,
			valueField : 'id',
			editable : 'false',
			textField : 'desc'
		});

	});
	
	function openBuyDialog(index){

		$('#buyFm').form('clear');
		
		var _rows = tabInfoCol.datagrid("getRows");
		var _row = _rows[index];
		var id = _row.id;

		$('#buyPropertyId').val(id);
		
		buyDialog.window('open');
		buyDialog.window('setTitle', '房产购买');
		
		
		
	}
	
	function saveBuy(){
		
		if($('#userName').val() == ''){
			$.messager.alert('我的消息', "业主用户名不能为空", 'info');
			return;
		}
		
		if($('#userPwd').val() == ''){
			$.messager.alert('我的消息', "登录密码不能为空", 'info');
			return;
		}
		
		if($('#realName').val() == ''){
			$.messager.alert('我的消息', "业主姓名不能为空", 'info');
			return;
		}
		
		if($('#phone').val() == ''){
			$.messager.alert('我的消息', "联系方式不能为空", 'info');
			return;
		}
		
		if($('#idCard').val() == ''){
			$.messager.alert('我的消息', "身份证号不能为空", 'info');
			return;
		}
		
		
		$('#buyFm').form('submit', {
			url : 'user!save.action',
			success : function(data){
				var _data = $.parseJSON(data);
				if (_data.status == "OK") {
					$.messager.alert('我的消息', _data.desc, 'info');
					tabInfoCol.datagrid("reload");
				} else {
					$.messager.alert('我的消息', _data.desc, 'info');
				}
				buyDialog.window('close');
			}
		});
		
	}

	function newProperty() {
		addDialog.window('open');
		addDialog.window("setTitle", "添加房产信息");
		$('#fm').form('clear');
	}

	function editProperty(index) {

		var _rows = tabInfoCol.datagrid("getRows");
		var _row = _rows[index];
		var id = _row.id;

		$('#editId').val(id);
		$('#editAddress').val(_row.address);
		$('#editArea').val(_row.area);
		$('#editLayout').val(_row.layout);

		editDialog.window('open');
		editDialog.window("setTitle", "修改房产信息");
		$('editFm').form('clear');

		//url = 'property!updateProperty?id=' + id;

	}

	function update() {
		var area = $('#editArea').val();
		var layout = $('#editLayout').val();

		if (area == "") {
			$.messager.alert('我的消息', "房产面积不能为空", 'info');
			return;
		}
		if (layout == "") {
			$.messager.alert('我的消息', "房产结构不能为空", 'info');
			return;
		}

		$('#editFm').form('submit', {
			url : 'property!updateProperty.action',
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

		var address = $('#addAddress').val();
		var area = $('#addArea').val();
		var layout = $('#addLayout').val();

		if (address == "") {
			$.messager.alert('我的消息', '房产地址不能为空', 'info');
			return;
		}
		if (area == "") {
			$.messager.alert('我的消息', "房产面积不能为空", 'info');
			return;
		}
		if (layout == "") {
			$.messager.alert('我的消息', "房产结构不能为空", 'info');
			return;
		}

		$('#fm').form('submit', {
			url : 'property!saveProperty.action',
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

	// 分页条件查询开始
	function queryPage() {

		$('#tabInfo').datagrid('load', {
			saleStatus : $('#saleStatus').combobox('getValue'),
			address : $('#address').val(),
			layout : $('#layout').val(),
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
					arr = arr + "," + row.id;
				}
				arr = arr.substring(1, arr.length);
			}

			$.ajax({
				url : "property!batchRemove.action",
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
						房屋状态：<select style="width: 100px" id="saleStatus"
						class="easyui-validatebox" name="saleStatus"></select>
					</td>
					<td style="text-align: right; width: 300px; font-size: 14px;">
						地址：<input type="text" id="address" name="address"
						style="margin-left: 10px; width: 150px" />
					</td>
					<td style="text-align: right; width: 300px; font-size: 14px;">
						布局结构：<input type="text" id="layout" name="layout"
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
		<form id="fm" method="post">
			<div class="fitem">
				<label>房产地址：</label> <input id="addAddress" type="text"
					name="address">
			</div>
			<br />
			<div class="fitem">
				<label>房产面积：</label> <input id="addArea" type="number" name="area">
				(平方米)
			</div>
			<br />
			<div class="fitem">
				<label>房产结构：</label> <input id="addLayout" type="text" name="layout">
			</div>
		</form>
	</div>

	<div id="editDialog" class="easyui-dialog"
		style="width: 600px; height: 280px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="editFm" method="post">
			<!-- ID隐藏域 -->
			<input id="editId" type="hidden" name="id">
			<div class="fitem">
				<label>房产地址：</label> <input id="editAddress" type="text"
					name="address" disabled="false">
			</div>
			<br />
			<div class="fitem">
				<label>房产面积：</label> <input id="editArea" type="number" name="area">
				(平方米)
			</div>
			<br />
			<div class="fitem">
				<label>房产结构：</label> <input id="editLayout" type="text"
					name="layout">
			</div>


		</form>
	</div>


	<div id="buyDialog" class="easyui-dialog"
		style="width: 600px; height: 280px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="buyFm" method="post">
			<!-- propertyId隐藏域 -->
			<input id="buyPropertyId" type="hidden" name="propertyId">

			<div class="fitem">
				<label>业主用户名：</label> <input id="userName" type="text" name="userName">
			</div>
			<br />
			<div class="fitem">
				<label>登录密码：&nbsp;&nbsp;&nbsp;&nbsp;</label> <input id="userPwd" type="password" name="userPwd">
			</div>
			<br />
			<div class="fitem">
				<label>业主姓名：&nbsp;&nbsp;&nbsp;&nbsp;</label> <input id="realName" type="text" name="realName">
			</div>
			<br />
			<div class="fitem">
				<label>联系方式：&nbsp;&nbsp;&nbsp;&nbsp;</label> <input id="phone" type="text" name="phone">
			</div>
			<br />
			<div class="fitem">
				<label>身份证号：&nbsp;&nbsp;&nbsp;&nbsp;</label> <input id="idCard" type="text" name="idCard">
			</div>


		</form>
	</div>


</body>
</html>