package com.onlymvp.service;

import java.util.Map;

import com.onlymvp.entity.PropertyInfoEntity;
import com.onlymvp.entity.UserInfoEntity;

/**
 * 业主信息管理业务操作接口
 * 
 * chengzhi
 *
 */
public interface UserInfoService extends BaseService<UserInfoEntity> {

	/**
	 * 保存业主信息以及更新房产状态
	 * 
	 * @param userInfoEntity
	 *            业主信息
	 */
	void saveUserInfoAndUpdatePropertySaleStatus(UserInfoEntity userInfoEntity) throws Exception;

	/**
	 * 模糊查询业主列表和记录条数
	 * 
	 * @param userInfoEntity
	 *            查询条件
	 * @param start
	 *            起始位置
	 * @param size
	 *            查询长度
	 * @return 包含数据列表以及记录条数的Map集合
	 * @throws Exception
	 */
	Map<String, Object> queryUserInfoListAndSize(UserInfoEntity userInfoEntity, int start, int size) throws Exception;

	/**
	 * 通过业主用户名查询业主详细信息
	 * 
	 * @param userName
	 *            业主用户名
	 * @return 业主信息
	 * @throws Exception
	 */
	UserInfoEntity queryByUserName(String userName) throws Exception;

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

}
