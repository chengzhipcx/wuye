package com.onlymvp.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.onlymvp.tool.Const;

/**
 * 统一认证过滤器
 *
 * 原项目所有后台页面与数据接口均无登录校验(AdminInterceptor存在逻辑问题且未注册),
 * 本过滤器统一拦截:
 *  1. /views/admin/**  与 adminIndex 开头的后台资源 -> 需要管理员会话
 *  2. /views/user/**   前台用户页面               -> 需要业主会话
 *  3. 其余 *.action 数据接口                       -> 需要任意已登录会话
 * 登录/登出接口与静态资源放行。
 *
 * chengzhi
 *
 */
public class AuthFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String path = uri.substring(contextPath.length());// 去掉上下文根

		Object adminBean = request.getSession().getAttribute(Const.SESSION_ADMIN_BEAN);
		Object userBean = request.getSession().getAttribute(Const.SESSION_USER_BEAN);

		boolean needAdmin = path.startsWith("/views/admin/") || path.startsWith("/adminIndex");
		boolean needUser = path.startsWith("/views/user/");
		boolean isAction = path.endsWith(".action");

		// 放行登录页,静态资源与登录/登出接口
		boolean whitelist = path.equals("/") || path.equals("/index.jsp") || path.equals("/views/admin/login.jsp")
				|| path.startsWith("/css/") || path.startsWith("/js/") || path.startsWith("/images/")
				|| path.startsWith("/fonts/") || path.startsWith("/error/") || path.equals("/user!login.action")
				|| path.equals("/user!logout.action") || path.equals("/admin!login.action");

		if (whitelist) {
			chain.doFilter(req, resp);
			return;
		}

		if (needAdmin) {
			if (adminBean == null) {
				response.sendRedirect(contextPath + "/views/admin/login.jsp");
				return;
			}
			chain.doFilter(req, resp);
			return;
		}

		if (needUser) {
			if (userBean == null) {
				response.sendRedirect(contextPath + "/index.jsp");
				return;
			}
			chain.doFilter(req, resp);
			return;
		}

		if (isAction) {
			// 其余数据接口要求任意已登录身份,未登录返回403
			if (adminBean == null && userBean == null) {
				response.setContentType("application/json;charset=UTF-8");
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				response.getWriter().write("{\"status\":\"FAIL\",\"desc\":\"未登录或会话已过期\"}");
				return;
			}
		}

		chain.doFilter(req, resp);
	}

	@Override
	public void destroy() {
	}
}
