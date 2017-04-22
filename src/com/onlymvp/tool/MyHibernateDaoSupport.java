package com.onlymvp.tool;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * DAO层支持
 * 
 * chengzhi
 *
 */
public class MyHibernateDaoSupport extends HibernateDaoSupport {
	@Resource(name = "sessionFactory") // 注入sessionFactory
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
}
