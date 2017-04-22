package com.onlymvp.dao;

import java.util.List;

import com.onlymvp.entity.PropertyInfoEntity;

public interface PropertyInfoDAO extends BaseDAO<PropertyInfoEntity> {
	/**
	 * 分页模糊查询房产信息
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
	List<PropertyInfoEntity> queryPropertyList(PropertyInfoEntity propertyInfoEntity, int start, int size)
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
	 * 更新房产状态
	 * 
	 * @param id
	 *            PropertyInfo表主键ID
	 * @param saleStatus
	 *            房产状态"0"未出售,"1"已出售
	 * @return 成功返回"true",否则返回"false"
	 * @throws Exception
	 */
	boolean updateSaleStatus(Integer id, Integer saleStatus) throws Exception;

	/**
	 * 根据条件查询记录条数
	 * 
	 * @param propertyInfoEntity
	 *            模糊查询条件
	 * @return
	 * @throws Exception
	 */
	long queryCount(PropertyInfoEntity propertyInfoEntity) throws Exception;
}
