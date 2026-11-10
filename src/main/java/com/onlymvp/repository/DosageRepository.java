package com.onlymvp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onlymvp.entity.DosageEntity;

/**
 * 住户用量信息数据访问
 *
 * chengzhi
 *
 */
public interface DosageRepository extends JpaRepository<DosageEntity, Integer> {

	List<DosageEntity> findByUserIdOrderByDateAsc(Integer userId);
}
