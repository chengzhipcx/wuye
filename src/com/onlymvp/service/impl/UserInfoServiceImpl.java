package com.onlymvp.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlymvp.dao.PropertyInfoDAO;
import com.onlymvp.dao.UserInfoDAO;
import com.onlymvp.entity.PropertyInfoEntity;
import com.onlymvp.entity.UserInfoEntity;
import com.onlymvp.service.UserInfoService;
import com.onlymvp.tool.Const;

/**
 * 业主管理业务实现
 * 
 * chengzhi
 *
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Resource
	private UserInfoDAO userInfoDAO;

	@Resource
	private PropertyInfoDAO propertyInfoDAO;

	@Override
	public Serializable insert(UserInfoEntity t) throws Exception {
		return this.userInfoDAO.insert(t);
	}

	@Override
	public boolean update(UserInfoEntity t) throws Exception {
		return this.userInfoDAO.update(t);
	}

	@Override
	public void delete(UserInfoEntity t) throws Exception {

		this.userInfoDAO.delete(t);// 删除业主

	}

	@Transactional
	@Override
	public boolean deleteById(Integer id) throws Exception {

		boolean t1 = false;

		boolean t2 = false;

		try {
			// 查询UserInfo表中propertyId字段

			UserInfoEntity userInfoEntity = this.userInfoDAO.queryById(id);

			Integer propertyId = userInfoEntity.getPropertyId();

			// 更新ProperInfo表中saleStatus为未出售

			t1 = this.propertyInfoDAO.updateSaleStatus(propertyId, Const.PROPERTY_SALE_STATUS_NO);

			// 删除UserInfo表中此条数据

			t2 = this.userInfoDAO.deleteById(id);

		} catch (Exception e) {
			throw e;
		}

		return t1 && t2;
	}

	@Transactional
	@Override
	public void saveUserInfoAndUpdatePropertySaleStatus(UserInfoEntity userInfoEntity) throws Exception {

		try {

			this.userInfoDAO.insert(userInfoEntity);// 业主信息保存到数据库

			this.propertyInfoDAO.updateSaleStatus(userInfoEntity.getPropertyId(), Const.PROPERTY_SALE_STATUS_YES);// 更新房产状态为"已出售"

		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public Map<String, Object> queryUserInfoListAndSize(UserInfoEntity userInfoEntity, int start, int size)
			throws Exception {

		Map<String, Object> resMap = new HashMap<String, Object>();

		List<UserInfoEntity> list = this.userInfoDAO.queryUserInfoList(userInfoEntity, start, size);

		long count = this.userInfoDAO.queryCount(userInfoEntity);

		resMap.put("rows", list);
		resMap.put("total", count);

		return resMap;
	}

	@Override
	public UserInfoEntity queryById(Integer id) throws Exception {

		return this.userInfoDAO.queryById(id);
	}

	@Override
	public UserInfoEntity queryByUserName(String userName) throws Exception {

		return this.userInfoDAO.queryByUserName(userName);
	}

	@Override
	public UserInfoEntity queryByUserNameAndUserPwd(String userName, String userPwd) throws Exception {
		return this.userInfoDAO.queryByUserNameAndUserPwd(userName, userPwd);
	}

}
