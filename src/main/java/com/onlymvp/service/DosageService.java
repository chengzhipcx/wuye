package com.onlymvp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlymvp.entity.DosageEntity;
import com.onlymvp.repository.DosageRepository;

/**
 * 住户用量业务
 *
 * chengzhi
 *
 */
@Service
public class DosageService {

	private final DosageRepository dosageRepository;

	public DosageService(DosageRepository dosageRepository) {
		this.dosageRepository = dosageRepository;
	}

	public List<DosageEntity> queryByUserId(Integer userId) {
		return dosageRepository.findByUserIdOrderByDateAsc(userId);
	}

	/**
	 * 分页查询用量列表
	 */
	public Map<String, Object> queryDosagePage(int page, int number) {

		Pageable pageable = PageRequest.of(page - 1, number);

		List<DosageEntity> rows = dosageRepository.findAll(pageable).getContent();

		long count = dosageRepository.count();

		Map<String, Object> resMap = new HashMap<>();
		resMap.put("rows", rows);
		resMap.put("total", count);
		return resMap;
	}

	@Transactional
	public DosageEntity saveDosage(DosageEntity dosage) {
		return dosageRepository.save(dosage);
	}

	@Transactional
	public void deleteById(Integer id) {
		dosageRepository.deleteById(id);
	}
}
