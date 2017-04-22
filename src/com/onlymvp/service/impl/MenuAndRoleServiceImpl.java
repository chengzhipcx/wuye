package com.onlymvp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.onlymvp.dao.MenuAndRoleDAO;
import com.onlymvp.entity.MenuAndRoleEntity;
import com.onlymvp.service.MenuAndRoleService;

/**
 * 角色菜单业务实现
 * 
 * chengzhi
 *
 */
@Service
public class MenuAndRoleServiceImpl implements MenuAndRoleService {

	@Resource
	private MenuAndRoleDAO menuAndRoleDAO;

	@Override
	public List<MenuAndRoleEntity> findAllByAdminId(String adminId) throws Exception {
		return menuAndRoleDAO.findAllByAdminId(adminId);
	}

}
