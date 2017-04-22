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
import com.onlymvp.service.PropertyInfoService;

/**
 * 
 * 房产管理业务实现
 * 
 * chengzhi
 *
 */
@Service
public class PropertyInfoServiceImpl implements PropertyInfoService {

	@Resource
	private PropertyInfoDAO propertyInfoDAO;

	@Resource
	private UserInfoDAO userInfoDAO;

	@Override
	public Serializable insert(PropertyInfoEntity t) throws Exception {

		return this.propertyInfoDAO.insert(t);
	}

	@Override
	public boolean update(PropertyInfoEntity t) throws Exception {

		return propertyInfoDAO.update(t);

	}

	@Override
	public Map<String, Object> queryPropertyListAndSize(PropertyInfoEntity propertyInfoEntity, int start, int size)
			throws Exception {

		Map<String, Object> resMap = new HashMap<String, Object>();

		List<PropertyInfoEntity> list = this.propertyInfoDAO.queryPropertyList(propertyInfoEntity, start, size);// 查询数据列表

		long count = this.propertyInfoDAO.queryCount(propertyInfoEntity);// 查询记录条数

		resMap.put("rows", list);
		resMap.put("total", count);

		return resMap;
	}

	@Transactional
	@Override
	public void delete(PropertyInfoEntity t) throws Exception {

		this.propertyInfoDAO.delete(t);// 删除房产信息

		this.userInfoDAO.deleteByPropertyId(t.getId());// 及联删除该房产的业主信息

	}

	@Transactional
	@Override
	public boolean deleteById(Integer id) throws Exception {

		return this.propertyInfoDAO.deleteById(id) && this.userInfoDAO.deleteByPropertyId(id);// 及联删除该房产的业主信息

	}

	@Override
	public PropertyInfoEntity queryById(Integer id) throws Exception {

		return this.propertyInfoDAO.queryById(id);
	}

	@Override
	public PropertyInfoEntity queryPropertyInfoByUserName(String userName) throws Exception {

		UserInfoEntity userInfoEntity = this.userInfoDAO.queryByUserName(userName);

		PropertyInfoEntity properInfoEntity = this.propertyInfoDAO.queryById(userInfoEntity.getPropertyId());

		return properInfoEntity;
	}

}
