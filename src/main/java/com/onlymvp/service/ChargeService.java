package com.onlymvp.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlymvp.dto.UserAndPropertyAndChargeInfoEntity;
import com.onlymvp.entity.ChargeInfoEntity;
import com.onlymvp.repository.ChargeInfoRepository;
import com.onlymvp.tool.Const;
import com.onlymvp.tool.DateTimeTool;

/**
 * 缴费业务
 *
 * chengzhi
 *
 */
@Service
public class ChargeService {

	private final ChargeInfoRepository chargeInfoRepository;

	public ChargeService(ChargeInfoRepository chargeInfoRepository) {
		this.chargeInfoRepository = chargeInfoRepository;
	}

	public ChargeInfoEntity queryById(Integer id) {
		return chargeInfoRepository.findById(id).orElse(null);
	}

	public List<ChargeInfoEntity> queryByUserId(Integer userId) {
		return chargeInfoRepository.findByUserIdAndDelStatusOrderByIdDesc(userId, Const.SYSTEM_DEL_STATUS_NO);
	}

	/**
	 * 分页三表联查缴费列表
	 */
	public Map<String, Object> queryChargePage(String realName, String address, String phone, Integer chargeType,
			int page, int number) {

		Pageable pageable = PageRequest.of(page - 1, number);

		List<UserAndPropertyAndChargeInfoEntity> rows = chargeInfoRepository.queryChargePage(realName, address, phone,
				chargeType, pageable);

		long count = chargeInfoRepository.queryChargeCount(realName, address, phone, chargeType);

		Map<String, Object> resMap = new HashMap<>();
		resMap.put("rows", rows);
		resMap.put("total", count);
		return resMap;
	}

	/**
	 * 保存缴费信息(初始状态: 未缴费)
	 */
	@Transactional
	public void saveCharge(Integer userId, Integer propertyId, Integer chargeType, Integer payment, String remark) {

		ChargeInfoEntity entity = new ChargeInfoEntity();

		entity.setUserId(userId);
		entity.setPropertyId(propertyId);
		entity.setChargeType(chargeType);
		entity.setPayment(payment);
		entity.setRemark(remark);
		entity.setDelStatus(Const.SYSTEM_DEL_STATUS_NO);
		entity.setStatus("0");
		entity.setCreateTime(DateTimeTool.getDateTime(new Date()));

		chargeInfoRepository.save(entity);
	}

	/**
	 * 更新缴费信息(类型/金额/备注)
	 */
	@Transactional
	public int updateCharge(Integer chargeInfoId, Integer chargeType, Integer payment, String remark) {
		return chargeInfoRepository.updateChargeInfo(chargeType, payment, remark, chargeInfoId);
	}

	/**
	 * 业主点击缴费: 状态置为已缴费
	 */
	@Transactional
	public boolean updateChargeStatus(Integer chargeInfoId) {

		ChargeInfoEntity entity = chargeInfoRepository.findById(chargeInfoId).orElse(null);

		if (entity == null) {
			return false;
		}

		entity.setStatus("1");

		chargeInfoRepository.save(entity);

		return true;
	}

	/**
	 * 批量逻辑删除缴费记录
	 */
	@Transactional
	public void deleteCharges(List<Integer> ids) {

		for (Integer id : ids) {

			ChargeInfoEntity entity = chargeInfoRepository.findById(id).orElse(null);

			if (entity != null) {
				entity.setDelStatus(Const.SYSTEM_DEL_STATUS_YES);
				chargeInfoRepository.save(entity);
			}
		}
	}
}
