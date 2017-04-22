package com.onlymvp.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.onlymvp.dao.RepairAndComplainInfoDAO;
import com.onlymvp.dto.UserAndPropertyAndReapirAndComplainInfoEntity;
import com.onlymvp.entity.RepairAndComplainInfoEntity;
import com.onlymvp.service.RepairAndComplainInfoService;

/**
 * 报修投诉业务实现
 * 
 * chengzhi
 *
 */
@Service
public class RepairAndComplainInfoServiceImpl implements RepairAndComplainInfoService {

	@Resource
	private RepairAndComplainInfoDAO repairAndComplainInfoDAO;

	@Override
	public Serializable insert(RepairAndComplainInfoEntity t) throws Exception {
		return this.repairAndComplainInfoDAO.insert(t);
	}

	@Override
	public boolean update(RepairAndComplainInfoEntity t) throws Exception {
		return this.repairAndComplainInfoDAO.update(t);
	}

	@Override
	public void delete(RepairAndComplainInfoEntity t) throws Exception {
		this.repairAndComplainInfoDAO.delete(t);
	}

	@Override
	public boolean deleteById(Integer id) throws Exception {
		return this.repairAndComplainInfoDAO.deleteById(id);
	}

	@Override
	public RepairAndComplainInfoEntity queryById(Integer id) throws Exception {
		return this.repairAndComplainInfoDAO.queryById(id);
	}

	@Override
	public Map<String, Object> queryListAndSize(UserAndPropertyAndReapirAndComplainInfoEntity uprc, int start, int size)
			throws Exception {

		Map<String, Object> resMap = new HashMap<String, Object>();

		List<UserAndPropertyAndReapirAndComplainInfoEntity> list = this.repairAndComplainInfoDAO.queryPageList(uprc,
				start, size);

		long count = this.repairAndComplainInfoDAO.queryCount(uprc);

		resMap.put("rows", list);
		resMap.put("total", count);

		return resMap;
	}

}
