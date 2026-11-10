package com.onlymvp.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlymvp.entity.AdminInfoEntity;
import com.onlymvp.entity.MenuAndRoleEntity;
import com.onlymvp.repository.AdminInfoRepository;
import com.onlymvp.repository.MenuAndRoleRepository;
import com.onlymvp.tool.Const;
import com.onlymvp.tool.DateTimeTool;

/**
 * 管理员业务
 *
 * chengzhi
 *
 */
@Service
public class AdminService {

	private final AdminInfoRepository adminInfoRepository;

	private final MenuAndRoleRepository menuAndRoleRepository;

	public AdminService(AdminInfoRepository adminInfoRepository, MenuAndRoleRepository menuAndRoleRepository) {
		this.adminInfoRepository = adminInfoRepository;
		this.menuAndRoleRepository = menuAndRoleRepository;
	}

	public AdminInfoEntity queryByUserNameAndUserPwd(String userName, String userPwd) {
		return adminInfoRepository.findByUserNameAndUserPwd(userName, userPwd);
	}

	public AdminInfoEntity queryById(Integer id) {
		return adminInfoRepository.findById(id).orElse(null);
	}

	/**
	 * 分页查询普通管理员列表
	 */
	public Map<String, Object> queryNormalAdminListAndSize(int page, int number) {

		Pageable pageable = PageRequest.of(page - 1, number);

		List<AdminInfoEntity> list = adminInfoRepository.queryNormalAdminList(pageable);

		long count = adminInfoRepository.queryNormalAdminCount();

		Map<String, Object> resMap = new HashMap<>();
		resMap.put("rows", list);
		resMap.put("total", count);
		return resMap;
	}

	/**
	 * 保存普通管理员及其菜单权限
	 */
	@Transactional
	public void saveAdminAndMenuPower(AdminInfoEntity admin, String[] menuIds) {

		admin.setUserLevel(Const.ADMIN_USER_LEVEL_NORMAL);
		admin.setUserStatus(Const.SYSTEM_STATUS_ON);
		admin.setCreateTime(DateTimeTool.getDateTime(new Date()));

		AdminInfoEntity saved = adminInfoRepository.saveAndFlush(admin);

		insertMenuPowers(saved.getId().intValue(), menuIds);
	}

	/**
	 * 更新管理员信息(密码)及其菜单权限
	 */
	@Transactional
	public void updateAdminAndMenuPower(AdminInfoEntity from, String[] menuIds) {

		AdminInfoEntity origin = adminInfoRepository.findById(from.getId()).orElse(null);

		if (origin == null) {
			throw new IllegalStateException("管理员不存在");
		}

		if (from.getUserPwd() != null && !from.getUserPwd().trim().isEmpty()) {
			origin.setUserPwd(from.getUserPwd());
		}

		adminInfoRepository.saveAndFlush(origin);

		menuAndRoleRepository.deleteByAdminId(from.getId());

		insertMenuPowers(from.getId(), menuIds);
	}

	/**
	 * 批量删除普通管理员及其菜单权限
	 */
	@Transactional
	public void removeAdminWithRole(Integer id) {

		adminInfoRepository.deleteById(id);

		menuAndRoleRepository.deleteByAdminId(id);
	}

	private void insertMenuPowers(int adminId, String[] menuIds) {

		if (menuIds == null) {
			return;
		}

		for (String menuId : menuIds) {
			MenuAndRoleEntity role = new MenuAndRoleEntity();
			role.setAdminId(adminId);
			role.setMenuId(Integer.parseInt(menuId));
			menuAndRoleRepository.save(role);
		}
	}
}
