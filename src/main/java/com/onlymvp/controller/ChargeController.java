package com.onlymvp.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlymvp.entity.ChargeInfoEntity;
import com.onlymvp.entity.MessageInfoEntity;
import com.onlymvp.service.ChargeService;
import com.onlymvp.tool.Const;

/**
 * 缴费管理接口
 *
 * chengzhi
 *
 */
@RestController

public class ChargeController {

	private final Logger logger = LoggerFactory.getLogger(ChargeController.class);

	private final ChargeService chargeService;

	public ChargeController(ChargeService chargeService) {
		this.chargeService = chargeService;
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
	 * EasyUI 分页查询缴费列表(三表联查)
	 */
	@RequestMapping("/charge!list.action")
	public Map<String, Object> list(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "20") int number, @RequestParam(required = false) String realName,
			@RequestParam(required = false) String address, @RequestParam(required = false) String phone,
			@RequestParam(required = false) Integer chargeType) {

		return chargeService.queryChargePage(blank(realName) ? null : realName, blank(address) ? null : address,
				blank(phone) ? null : phone, chargeType, page, number);
	}

	/**
	 * 业主查询自己的缴费记录
	 */
	@RequestMapping("/charge!listByUserId.action")
	public List<ChargeInfoEntity> listByUserId(@RequestParam(required = false) Integer userId) {
		return chargeService.queryByUserId(userId);
	}

	/**
	 * 保存缴费信息
	 */
	@RequestMapping("/charge!save.action")
	public MessageInfoEntity save(@RequestParam(required = false) Integer userId,
			@RequestParam(required = false) Integer propertyId, @RequestParam(required = false) Integer chargeType,
			@RequestParam(required = false) Integer payment, @RequestParam(required = false) String remark) {

		try {

			chargeService.saveCharge(userId, propertyId, chargeType, payment, remark);

			return msg(Const.RETURN_STATUS_OK, "缴费成功", null);

		} catch (Exception e) {
			logger.error("保存缴费失败", e);
			return msg(Const.RETURN_STATUS_FAIL, "缴费失败", null);
		}
	}

	/**
	 * 更新缴费信息
	 */
	@RequestMapping("/charge!update.action")
	public MessageInfoEntity update(@RequestParam(required = false) Integer chargeInfoId,
			@RequestParam(required = false) Integer chargeType, @RequestParam(required = false) Integer payment,
			@RequestParam(required = false) String remark) {

		try {

			chargeService.updateCharge(chargeInfoId, chargeType, payment, remark);

			return msg(Const.RETURN_STATUS_OK, "更新成功", null);

		} catch (Exception e) {
			logger.error("更新缴费失败", e);
			return msg(Const.RETURN_STATUS_FAIL, "更新失败", null);
		}
	}

	/**
	 * 业主点击缴费, 状态置为已缴费后回到业主首页
	 */
	@RequestMapping("/charge!updateStatus.action")
	public String updateStatus(@RequestParam(required = false) Integer chargeInfoId) {

		try {
			chargeService.updateChargeStatus(chargeInfoId);
		} catch (Exception e) {
			logger.error("更新缴费状态失败", e);
		}

		return "forward:/views/user/index.jsp";
	}

	/**
	 * 批量逻辑删除缴费记录
	 */
	@RequestMapping("/charge!batchRemove.action")
	public MessageInfoEntity batchRemove(@RequestParam(required = false) String ids) {

		if (blank(ids)) {
			return msg(Const.RETURN_STATUS_FAIL, "请选择", null);
		}

		try {

			List<Integer> idList = java.util.Arrays.stream(ids.split(",")).map(Integer::parseInt)
					.collect(Collectors.toList());

			chargeService.deleteCharges(idList);

			return msg(Const.RETURN_STATUS_OK, "批量删除成功", null);

		} catch (Exception e) {
			logger.error("批量删除缴费失败", e);
			return msg(Const.RETURN_STATUS_FAIL, "批量删除失败", null);
		}
	}
}
