package com.onlymvp.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.onlymvp.dao.RepairAndComplainInfoDAO;
import com.onlymvp.dto.UserAndPropertyAndReapirAndComplainInfoEntity;
import com.onlymvp.entity.RepairAndComplainInfoEntity;
import com.onlymvp.tool.Const;
import com.onlymvp.tool.DateTimeTool;
import com.onlymvp.tool.MyHibernateDaoSupport;
import com.onlymvp.tool.StringTool;

@Repository(value = "repairAndComplainInfoDAO")
public class RepairAndComplainInfoDAOImpl extends MyHibernateDaoSupport implements RepairAndComplainInfoDAO {

	@Override
	public Serializable insert(RepairAndComplainInfoEntity t) throws Exception {

		t.setDelStatus(Const.SYSTEM_DEL_STATUS_NO);
		t.setCreateTime(DateTimeTool.getDateTime(new Date()));
		t.setSolveStatus(Const.REPAIR_AND_COMPLAIN_INFO_SOLVED_NO);

		return this.getHibernateTemplate().save(t);
	}

	@Override
	public boolean update(RepairAndComplainInfoEntity t) throws Exception {

		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();

		String hql = "UPDATE RepairAndComplainInfoEntity SET dealUser = ?, solveStatus = ?, solveTime = ? WHERE id = ?";

		int res = hibernateTemplate.bulkUpdate(hql, t.getDealUser(), Const.REPAIR_AND_COMPLAIN_INFO_SOLVED_YES,
				DateTimeTool.getDateTime(new Date()), t.getId());

		return false;
	}

	@Override
	public void delete(RepairAndComplainInfoEntity t) throws Exception {
		this.getHibernateTemplate().delete(t);
	}

	@Override
	public boolean deleteById(Integer id) throws Exception {
		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();

		String hql = "DELETE FROM RepairAndComplainInfo WHERE id = ?";

		int res = hibernateTemplate.bulkUpdate(hql, id);

		return res > 0 ? true : false;
	}

	@Override
	public RepairAndComplainInfoEntity queryById(Integer id) throws Exception {

		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();

		String hql = "FROM RepairAndComplainInfo WHERE id = ?";

		@SuppressWarnings("unchecked")
		List<RepairAndComplainInfoEntity> list = hibernateTemplate.find(hql, id);

		return StringTool.isObjectNull(list) ? null : list.get(0);
	}

	@Override
	public List<UserAndPropertyAndReapirAndComplainInfoEntity> queryPageList(
			UserAndPropertyAndReapirAndComplainInfoEntity uprc, int start, int size) throws Exception {

		List<UserAndPropertyAndReapirAndComplainInfoEntity> resList = new ArrayList<UserAndPropertyAndReapirAndComplainInfoEntity>();

		Session session = this.getSession(true);

		StringBuilder sb = new StringBuilder();

		sb.append(" AND A.delStatus = " + Const.SYSTEM_DEL_STATUS_NO);
		sb.append(" AND B.delStatus = " + Const.SYSTEM_DEL_STATUS_NO);
		sb.append(" AND C.delStatus = " + Const.SYSTEM_DEL_STATUS_NO);

		Integer infoType = uprc.getInfoType();// 报修or投诉,不加入前台模糊查询,只作为参数供Action调用

		sb.append(" AND A.infoType = " + infoType);

		String realName = uprc.getRealName();

		String address = uprc.getAddress();

		String phone = uprc.getPhone();

		Integer solveStatus = uprc.getSolveStatus();// 解决状态

		if (!StringTool.isNull(realName))
			sb.append(" AND B.realName like '%" + realName + "%'");

		if (!StringTool.isNull(address))
			sb.append(" AND C.address like '%" + address + "%'");

		if (!StringTool.isNull(phone))
			sb.append(" AND B.phone like '%" + address + "%'");

		if (null != solveStatus)
			sb.append(" AND A.solveStatus = " + solveStatus);

		String hql = "SELECT A.id, A.userId, A.propertyId, A.content, A.infoType, A.solveStatus, A.delStatus, "
				+ "A.createTime, A.solveTime, B.realName, B.phone, C.address, A.dealUser "
				+ "FROM RepairAndComplainInfoEntity AS A, UserInfoEntity AS B, PropertyInfoEntity AS C "
				+ "WHERE A.userId = B.id AND A.propertyId = C.id " + sb.toString();

		Query query = session.createQuery(hql);

		query.setFirstResult(start);
		query.setMaxResults(size);

		@SuppressWarnings("unchecked")
		List<Object[]> list = query.list();

		if (!StringTool.isObjectNull(list)) {
			for (Object[] t : list) {
				UserAndPropertyAndReapirAndComplainInfoEntity entity = new UserAndPropertyAndReapirAndComplainInfoEntity();
				entity.setRepairAndComplainInfoId((Integer) t[0]);
				entity.setUserId((Integer) t[1]);
				entity.setPropertyId((Integer) t[2]);
				entity.setContent((String) t[3]);
				entity.setInfoType((Integer) t[4]);
				entity.setSolveStatus((Integer) t[5]);
				entity.setDelStatus((Integer) t[6]);
				entity.setCreateTime((String) t[7]);
				entity.setSolveTime((String) t[8]);
				entity.setRealName((String) t[9]);
				entity.setPhone((String) t[10]);
				entity.setAddress((String) t[11]);
				entity.setDealUser((String) t[12]);
				resList.add(entity);
			}
		}

		return resList;
	}

	@Override
	public long queryCount(UserAndPropertyAndReapirAndComplainInfoEntity uprc) throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(" AND A.delStatus = " + Const.SYSTEM_DEL_STATUS_NO);
		sb.append(" AND B.delStatus = " + Const.SYSTEM_DEL_STATUS_NO);
		sb.append(" AND C.delStatus = " + Const.SYSTEM_DEL_STATUS_NO);

		Integer infoType = uprc.getInfoType();// 报修or投诉,不加入前台模糊查询,只作为参数供Action调用

		sb.append(" AND A.infoType = " + infoType);

		String realName = uprc.getRealName();

		String address = uprc.getAddress();

		String phone = uprc.getPhone();

		Integer solveStatus = uprc.getSolveStatus();// 解决状态

		if (!StringTool.isNull(realName))
			sb.append(" AND B.realName like '%" + realName + "%'");

		if (!StringTool.isNull(address))
			sb.append(" AND C.address like '%" + address + "%'");

		if (!StringTool.isNull(phone))
			sb.append(" AND B.phone like '%" + address + "%'");

		if (null != solveStatus)
			sb.append(" AND A.solveStatus = " + solveStatus);

		String hql = "SELECT COUNT(A.id) "
				+ "FROM RepairAndComplainInfoEntity AS A, UserInfoEntity AS B, PropertyInfoEntity AS C "
				+ "WHERE A.userId = B.id AND A.propertyId = C.id " + sb.toString();

		return (long) this.getHibernateTemplate().find(hql).listIterator().next();
	}

}
