package com.onlymvp.service;

import java.util.List;
import java.util.Map;

import com.onlymvp.entity.MenuInfoEntity;

/**
 * 菜单信息管理业务操作接口
 * 
 * chengzhi
 *
 */
public interface MenuInfoService {
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
	public List<MenuInfoEntity> findClildByParentId(String parentId) throws Exception;

	/**
	 * 查询管理员所有菜单
	 * 
	 * @param adminId
	 *            AdminInfo表主键ID
	 * @return 父菜单包含子菜单的Map集合
	 * @throws Exception
	 */
	public Map<MenuInfoEntity, List<MenuInfoEntity>> findMenuByAdminId(String adminId) throws Exception;

	/**
	 * 查询所有父菜单,不包括"管理员管理"菜单
	 * 
	 * @return 父菜单列表
	 * @throws Exception
	 */
	public List<MenuInfoEntity> findAllNormalParent() throws Exception;

}
