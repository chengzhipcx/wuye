package com.onlymvp.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.onlymvp.entity.AdminInfoEntity;

/**
 * 管理员信息数据访问
 *
 * chengzhi
 *
 */
public interface AdminInfoRepository extends JpaRepository<AdminInfoEntity, Integer> {

	AdminInfoEntity findByUserNameAndUserPwd(String userName, String userPwd);

	AdminInfoEntity findByUserName(String userName);

	/**
	 * 分页查询普通管理员(排除超管, 只取启用状态)
	 */
	@Query("SELECT a FROM AdminInfoEntity a WHERE a.userLevel = 1 AND a.userStatus = 1 ORDER BY a.id ASC")
	List<AdminInfoEntity> queryNormalAdminList(Pageable pageable);

	@Query("SELECT COUNT(a) FROM AdminInfoEntity a WHERE a.userLevel = 1 AND a.userStatus = 1")
	long queryNormalAdminCount();
}
