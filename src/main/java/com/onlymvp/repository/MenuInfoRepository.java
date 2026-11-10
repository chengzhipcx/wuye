package com.onlymvp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.onlymvp.entity.MenuInfoEntity;

/**
 * 菜单信息数据访问
 *
 * chengzhi
 *
 */
public interface MenuInfoRepository extends JpaRepository<MenuInfoEntity, Integer> {

	@Query("SELECT m FROM MenuInfoEntity m WHERE m.parentId = :parentId ORDER BY m.orderBy ASC")
	List<MenuInfoEntity> findChildMenus(@Param("parentId") Integer parentId);

	/**
	 * 普通管理员可用父菜单(排除超管专属的"管理员管理")
	 */
	@Query("SELECT m FROM MenuInfoEntity m WHERE m.parentId = 0 AND m.delStatus = 0 AND m.id <> 1 ORDER BY m.orderBy ASC")
	List<MenuInfoEntity> findNormalParentMenus();
}
