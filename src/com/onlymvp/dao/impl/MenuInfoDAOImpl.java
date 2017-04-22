package com.onlymvp.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.onlymvp.dao.MenuInfoDAO;
import com.onlymvp.entity.MenuInfoEntity;
import com.onlymvp.tool.Const;
import com.onlymvp.tool.MyHibernateDaoSupport;

@Repository(value = "menuInfoDAO")
public class MenuInfoDAOImpl extends MyHibernateDaoSupport implements MenuInfoDAO {

	@Override
	public MenuInfoEntity findParentById(String id) throws Exception {

		Session session = this.getSession(true);

		String hql = "FROM MenuInfoEntity WHERE id = :id AND parentId = :parentId AND delStatus = :delStatus";

		Query query = session.createQuery(hql).setString("id", id).setString("parentId", Const.MENU_STATUS_IS_PARENT)
				.setInteger("delStatus", Const.SYSTEM_DEL_STATUS_NO);

		@SuppressWarnings("unchecked")
		List<MenuInfoEntity> list = query.list();

		session.close();

		return list == null ? null : list.get(0);
	}

	@Override
	public List<MenuInfoEntity> findChildByParentId(String parentId) throws Exception {

		Session session = this.getSession(true);

		String hql = "FROM MenuInfoEntity WHERE parentId = :parentId AND delStatus = :delStatus ORDER BY orderBy ASC";

		Query query = session.createQuery(hql).setString("parentId", parentId).setInteger("delStatus",
				Const.SYSTEM_DEL_STATUS_NO);

		@SuppressWarnings("unchecked")
		List<MenuInfoEntity> list = query.list();

		session.close();

		return list;
	}

	@Override
	public List<MenuInfoEntity> findAllNormalParent() throws Exception {

		Session session = this.getSession(true);

		String hql = "FROM MenuInfoEntity WHERE id != :id AND parentId = :parentId ORDER BY orderBy ASC";

		Query query = session.createQuery(hql).setString("id", Const.SUPER_ADMIN_MENU_INFO_ID).setString("parentId",
				Const.MENU_STATUS_IS_PARENT);

		@SuppressWarnings("unchecked")
		List<MenuInfoEntity> list = query.list();

		session.close();

		return list;
	}

}
