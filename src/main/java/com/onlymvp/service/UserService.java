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
 * 业主业务
 *
 * chengzhi
 *
 */
@Service
public class UserService {

	private final UserInfoRepository userInfoRepository;

	private final PropertyInfoRepository propertyInfoRepository;

	public UserService(UserInfoRepository userInfoRepository, PropertyInfoRepository propertyInfoRepository) {
		this.userInfoRepository = userInfoRepository;
		this.propertyInfoRepository = propertyInfoRepository;
	}

	public UserInfoEntity queryByUserName(String userName) {
		return userInfoRepository.findByUserName(userName);
	}

	public UserInfoEntity queryByUserNameAndUserPwd(String userName, String userPwd) {
		return userInfoRepository.findByUserNameAndUserPwd(userName, userPwd);
	}

	public UserInfoEntity queryById(Integer id) {
		return userInfoRepository.findById(id).orElse(null);
	}

	/**
	 * 分页模糊查询业主列表
	 */
	public Map<String, Object> queryUserListAndSize(String userName, String realName, String phone, int page,
			int number) {

		Pageable pageable = PageRequest.of(page - 1, number);

		List<UserInfoEntity> list = userInfoRepository.queryUserList(userName, realName, phone, pageable);

		long count = userInfoRepository.queryUserCount(userName, realName, phone);

		Map<String, Object> resMap = new HashMap<>();
		resMap.put("rows", list);
		resMap.put("total", count);
		return resMap;
	}

	/**
	 * 保存业主并更新房产出售状态(业主购买)
	 */
	@Transactional
	public void saveUserAndUpdateProperty(UserInfoEntity user) {

		user.setDelStatus(Const.SYSTEM_DEL_STATUS_NO);
		user.setCreateTime(DateTimeTool.getDateTime(new Date()));

		userInfoRepository.saveAndFlush(user);

		propertyInfoRepository.updateSaleStatus(user.getPropertyId(), Const.PROPERTY_SALE_STATUS_YES);
	}

	@Transactional
	public void updateUser(UserInfoEntity origin, UserInfoEntity from) {
		origin.setEmail(from.getEmail());
		origin.setIdCard(from.getIdCard());
		origin.setPhone(from.getPhone());
		origin.setQq(from.getQq());
		origin.setRealName(from.getRealName());
		origin.setUserPwd(from.getUserPwd());
		userInfoRepository.save(origin);
	}

	/**
	 * 删除业主并恢复房产为未出售
	 */
	@Transactional
	public boolean deleteUser(Integer id) {

		UserInfoEntity user = userInfoRepository.findById(id).orElse(null);

		if (user == null) {
			return false;
		}

		propertyInfoRepository.updateSaleStatus(user.getPropertyId(), Const.PROPERTY_SALE_STATUS_NO);

		userInfoRepository.deleteById(id);

		return true;
	}
}
