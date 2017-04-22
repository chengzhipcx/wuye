package com.onlymvp.action;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onlymvp.entity.AdminInfoEntity;
import com.onlymvp.entity.MenuAndRoleEntity;
import com.onlymvp.entity.MenuInfoEntity;
import com.onlymvp.entity.MessageInfoEntity;
import com.onlymvp.service.AdminInfoService;
import com.onlymvp.service.MenuAndRoleService;
import com.onlymvp.service.MenuInfoService;
import com.onlymvp.tool.Const;
import com.onlymvp.tool.OutTool;
import com.onlymvp.tool.StringTool;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 管理员管理访问控制器 提供EasyUI访问接口并返回Json数据
 * 
 *
 */
@ParentPackage(value = "json-default")
@Action(value = "admin", results = { @Result(name = "success", type = "json") })
public class AdminAction extends ActionSupport implements ModelDriven<AdminInfoEntity>, ServletRequestAware {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private HttpServletRequest request;
	private MessageInfoEntity messageInfoEntity;// 返回Json数据对象
	private String number;// 每页展示条数
	private String page;// 当前页数
	private String[] menuIds;// 权限ID数组
	private String ids;// 批量删除接收的ID数组,自行截取","
	private AdminInfoEntity entity = new AdminInfoEntity();// 对象模型

	@Resource
	private AdminInfoService adminInfoService;

	@Resource
	private MenuAndRoleService menuAndRoleService;

	@Resource
	private MenuInfoService menuInfoService;

	@Override
	public AdminInfoEntity getModel() {
		return entity;
	}

	// ------------ Get And Set --------------

