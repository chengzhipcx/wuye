package com.onlymvp.service;

import java.util.Map;

import com.onlymvp.dto.UserAndPropertyAndReapirAndComplainInfoEntity;
import com.onlymvp.entity.RepairAndComplainInfoEntity;

/**
 * 报修投诉业务操作接口
 * 
 * chengzhi
 *
 */
public interface RepairAndComplainInfoService extends BaseService<RepairAndComplainInfoEntity> {

	/**
	 * 分页模糊查询报修或投诉列表以及符合记录的条数
	 * 
	 * @param uprc
	 *            查询条件
	 * @param start
	 *            起始位置
	 * @param size
	 *            查询长度
	 * @return 包含报修或投诉列表以及符合记录的条数的Map集合
	 * @throws Exception
	 */
	Map<String, Object> queryListAndSize(UserAndPropertyAndReapirAndComplainInfoEntity uprc, int start, int size)
			throws Exception;

}
