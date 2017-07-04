package com.onlymvp.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onlymvp.dto.UserAndPropertyAndChargeInfoEntity;
import com.onlymvp.entity.ChargeInfoEntity;
import com.onlymvp.entity.MessageInfoEntity;
import com.onlymvp.service.ChargeInfoService;
import com.onlymvp.tool.Const;
import com.onlymvp.tool.OutTool;
import com.onlymvp.tool.StringTool;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 缴费管理控制器,提供EasyUI访问接口并返回Json数据
 * 
 * chengzhi
 *
 */
@ParentPackage(value = "json-default")
@Action(value = "charge", results = { @Result(name = "success", type = "json") ,
		@Result(name = "shouye",  location = "/views/user/index.jsp")
})
public class ChargeInfoAction extends ActionSupport implements ModelDriven<UserAndPropertyAndChargeInfoEntity> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private UserAndPropertyAndChargeInfoEntity model = new UserAndPropertyAndChargeInfoEntity();// 对象模型

	private String ids;// 要删除缴费记录的的ID列表,自行用","分割
	private String number;// 每页展示条数
	private String page;// 当前页数

	@Resource
	private ChargeInfoService chargeInfoService;

	
	@Override
	public UserAndPropertyAndChargeInfoEntity getModel() {
		return model;
	}

	// -------SET AND GET --------
	
	
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	// -------SET AND GET END --------

	/**
	 * 保存缴费信息
	 * 
	 * @return
	 */
	public String save() {

		logger.info("接收参数对象 -> {}", model);

		MessageInfoEntity messageInfoEntity = new MessageInfoEntity();

		try {
			ChargeInfoEntity entity = new ChargeInfoEntity();

			entity.setPayment(model.getPayment());
			entity.setChargeType(model.getChargeType());
			entity.setUserId(model.getUserId());
			entity.setPropertyId(model.getPropertyId());
			entity.setRemark(model.getRemark());

			this.chargeInfoService.insert(entity);

			messageInfoEntity.setStatus(Const.RETURN_STATUS_OK);
			messageInfoEntity.setDesc("缴费成功");

		} catch (Exception e) {
			e.printStackTrace();
			messageInfoEntity.setStatus(Const.RETURN_STATUS_FAIL);
			messageInfoEntity.setDesc("缴费失败");
		}

		OutTool.writeJsonObject(messageInfoEntity);

		return null;
	}

	/**
	 * 更新缴费信息
	 * 
	 * @return
	 */
	public String update() {

		MessageInfoEntity messageInfoEntity = new MessageInfoEntity();

		try {
			this.chargeInfoService.updateChargeInfo(model);
			messageInfoEntity.setStatus(Const.RETURN_STATUS_OK);
			messageInfoEntity.setDesc("更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			messageInfoEntity.setStatus(Const.RETURN_STATUS_FAIL);
			messageInfoEntity.setDesc("更新失败");
		}

		OutTool.writeJsonObject(messageInfoEntity);

		return null;
	}

	/**
	 * 更新缴费信息
	 * 
	 * @return
	 */
	public String updateStatus() {

		MessageInfoEntity messageInfoEntity = new MessageInfoEntity();

		try {
			ChargeInfoEntity queryById = chargeInfoService.queryById(model.getChargeInfoId());
			queryById.setStatus("1");
			this.chargeInfoService.update(queryById);
			messageInfoEntity.setStatus(Const.RETURN_STATUS_OK);
			messageInfoEntity.setDesc("更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			messageInfoEntity.setStatus(Const.RETURN_STATUS_FAIL);
			messageInfoEntity.setDesc("更新失败");
		}


		return "shouye";
	}
	/**
	 * 批量删除
	 * 
	 * @return
	 */
	public String batchRemove() {

		MessageInfoEntity messageInfoEntity = new MessageInfoEntity();

		if (StringTool.isNull(ids)) {
			messageInfoEntity.setStatus(Const.RETURN_STATUS_FAIL);
			messageInfoEntity.setDesc("请选择");

			OutTool.writeJsonObject(messageInfoEntity);

			return null;
		}

		try {
			String[] idArr = ids.split(",");

			for (String id : idArr)
				this.chargeInfoService.deleteById(Integer.parseInt(id));

			messageInfoEntity.setStatus(Const.RETURN_STATUS_OK);
			messageInfoEntity.setDesc("批量删除成功");

		} catch (Exception e) {
			e.printStackTrace();
			messageInfoEntity.setStatus(Const.RETURN_STATUS_FAIL);
			messageInfoEntity.setDesc("删除失败");
		}

		OutTool.writeJsonObject(messageInfoEntity);

		return null;
	}

	/**
	 * EasyUI Ajax 分页模糊查询加载datagrid
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {

		logger.info("接收到参数对象 -> {}", model);

		int intNum = Integer.parseInt(StringTool.isNull(number) ? "1" : number);

		int intPage = Integer.parseInt(StringTool.isNull(page) ? "1" : page);

		int start = (intPage - 1) * intNum;

		Map<String, Object> resMap = this.chargeInfoService.queryListAndSize(model, start, intNum);

		OutTool.writeJsonObject(resMap);

		return null;
	}

	public String listByUserId() throws Exception {

		OutTool.writeJsonArray(this.chargeInfoService.queryByUserId(model.getUserId()));

		return null;

	}

}
