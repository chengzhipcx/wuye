package com.onlymvp.service;

import java.util.Map;

import com.onlymvp.entity.PropertyInfoEntity;

/**
 * 房产管理业务操作接口
 * 
 * chengzhi
 *
 */
public interface PropertyInfoService extends BaseService<PropertyInfoEntity> {

	/**
	 * 分页模糊查询房产信息以及总数据数
	 * 
	 * @param propertyInfoEntity
	 *            模糊查询条件
	 * @param start
	 *            查询起点
	 * @param size
	 *            每页显示条数
	 * @return 房产列表
	 * @throws Exception
	 */
	Map<String, Object> queryPropertyListAndSize(PropertyInfoEntity propertyInfoEntity, int start, int size)
			throws Exception;

	/**
	 * 通过主键ID查询房产信息
	 * 
	 * @param id
	 *            PropertyInfo表主键ID
	 * @return 查询到的房产信息
	 * @throws Exception
	 */
	PropertyInfoEntity queryById(Integer id) throws Exception;

	/**
	 * 通过业主用户名查询该业主房产信息
	 * 
	 * @param userName
	 *            业主用户名 UserInfo表中唯一约束
	 * @return 该业主房产信息
	 * @throws Exception
	 */
	PropertyInfoEntity queryPropertyInfoByUserName(String userName) throws Exception;

}
