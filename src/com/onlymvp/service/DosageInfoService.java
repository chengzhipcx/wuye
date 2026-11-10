package com.onlymvp.service;

import java.io.Serializable;
import java.util.List;

import com.onlymvp.entity.DosageEntity;

public interface DosageInfoService {

	/**
	 * 保存用量信息
	 *
	 * @param t
	 *            用量实体
	 * @return 主键
	 * @throws Exception
	 */
	Serializable insert(DosageEntity t) throws Exception;

	/**
	 * 删除用量信息
	 *
	 * @param id
	 *            用量表主键ID
	 * @return
	 * @throws Exception
	 */
	boolean deleteById(Integer id) throws Exception;

	/**
	 * 查询用户用量列表
	 *
	 * @param userId
	 *            用户表主键ID
	 * @return
	 * @throws Exception
	 */
	List<DosageEntity> queryByUserId(int userId) throws Exception;

	/**
	 * 分页查询用量信息列表
	 *
	 * @param start
	 *            起始位置
	 * @param size
	 *            查询长度
	 * @return
	 * @throws Exception
	 */
	List<DosageEntity> queryPageList(int start, int size) throws Exception;

	/**
	 * 查询用量信息总数
	 *
	 * @return
	 * @throws Exception
	 */
	long queryCount() throws Exception;
}
