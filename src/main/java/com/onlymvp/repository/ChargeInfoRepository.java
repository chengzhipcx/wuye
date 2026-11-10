package com.onlymvp.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.onlymvp.dto.UserAndPropertyAndChargeInfoEntity;
import com.onlymvp.entity.ChargeInfoEntity;

/**
 * 缴费信息数据访问
 *
 * chengzhi
 *
 */
public interface ChargeInfoRepository extends JpaRepository<ChargeInfoEntity, Integer> {

	List<ChargeInfoEntity> findByUserIdAndDelStatusOrderByIdDesc(Integer userId, Integer delStatus);

	/**
	 * 三表联查分页(缴费+房产+业主), 过滤语义与原 SSH 版一致
	 */
	@Query("SELECT new com.onlymvp.dto.UserAndPropertyAndChargeInfoEntity("
			+ "a.id, a.propertyId, a.userId, a.chargeType, a.payment, a.remark, a.createTime, "
			+ "p.address, u.realName, u.phone, a.status) "
			+ "FROM ChargeInfoEntity a, PropertyInfoEntity p, UserInfoEntity u "
			+ "WHERE a.propertyId = p.id AND a.userId = u.id "
			+ " AND a.delStatus = 0 AND p.delStatus = 0 AND u.delStatus = 0"
			+ " AND (:realName IS NULL OR u.realName LIKE CONCAT('%', :realName, '%'))"
			+ " AND (:address IS NULL OR p.address LIKE CONCAT('%', :address, '%'))"
			+ " AND (:phone IS NULL OR u.phone LIKE CONCAT('%', :phone, '%'))"
			+ " AND (:chargeType IS NULL OR a.chargeType = :chargeType)"
			+ " ORDER BY a.chargeType ASC, a.id DESC")
	List<UserAndPropertyAndChargeInfoEntity> queryChargePage(@Param("realName") String realName,
			@Param("address") String address, @Param("phone") String phone, @Param("chargeType") Integer chargeType,
			Pageable pageable);

	@Query("SELECT COUNT(a.id) FROM ChargeInfoEntity a, PropertyInfoEntity p, UserInfoEntity u "
			+ "WHERE a.propertyId = p.id AND a.userId = u.id "
			+ " AND a.delStatus = 0 AND p.delStatus = 0 AND u.delStatus = 0"
			+ " AND (:realName IS NULL OR u.realName LIKE CONCAT('%', :realName, '%'))"
			+ " AND (:address IS NULL OR p.address LIKE CONCAT('%', :address, '%'))"
			+ " AND (:phone IS NULL OR u.phone LIKE CONCAT('%', :phone, '%'))"
			+ " AND (:chargeType IS NULL OR a.chargeType = :chargeType)")
	long queryChargeCount(@Param("realName") String realName, @Param("address") String address,
			@Param("phone") String phone, @Param("chargeType") Integer chargeType);

	@Modifying
	@Query("UPDATE ChargeInfoEntity SET chargeType = :chargeType, payment = :payment, remark = :remark "
			+ "WHERE id = :id")
	int updateChargeInfo(@Param("chargeType") Integer chargeType, @Param("payment") Integer payment,
			@Param("remark") String remark, @Param("id") Integer id);
}
