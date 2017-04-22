package com.onlymvp.dao.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.onlymvp.dao.PropertyInfoDAO;
import com.onlymvp.entity.PropertyInfoEntity;
import com.onlymvp.tool.Const;
import com.onlymvp.tool.DateTimeTool;
import com.onlymvp.tool.MyHibernateDaoSupport;
import com.onlymvp.tool.StringTool;

/**
 * 
 * 房产信息管理数据库操作接口
 * 
 * @author onlymvp.com
 *
 */
@Repository(value = "propertyInfoDAO")
public class PropertyInfoDAOImpl extends MyHibernateDaoSupport implements PropertyInfoDAO {

	@Override
	public Serializable insert(PropertyInfoEntity t) throws Exception {

		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();

		t.setSaleStatus(Const.PROPERTY_SALE_STATUS_NO);// 未出售状态
		t.setDelStatus(Const.SYSTEM_DEL_STATUS_NO);// 未删除
		t.setCreateTime(DateTimeTool.getDateTime(new Date()));

		return hibernateTemplate.save(t);
	}

	@Override
	public boolean update(PropertyInfoEntity t) throws Exception {

		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();

		String hql = "UPDATE PropertyInfoEntity SET area = ?, layout = ? WHERE id = ?";

		int res = hibernateTemplate.bulkUpdate(hql, t.getArea(), t.getLayout(), t.getId());

		return res > 0 ? true : false;
	}

	@Override
	public void delete(PropertyInfoEntity t) throws Exception {

		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();

		hibernateTemplate.delete(t);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PropertyInfoEntity> queryPropertyList(PropertyInfoEntity propertyInfoEntity, int start, int size)
			throws Exception {

		Session session = this.getSession(true);

		Integer saleStatus = propertyInfoEntity.getSaleStatus();// 房产状态

		String address = propertyInfoEntity.getAddress();// 地址

		String layout = propertyInfoEntity.getLayout();// 布局结构

		Integer area = propertyInfoEntity.getArea();// 房产面积

		StringBuilder sb = new StringBuilder();

		sb.append(" AND delStatus = " + Const.SYSTEM_DEL_STATUS_NO);

		if (saleStatus != null)
			sb.append(" AND saleStatus = " + saleStatus);

		if (!StringTool.isNull(address))
			sb.append(" AND address LIKE '%" + address + "%'");

		if (!StringTool.isNull(layout))
			sb.append(" AND layout LIKE '%" + layout + "%'");

		if (area != null)
			sb.append(" AND area = " + area);

		String hql = "FROM PropertyInfoEntity WHERE 1 = 1 " + sb.toString() + " ORDER BY saleStatus ASC";

		Query query = session.createQuery(hql);

		query.setFirstResult(start);
		query.setMaxResults(size);

		List<PropertyInfoEntity> list = query.list();

		session.close();

		return list;
	}

	@Override
	public long queryCount(PropertyInfoEntity propertyInfoEntity) throws Exception {


		Integer saleStatus = propertyInfoEntity.getSaleStatus();// 房产状态

		String address = propertyInfoEntity.getAddress();// 地址

		String layout = propertyInfoEntity.getLayout();// 布局结构

		Integer area = propertyInfoEntity.getArea();// 房产面积

		StringBuilder sb = new StringBuilder();

		sb.append(" AND delStatus = " + Const.SYSTEM_DEL_STATUS_NO);

		if (saleStatus != null)
			sb.append(" AND saleStatus = " + saleStatus);

		if (!StringTool.isNull(address))
			sb.append(" AND address LIKE '%" + address + "%'");

		if (!StringTool.isNull(layout))
			sb.append(" AND layout LIKE '%" + layout + "%'");

		if (area != null)
			sb.append(" AND area = " + area);

		String hql = "SELECT COUNT(*) FROM PropertyInfoEntity WHERE 1 = 1" + sb.toString();

		long count = (long) (this.getHibernateTemplate().find(hql).listIterator().next());


		return count;
	}

	@Override
	public boolean deleteById(Integer id) throws Exception {

		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();

		String hql = "DELETE FROM PropertyInfoEntity WHERE id = ?";

		int res = hibernateTemplate.bulkUpdate(hql, id);

		return res > 0 ? true : false;
	}

	@Override
	public boolean updateSaleStatus(Integer id, Integer saleStatus) throws Exception {

		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();

		String hql = "UPDATE PropertyInfoEntity SET saleStatus = ? WHERE id = ?";

		int res = hibernateTemplate.bulkUpdate(hql, saleStatus, id);

		return res > 0 ? true : false;
	}

	@Override
	public PropertyInfoEntity queryById(Integer id) throws Exception {
		
		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();
		
		String hql = "FROM PropertyInfoEntity WHERE id = ?";
		
		@SuppressWarnings("unchecked")
		List<PropertyInfoEntity> list = hibernateTemplate.find(hql, id);
		
		return StringTool.isObjectNull(list) ? null : list.get(0);
	}

}
