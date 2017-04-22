package com.onlymvp.dao;

import java.util.List;

import com.onlymvp.dto.UserAndPropertyAndChargeInfoEntity;
import com.onlymvp.entity.ChargeInfoEntity;

public interface ChargeInfoDAO extends BaseDAO<ChargeInfoEntity> {

	/**
	 * 查询用户缴费列表
	 * 
	 * @param userId
	 *            用户表主键ID
	 * @return
	 * @throws Exception
	 */
	List<ChargeInfoEntity> queryByUserId(int userId) throws Exception;

	/**
	 * 分页模糊查询缴费信息列表
	 * 
	 * @param userAndPropertyAndChargeInfoEntity
	 *            包含UserInfo,PropertyInfo,ChargeInfo三张表中需要模糊查询的字段
	 * @param start
	 *            起始位置
	 * @param size
	 *            查询长度
	 * @return DTO数据传输对象
	 * @throws Exception
	 */
	List<UserAndPropertyAndChargeInfoEntity> queryPageList(
			UserAndPropertyAndChargeInfoEntity userAndPropertyAndChargeInfoEntity, int start, int size)
			throws Exception;

	/**
	 * 模糊条件查询缴费列表长度
	 * 
	 * @param userAndPropertyAndChargeInfoEntity
	 *            模糊查询条件
	 * @return 查询到的长度
	 * @throws Exception
	 */
	long queryCount(UserAndPropertyAndChargeInfoEntity userAndPropertyAndChargeInfoEntity) throws Exception;

	/**
	 * 更新缴费信息
	 * 
	 * @param userAndPropertyAndChargeInfoEntity
	 *            更新的数据以及主键ID
	 * @return 成功返回"true",否则返回"false"
	 * @throws Exception
	 */
	boolean updateChargeInfo(UserAndPropertyAndChargeInfoEntity userAndPropertyAndChargeInfoEntity) throws Exception;

}
