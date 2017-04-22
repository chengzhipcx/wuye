package com.onlymvp.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.onlymvp.dao.MenuAndRoleDAO;
import com.onlymvp.entity.MenuAndRoleEntity;
import com.onlymvp.tool.Const;
import com.onlymvp.tool.MyHibernateDaoSupport;
import com.onlymvp.tool.StringTool;

@Repository(value = "menuAndRoleDAO")
public class MenuAndRoleDAOImpl extends MyHibernateDaoSupport implements MenuAndRoleDAO {

	@Override
	public List<MenuAndRoleEntity> findAllByAdminId(String adminId) throws Exception {

		List<MenuAndRoleEntity> resList = null;

		Session session = this.getSession(true);

		String hql = "SELECT A.id, A.menuId, A.adminId FROM MenuAndRoleEntity AS A, MenuInfoEntity AS B, AdminInfoEntity AS C WHERE A.menuId = B.id AND C.id = A.adminId AND adminId = :adminId AND B.delStatus = :delStatus AND C.userStatus = :userStatus";

		Query query = session.createQuery(hql).setString("adminId", adminId)
				.setInteger("delStatus", Const.SYSTEM_DEL_STATUS_NO).setInteger("userStatus", Const.SYSTEM_STATUS_ON);

		// List<MenuAndRoleEntity> list = query.list();

		List<Object[]> list = query.list();

		if (!StringTool.isObjectNull(list)) {
			resList = new ArrayList<MenuAndRoleEntity>();
			for (Object[] t : list) {
				MenuAndRoleEntity entity = new MenuAndRoleEntity();
				entity.setId((int) t[0]);
				entity.setMenuId((int) t[1]);
				entity.setAdminId((int) t[2]);
				resList.add(entity);
			}
		}

		session.close();

		return resList;
	}

	@Override
	public Serializable insert(MenuAndRoleEntity menuAndRoleEntity) throws Exception {

		// Session session = this.getSession(true);

		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();

		Serializable serializable = hibernateTemplate.save(menuAndRoleEntity);

		// Serializable serializable = session.save(menuAndRoleEntity);

		// session.close();

		return serializable;
	}

	@Override
	public void deleteByAdminId(Integer adminId) throws Exception {

		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();

		String hql = "DELETE FROM MenuAndRoleEntity WHERE adminId = ?";

		hibernateTemplate.bulkUpdate(hql, new Integer[] { adminId });

	}

}
