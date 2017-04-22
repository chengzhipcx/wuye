package com.onlymvp.tool;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Hibernate工具类
 * 
 * chengzhi
 *
 */
public class HibernateUtil extends MyHibernateDaoSupport {
	public ThreadLocal session = new ThreadLocal();
	public SessionFactory sessionFactoy = this.getSessionFactory();

	/**
	 * 获取当前Session
	 * 
	 * @return
	 */
	public Session currentSession() {
		Session s = (Session) session.get();
		if (s == null) {
			s = sessionFactoy.openSession();
			session.set(s);
		}
		return s;
	}

}
