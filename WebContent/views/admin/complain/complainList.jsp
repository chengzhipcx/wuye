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

	var _solveStatus = [ {
		id : '',
		desc : '请选择'
	}, {
		id : '0',
		desc : '未解决'
	}, {
		id : '1',
		desc : '已解决'
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
								number : page,
								infoType : '2'
							},
							url : 'repairAndComplain!list.action',
							loadMsg : '数据装载中......',
							columns : [ [
									{
										title : 'ID',
										field : 'repairAndComplainInfoId',
										align : 'center',
										hidden : true
									},
									{
										title : '投诉人',
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
										title : '内容',
										field : 'content',
										align : 'center',
										formatter : function(value, row, index) {

											var opt = '<a href="javascript:void(0);" onclick="queryInfo('
													+ index
													+ ')" style="color:#0000FF">查看详情</a>';

											return opt;
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
										title : '解决时间',
										field : 'solveTime',
										align : 'center',
										formatter : function(value, row, index) {

											var solveTime = '未处理';

											if (value != '')
												solveTime = value.substring(0,
														value.length - 2);

											return solveTime;
										}
									},
									{
										title : '报修状态',
										field : 'solveStatus',
										align : 'center',
										formatter : function(value, row, index) {
											var desc = '';
											for (var i = 0; i < _solveStatus.length; i++) {
												if (value == _solveStatus[i].id) {
													desc = _solveStatus[i].desc;
												}
											}
											return desc;
										}
									},
									{
										title : '经手人',
										field : 'dealUser',
										align : 'center',
										formatter : function(value, row, index) {

											return value == '' ? '暂无' : value;
										}
									},
									{
										title : '操作',
										field : 'OP',
										align : 'center',
										formatter : function(value, row, index) {

											var edit = '';

											if (row.solveStatus == '0') {
												edit = '<a href="javascript:void(0);" onclick="editInfo('
														+ index
														+ ')" style="color:#0000FF">处理为已完成</a>';
											} else {
												edit = '处理完毕';
											}

											return edit;

										}
									} ] ]

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
					solveStatus : $('#solveStatus').combobox('getValue'),
					infoType : '2',
					number : pageSize,
					page : $(this).pageNumber
				};
			}
		});//分页控件结束

		$('#solveStatus').combobox({
			data : _solveStatus,
			valueField : 'id',
			textField : 'desc'
		});

		//修改窗口
		editDialog = $('#editDialog').dialog({
			title : '修改缴费信息',
			width : 500,
			height : 300,
			buttons : [ {
				text : '确认完成',
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

		//修改窗口
		queryDialog = $('#queryDialog').dialog({
			title : '修改缴费信息',
			width : 500,
			height : 300,
			buttons : [ {
				text : '关闭',
				handler : function() {
					queryDialog.window('close');
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

	function queryInfo(index) {

		var _rows = tabInfoCol.datagrid("getRows");
		var _row = _rows[index];
		
		queryDialog.window('open');
		queryDialog.window('setTitle', '查看详情');
		
		
		$('#detailContent').text(_row.content);
		
	}

	function editInfo(index) {

		var _rows = tabInfoCol.datagrid("getRows");
		var _row = _rows[index];
		var repairAndComplainInfoId = _row.repairAndComplainInfoId;

		$('#repairAndComplainInfoId').val(repairAndComplainInfoId);

		editDialog.window('open');
		editDialog.window("setTitle", "更新报修信息");
		$('editFm').form('clear');

	}

	function update() {
		
		if($('#editDealUser').val() == ''){
			$.messager.alert('我的消息', "经手人不能为空", 'info');
			return;
		}


		$('#editFm').form('submit', {
			url : 'repairAndComplain!update.action',
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
			solveStatus : $('#solveStatus').combobox('getValue'),
			infoType : '1',
			number : page

		});
	}// 分页条件查询结束
</script>
<body>


	<div class="easyui-layout" fit="true" style="border: 0px;">

		<!-- 模糊查询条件 布局开始 -->
		<div region="north" style="height: 73px;">
			<table style="margin-top: 15px; margin-left: 0px; width: 90%;">
				<tr>
					<td style="text-align: right; width: 300px; font-size: 14px;">
						状态：<input type="text" id="solveStatus" name="solveStatus"
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
			<input id="repairAndComplainInfoId" type="hidden" name="repairAndComplainInfoId">
			<div class="fitem">
				<label>经手人：</label> <input id="editDealUser" type="text"
					name="dealUser">
			</div>

		</form>
	</div>


	<div id="queryDialog" class="easyui-dialog"
		style="width: 600px; height: 280px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">

		<span id="detailContent"></span>

	</div>





</body>
</html>