package com.onlymvp.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.onlymvp.dao.ChargeInfoDAO;
import com.onlymvp.dto.UserAndPropertyAndChargeInfoEntity;
import com.onlymvp.entity.ChargeInfoEntity;
import com.onlymvp.entity.MenuAndRoleEntity;
import com.onlymvp.tool.Const;
import com.onlymvp.tool.DateTimeTool;
import com.onlymvp.tool.MyHibernateDaoSupport;
import com.onlymvp.tool.StringTool;

@Repository(value = "chargeInfoDAO")
public class ChargeInfoDAOImpl extends MyHibernateDaoSupport implements ChargeInfoDAO {

	@Override
	public Serializable insert(ChargeInfoEntity t) throws Exception {

		t.setCreateTime(DateTimeTool.getDateTime(new Date()));
		t.setDelStatus(Const.SYSTEM_DEL_STATUS_NO);

		return this.getHibernateTemplate().save(t);
	}

	@Override
	public boolean update(ChargeInfoEntity t) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void delete(ChargeInfoEntity t) throws Exception {
		this.getHibernateTemplate().delete(t);
	}

	@Override
	public boolean deleteById(Integer id) throws Exception {

		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();

		String hql = "UPDATE ChargeInfoEntity SET delStatus = ? WHERE id = ?";// 逻辑删除

		int res = hibernateTemplate.bulkUpdate(hql, Const.SYSTEM_DEL_STATUS_YES, id);

		return res > 0 ? true : false;
	}

	@Override
	public ChargeInfoEntity queryById(Integer id) throws Exception {

		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();

		String hql = "FROM ChargeInfoEntity WHERE id = ?";

		@SuppressWarnings("unchecked")
		List<ChargeInfoEntity> list = hibernateTemplate.find(hql, id);

		return StringTool.isObjectNull(list) ? null : list.get(0);
	}

	@Override
	public List<UserAndPropertyAndChargeInfoEntity> queryPageList(
			UserAndPropertyAndChargeInfoEntity userAndPropertyAndChargeInfoEntity, int start, int size)
			throws Exception {

		List<UserAndPropertyAndChargeInfoEntity> resList = new ArrayList<UserAndPropertyAndChargeInfoEntity>();

		Session session = this.getSession();

		StringBuilder sb = new StringBuilder();

		sb.append(" AND A.delStatus = " + Const.SYSTEM_DEL_STATUS_NO);
		sb.append(" AND B.delStatus = " + Const.SYSTEM_DEL_STATUS_NO);
		sb.append(" AND C.delStatus = " + Const.SYSTEM_DEL_STATUS_NO);

		String realName = userAndPropertyAndChargeInfoEntity.getRealName();

		String address = userAndPropertyAndChargeInfoEntity.getAddress();

		String phone = userAndPropertyAndChargeInfoEntity.getPhone();

		Integer chargeType = userAndPropertyAndChargeInfoEntity.getChargeType();

		if (!StringTool.isNull(realName))
			sb.append(" AND C.realName like '%" + realName + "%'");

		if (!StringTool.isNull(address))
			sb.append(" AND B.address like '%" + address + "%'");

		if (!StringTool.isNull(phone))
			sb.append(" AND C.phone like '%" + phone + "%'");

		if (null != chargeType)
			sb.append(" AND A.chargeType = " + chargeType);

		String hql = "SELECT A.id, A.propertyId, A.userId, A.chargeType, A.payment, "
				+ "A.remark, A.createTime, B.address, C.realName,  C.phone "
				+ "FROM ChargeInfoEntity AS A, PropertyInfoEntity AS B, UserInfoEntity AS C "
				+ "WHERE A.propertyId = B.id AND A.userId = C.id " + sb.toString()
				+ " ORDER BY A.chargeType, A.id DESC";

		Query query = session.createQuery(hql);

		query.setFirstResult(start);
		query.setMaxResults(size);

		@SuppressWarnings("unchecked")
		List<Object[]> list = query.list();

		if (!StringTool.isObjectNull(list)) {
			for (Object[] t : list) {
				UserAndPropertyAndChargeInfoEntity upc = new UserAndPropertyAndChargeInfoEntity();
				upc.setChargeInfoId((Integer) t[0]);
				upc.setPropertyId((Integer) t[1]);
				upc.setUserId((Integer) t[2]);
				upc.setChargeType((Integer) t[3]);
				upc.setPayment((Integer) t[4]);
				upc.setRemark((String) t[5]);
				upc.setCreateTime((String) t[6]);
				upc.setAddress((String) t[7]);
				upc.setRealName((String) t[8]);
				upc.setPhone((String) t[9]);
				resList.add(upc);
			}
		}

		session.close();

		return resList;
	}

	@Override
	public long queryCount(UserAndPropertyAndChargeInfoEntity userAndPropertyAndChargeInfoEntity) throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(" AND A.delStatus = " + Const.SYSTEM_DEL_STATUS_NO);
		sb.append(" AND B.delStatus = " + Const.SYSTEM_DEL_STATUS_NO);
		sb.append(" AND C.delStatus = " + Const.SYSTEM_DEL_STATUS_NO);

		String realName = userAndPropertyAndChargeInfoEntity.getRealName();

		String address = userAndPropertyAndChargeInfoEntity.getAddress();

		String phone = userAndPropertyAndChargeInfoEntity.getPhone();

		Integer chargeType = userAndPropertyAndChargeInfoEntity.getChargeType();

		if (!StringTool.isNull(realName))
			sb.append(" AND C.realName like '%" + realName + "%'");

		if (!StringTool.isNull(address))
			sb.append(" AND B.address like '%" + address + "%'");

		if (!StringTool.isNull(phone))
			sb.append(" AND C.phone like '%" + phone + "%'");

		if (null != chargeType)
			sb.append(" AND A.chargeType = " + chargeType);

		String hql = "SELECT COUNT(A.id) " + "FROM ChargeInfoEntity AS A, PropertyInfoEntity AS B, UserInfoEntity AS C "
				+ "WHERE A.propertyId = B.id AND A.userId = C.id " + sb.toString()
				+ " ORDER BY A.chargeType, A.id DESC";

		long count = (long) (this.getHibernateTemplate().find(hql).listIterator().next());

		return count;
	}

	@Override
	public boolean updateChargeInfo(UserAndPropertyAndChargeInfoEntity userAndPropertyAndChargeInfoEntity)
			throws Exception {

		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();

		String hql = "UPDATE ChargeInfoEntity SET chargeType = ?, payment = ?, remark = ? WHERE id = ?";

		int res = hibernateTemplate.bulkUpdate(hql, userAndPropertyAndChargeInfoEntity.getChargeType(),
				userAndPropertyAndChargeInfoEntity.getPayment(), userAndPropertyAndChargeInfoEntity.getRemark(),
				userAndPropertyAndChargeInfoEntity.getChargeInfoId());

		return res > 0 ? true : false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChargeInfoEntity> queryByUserId(int userId) throws Exception {

		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();

		String hql = "SELECT t FROM ChargeInfoEntity t WHERE t.userId = ? AND t.delStatus = ?";

		return hibernateTemplate.find(hql, userId, Const.SYSTEM_DEL_STATUS_NO);
	}

}
