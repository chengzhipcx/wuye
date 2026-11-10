package com.onlymvp.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlymvp.entity.DosageEntity;
import com.onlymvp.entity.MessageInfoEntity;
import com.onlymvp.service.DosageService;
import com.onlymvp.tool.Const;

/**
 * 住户用量管理接口
 *
 * chengzhi
 *
 */
@RestController

public class DosageController {

	private final Logger logger = LoggerFactory.getLogger(DosageController.class);

	private final DosageService dosageService;

	public DosageController(DosageService dosageService) {
		this.dosageService = dosageService;
	}

	private static MessageInfoEntity msg(String status, String desc, Object detail) {
		MessageInfoEntity m = new MessageInfoEntity();
		m.setStatus(status);
		m.setDesc(desc);
		m.setDetail(detail);
		return m;
	}

	/**
	 * EasyUI 分页查询用量列表(后台管理)
	 */
	@RequestMapping("/dosage!list.action")
	public Map<String, Object> list(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "20") int number) {
		return dosageService.queryDosagePage(page, number);
	}

	/**
	 * 业主前端查询自己的历史用量
	 */
	@RequestMapping("/dosage!listByUserId.action")
	public List<DosageEntity> listByUserId(@RequestParam(value = "user_id", required = false) String userId) {

		try {
			return dosageService.queryByUserId(Integer.parseInt(userId));
		} catch (Exception e) {
			logger.error("查询业主用量失败", e);
			return java.util.Collections.emptyList();
		}
	}

	/**
	 * 保存用量信息
	 */
	@RequestMapping("/dosage!save.action")
	public MessageInfoEntity save(@RequestParam(required = false) Integer userId,
			@RequestParam(required = false) String type, @RequestParam(required = false) Double count,
			@RequestParam(required = false) String date) {

		if (userId == null || type == null || type.trim().isEmpty() || count == null || date == null
				|| date.trim().isEmpty()) {
			return msg(Const.RETURN_STATUS_FAIL, "请填写完整用量信息", null);
		}

		try {

			DosageEntity dosage = new DosageEntity();
			dosage.setUserId(userId);
			dosage.setType(type);
			dosage.setCount(count);
			dosage.setDate(date);

			dosageService.saveDosage(dosage);

			return msg(Const.RETURN_STATUS_OK, "保存成功", null);

		} catch (Exception e) {
			logger.error("保存用量失败", e);
			return msg(Const.RETURN_STATUS_FAIL, "保存失败", null);
		}
	}

	/**
	 * 批量删除用量信息
	 */
	@RequestMapping("/dosage!batchRemove.action")
	public MessageInfoEntity batchRemove(@RequestParam(required = false) String ids) {

		if (ids == null || ids.trim().isEmpty()) {
			return msg(Const.RETURN_STATUS_FAIL, "请选择", null);
		}

		try {

			List<Integer> idList = java.util.Arrays.stream(ids.split(",")).map(Integer::parseInt)
					.collect(Collectors.toList());

			idList.forEach(dosageService::deleteById);

			return msg(Const.RETURN_STATUS_OK, "删除成功", null);

		} catch (Exception e) {
			logger.error("批量删除用量失败", e);
			return msg(Const.RETURN_STATUS_FAIL, "删除失败", null);
		}
	}
}
