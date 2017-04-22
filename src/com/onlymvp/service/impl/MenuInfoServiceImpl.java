package com.onlymvp.service.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.onlymvp.dao.MenuAndRoleDAO;
import com.onlymvp.dao.MenuInfoDAO;
import com.onlymvp.entity.MenuAndRoleEntity;
import com.onlymvp.entity.MenuInfoEntity;
import com.onlymvp.service.MenuInfoService;

/**
 * 菜单信息业务实现
 * 
 * chengzhi
 *
 */
@Service
public class MenuInfoServiceImpl implements MenuInfoService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private MenuInfoDAO menuInfoDAO;

	@Resource
	private MenuAndRoleDAO menuAndRoleDAO;

	@Override
	public MenuInfoEntity findParentById(String id) throws Exception {
		return this.menuInfoDAO.findParentById(id);
	}

	@Override
	public List<MenuInfoEntity> findClildByParentId(String parentId) throws Exception {
		return this.menuInfoDAO.findChildByParentId(parentId);
	}

	@Override
	public Map<MenuInfoEntity, List<MenuInfoEntity>> findMenuByAdminId(String adminId) throws Exception {

		List<MenuAndRoleEntity> menuAndRoleEntities = this.menuAndRoleDAO.findAllByAdminId(adminId);
		logger.info("菜单角色列表 -> {}", menuAndRoleEntities);

		Map<MenuInfoEntity, List<MenuInfoEntity>> menuMap = new TreeMap<MenuInfoEntity, List<MenuInfoEntity>>();// 有序Map

		if (menuAndRoleEntities != null) {
			for (MenuAndRoleEntity menuAndRole : menuAndRoleEntities) {
				MenuInfoEntity menuInfoEntity = this.menuInfoDAO.findParentById(menuAndRole.getMenuId() + "");// 查询父菜单
				List<MenuInfoEntity> childMenuInfoEntities = this.menuInfoDAO
						.findChildByParentId(menuInfoEntity.getId() + "");// 查询当前父类的子类菜单
				menuMap.put(menuInfoEntity, childMenuInfoEntities);// 父菜单与子菜单列表

			}
		}
		logger.info("父菜单与子菜单列表 -> {}", menuMap);
		return menuMap;
	}

	@Override
	public List<MenuInfoEntity> findAllNormalParent() throws Exception {

		return this.menuInfoDAO.findAllNormalParent();
	}

}
