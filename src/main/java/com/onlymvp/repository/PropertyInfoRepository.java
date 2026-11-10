package com.onlymvp.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.onlymvp.entity.PropertyInfoEntity;

/**
 * 房产信息数据访问
 *
 * chengzhi
 *
 */
public interface PropertyInfoRepository extends JpaRepository<PropertyInfoEntity, Integer> {

	/**
	 * 分页模糊查询(地址模糊 + 出售状态过滤)
	 */
	@Query("SELECT p FROM PropertyInfoEntity p WHERE p.delStatus = 0"
			+ " AND (:address IS NULL OR p.address LIKE CONCAT('%', :address, '%'))"
			+ " AND (:saleStatus IS NULL OR p.saleStatus = :saleStatus)")
	List<PropertyInfoEntity> queryPropertyList(@Param("address") String address,
			@Param("saleStatus") Integer saleStatus, Pageable pageable);

	@Query("SELECT COUNT(p) FROM PropertyInfoEntity p WHERE p.delStatus = 0"
			+ " AND (:address IS NULL OR p.address LIKE CONCAT('%', :address, '%'))"
			+ " AND (:saleStatus IS NULL OR p.saleStatus = :saleStatus)")
	long queryPropertyCount(@Param("address") String address, @Param("saleStatus") Integer saleStatus);

	@Modifying
	@Query("UPDATE PropertyInfoEntity SET saleStatus = :saleStatus WHERE id = :id")
	int updateSaleStatus(@Param("id") Integer id, @Param("saleStatus") Integer saleStatus);
}
