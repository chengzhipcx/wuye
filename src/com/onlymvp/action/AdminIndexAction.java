package com.onlymvp.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onlymvp.entity.AdminInfoEntity;
import com.onlymvp.entity.MenuInfoEntity;
import com.onlymvp.service.AdminInfoService;
import com.onlymvp.service.MenuAndRoleService;
import com.onlymvp.service.MenuInfoService;
import com.onlymvp.tool.Const;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 后台管理主页以及IFrame内嵌页面跳转 提供EasyUI访问接口并返回Json数据
 * 
 * chengzhi
 *
 */
@Action(value = "adminIndex", results = { @Result(name = "success", location = "/views/admin/index.jsp"),
		@Result(name = "login", location = "/views/admin/login.jsp"),
		@Result(name = "adminList", location = "/views/admin/admin/adminList.jsp"),
		@Result(name = "editAdmin", location = "/views/admin/admin/editAdmin.jsp"),
		@Result(name = "propertyList", location = "/views/admin/property/propertyList.jsp"),
		@Result(name = "userList", location = "/views/admin/user/userList.jsp"),
		@Result(name = "chargeList", location = "/views/admin/charge/chargeList.jsp"),
		@Result(name = "repairList", location = "/views/admin/repair/repairList.jsp"),
		@Result(name = "dosageList", location = "/views/admin/dosage/dosageList.jsp"),
		@Result(name = "chargeTypeList", location = "/views/admin/charge/chargeTypeList.jsp"),
		@Result(name = "complainList", location = "/views/admin/complain/complainList.jsp") })
public class AdminIndexAction extends ActionSupport implements ServletRequestAware {

	private static final long serialVersionUID = -1322041600759074761L;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private HttpServletRequest request;
	private String adminId;

	@Resource
	private MenuAndRoleService menuAndRoleService;

	@Resource
	private MenuInfoService menuInfoService;

	@Resource
	private AdminInfoService adminInfoService;

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

	/**
	 * 跳转到修改管理员信息页面
	 * 
	 * @return "editAdmin.jsp"
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public String toEditAdmin() throws NumberFormatException, Exception {

		AdminInfoEntity entity = this.adminInfoService.queryById(Integer.parseInt(adminId));

		List<MenuInfoEntity> menuInfoList = this.menuInfoService.findAllNormalParent();

		this.request.setAttribute("entity", entity);

		this.request.setAttribute("menuInfoList", menuInfoList);// 所有父菜单

		return "editAdmin";
	}

	/**
	 * 
	 * @return 投诉管理主页
	 */
	public String complainList() {

		return "complainList";
	}

	/**
	 * 
	 * @return 报修管理主页
	 */
	public String repairList() {

		return "repairList";
	}

	/**
	 * 
	 * @return 收费管理主页
	 */
	public String chargeList() {

		return "chargeList";
	}

	/**
	 * @return 房产管理主页
	 */
	public String propertyList() {

		return "propertyList";
	}

	/**
	 * @return 业主管理主页
	 */
	public String userList() {

		return "userList";
	}

	/**
	 * @return 用量列表页
	 */
	public String dosageList() {

		return "dosageList";
	}
	
	/**
	 * @return 收费类型页
	 */
	public String chargeTypeList() {

		return "chargeTypeList";
	}
	
	/**
	 * @return 普通管理员管理主页
	 * @throws Exception
	 */
	public String adminList() throws Exception {

		List<MenuInfoEntity> normalMenuInfoList = this.menuInfoService.findAllNormalParent();// 查询所有父菜单,不包括"管理员管理"

		logger.info("普通菜单列表 -> {}", normalMenuInfoList);

		this.request.setAttribute("normalMenuInfoList", normalMenuInfoList);

		return "adminList";
	}

	public String logout() {

		this.request.getSession().setAttribute(Const.SESSION_ADMIN_BEAN, null);

		return LOGIN;
	}

	/**
	 * @return 管理员后台主页
	 * @throws Exception
	 */
	public String index() throws Exception {

		if (this.request.getSession().getAttribute(Const.SESSION_ADMIN_BEAN) == null) {
			this.request.setAttribute(Const.ERROR_INFO_LABEL, "非法访问");
			return LOGIN;
		}

		Map<MenuInfoEntity, List<MenuInfoEntity>> menuMap = this.menuInfoService.findMenuByAdminId(adminId);

		logger.info("菜单列表 -> {}", menuMap);

		this.request.setAttribute("menuMap", menuMap);

		return SUCCESS;
	}

}
