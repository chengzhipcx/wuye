package com.onlymvp.dao.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.onlymvp.dao.AdminInfoDAO;
import com.onlymvp.entity.AdminInfoEntity;
import com.onlymvp.tool.Const;
import com.onlymvp.tool.DateTimeTool;
import com.onlymvp.tool.MyHibernateDaoSupport;
import com.onlymvp.tool.StringTool;

/**
 * @describe 管理员控制dao数据库操作实现
 * @author onlymvp.com
 *
 */
@Repository(value = "adminInfoDAO")
public class AdminInfoDAOImpl extends MyHibernateDaoSupport implements AdminInfoDAO {

	@Override
	public Serializable insert(AdminInfoEntity t) throws Exception {
		// Session session = this.getSession(true);

		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();

		t.setCreateTime(DateTimeTool.getDateTime(new Date()));
		t.setUserLevel(Const.ADMIN_USER_LEVEL_NORMAL);// 普通管理员
		t.setUserStatus(Const.SYSTEM_STATUS_ON);// 默认开启

		Serializable serializable = hibernateTemplate.save(t);

		// Serializable serializable = session.save(t);
		//
		// session.close();

		return serializable;
	}

	@Override
	public boolean update(AdminInfoEntity t) throws Exception {

		Session session = this.getSession();

		session.update(t);

		session.close();
		
		return true;
	}

	@Override
	public AdminInfoEntity queryByNameAndPwd(String userName, String userPwd) throws Exception {
		// Session session = this.getSession();

		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();

		String hql = "FROM AdminInfoEntity WHERE userName = ? AND userPwd = ? AND userStatus = ?";

		// String hql = "FROM AdminInfoEntity WHERE userName = :usrName AND
		// userPwd = :userPwd AND userStatus = :userStatus";
		// Query query = hibernateTemplate.createQuery(hql).setString("usrName",
		// userName).setString("userPwd", userPwd)
		// .setInteger("userStatus", Const.SYSTEM_STATUS_ON);

		@SuppressWarnings("unchecked")
		List<AdminInfoEntity> list = hibernateTemplate.find(hql, userName, userPwd, Const.SYSTEM_STATUS_ON);

		// @SuppressWarnings("unchecked")
		// List<AdminInfoEntity> list = query.list();
		// session.close();
		return StringTool.isObjectNull(list) ? null : list.get(0);
	}

	@Override
	public List<AdminInfoEntity> queryNormalAdminList(int start, int size) throws Exception {
		Session session = this.getSession(true);
		String hql = "FROM AdminInfoEntity WHERE userLevel = :userLevel AND userStatus = :userStatus";
		Query query = session.createQuery(hql).setInteger("userLevel", Const.ADMIN_USER_LEVEL_NORMAL)
				.setInteger("userStatus", Const.SYSTEM_STATUS_ON);
		query.setFirstResult(start);// 设置起始行
		query.setMaxResults(size);// 每页显示条数
		@SuppressWarnings("unchecked")
		List<AdminInfoEntity> list = query.list();
		session.close();
		return list;
	}

	@Override
	public long queryNormalAdminCount() throws Exception {
		Session session = this.getSession(true);
		String hql = "SELECT COUNT(*) FROM AdminInfoEntity WHERE userLevel = " + Const.ADMIN_USER_LEVEL_NORMAL
				+ " AND userStatus = " + Const.SYSTEM_STATUS_ON;
		long count = (long) (this.getHibernateTemplate().find(hql).listIterator().next());
		return count;
	}

	@Override
	public void delete(AdminInfoEntity t) throws Exception {
		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();

		hibernateTemplate.delete(t);

	}

	@Override
	public AdminInfoEntity queryById(int id) throws Exception {

		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();

		String hql = "FROM AdminInfoEntity WHERE id = ? AND userStatus = ?";

		List<AdminInfoEntity> list = hibernateTemplate.find(hql, new Integer[] { id, Const.SYSTEM_STATUS_ON });

		return StringTool.isObjectNull(list) ? null : list.get(0);
	}

	@Override
	public boolean updateAdminPassword(int id, String userPwd) throws Exception {

		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();

		String hql = "UPDATE AdminInfoEntity SET userPwd = ? WHERE id = ?";

		int res = hibernateTemplate.bulkUpdate(hql, userPwd, id);

		return res > 0 ? true : false;
	}

	@Override
	public boolean deleteById(Integer id) throws Exception {

		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();

		String hql = "DELETE FROM AdminInfoEntity WHERE id = ?";

		int res = hibernateTemplate.bulkUpdate(hql, id);

		return res > 0 ? true : false;

	}

	@Override
	public AdminInfoEntity queryById(Integer id) throws Exception {
		
		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();
		
		String hql = "FROM AdminInfoEntity WHERE id = ?";
		
		List<AdminInfoEntity> list = hibernateTemplate.find(hql, id);
		
		return StringTool.isObjectNull(list) ? null : list.get(0);
	}


}
