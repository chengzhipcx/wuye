package com.onlymvp.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.dao.DataIntegrityViolationException;

import com.onlymvp.entity.MessageInfoEntity;
import com.onlymvp.entity.PropertyInfoEntity;
import com.onlymvp.service.PropertyInfoService;
import com.onlymvp.tool.Const;
import com.onlymvp.tool.OutTool;
import com.onlymvp.tool.StringTool;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 
 * 后台"房产管理"控制器 提供EasyUI访问接口并返回Json数据
 * 
 * chengzhi
 *
 */
@ParentPackage(value = "json-default")
@Action(value = "property", results = { @Result(name = "success", type = "json") })
public class PropertyAction extends ActionSupport implements ModelDriven<PropertyInfoEntity> {

	private PropertyInfoEntity entity = new PropertyInfoEntity();// 对象模型
	private String ids;// 批量删除接收的ID数组,自行截取","
	private String number;// 每页展示条数
	private String page;// 当前页数
	private String userName;// 业主用户名

	@Resource
	private PropertyInfoService propertyInfoService;

	@Override
	public PropertyInfoEntity getModel() {

		return entity;
	}

	// ------------ GET AND SET START -------------

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	// ------------ GET AND SET END -------------

	/**
	 * 通过用户名查询业主房产信息
	 * 
	 * @return
	 */
	public String queryByUserName() {

		MessageInfoEntity messageInfoEntity = new MessageInfoEntity();

		try {

			PropertyInfoEntity propertyInfoEntity = this.propertyInfoService.queryPropertyInfoByUserName(userName);

			messageInfoEntity.setStatus(Const.RETURN_STATUS_OK);
			messageInfoEntity.setDetail(propertyInfoEntity);

		} catch (Exception e) {
			e.printStackTrace();
			messageInfoEntity.setStatus(Const.RETURN_STATUS_FAIL);
			messageInfoEntity.setDesc("查无该业主房产信息");
		}

		OutTool.writeJsonObject(messageInfoEntity);

		return null;
	}

	/**
	 * 批量删除
	 * 
	 * @return
	 */
	public String batchRemove() {

		MessageInfoEntity messageInfoEntity = new MessageInfoEntity();

		if (StringTool.isNull(ids)) {// 如果传递过来批量删除的id字符串为空

			messageInfoEntity.setStatus(Const.RETURN_STATUS_FAIL);
			messageInfoEntity.setDesc("请选择");

			OutTool.writeJsonObject(messageInfoEntity);

			return null;
		}

		try {
			String[] idArr = ids.split(",");

			for (String id : idArr)
				this.propertyInfoService.deleteById(Integer.parseInt(id));

			messageInfoEntity.setStatus(Const.RETURN_STATUS_OK);
			messageInfoEntity.setDesc("批量删除成功");

		} catch (Exception e) {
			e.printStackTrace();
			messageInfoEntity.setStatus(Const.RETURN_STATUS_FAIL);
			messageInfoEntity.setDesc("批量删除失败");
		}

		OutTool.writeJsonObject(messageInfoEntity);

		return null;
	}

	/**
	 * 更新房产信息
	 * 
	 * @return
	 */
	public String updateProperty() {

		MessageInfoEntity messageInfoEntity = new MessageInfoEntity();

		try {

			this.propertyInfoService.update(entity);

			messageInfoEntity.setStatus(Const.RETURN_STATUS_OK);
			messageInfoEntity.setDesc("更新信息成功");

		} catch (Exception e) {
			e.printStackTrace();
			messageInfoEntity.setStatus(Const.RETURN_STATUS_OK);
			messageInfoEntity.setDesc("更新信息失败");
		}

		OutTool.writeJsonObject(messageInfoEntity);

		return null;
	}

	/**
	 * 通过ID查询房产信息
	 * 
	 * @return
	 */
	public String queryById() {

		MessageInfoEntity messageInfoEntity = new MessageInfoEntity();

		try {

			PropertyInfoEntity propertyInfoEntity = this.propertyInfoService.queryById(entity.getId());

			messageInfoEntity.setStatus(Const.RETURN_STATUS_OK);
			messageInfoEntity.setDetail(propertyInfoEntity);

		} catch (Exception e) {
			e.printStackTrace();
			messageInfoEntity.setStatus(Const.RETURN_STATUS_FAIL);
			messageInfoEntity.setDesc("查询失败");
		}

		OutTool.writeJsonObject(messageInfoEntity);

		return null;
	}

	/**
	 * 保存房产信息
	 * 
	 * @return
	 */
	public String saveProperty() {

		MessageInfoEntity messageInfoEntity = new MessageInfoEntity();

		try {

			this.propertyInfoService.insert(entity);

			messageInfoEntity.setStatus(Const.RETURN_STATUS_OK);
			messageInfoEntity.setDesc("保存成功");

		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			messageInfoEntity.setStatus(Const.RETURN_STATUS_FAIL);
			messageInfoEntity.setDesc("地址重复,保存失败");
		} catch (Exception e) {
			e.printStackTrace();
			messageInfoEntity.setStatus(Const.RETURN_STATUS_FAIL);
			messageInfoEntity.setDesc("保存失败");

		}

		OutTool.writeJsonObject(messageInfoEntity);

		return null;
	}

	/**
	 * EasyUI Ajax 查询房产列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String propertyList() throws Exception {

		int intPage = Integer.parseInt(StringTool.isNull(page) ? "1" : page);

		int intNum = Integer.parseInt(StringTool.isNull(number) ? "1" : number);

		int start = (intPage - 1) * intNum;

		Map<String, Object> resMap = this.propertyInfoService.queryPropertyListAndSize(entity, start, intNum);

		OutTool.writeJsonObject(resMap);

		return null;
	}

}