	public String getNumber() {
		return number;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String[] getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(String[] menuIds) {
		this.menuIds = menuIds;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

	public MessageInfoEntity getMessageInfoEntity() {
		return messageInfoEntity;
	}

	public void setMessageInfoEntity(MessageInfoEntity messageInfoEntity) {
		this.messageInfoEntity = messageInfoEntity;
	}

	// ------------ Get And Set --------------

	/**
	 * Ajax查询角色菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	public String findMenuAndRoleList() throws Exception {

		List<MenuAndRoleEntity> menuAndRoleList = this.menuAndRoleService.findAllByAdminId(entity.getId() + "");

		logger.info("Ajax查询角色菜单 -> {}", menuAndRoleList);

		OutTool.writeJsonArray(menuAndRoleList);

		return null;
	}

	/**
	 * Ajax查询属于管理员的父菜单列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryAdminParentMenu() throws Exception {

		List<MenuAndRoleEntity> menuAndRoleList = this.menuAndRoleService.findAllByAdminId(entity.getId() + "");

		logger.info("Ajax查询角色菜单权限 -> {}", menuAndRoleList);

		List<MenuInfoEntity> menuInfoEntities = new LinkedList<MenuInfoEntity>();

		if (!StringTool.isObjectNull(menuAndRoleList)) {

			for (MenuAndRoleEntity menu : menuAndRoleList) {

				MenuInfoEntity mie = this.menuInfoService.findParentById(menu.getMenuId() + "");

				menuInfoEntities.add(mie);

			}
		}

		logger.info("Ajax查询角色菜单列表 -> {}", menuInfoEntities);

		OutTool.writeJsonArray(menuInfoEntities);

		return null;
	}

	/**
	 * 管理员信息更新,密码和菜单权限
	 * 
	 * @return
	 */
	public String updateAdmin() {

		messageInfoEntity = new MessageInfoEntity();

		logger.info("修改管理员信息 -> {}, 权限列表 -> {}", entity, Arrays.asList(menuIds));

		try {

			this.adminInfoService.updateAdminInfoAndMenuPower(entity, menuIds);

			messageInfoEntity.setStatus(Const.RETURN_STATUS_OK);
			messageInfoEntity.setDesc("更新成功");

		} catch (Exception e) {
			e.printStackTrace();
			messageInfoEntity.setStatus(Const.RETURN_STATUS_FAIL);
			messageInfoEntity.setDesc("更新失败");
		}

		OutTool.writeJsonObject(messageInfoEntity);

		return null;
	}

	/**
	 * 批量删除普通管理员
	 * 
	 * @return
	 * @throws Exception
	 */
	public String batchRemoveAdmin() {

		logger.info("批量删除的ID数组 -> {}", ids);

		messageInfoEntity = new MessageInfoEntity();

		if (StringTool.isNull(ids)) {// 如果传递过来批量删除的id字符串为空

			messageInfoEntity.setStatus(Const.RETURN_STATUS_FAIL);
			messageInfoEntity.setDesc("请选择");

			OutTool.writeJsonObject(messageInfoEntity);

			return null;
		}

		try {

			String[] idArr = ids.split(",");// ","拆分字符串,转为字符串数组

			for (String id : idArr) {// 遍历ID删除

				this.adminInfoService.batchRemoveAdminAndRole(Integer.parseInt(id));

			}

			messageInfoEntity.setStatus(Const.RETURN_STATUS_OK);
			messageInfoEntity.setDesc("删除成功");

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			messageInfoEntity.setStatus(Const.RETURN_STATUS_FAIL);
			messageInfoEntity.setDesc("删除失败");
		}

		OutTool.writeJsonObject(messageInfoEntity);

		return null;
	}

	/**
	 * Ajax保存普通管理员Json接口
	 * 
	 * @return
	 */
	public String saveAdmin() {

		messageInfoEntity = new MessageInfoEntity();

		logger.info("权限ID -> {}", Arrays.asList(menuIds));

		try {
			this.adminInfoService.saveAdminInfoAndMenuPower(entity, menuIds);
			messageInfoEntity.setStatus(Const.RETURN_STATUS_OK);
			messageInfoEntity.setDesc("保存成功");
		} catch (ConstraintViolationException e) {
			logger.error(e.getMessage());
			messageInfoEntity.setStatus(Const.RETURN_STATUS_FAIL);
			messageInfoEntity.setDesc("管理员账号重复,保存失败");
		} catch (Exception e) {
			logger.error(e.getMessage());
			messageInfoEntity.setStatus(Const.RETURN_STATUS_FAIL);
			messageInfoEntity.setDesc("服务器错误");
		} // 保存到数据库

		logger.info("保存信息对象数据 -> {}", messageInfoEntity);

		OutTool.writeJsonObject(messageInfoEntity);

		return null;
	}

	/**
	 * EasyUI Ajax获取管理员列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String adminList() throws Exception {

		// 每页显示多少条
		int intNum = Integer.parseInt(StringTool.isNull(number) ? "1" : number);
		// 当前页数
		int intPage = Integer.parseInt(StringTool.isNull(page) ? "1" : page);
		// 开始索引号
		int start = (intPage - 1) * intNum;

		Map<String, Object> resMap = this.adminInfoService.pageQueryNormalAdminListAndSize(start, intNum);

		logger.info("Easy UI Ajax分页查询结果 -> {}", resMap);

		// JSONObject jsonObj = JSONObject.fromObject(resMap);

		OutTool.writeJsonObject(resMap);

		return null;
	}

	/**
	 * 管理员登录
	 * 
	 * @return
	 */
	public String login() {
		messageInfoEntity = new MessageInfoEntity();
		try {
			AdminInfoEntity adminInfoEntity = this.adminInfoService.queryByNameAndPwd(entity.getUserName(),
					entity.getUserPwd());
			logger.info("登录的管理员 -> {}", adminInfoEntity);
			if (adminInfoEntity != null) {
				this.request.getSession().setAttribute(Const.SESSION_ADMIN_BEAN, adminInfoEntity);// 把登录信息添加到session
				messageInfoEntity.setStatus(Const.RETURN_STATUS_OK);
				messageInfoEntity.setDetail(adminInfoEntity);
			} else {
				messageInfoEntity.setStatus(Const.RETURN_STATUS_FAIL);
				messageInfoEntity.setDesc("用户名/密码错误");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

}
