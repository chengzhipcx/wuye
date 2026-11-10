package com.onlymvp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 小区物业管理系统启动类
 *
 * 打包为可执行 WAR: java -jar wuye.war
 * 也可部署到外置 Tomcat(保持上下文名 PropertyManagerSystem)
 *
 * chengzhi
 *
 */
@SpringBootApplication
public class WuyeApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(WuyeApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(WuyeApplication.class, args);
	}
}
