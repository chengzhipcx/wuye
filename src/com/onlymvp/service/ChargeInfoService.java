package com.onlymvp.service;

import java.util.List;
import java.util.Map;

import com.onlymvp.dto.UserAndPropertyAndChargeInfoEntity;
import com.onlymvp.entity.ChargeInfoEntity;

/**
 * 缴费管理业务操作接口
 * 
 * chengzhi
 *
 */
public interface ChargeInfoService extends BaseService<ChargeInfoEntity> {
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
	 * 分页模糊查询收费列表以及数据长度
	 * 
	 * @param userAndPropertyAndChargeInfoEntity
	 *            模糊查询条件
	 * @param start
	 *            起始位置
	 * @param size
	 *            查询长度
	 * @return 包含收费列表以及数据长度的Map集合
	 * @throws Exception
	 */
	Map<String, Object> queryListAndSize(UserAndPropertyAndChargeInfoEntity userAndPropertyAndChargeInfoEntity,
			int start, int size) throws Exception;

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
