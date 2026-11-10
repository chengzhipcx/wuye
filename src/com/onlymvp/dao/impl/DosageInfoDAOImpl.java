package com.onlymvp.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.onlymvp.dao.DosageInfoDAO;
import com.onlymvp.entity.DosageEntity;
import com.onlymvp.tool.MyHibernateDaoSupport;

@Repository(value = "dosageInfoDAO")
public class DosageInfoDAOImpl extends MyHibernateDaoSupport implements DosageInfoDAO {

	@Override
	public Serializable insert(DosageEntity t) throws Exception {
		return this.getHibernateTemplate().save(t);
	}

	@Override
	public boolean update(DosageEntity t) throws Exception {

		Session session = this.getSession();
		session.update(t);
		session.close();
		return true;
	}

	@Override
	public void delete(DosageEntity t) throws Exception {
		this.getHibernateTemplate().delete(t);
	}

	@Override
	public boolean deleteById(Integer id) throws Exception {

		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();

		String hql = "DELETE FROM DosageEntity WHERE id = ?";

		int res = hibernateTemplate.bulkUpdate(hql, id);

		return res > 0 ? true : false;
	}

	@Override
	public DosageEntity queryById(Integer id) throws Exception {

		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();

		String hql = "FROM DosageEntity WHERE id = ?";

		@SuppressWarnings("unchecked")
		List<DosageEntity> list = hibernateTemplate.find(hql, id);

		return list == null || list.isEmpty() ? null : list.get(0);
	}

	@Override
	public List<DosageEntity> queryByUserId(int userId) throws Exception {

		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();

		String hql = "FROM DosageEntity WHERE userId = ? ORDER BY date ASC";

		@SuppressWarnings("unchecked")
		List<DosageEntity> list = hibernateTemplate.find(hql, userId);

		return list;
	}

	@Override
	public List<DosageEntity> queryPageList(int start, int size) throws Exception {

		Session session = this.getSession(true);

		String hql = "FROM DosageEntity ORDER BY date DESC, id DESC";

		Query query = session.createQuery(hql);

		query.setFirstResult(start);

		query.setMaxResults(size);

		@SuppressWarnings("unchecked")
		List<DosageEntity> list = query.list();

		session.close();

		return list;
	}

	@Override
	public long queryCount() throws Exception {

		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();

		String hql = "SELECT COUNT(*) FROM DosageEntity";

		@SuppressWarnings("unchecked")
		List<Long> list = hibernateTemplate.find(hql);

		return list == null || list.isEmpty() ? 0 : list.get(0);
	}
}
