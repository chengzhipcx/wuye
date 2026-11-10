package com.onlymvp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import com.onlymvp.entity.MessageInfoEntity;
import com.onlymvp.entity.PropertyInfoEntity;
import com.onlymvp.service.PropertyService;
import com.onlymvp.tool.Const;

/**
 * 房产管理接口
 *
 * chengzhi
 *
 */
@RestController

public class PropertyController {

	private final Logger logger = LoggerFactory.getLogger(PropertyController.class);

	private final PropertyService propertyService;

	public PropertyController(PropertyService propertyService) {
		this.propertyService = propertyService;
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
	 * EasyUI 分页查询房产列表
	 */
	@RequestMapping("/property!propertyList.action")
	public Map<String, Object> propertyList(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "20") int number, @RequestParam(required = false) String address,
			@RequestParam(required = false) Integer saleStatus) {

		return propertyService.queryPropertyListAndSize(blank(address) ? null : address, saleStatus, page, number);
	}

	/**
	 * 保存房产
	 */
	@RequestMapping("/property!saveProperty.action")
	public MessageInfoEntity saveProperty(@RequestParam(required = false) String address,
			@RequestParam(required = false) Integer area, @RequestParam(required = false) String layout,
			@RequestParam(required = false) Integer saleStatus) {

		try {

			PropertyInfoEntity property = new PropertyInfoEntity();
			property.setAddress(address);
			property.setArea(area);
			property.setLayout(layout);
			property.setSaleStatus(saleStatus == null ? Const.PROPERTY_SALE_STATUS_NO : saleStatus);

			propertyService.saveProperty(property);

			return msg(Const.RETURN_STATUS_OK, "保存成功", null);

		} catch (DataIntegrityViolationException e) {
			logger.error("保存房产失败(地址重复)", e);
			return msg(Const.RETURN_STATUS_FAIL, "地址重复,保存失败", null);
		} catch (Exception e) {
			logger.error("保存房产失败", e);
			return msg(Const.RETURN_STATUS_FAIL, "保存失败", null);
		}
	}

	/**
	 * 更新房产
	 */
	@RequestMapping("/property!updateProperty.action")
	public MessageInfoEntity updateProperty(@RequestParam(required = false) Integer id,
			@RequestParam(required = false) String address, @RequestParam(required = false) Integer area,
			@RequestParam(required = false) String layout, @RequestParam(required = false) Integer saleStatus) {

		try {

			PropertyInfoEntity from = new PropertyInfoEntity();
			from.setId(id);
			from.setAddress(address);
			from.setArea(area);
			from.setLayout(layout);
			from.setSaleStatus(saleStatus);

			propertyService.updateProperty(from);

			return msg(Const.RETURN_STATUS_OK, "更新信息成功", null);

		} catch (Exception e) {
			logger.error("更新房产失败", e);
			return msg(Const.RETURN_STATUS_FAIL, "更新信息失败", null);
		}
	}

	/**
	 * 根据ID查询房产
	 */
	@RequestMapping("/property!queryById.action")
	public MessageInfoEntity queryById(@RequestParam(required = false) Integer id) {
		return msg(Const.RETURN_STATUS_OK, "", propertyService.queryById(id));
	}

	/**
	 * 根据业主用户名查询房产
	 */
	@RequestMapping("/property!queryByUserName.action")
	public MessageInfoEntity queryByUserName(@RequestParam(required = false) String userName) {

		try {
			return msg(Const.RETURN_STATUS_OK, "", propertyService.queryByUserName(userName));
		} catch (Exception e) {
			logger.error("查询业主房产失败", e);
			return msg(Const.RETURN_STATUS_FAIL, "查无该业主房产信息", null);
		}
	}

	/**
	 * 批量删除房产(级联删除业主)
	 */
	@RequestMapping("/property!batchRemove.action")
	public MessageInfoEntity batchRemove(@RequestParam(required = false) String ids) {

		if (blank(ids)) {
			return msg(Const.RETURN_STATUS_FAIL, "请选择", null);
		}

		try {

			for (String id : ids.split(",")) {
				propertyService.deleteProperty(Integer.parseInt(id));
			}

			return msg(Const.RETURN_STATUS_OK, "批量删除成功", null);

		} catch (Exception e) {
			logger.error("批量删除房产失败", e);
			return msg(Const.RETURN_STATUS_FAIL, "批量删除失败", null);
		}
	}
}
