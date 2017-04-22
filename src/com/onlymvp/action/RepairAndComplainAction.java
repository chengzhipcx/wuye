package com.onlymvp.action;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onlymvp.dto.UserAndPropertyAndReapirAndComplainInfoEntity;
import com.onlymvp.entity.MessageInfoEntity;
import com.onlymvp.entity.RepairAndComplainInfoEntity;
import com.onlymvp.entity.UserInfoEntity;
import com.onlymvp.service.RepairAndComplainInfoService;
import com.onlymvp.tool.Const;
import com.onlymvp.tool.OutTool;
import com.onlymvp.tool.StringTool;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 报修投诉访问控制器 提供EasyUI访问接口并返回Json数据
 * 
 * chengzhi
 *
 */
@ParentPackage(value = "json-default")
@Action(value = "repairAndComplain", results = { @Result(name = "success", type = "json") })
public class RepairAndComplainAction extends ActionSupport
		implements ModelDriven<UserAndPropertyAndReapirAndComplainInfoEntity> {

	private static final long serialVersionUID = -2494847089510226776L;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private UserAndPropertyAndReapirAndComplainInfoEntity model = new UserAndPropertyAndReapirAndComplainInfoEntity();// 对象模型

	private String ids;// 要删除缴费记录的的ID列表,自行用","分割
	private String number;// 每页展示条数
	private String page;// 当前页数

	@Resource
	private RepairAndComplainInfoService repairAndComplainInfoService;

	@Override
	public UserAndPropertyAndReapirAndComplainInfoEntity getModel() {
		return model;
	}

	// -------- GET AND SET ---------

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

	// -------- GET AND SET END ---------

	/**
	 * 保存报修,投诉信息
	 * 
	 * @return
	 */
	public String save() {

		MessageInfoEntity messageInfoEntity = new MessageInfoEntity();

		logger.info("接收参数对象 -> {}", model);

		try {

			HttpSession session = ServletActionContext.getRequest().getSession();

			UserInfoEntity userInfoEntity = (UserInfoEntity) session.getAttribute(Const.SESSION_USER_BEAN);

			RepairAndComplainInfoEntity entity = new RepairAndComplainInfoEntity();

			if (null == userInfoEntity) {
				messageInfoEntity.setStatus(Const.RETURN_STATUS_OK);
				messageInfoEntity.setDesc("验证用户出错");

				OutTool.writeJsonObject(messageInfoEntity);
				return null;
			}

			// 置入数据
			entity.setUserId(userInfoEntity.getId());
			entity.setPropertyId(userInfoEntity.getPropertyId());
			entity.setContent(model.getContent());
			entity.setInfoType(model.getInfoType());

			this.repairAndComplainInfoService.insert(entity);// 保存到数据库

			messageInfoEntity.setStatus(Const.RETURN_STATUS_OK);
			messageInfoEntity.setDesc("提交成功！");

		} catch (Exception e) {
			e.printStackTrace();
			messageInfoEntity.setStatus(Const.RETURN_STATUS_OK);
			messageInfoEntity.setDesc("提交失败,服务器错误");
		}

		OutTool.writeJsonObject(messageInfoEntity);

		return null;
	}

	/**
	 * 更新状态为"已处理"
	 * 
	 * @return
	 */
	public String update() {

		MessageInfoEntity messageInfoEntity = new MessageInfoEntity();

		try {
			RepairAndComplainInfoEntity entity = new RepairAndComplainInfoEntity();
			entity.setDealUser(model.getDealUser());
			entity.setId(model.getRepairAndComplainInfoId());

			this.repairAndComplainInfoService.update(entity);

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
	 * EasyUI Ajax分页模糊查询列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {

		logger.info("查询参数对象 -> {}", model);

		int intNum = Integer.parseInt(StringTool.isNull(number) ? "1" : number);
		int intPage = Integer.parseInt(StringTool.isNull(page) ? "1" : page);

		int start = (intPage - 1) * intNum;

		Map<String, Object> resMap = this.repairAndComplainInfoService.queryListAndSize(model, start, intNum);

		OutTool.writeJsonObject(resMap);

		return null;
	}

}
