package com.onlymvp.dao;

import java.util.List;

import com.onlymvp.dto.UserAndPropertyAndReapirAndComplainInfoEntity;
import com.onlymvp.entity.RepairAndComplainInfoEntity;

public interface RepairAndComplainInfoDAO extends BaseDAO<RepairAndComplainInfoEntity> {

	/**
	 * 分页模糊查询报修或投诉列表
	 * 
	 * @param uprc
	 *            查询条件
	 * @param start
	 *            起始位置
	 * @param size
	 *            查询长度
	 * @return 查询到的报修或投诉数据列表
	 * @throws Exception
	 */
	List<UserAndPropertyAndReapirAndComplainInfoEntity> queryPageList(
			UserAndPropertyAndReapirAndComplainInfoEntity uprc, int start, int size) throws Exception;

	/**
	 * 查询符合条件的记录条数
	 * 
	 * @param uprc
	 *            查询条件
	 * @return 查询到的报修或投诉数据列表
	 * @throws Exception
	 */
	long queryCount(UserAndPropertyAndReapirAndComplainInfoEntity uprc) throws Exception;

}
