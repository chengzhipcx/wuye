package com.onlymvp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.onlymvp.entity.MenuAndRoleEntity;

/**
 * 管理员菜单角色数据访问
 *
 * chengzhi
 *
 */
public interface MenuAndRoleRepository extends JpaRepository<MenuAndRoleEntity, Integer> {

	List<MenuAndRoleEntity> findByAdminId(Integer adminId);

	@Modifying
	@Query("DELETE FROM MenuAndRoleEntity WHERE adminId = :adminId")
	int deleteByAdminId(@Param("adminId") Integer adminId);
}
