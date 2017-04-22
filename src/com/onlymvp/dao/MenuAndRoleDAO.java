package com.onlymvp.dao;

import java.io.Serializable;
import java.util.List;

import com.onlymvp.entity.MenuAndRoleEntity;

public interface MenuAndRoleDAO {

	/**
	 * 通过管理员主键ID查询所属菜单关联
	 * 
	 * @param adminId
	 *            管理员主键ID
	 * @return
	 * @throws Exception
	 */
	List<MenuAndRoleEntity> findAllByAdminId(String adminId) throws Exception;

	/**
	 * 保存菜单角色到数据库
	 * 
	 * @param menuAndRoleEntity
	 * @return
	 * @throws Exception
	 */
	Serializable insert(MenuAndRoleEntity menuAndRoleEntity) throws Exception;

	/**
	 * 删除对应管理员所有父菜单
	 * 
	 * @param adminId
	 *            AdminInfoEntity 主键ID
	 * @throws Exception
	 */
	void deleteByAdminId(Integer adminId) throws Exception;

}
