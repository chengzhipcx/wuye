package com.onlymvp.tool;

/**
 * 系统常量存储类
 * 
 * chengzhi
 *
 */
public class Const {

	public static final String RETURN_STATUS_OK = "OK";// 信息返回参数"成功"

	public static final String RETURN_STATUS_FAIL = "FAIL";// 信息返回参数"失败"

	public static final String ERROR_INFO_LABEL = "errorInfo";// 信息返回参数"错误"

	public static final Integer ADMIN_USER_LEVEL_SUPER = 0;// 超级管理员

	public static final Integer ADMIN_USER_LEVEL_NORMAL = 1;// 普通管理员

	public static final Integer SYSTEM_STATUS_OFF = 0;// 开关状态 "0"关闭

	public static final Integer SYSTEM_STATUS_ON = 1;// 开关状态 "1"开启 默认状态

	public static final Integer SYSTEM_DEL_STATUS_NO = 0;// 未删除

	public static final Integer SYSTEM_DEL_STATUS_YES = 1;// 已删除

	public static final Integer PROPERTY_SALE_STATUS_NO = 0;// 未出售

	public static final Integer PROPERTY_SALE_STATUS_YES = 1;// 已出售

	public static final String MENU_STATUS_IS_PARENT = "0";// 父菜单

	public static final String SUPER_ADMIN_MENU_INFO_ID = "1";// 超级管理员单独持有的菜单,不允许其他普通管理员拥有

	public static final Integer REPAIR_AND_COMPLAIN_INFO_SOLVED_NO = 0;// 报修或投诉解决状态
																		// 未解决

	public static final Integer REPAIR_AND_COMPLAIN_INFO_SOLVED_YES = 1;// 报修或投诉解决状态已解决

	public static final Integer REPAIR_AND_COMPLAIN_INFO_TYPE_REPAIR = 1;// 信息类型报修

	public static final Integer REPAIR_AND_COMPLAIN_INFO_TYPE_COMPLAIN = 2;// 信息类型投诉

	public static final String SESSION_ADMIN_BEAN = "adminBean";// session记录的管理员对象

	public static final String SESSION_USER_BEAN = "userBean";// session记录的业主对象

}
