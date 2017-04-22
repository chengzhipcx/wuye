package com.onlymvp.dao;

import java.util.List;

import com.onlymvp.entity.MenuInfoEntity;

public interface MenuInfoDAO {

	/**
	 * 查询父菜单
	 * 
	 * @param id
	 *            MenuInfo表主键ID
	 * @return 菜单详细信息
	 * @throws Exception
	 */
	public MenuInfoEntity findParentById(String id) throws Exception;

	/**
	 * 查询父菜单所拥有的所有子菜单
	 * 
	 * @param parentId
	 *            父菜单主键ID
	 * @return 子菜单列表
	 * @throws Exception
	 */
	public List<MenuInfoEntity> findChildByParentId(String parentId) throws Exception;

	/**
	 * 查询所有父菜单,不包括"管理员管理"菜单
	 * 
	 * @return 父菜单列表
	 * @throws Exception
	 */
	public List<MenuInfoEntity> findAllNormalParent() throws Exception;

}
