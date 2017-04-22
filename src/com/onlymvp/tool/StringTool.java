package com.onlymvp.tool;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * 字符串处理工具类
 * 
 * chengzhi
 *
 */
public class StringTool {

	/**
	 * 判断字符串是否是空
	 * 
	 * @param t
	 *            要判断空的字符串
	 * @return 字符串为空返回"true",否则返回"false"
	 */
	public static boolean isNull(String t) {
		return t == null || "".equals(t) ? true : false;
	}

	/**
	 * 判断对象是否为空
	 * 
	 * @param obj
	 *            要判断空的对象
	 * @return 如果是空返回"true",否则返回"false"
	 */
	public static boolean isObjectNull(Object obj) {
		Boolean flag = true;
		if (obj instanceof Collection) { // List/Set/ArrayList等
			Collection temp = (Collection) obj;
			if (temp != null && !temp.isEmpty()) {
				flag = false;
			}
		} else if (obj instanceof Map) { // Map/HashMap等
			Map temp = (Map) obj;
			if (temp != null && !temp.isEmpty()) {
				flag = false;
			}
		} else if (obj instanceof Iterator) { // Iterator等
			Iterator temp = (Iterator) obj;
			if (temp != null && temp.hasNext()) {
				flag = false;
			}
		} else if (obj != null) {
			flag = false;
		} else {
			flag = true;
		}
		return flag;
	}

}
