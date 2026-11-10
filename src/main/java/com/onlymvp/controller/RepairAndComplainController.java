package com.onlymvp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlymvp.entity.MessageInfoEntity;
import com.onlymvp.entity.UserInfoEntity;
import com.onlymvp.service.RepairService;
import com.onlymvp.tool.Const;

/**
 * 报修/投诉管理接口
 *
 * chengzhi
 *
 */
@RestController

public class RepairAndComplainController {

	private final Logger logger = LoggerFactory.getLogger(RepairAndComplainController.class);

	private final RepairService repairService;

	public RepairAndComplainController(RepairService repairService) {
		this.repairService = repairService;
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
	 * EasyUI 分页查询报修/投诉列表
	 */
	@RequestMapping("/repairAndComplain!list.action")
	public Map<String, Object> list(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "20") int number, @RequestParam Integer infoType,
			@RequestParam(required = false) String realName, @RequestParam(required = false) String address,
			@RequestParam(required = false) String phone, @RequestParam(required = false) Integer solveStatus) {

		return repairService.queryRepairPage(infoType, blank(realName) ? null : realName,
				blank(address) ? null : address, blank(phone) ? null : phone, solveStatus, page, number);
	}

	/**
	 * 业主提交报修/投诉(业主身份取自会话)
	 */
	@RequestMapping("/repairAndComplain!save.action")
	public MessageInfoEntity save(@RequestParam(required = false) String content,
			@RequestParam(required = false) Integer infoType, HttpServletRequest request) {

		try {

			HttpSession session = request.getSession();

			UserInfoEntity user = (UserInfoEntity) session.getAttribute(Const.SESSION_USER_BEAN);

			if (user == null) {
				return msg(Const.RETURN_STATUS_OK, "验证用户出错", null);
			}

			repairService.saveRepair(user.getId(), user.getPropertyId(), content, infoType);

			return msg(Const.RETURN_STATUS_OK, "提交成功！", null);

		} catch (Exception e) {
			logger.error("提交报修/投诉失败", e);
			return msg(Const.RETURN_STATUS_FAIL, "提交失败", null);
		}
	}

	/**
	 * 处理报修/投诉(记录经办人, 置为已解决)
	 */
	@RequestMapping("/repairAndComplain!update.action")
	public MessageInfoEntity update(@RequestParam(required = false) Integer repairAndComplainInfoId,
			@RequestParam(required = false) String dealUser) {

		try {

			repairService.updateDealInfo(repairAndComplainInfoId, dealUser);

			return msg(Const.RETURN_STATUS_OK, "更新成功", null);

		} catch (Exception e) {
			logger.error("更新报修/投诉失败", e);
			return msg(Const.RETURN_STATUS_FAIL, "更新失败", null);
		}
	}
}
