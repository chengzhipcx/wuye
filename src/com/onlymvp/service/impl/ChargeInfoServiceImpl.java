package com.onlymvp.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.onlymvp.dao.ChargeInfoDAO;
import com.onlymvp.dto.UserAndPropertyAndChargeInfoEntity;
import com.onlymvp.entity.ChargeInfoEntity;
import com.onlymvp.service.ChargeInfoService;

/**
 * 缴费管理业务实现
 * 
 * chengzhi
 *
 */
@Service
public class ChargeInfoServiceImpl implements ChargeInfoService {

	@Resource
	private ChargeInfoDAO chargeInfoDAO;

	@Override
	public Serializable insert(ChargeInfoEntity t) throws Exception {
		return this.chargeInfoDAO.insert(t);
	}

	@Override
	public boolean update(ChargeInfoEntity t) throws Exception {
		return this.chargeInfoDAO.update(t);
	}

	@Override
	public void delete(ChargeInfoEntity t) throws Exception {
		this.chargeInfoDAO.delete(t);
	}

	@Override
	public boolean deleteById(Integer id) throws Exception {
		return this.chargeInfoDAO.deleteById(id);
	}

	@Override
	public ChargeInfoEntity queryById(Integer id) throws Exception {
		return this.chargeInfoDAO.queryById(id);
	}

	@Override
	public Map<String, Object> queryListAndSize(UserAndPropertyAndChargeInfoEntity userAndPropertyAndChargeInfoEntity,
			int start, int size) throws Exception {

		List<UserAndPropertyAndChargeInfoEntity> list = this.chargeInfoDAO
				.queryPageList(userAndPropertyAndChargeInfoEntity, start, size);

		long count = this.chargeInfoDAO.queryCount(userAndPropertyAndChargeInfoEntity);

		Map<String, Object> resMap = new HashMap<String, Object>();

		resMap.put("rows", list);
		resMap.put("total", count);

		return resMap;
	}

	@Override
	public boolean updateChargeInfo(UserAndPropertyAndChargeInfoEntity userAndPropertyAndChargeInfoEntity)
			throws Exception {
		return this.chargeInfoDAO.updateChargeInfo(userAndPropertyAndChargeInfoEntity);
	}

	@Override
	public List<ChargeInfoEntity> queryByUserId(int userId) throws Exception {
		return this.chargeInfoDAO.queryByUserId(userId);
	}

}
