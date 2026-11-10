package com.onlymvp.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.onlymvp.dto.UserAndPropertyAndReapirAndComplainInfoEntity;
import com.onlymvp.entity.RepairAndComplainInfoEntity;

/**
 * 报修/投诉信息数据访问
 *
 * chengzhi
 *
 */
public interface RepairAndComplainInfoRepository extends JpaRepository<RepairAndComplainInfoEntity, Integer> {

	/**
	 * 三表联查分页(报修投诉+业主+房产), 过滤语义与原 SSH 版一致
	 */
	@Query("SELECT new com.onlymvp.dto.UserAndPropertyAndReapirAndComplainInfoEntity("
			+ "a.id, a.userId, a.propertyId, a.content, a.infoType, a.solveStatus, a.delStatus, "
			+ "a.createTime, a.solveTime, u.realName, u.phone, p.address, a.dealUser) "
			+ "FROM RepairAndComplainInfoEntity a, UserInfoEntity u, PropertyInfoEntity p "
			+ "WHERE a.userId = u.id AND a.propertyId = p.id "
			+ " AND a.delStatus = 0 AND u.delStatus = 0 AND p.delStatus = 0"
			+ " AND a.infoType = :infoType"
			+ " AND (:realName IS NULL OR u.realName LIKE CONCAT('%', :realName, '%'))"
			+ " AND (:address IS NULL OR p.address LIKE CONCAT('%', :address, '%'))"
			+ " AND (:phone IS NULL OR u.phone LIKE CONCAT('%', :phone, '%'))"
			+ " AND (:solveStatus IS NULL OR a.solveStatus = :solveStatus)")
	List<UserAndPropertyAndReapirAndComplainInfoEntity> queryRepairPage(@Param("infoType") Integer infoType,
			@Param("realName") String realName, @Param("address") String address, @Param("phone") String phone,
			@Param("solveStatus") Integer solveStatus, Pageable pageable);

	@Query("SELECT COUNT(a.id) FROM RepairAndComplainInfoEntity a, UserInfoEntity u, PropertyInfoEntity p "
			+ "WHERE a.userId = u.id AND a.propertyId = p.id "
			+ " AND a.delStatus = 0 AND u.delStatus = 0 AND p.delStatus = 0"
			+ " AND a.infoType = :infoType"
			+ " AND (:realName IS NULL OR u.realName LIKE CONCAT('%', :realName, '%'))"
			+ " AND (:address IS NULL OR p.address LIKE CONCAT('%', :address, '%'))"
			+ " AND (:phone IS NULL OR u.phone LIKE CONCAT('%', :phone, '%'))"
			+ " AND (:solveStatus IS NULL OR a.solveStatus = :solveStatus)")
	long queryRepairCount(@Param("infoType") Integer infoType, @Param("realName") String realName,
			@Param("address") String address, @Param("phone") String phone,
			@Param("solveStatus") Integer solveStatus);

	@Modifying
	@Query("UPDATE RepairAndComplainInfoEntity SET dealUser = :dealUser, solveStatus = 1, solveTime = :solveTime "
			+ "WHERE id = :id")
	int updateDealInfo(@Param("dealUser") String dealUser, @Param("solveTime") String solveTime,
			@Param("id") Integer id);
}
