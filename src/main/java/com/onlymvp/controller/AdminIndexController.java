package com.onlymvp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.onlymvp.entity.AdminInfoEntity;
import com.onlymvp.entity.MenuInfoEntity;
import com.onlymvp.service.AdminService;
import com.onlymvp.service.MenuService;
import com.onlymvp.tool.Const;

/**
 * 后台页面跳转控制器
 *
 * 对应原 AdminIndexAction, 各端点返回 EasyUI 管理页面 JSP
 *
 * chengzhi
 *
 */
@Controller
public class AdminIndexController {

	private final MenuService menuService;

	private final AdminService adminService;

	public AdminIndexController(MenuService menuService, AdminService adminService) {
		this.menuService = menuService;
		this.adminService = adminService;
	}

	private ModelAndView loginView() {
		return new ModelAndView("views/admin/login");
	}

	/**
	 * 后台主页(未登录跳登录页)
	 */
	@RequestMapping("/adminIndex!index.action")
	public ModelAndView index(@RequestParam(required = false) Integer adminId, HttpServletRequest request) {

		if (request.getSession().getAttribute(Const.SESSION_ADMIN_BEAN) == null) {
			return loginView();
		}

		ModelAndView mv = new ModelAndView("views/admin/index");
		mv.addObject("menuMap", menuService.findMenuByAdminId(adminId));
		return mv;
	}

	/**
	 * 管理员退出
	 */
	@RequestMapping("/adminIndex!logout.action")
	public ModelAndView logout(HttpSession session) {
		session.setAttribute(Const.SESSION_ADMIN_BEAN, null);
		return loginView();
	}

	/**
	 * 跳转管理员管理页
	 */
	@RequestMapping("/adminIndex!adminList.action")
	public ModelAndView adminList() {
		ModelAndView mv = new ModelAndView("views/admin/admin/adminList");
		mv.addObject("normalMenuInfoList", menuService.findNormalParentMenus());
		return mv;
	}

	/**
	 * 跳转修改管理员页
	 */
	@RequestMapping("/adminIndex!toEditAdmin.action")
	public ModelAndView toEditAdmin(@RequestParam(required = false) Integer adminId) {

		ModelAndView mv = new ModelAndView("views/admin/admin/editAdmin");
		mv.addObject("entity", adminService.queryById(adminId));
		mv.addObject("menuInfoList", menuService.findNormalParentMenus());
		return mv;
	}

	/**
	 * 跳转住户管理页
	 */
	@RequestMapping("/adminIndex!userList.action")
	public ModelAndView userList() {
		return new ModelAndView("views/admin/user/userList");
	}

	/**
	 * 跳转房产管理页
	 */
	@RequestMapping("/adminIndex!propertyList.action")
	public ModelAndView propertyList() {
		return new ModelAndView("views/admin/property/propertyList");
	}

	/**
	 * 跳转收费管理页
	 */
	@RequestMapping("/adminIndex!chargeList.action")
	public ModelAndView chargeList() {
		return new ModelAndView("views/admin/charge/chargeList");
	}

	/**
	 * 跳转报修管理页
	 */
	@RequestMapping("/adminIndex!repairList.action")
	public ModelAndView repairList() {
		return new ModelAndView("views/admin/repair/repairList");
	}

	/**
	 * 跳转投诉管理页
	 */
	@RequestMapping("/adminIndex!complainList.action")
	public ModelAndView complainList() {
		return new ModelAndView("views/admin/complain/complainList");
	}

	/**
	 * 跳转用量管理页
	 */
	@RequestMapping("/adminIndex!dosageList.action")
	public ModelAndView dosageList() {
		return new ModelAndView("views/admin/dosage/dosageList");
	}
}
