package com.onlymvp.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.onlymvp.entity.UserInfoEntity;

/**
 * 业主信息数据访问
 *
 * chengzhi
 *
 */
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Integer> {

	UserInfoEntity findByUserName(String userName);

	UserInfoEntity findByUserNameAndUserPwd(String userName, String userPwd);

	List<UserInfoEntity> findByPropertyId(Integer propertyId);

	/**
	 * 分页模糊查询(业主名/真实姓名/手机号), 与原 SSH 版过滤语义一致
	 */
	@Query("SELECT u FROM UserInfoEntity u WHERE u.delStatus = 0"
			+ " AND (:userName IS NULL OR u.userName LIKE CONCAT('%', :userName, '%'))"
			+ " AND (:realName IS NULL OR u.realName LIKE CONCAT('%', :realName, '%'))"
			+ " AND (:phone IS NULL OR u.phone LIKE CONCAT('%', :phone, '%'))")
	List<UserInfoEntity> queryUserList(@Param("userName") String userName, @Param("realName") String realName,
			@Param("phone") String phone, Pageable pageable);

	@Query("SELECT COUNT(u) FROM UserInfoEntity u WHERE u.delStatus = 0"
			+ " AND (:userName IS NULL OR u.userName LIKE CONCAT('%', :userName, '%'))"
			+ " AND (:realName IS NULL OR u.realName LIKE CONCAT('%', :realName, '%'))"
			+ " AND (:phone IS NULL OR u.phone LIKE CONCAT('%', :phone, '%'))")
	long queryUserCount(@Param("userName") String userName, @Param("realName") String realName,
			@Param("phone") String phone);
}
