package com.onlymvp.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlymvp.entity.PropertyInfoEntity;
import com.onlymvp.entity.UserInfoEntity;
import com.onlymvp.repository.PropertyInfoRepository;
import com.onlymvp.repository.UserInfoRepository;
import com.onlymvp.tool.Const;
import com.onlymvp.tool.DateTimeTool;

/**
 * 房产业务
 *
 * chengzhi
 *
 */
@Service
public class PropertyService {

	private final PropertyInfoRepository propertyInfoRepository;

	private final UserInfoRepository userInfoRepository;

	private final UserService userService;

	public PropertyService(PropertyInfoRepository propertyInfoRepository, UserInfoRepository userInfoRepository,
			UserService userService) {
		this.propertyInfoRepository = propertyInfoRepository;
		this.userInfoRepository = userInfoRepository;
		this.userService = userService;
	}

	public PropertyInfoEntity queryById(Integer id) {
		return propertyInfoRepository.findById(id).orElse(null);
	}

	/**
	 * 根据业主用户名查询其房产
	 */
	public PropertyInfoEntity queryByUserName(String userName) {

		UserInfoEntity user = userInfoRepository.findByUserName(userName);

		if (user == null) {
			throw new IllegalStateException("查无该业主房产信息");
		}

		PropertyInfoEntity property = propertyInfoRepository.findById(user.getPropertyId()).orElse(null);

		if (property == null) {
			throw new IllegalStateException("查无该业主房产信息");
		}

		return property;
	}

	/**
	 * 分页模糊查询房产列表
	 */
	public Map<String, Object> queryPropertyListAndSize(String address, Integer saleStatus, int page, int number) {

		Pageable pageable = PageRequest.of(page - 1, number);

		List<PropertyInfoEntity> list = propertyInfoRepository.queryPropertyList(address, saleStatus, pageable);

		long count = propertyInfoRepository.queryPropertyCount(address, saleStatus);

		Map<String, Object> resMap = new HashMap<>();
		resMap.put("rows", list);
		resMap.put("total", count);
		return resMap;
	}

	@Transactional
	public void saveProperty(PropertyInfoEntity property) {

		property.setDelStatus(Const.SYSTEM_DEL_STATUS_NO);
		property.setCreateTime(DateTimeTool.getDateTime(new Date()));

		propertyInfoRepository.saveAndFlush(property);
	}

	@Transactional
	public void updateProperty(PropertyInfoEntity from) {

		PropertyInfoEntity origin = propertyInfoRepository.findById(from.getId()).orElse(null);

		if (origin == null) {
			throw new IllegalStateException("房产不存在");
		}

		origin.setAddress(from.getAddress());
		origin.setArea(from.getArea());
		origin.setLayout(from.getLayout());
		origin.setSaleStatus(from.getSaleStatus());

		propertyInfoRepository.saveAndFlush(origin);
	}

	/**
	 * 删除房产并级联删除其业主信息
	 */
	@Transactional
	public boolean deleteProperty(Integer id) {

		List<UserInfoEntity> users = userInfoRepository.findByPropertyId(id);

		userInfoRepository.deleteAll(users);

		propertyInfoRepository.deleteById(id);

		return true;
	}
}
