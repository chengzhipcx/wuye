package com.onlymvp.tool;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间处理工具类
 * 
 * chengzhi
 *
 */
public class DateTimeTool {

	private static final SimpleDateFormat dateTimeFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 将当前日期转换为"yyyy-MM-dd HH:mm:ss"格式的字符串
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static String getDateTime(Date date) {

		return dateTimeFmt.format(date);
	}

}
