package com.onlymvp.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.onlymvp.entity.MenuAndRoleEntity;
import com.onlymvp.entity.MenuInfoEntity;
import com.onlymvp.repository.MenuAndRoleRepository;
import com.onlymvp.repository.MenuInfoRepository;

/**
 * 菜单业务
 *
 * chengzhi
 *
 */
@Service
public class MenuService {

	private final MenuInfoRepository menuInfoRepository;

	private final MenuAndRoleRepository menuAndRoleRepository;

	public MenuService(MenuInfoRepository menuInfoRepository, MenuAndRoleRepository menuAndRoleRepository) {
		this.menuInfoRepository = menuInfoRepository;
		this.menuAndRoleRepository = menuAndRoleRepository;
	}

	/**
	 * 管理员可访问的父菜单列表
	 */
	public List<MenuAndRoleEntity> findMenuAndRoleByAdminId(Integer adminId) {
		return menuAndRoleRepository.findByAdminId(adminId);
	}

	/**
	 * 根据菜单ID查询父菜单
	 */
	public MenuInfoEntity findParentById(Integer menuId) {
		return menuInfoRepository.findById(menuId).orElse(null);
	}

	/**
	 * 父菜单下的子菜单
	 */
	public List<MenuInfoEntity> findChildByParentId(Integer parentId) {
		return menuInfoRepository.findChildMenus(parentId);
	}

	/**
	 * 普通管理员可分配的父菜单(排除超管专属"管理员管理")
	 */
	public List<MenuInfoEntity> findNormalParentMenus() {
		return menuInfoRepository.findNormalParentMenus();
	}

	/**
	 * 构建后台左侧菜单树(父菜单 -> 子菜单列表), 与原 SSH 版菜单结构一致
	 */
	public Map<MenuInfoEntity, List<MenuInfoEntity>> findMenuByAdminId(Integer adminId) {

		List<MenuAndRoleEntity> roleList = menuAndRoleRepository.findByAdminId(adminId);

		List<MenuInfoEntity> parents = new ArrayList<>();

		for (MenuAndRoleEntity role : roleList) {
			MenuInfoEntity parent = menuInfoRepository.findById(role.getMenuId()).orElse(null);
			if (parent != null && !parents.contains(parent)) {
				parents.add(parent);
			}
		}

		parents.sort(MenuInfoEntity::compareTo);

		Map<MenuInfoEntity, List<MenuInfoEntity>> menuMap = new LinkedHashMap<>();

		for (MenuInfoEntity parent : parents) {
			menuMap.put(parent, menuInfoRepository.findChildMenus(parent.getId()));
		}

		return menuMap;
	}
}
