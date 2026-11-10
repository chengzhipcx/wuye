package com.onlymvp.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.onlymvp.dao.DosageInfoDAO;
import com.onlymvp.entity.DosageEntity;
import com.onlymvp.service.DosageInfoService;

/**
 * 住户用量管理业务实现
 *
 * chengzhi
 *
 */
@Service
public class DosageInfoServiceImpl implements DosageInfoService {

	@Resource
	private DosageInfoDAO dosageInfoDAO;

	@Override
	public Serializable insert(DosageEntity t) throws Exception {
		return this.dosageInfoDAO.insert(t);
	}

	@Override
	public boolean deleteById(Integer id) throws Exception {
		return this.dosageInfoDAO.deleteById(id);
	}

	@Override
	public List<DosageEntity> queryByUserId(int userId) throws Exception {
		return this.dosageInfoDAO.queryByUserId(userId);
	}

	@Override
	public List<DosageEntity> queryPageList(int start, int size) throws Exception {
		return this.dosageInfoDAO.queryPageList(start, size);
	}

	@Override
	public long queryCount() throws Exception {
		return this.dosageInfoDAO.queryCount();
	}
}
