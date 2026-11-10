package com.onlymvp.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlymvp.dto.UserAndPropertyAndReapirAndComplainInfoEntity;
import com.onlymvp.entity.RepairAndComplainInfoEntity;
import com.onlymvp.repository.RepairAndComplainInfoRepository;
import com.onlymvp.tool.Const;
import com.onlymvp.tool.DateTimeTool;

/**
 * 报修/投诉业务
 *
 * chengzhi
 *
 */
@Service
public class RepairService {

	private final RepairAndComplainInfoRepository repairRepository;

	public RepairService(RepairAndComplainInfoRepository repairRepository) {
		this.repairRepository = repairRepository;
	}

	/**
	 * 分页三表联查报修/投诉列表
	 */
	public Map<String, Object> queryRepairPage(Integer infoType, String realName, String address, String phone,
			Integer solveStatus, int page, int number) {

		Pageable pageable = PageRequest.of(page - 1, number);

		List<UserAndPropertyAndReapirAndComplainInfoEntity> rows = repairRepository.queryRepairPage(infoType, realName,
				address, phone, solveStatus, pageable);

		long count = repairRepository.queryRepairCount(infoType, realName, address, phone, solveStatus);

		Map<String, Object> resMap = new HashMap<>();
		resMap.put("rows", rows);
		resMap.put("total", count);
		return resMap;
	}

	/**
	 * 业主提交报修/投诉
	 */
	@Transactional
	public void saveRepair(Integer userId, Integer propertyId, String content, Integer infoType) {

		RepairAndComplainInfoEntity entity = new RepairAndComplainInfoEntity();

		entity.setUserId(userId);
		entity.setPropertyId(propertyId);
		entity.setContent(content);
		entity.setInfoType(infoType);
		entity.setSolveStatus(Const.REPAIR_AND_COMPLAIN_INFO_SOLVED_NO);
		entity.setDelStatus(Const.SYSTEM_DEL_STATUS_NO);
		entity.setCreateTime(DateTimeTool.getDateTime(new Date()));

		repairRepository.save(entity);
	}

	/**
	 * 处理报修/投诉: 记录经办人并置为已解决
	 */
	@Transactional
	public int updateDealInfo(Integer id, String dealUser) {
		return repairRepository.updateDealInfo(dealUser, DateTimeTool.getDateTime(new Date()), id);
	}
}
