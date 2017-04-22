package com.onlymvp.dao;

import java.util.List;

import com.onlymvp.entity.UserInfoEntity;

public interface UserInfoDAO extends BaseDAO<UserInfoEntity> {

	/**
	 * 通过PropertyInfo表主键ID删除UserInfo表业主数据
	 * 
	 * @param propertyId
	 *            PropertyInfo表主键ID
	 * @return 成功返回"true",否则返回"false"
	 * @throws Exception
	 */
	boolean deleteByPropertyId(Integer propertyId) throws Exception;

	/**
	 * 通过用户名密码查询业主信息
	 * 
	 * @param userName
	 *            业主用户名
	 * @param userPwd
	 *            登录密码
	 * @return 查询成功返回业主对象否则返回"null"
	 * @throws Exception
	 */
	UserInfoEntity queryByUserNameAndUserPwd(String userName, String userPwd) throws Exception;

	/**
	 * 分页模糊查询业主列表
	 * 
	 * @param userInfoEntity
	 *            查询条件
	 * @param start
	 *            查询起点
	 * @param size
	 *            查询长度
	 * @return 符合条件的业主列表
	 * @throws Exception
	 */
	List<UserInfoEntity> queryUserInfoList(UserInfoEntity userInfoEntity, int start, int size) throws Exception;

	/**
	 * 模糊查询符合条件的业主条数
	 * 
	 * @param userInfoEntity
	 *            查询条件
	 * @return 查询到的记录总条数
	 * @throws Exception
	 */
	long queryCount(UserInfoEntity userInfoEntity) throws Exception;

	/**
	 * 通过业主账号查询业主信息
	 * 
	 * @param userName
	 *            UserInfo表唯一约束字段
	 * @return 查询到数据返回业主信息否则返回"null"
	 * @throws Exception
	 */
	UserInfoEntity queryByUserName(String userName) throws Exception;

}
