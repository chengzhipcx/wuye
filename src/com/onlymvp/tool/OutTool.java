package com.onlymvp.tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 输出工具类
 * 
 * chengzhi
 *
 */
public class OutTool {

	private static final Logger logger = LoggerFactory.getLogger(OutTool.class);

	/**
	 * 获取PrintWrite对象并设置字符集编码格式
	 * 
	 * @return PrintWrite对象
	 */
	private static PrintWriter getPrintWriter() {
		PrintWriter out = null;
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");// 设置编码格式 否则会出现乱码
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;
	}

	/**
	 * 串输出Json字符串
	 * 
	 * @param jsonStr
	 */
	public static void write(String jsonStr) {
		getPrintWriter().write(jsonStr);
	}

	/**
	 * 把对象转为Json后输出
	 * 
	 * @param obj
	 *            要输出的对象
	 */
	public static void writeJsonObject(Object obj) {
		String json = JSONObject.fromObject(obj).toString();
		logger.info("Wrinte JSON Object -> {}", json);
		getPrintWriter().write(json);
	}

	/**
	 * 把List转为Json后输出
	 * 
	 * @param list
	 *            要输出的List
	 */
	public static void writeJsonArray(List<?> list) {
		String json = JSONArray.fromObject(list).toString();
		logger.info("Wrinte JSON Array -> {}", json);
		getPrintWriter().write(json);
	}

}
