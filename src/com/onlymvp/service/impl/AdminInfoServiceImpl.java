package com.onlymvp.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlymvp.dao.AdminInfoDAO;
import com.onlymvp.dao.MenuAndRoleDAO;
import com.onlymvp.entity.AdminInfoEntity;
import com.onlymvp.entity.MenuAndRoleEntity;
import com.onlymvp.service.AdminInfoService;
import com.onlymvp.tool.StringTool;

/**
 * @describe 管理员信息操作业务实现
 * chengzhi
 *
 */
@Service
public class AdminInfoServiceImpl implements AdminInfoService {

	@Resource
	private AdminInfoDAO adminInfoDAO;

	@Resource
	private MenuAndRoleDAO menuAndRoleDAO;

	@Override
	public Serializable insert(AdminInfoEntity t) throws Exception {
		return this.adminInfoDAO.insert(t);
	}

	@Override
	public boolean update(AdminInfoEntity t) throws Exception {
		return this.adminInfoDAO.update(t);
	}

	@Override
	public AdminInfoEntity queryByNameAndPwd(String userName, String userPwd) throws Exception {
		return this.adminInfoDAO.queryByNameAndPwd(userName, userPwd);
	}

	@Override
	public Map<String, Object> pageQueryNormalAdminListAndSize(int start, int size) throws Exception {
		Map<String, Object> resMap = new HashMap<String, Object>();

		List<AdminInfoEntity> list = this.adminInfoDAO.queryNormalAdminList(start, size);

		long count = this.adminInfoDAO.queryNormalAdminCount();

		resMap.put("rows", list);

		resMap.put("total", count);

		return resMap;
	}

	@Transactional
	@Override
	public void saveAdminInfoAndMenuPower(AdminInfoEntity adminInfoEntity, String[] menuIds) throws Exception {

		try {
			int adminId = (int) this.adminInfoDAO.insert(adminInfoEntity);// 保存到数据库后返回主键ID
			MenuAndRoleEntity menuAndRoleEntity = null;
			for (String menuId : menuIds) {
				menuAndRoleEntity = new MenuAndRoleEntity();
				menuAndRoleEntity.setAdminId(adminId);
				menuAndRoleEntity.setMenuId(Integer.parseInt(menuId));
				this.menuAndRoleDAO.insert(menuAndRoleEntity);
			}

		} catch (Exception e) {
			throw e;
		}

	}

	@Transactional
	@Override
	public void batchRemoveAdminAndRole(Integer id) throws Exception {

		try {
			AdminInfoEntity adminInfoEntity = new AdminInfoEntity();// 要删除的管理员
			adminInfoEntity.setId(id);

			MenuAndRoleEntity menuAndRoleEntity = new MenuAndRoleEntity();// 删除对应的菜单权限
			menuAndRoleEntity.setAdminId(id);

			this.adminInfoDAO.delete(adminInfoEntity);

			this.menuAndRoleDAO.deleteByAdminId(id);

		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public AdminInfoEntity queryById(Integer id) throws Exception {

		return this.adminInfoDAO.queryById(id);
	}

	@Transactional
	@Override
	public boolean updateAdminInfoAndMenuPower(AdminInfoEntity adminInfoEntity, String[] menuIds) throws Exception {

		try {

			int id = adminInfoEntity.getId();// 获得要修改的管理员对象主键ID

			this.menuAndRoleDAO.deleteByAdminId(id);// 清空当前要修改的管理员角色菜单权限表数据

			MenuAndRoleEntity menuAndRoleEntity = null;
			for (String menuId : menuIds) {// 循环插入新的权限
				menuAndRoleEntity = new MenuAndRoleEntity();
				menuAndRoleEntity.setAdminId(id);
				menuAndRoleEntity.setMenuId(Integer.parseInt(menuId));
				this.menuAndRoleDAO.insert(menuAndRoleEntity);
			}

			boolean res = this.adminInfoDAO.updateAdminPassword(adminInfoEntity.getId(), adminInfoEntity.getUserPwd());

			if (res)
				return true;

		} catch (Exception e) {
			throw e;
		}

		return false;

	}

	@Override
	public void delete(AdminInfoEntity t) throws Exception {

		this.adminInfoDAO.delete(t);

	}

	@Override
	public boolean deleteById(Integer id) throws Exception {
		return this.adminInfoDAO.deleteById(id);
	}

}
