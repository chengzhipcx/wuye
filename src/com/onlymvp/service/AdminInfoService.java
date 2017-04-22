package com.onlymvp.service;

import java.util.Map;

import com.onlymvp.entity.AdminInfoEntity;

/**
 * 管理员信息业务操作接口
 * 
 * chengzhi
 *
 */
public interface AdminInfoService extends BaseService<AdminInfoEntity> {

	/**
	 * 通过主键ID查询管理员信息
	 * 
	 * @param id
	 *            AdminInfo主键ID
	 * @return 查询成功返回查询到的管理员对象,否则返回"null"
	 * @throws Exception
	 */
	AdminInfoEntity queryById(Integer id) throws Exception;

	/**
	 * 通过登录名,密码查询管理员信息
	 * 
	 * @param userName
	 *            登录名
	 * @param userPwd
	 *            密码
	 * @return 登录成功返回查询到的对象,否则返回"null"
	 * @throws Exception
	 */
	AdminInfoEntity queryByNameAndPwd(String userName, String userPwd) throws Exception;

	/**
	 * 分页查询普通管理员以及总人数
	 * 
	 * @param start
	 *            查询起点
	 * @param size
	 *            每页显示条数
	 * @return 包含普通管理员列表以及总人数的Map集合
	 * @throws Exception
	 */
	Map<String, Object> pageQueryNormalAdminListAndSize(int start, int size) throws Exception;

	/**
	 * 保存普通管理员和其所对应的菜单权限
	 * 
	 * @param adminInfoEntity
	 *            管理员对象
	 * @param menuIds
	 *            所拥有的父菜单ID
	 * @throws Exception
	 */
	void saveAdminInfoAndMenuPower(AdminInfoEntity adminInfoEntity, String[] menuIds) throws Exception;

	/**
	 * 更新普通管理员和其所对应的菜单权限
	 * 
	 * @param adminInfoEntity
	 *            管理员对象
	 * @param menuIds
	 *            所拥有的父菜单ID
	 * @throws Exception
	 * @return 成功返回"true",否则返回"false"
	 */
	boolean updateAdminInfoAndMenuPower(AdminInfoEntity adminInfoEntity, String[] menuIds) throws Exception;

	/**
	 * 批量删除普通管理员以及其对应的菜单角色
	 * 
	 * @param id
	 *            AdminInfoEntity 主键ID
	 * @throws Exception
	 */
	void batchRemoveAdminAndRole(Integer id) throws Exception;

}
