package com.onlymvp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlymvp.entity.AdminInfoEntity;
import com.onlymvp.entity.MenuAndRoleEntity;
import com.onlymvp.entity.MenuInfoEntity;
import com.onlymvp.entity.MessageInfoEntity;
import com.onlymvp.service.AdminService;
import com.onlymvp.service.MenuService;
import com.onlymvp.tool.Const;

/**
 * 管理员管理接口
 *
 * 注意: 原 Struts 版 admin!login 通过 @Result(type=json) 序列化整个 Action,
 * 前端读取 obj.messageInfoEntity.status, 因此登录接口返回
 * {"messageInfoEntity":{...}} 外层包装, 其余接口与原 OutTool 输出一致。
 *
 * chengzhi
 *
 */
@RestController

public class AdminController {

	private final Logger logger = LoggerFactory.getLogger(AdminController.class);

	private final AdminService adminService;

	private final MenuService menuService;

	public AdminController(AdminService adminService, MenuService menuService) {
		this.adminService = adminService;
		this.menuService = menuService;
	}

	private static MessageInfoEntity msg(String status, String desc, Object detail) {
		MessageInfoEntity m = new MessageInfoEntity();
		m.setStatus(status);
		m.setDesc(desc);
		m.setDetail(detail);
		return m;
	}

	private static boolean blank(String s) {
		return s == null || s.trim().isEmpty();
	}

	/**
	 * 管理员登录
	 */
	@RequestMapping("/admin!login.action")
	public Map<String, Object> login(@RequestParam(required = false) String userName,
			@RequestParam(required = false) String userPwd, HttpServletRequest request) {

		Map<String, Object> res = new HashMap<>();

		try {

			AdminInfoEntity admin = adminService.queryByUserNameAndUserPwd(userName, userPwd);

			MessageInfoEntity msg = new MessageInfoEntity();

			if (admin != null) {
				request.getSession().setAttribute(Const.SESSION_ADMIN_BEAN, admin);
				msg.setStatus(Const.RETURN_STATUS_OK);
				msg.setDetail(admin);
			} else {
				msg.setStatus(Const.RETURN_STATUS_FAIL);
				msg.setDesc("用户名/密码错误");
			}

			res.put("messageInfoEntity", msg);
			return res;

		} catch (Exception e) {
			logger.error("管理员登录失败", e);
			MessageInfoEntity msg = new MessageInfoEntity();
			msg.setStatus(Const.RETURN_STATUS_FAIL);
			msg.setDesc("服务器错误");
			res.put("messageInfoEntity", msg);
			return res;
		}
	}

	/**
	 * EasyUI 分页查询普通管理员列表
	 */
	@RequestMapping("/admin!adminList.action")
	public Map<String, Object> adminList(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "20") int number) {
		return adminService.queryNormalAdminListAndSize(page, number);
	}

	/**
	 * 保存普通管理员及其菜单权限
	 */
	@RequestMapping("/admin!saveAdmin.action")
	public MessageInfoEntity saveAdmin(@RequestParam(required = false) String userName,
			@RequestParam(required = false) String userPwd, @RequestParam(required = false) String[] menuIds) {

		try {

			AdminInfoEntity admin = new AdminInfoEntity();
			admin.setUserName(userName);
			admin.setUserPwd(userPwd);

			adminService.saveAdminAndMenuPower(admin, menuIds);

			return msg(Const.RETURN_STATUS_OK, "保存成功", null);

		} catch (DataIntegrityViolationException e) {
			logger.error("保存管理员失败(唯一约束)", e);
			return msg(Const.RETURN_STATUS_FAIL, "管理员账号重复,保存失败", null);
		} catch (Exception e) {
			logger.error("保存管理员失败", e);
			return msg(Const.RETURN_STATUS_FAIL, "服务器错误", null);
		}
	}

	/**
	 * 更新管理员信息与菜单权限
	 */
	@RequestMapping("/admin!updateAdmin.action")
	public MessageInfoEntity updateAdmin(@RequestParam(required = false) Integer id,
			@RequestParam(required = false) String userPwd, @RequestParam(required = false) String[] menuIds) {

		try {

			AdminInfoEntity from = new AdminInfoEntity();
			from.setId(id);
			from.setUserPwd(userPwd);

			adminService.updateAdminAndMenuPower(from, menuIds);

			return msg(Const.RETURN_STATUS_OK, "更新成功", null);

		} catch (Exception e) {
			logger.error("更新管理员失败", e);
			return msg(Const.RETURN_STATUS_FAIL, "更新失败", null);
		}
	}

	/**
	 * 批量删除普通管理员
	 */
	@RequestMapping("/admin!batchRemoveAdmin.action")
	public MessageInfoEntity batchRemoveAdmin(@RequestParam(required = false) String ids) {

		if (blank(ids)) {
			return msg(Const.RETURN_STATUS_FAIL, "请选择", null);
		}

		try {

			for (String id : ids.split(",")) {
				adminService.removeAdminWithRole(Integer.parseInt(id));
			}

			return msg(Const.RETURN_STATUS_OK, "删除成功", null);

		} catch (Exception e) {
			logger.error("批量删除管理员失败", e);
			return msg(Const.RETURN_STATUS_FAIL, "删除失败", null);
		}
	}

	/**
	 * Ajax查询管理员角色菜单
	 */
	@RequestMapping("/admin!findMenuAndRoleList.action")
	public List<MenuAndRoleEntity> findMenuAndRoleList(@RequestParam(required = false) Integer id) {
		return menuService.findMenuAndRoleByAdminId(id);
	}

	/**
	 * Ajax查询管理员拥有的父菜单列表
	 */
	@RequestMapping("/admin!queryAdminParentMenu.action")
	public List<MenuInfoEntity> queryAdminParentMenu(@RequestParam(required = false) Integer id) {

		List<MenuInfoEntity> parents = new ArrayList<>();

		for (MenuAndRoleEntity role : menuService.findMenuAndRoleByAdminId(id)) {

			MenuInfoEntity parent = menuService.findParentById(role.getMenuId());

			if (parent != null && !parents.contains(parent)) {
				parents.add(parent);
			}
		}

		return parents;
	}
}
