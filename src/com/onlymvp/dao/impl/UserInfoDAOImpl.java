package com.onlymvp.dao.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.onlymvp.dao.UserInfoDAO;
import com.onlymvp.entity.AdminInfoEntity;
import com.onlymvp.entity.UserInfoEntity;
import com.onlymvp.tool.Const;
import com.onlymvp.tool.DateTimeTool;
import com.onlymvp.tool.MyHibernateDaoSupport;
import com.onlymvp.tool.StringTool;

@Repository(value = "userInfoDAO")
public class UserInfoDAOImpl extends MyHibernateDaoSupport implements UserInfoDAO {

	@Override
	public Serializable insert(UserInfoEntity t) throws Exception {

		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();

		t.setDelStatus(Const.SYSTEM_DEL_STATUS_NO);
		t.setCreateTime(DateTimeTool.getDateTime(new Date()));

		return hibernateTemplate.save(t);
	}

	@Override
	public boolean update(UserInfoEntity t) throws Exception {

		Session session = this.getSession();
		Transaction beginTransaction = session.beginTransaction();
		session.update(t);
		beginTransaction.commit();
		session.close();
		return true;
	}

	@Override
	public void delete(UserInfoEntity t) throws Exception {

		this.getHibernateTemplate().delete(t);

	}

	@Override
	public boolean deleteById(Integer id) throws Exception {

		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();

		String hql = "DELETE FROM UserInfoEntity WHERE id = ?";

		int res = hibernateTemplate.bulkUpdate(hql, id);

		return res > 0 ? true : false;
	}

	@Override
	public boolean deleteByPropertyId(Integer propertyId) throws Exception {

		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();

		String hql = "DELETE FROM UserInfoEntity WHERE propertyId = ?";

		int res = hibernateTemplate.bulkUpdate(hql, propertyId);

		return res > 0 ? true : false;
	}

	@Override
	public List<UserInfoEntity> queryUserInfoList(UserInfoEntity userInfoEntity, int start, int size) throws Exception {

		Session session = this.getSession(true);

		String userName = userInfoEntity.getUserName();

		String realName = userInfoEntity.getRealName();

		String phone = userInfoEntity.getPhone();

		StringBuilder sb = new StringBuilder();

		sb.append(" AND delStatus = " + Const.SYSTEM_DEL_STATUS_NO);

		if (!StringTool.isNull(userName))
			sb.append(" AND userName like '%" + userName + "%'");

		if (!StringTool.isNull(realName))
			sb.append(" AND realName like '%" + realName + "%'");

		if (!StringTool.isNull(phone))
			sb.append(" AND phone like '%" + phone + "%'");

		String hql = "FROM UserInfoEntity WHERE 1 = 1 " + sb.toString();

		Query query = session.createQuery(hql);

		query.setFirstResult(start);

		query.setMaxResults(size);

		@SuppressWarnings("unchecked")
		List<UserInfoEntity> list = query.list();

		session.close();

		return list;
	}

	@Override
	public long queryCount(UserInfoEntity userInfoEntity) throws Exception {

		String userName = userInfoEntity.getUserName();

		String realName = userInfoEntity.getRealName();

		String phone = userInfoEntity.getPhone();

		StringBuilder sb = new StringBuilder();

		sb.append(" AND delStatus = " + Const.SYSTEM_DEL_STATUS_NO);

		if (!StringTool.isNull(userName))
			sb.append(" AND userName like '%" + userName + "%'");

		if (!StringTool.isNull(realName))
			sb.append(" AND realName like '%" + realName + "%'");

		if (!StringTool.isNull(phone))
			sb.append(" AND phone like '%" + phone + "%'");

		String hql = "SELECT COUNT(*) FROM UserInfoEntity WHERE 1 = 1" + sb.toString();

		long count = (long) (this.getHibernateTemplate().find(hql).listIterator().next());

		return count;
	}

	@Override
	public UserInfoEntity queryById(Integer id) throws Exception {
		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();

		String hql = "FROM UserInfoEntity WHERE id = ?";

		@SuppressWarnings("unchecked")
		List<UserInfoEntity> list = hibernateTemplate.find(hql, id);

		return StringTool.isObjectNull(list) ? null : list.get(0);
	}

	@Override
	public UserInfoEntity queryByUserName(String userName) throws Exception {

		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();

		String hql = "FROM UserInfoEntity WHERE userName = ?";

		@SuppressWarnings("unchecked")
		List<UserInfoEntity> list = hibernateTemplate.find(hql, userName);

		return StringTool.isObjectNull(list) ? null : list.get(0);
	}

	@Override
	public UserInfoEntity queryByUserNameAndUserPwd(String userName, String userPwd) throws Exception {

		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();

		String hql = "FROM UserInfoEntity WHERE userName = ? AND userPwd = ?";

		@SuppressWarnings("unchecked")
		List<UserInfoEntity> list = hibernateTemplate.find(hql, userName, userPwd);

		return StringTool.isObjectNull(list) ? null : list.get(0);
	}

}
