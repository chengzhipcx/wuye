package com.onlymvp.service;

import java.util.List;

import com.onlymvp.entity.MenuAndRoleEntity;

/**
 * 角色菜单业务操作接口
 * 
 * chengzhi
 *
 */
public interface MenuAndRoleService {
	/**
	 * 通过管理员主键ID查询所属菜单关联
	 * 
	 * @param adminId
	 *            管理员主键ID
	 * @return
	 * @throws Exception
	 */
	public List<MenuAndRoleEntity> findAllByAdminId(String adminId) throws Exception;
}
