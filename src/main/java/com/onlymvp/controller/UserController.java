package com.onlymvp.controller;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlymvp.entity.MessageInfoEntity;
import com.onlymvp.entity.UserInfoEntity;
import com.onlymvp.service.MailService;
import com.onlymvp.service.UserService;
import com.onlymvp.tool.Const;

/**
 * 业主前端/后台住户管理接口
 *
 * 沿用原 Struts DMI 风格 URL, 前端页面无需改动
 *
 * chengzhi
 *
 */
@RestController

public class UserController {

	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	private final UserService userService;

	private final MailService mailService;

	public UserController(UserService userService, MailService mailService) {
		this.userService = userService;
		this.mailService = mailService;
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
	 * 业主登录
	 */
	@RequestMapping("/user!login.action")
	public MessageInfoEntity login(@RequestParam(required = false) String userName,
			@RequestParam(required = false) String userPwd, HttpServletRequest request) {

		try {

			UserInfoEntity user = userService.queryByUserNameAndUserPwd(userName, userPwd);

			if (user != null) {
				request.getSession().setAttribute(Const.SESSION_USER_BEAN, user);
				return msg(Const.RETURN_STATUS_OK, "", user);
			}

			return msg(Const.RETURN_STATUS_FAIL, "用户名/密码错误", null);

		} catch (Exception e) {
			logger.error("业主登录失败", e);
			return msg(Const.RETURN_STATUS_FAIL, "服务器错误", null);
		}
	}

	/**
	 * 业主退出
	 */
	@RequestMapping("/user!logout.action")
	public String logout(HttpSession session) {
		session.setAttribute(Const.SESSION_USER_BEAN, null);
		return "forward:/index.jsp";
	}

	/**
	 * EasyUI 分页查询业主列表
	 */
	@RequestMapping("/user!list.action")
	public Map<String, Object> list(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "20") int number, @RequestParam(required = false) String userName,
			@RequestParam(required = false) String realName, @RequestParam(required = false) String phone) {

		return userService.queryUserListAndSize(blank(userName) ? null : userName, blank(realName) ? null : realName,
				blank(phone) ? null : phone, page, number);
	}

	/**
	 * 保存业主(业主购买)
	 */
	@RequestMapping("/user!save.action")
	public MessageInfoEntity save(@RequestParam(required = false) String userName,
			@RequestParam(required = false) String userPwd, @RequestParam(required = false) String realName,
			@RequestParam(required = false) String phone, @RequestParam(required = false) String idCard,
			@RequestParam(required = false) Integer propertyId, @RequestParam(required = false) String qq,
			@RequestParam(required = false) String email) {

		try {

			UserInfoEntity user = new UserInfoEntity();

			user.setUserName(userName);
			user.setUserPwd(userPwd);
			user.setRealName(realName);
			user.setPhone(phone);
			user.setIdCard(idCard);
			user.setPropertyId(propertyId);
			user.setQq(qq);
			user.setEmail(email);

			userService.saveUserAndUpdateProperty(user);

			return msg(Const.RETURN_STATUS_OK, "保存成功", null);

		} catch (DataIntegrityViolationException e) {
			logger.error("保存业主失败(唯一约束)", e);
			return msg(Const.RETURN_STATUS_FAIL, "业主用户名重复／房产已售", null);
		} catch (Exception e) {
			logger.error("保存业主失败", e);
			return msg(Const.RETURN_STATUS_FAIL, "保存失败", null);
		}
	}

	/**
	 * 更新业主信息
	 */
	@RequestMapping("/user!update.action")
	public MessageInfoEntity update(@RequestParam(required = false) Integer id,
			@RequestParam(required = false) String email, @RequestParam(required = false) String idCard,
			@RequestParam(required = false) String phone, @RequestParam(required = false) String qq,
			@RequestParam(required = false) String realName, @RequestParam(required = false) String userPwd) {

		try {

			UserInfoEntity origin = userService.queryById(id);

			if (origin == null) {
				return msg(Const.RETURN_STATUS_FAIL, "更新失败", null);
			}

			UserInfoEntity from = new UserInfoEntity();
			from.setEmail(email);
			from.setIdCard(idCard);
			from.setPhone(phone);
			from.setQq(qq);
			from.setRealName(realName);
			from.setUserPwd(userPwd);

			userService.updateUser(origin, from);

			return msg(Const.RETURN_STATUS_OK, "更新成功", null);

		} catch (Exception e) {
			logger.error("更新业主失败", e);
			return msg(Const.RETURN_STATUS_FAIL, "更新失败", null);
		}
	}

	/**
	 * 批量删除业主(恢复房产为未出售)
	 */
	@RequestMapping("/user!batchRemove.action")
	public MessageInfoEntity batchRemove(@RequestParam(required = false) String ids) {

		if (blank(ids)) {
			return msg(Const.RETURN_STATUS_FAIL, "请选择", null);
		}

		try {

			for (String id : ids.split(",")) {
				userService.deleteUser(Integer.parseInt(id));
			}

			return msg(Const.RETURN_STATUS_OK, "删除成功", null);

		} catch (Exception e) {
			logger.error("批量删除业主失败", e);
			return msg(Const.RETURN_STATUS_FAIL, "删除失败", null);
		}
	}

	/**
	 * 根据用户名查询业主
	 */
	@RequestMapping("/user!queryByUserName.action")
	public MessageInfoEntity queryByUserName(@RequestParam(required = false) String userName) {

		try {
			return msg(Const.RETURN_STATUS_OK, "", userService.queryByUserName(userName));
		} catch (Exception e) {
			logger.error("查询业主失败", e);
			return msg(Const.RETURN_STATUS_FAIL, "查询失败", null);
		}
	}

	/**
	 * 缴费通知邮件
	 */
	@RequestMapping("/user!sendMail.action")
	public MessageInfoEntity sendMail(@RequestParam(required = false) String tomail,
			@RequestParam(required = false) String mailcontent) {

		mailService.sendSimpleEmail(tomail, "缴费通知", mailcontent);

		return msg(Const.RETURN_STATUS_OK, "", null);
	}
}
