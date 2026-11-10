package com.onlymvp.config;

import com.onlymvp.filter.AuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 配置
 *
 * chengzhi
 *
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	/**
	 * JSP 视图解析器: 前端页面沿用原 /views 目录结构
	 */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/", ".jsp");
	}

	/**
	 * 统一认证过滤器(替代原 SSH 时代缺失的登录校验)
	 */
	@Bean
	public FilterRegistrationBean<AuthFilter> authFilterRegistration() {
		FilterRegistrationBean<AuthFilter> registration = new FilterRegistrationBean<>();
		registration.setFilter(new AuthFilter());
		registration.addUrlPatterns("/*");
		registration.setOrder(1);
		registration.setName("authFilter");
		return registration;
	}
}
