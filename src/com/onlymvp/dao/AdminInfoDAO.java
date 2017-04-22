package com.onlymvp.dao;

import java.util.List;

import com.onlymvp.entity.AdminInfoEntity;

public interface AdminInfoDAO extends BaseDAO<AdminInfoEntity> {

	/**
	 * 通过主键ID查询管理员信息
	 * 
	 * @param id
	 *            AdminInfo主键ID
	 * @return 查询成功返回查询到的管理员对象,否则返回"null"
	 * @throws Exception
	 */
	AdminInfoEntity queryById(int id) throws Exception;

	/**
	 * 更新普通管理员密码
	 * 
	 * @param id
	 *            管理员表主键ID
	 * @param userPwd
	 *            更新后的密码
	 * @return 更新成功返回"true",否则返回"false"
	 * @throws Exception
	 */
	boolean updateAdminPassword(int id, String userPwd) throws Exception;

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
	 * 查询所有普通管理员
	 * 
	 * @param start
	 *            查询起点
	 * @param size
	 *            每页显示条数
	 * @return 普通管理员列表
	 * @throws Exception
	 */
	List<AdminInfoEntity> queryNormalAdminList(int start, int size) throws Exception;

	/**
	 * 查询普通管理员总数
	 * 
	 * @return 普通管理员总数
	 * @throws Exception
	 */
	long queryNormalAdminCount() throws Exception;

}
