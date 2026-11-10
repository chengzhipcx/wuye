package com.onlymvp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onlymvp.entity.DosageEntity;
import com.onlymvp.entity.MessageInfoEntity;
import com.onlymvp.service.DosageInfoService;
import com.onlymvp.tool.Const;
import com.onlymvp.tool.OutTool;
import com.onlymvp.tool.StringTool;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 住户用量访问控制器 提供EasyUI与业主前端访问接口并返回Json数据
 *
 * chengzhi
 *
 */
@ParentPackage(value = "json-default")
@Action(value = "dosage", results = { @Result(name = "success", type = "json") })
public class DosageAction extends ActionSupport implements ModelDriven<DosageEntity>, ServletRequestAware {

	private static final long serialVersionUID = 1L;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private DosageEntity model = new DosageEntity();// 对象模型
	private HttpServletRequest request;

	@Resource
	private DosageInfoService dosageInfoService;

	private String number;// 每页展示条数
	private String page;// 当前页数
	private String ids;// 批量删除的id数组,自行用","分割
	private String user_id;// 业主用户ID(业主前端历史用量查询)

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

	@Override
	public DosageEntity getModel() {
		return model;
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

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	/**
	 * 查询指定业主的用量列表(业主前端历史用量)
	 *
	 * @return
	 */
	public String listByUserId() {

		List<DosageEntity> emptyList = new ArrayList<DosageEntity>();

		if (StringTool.isNull(user_id)) {
			OutTool.writeJsonArray(emptyList);
			return null;
		}

		try {

			OutTool.writeJsonArray(this.dosageInfoService.queryByUserId(Integer.parseInt(user_id)));

		} catch (Exception e) {
			logger.error("查询业主用量列表失败", e);
			OutTool.writeJsonArray(emptyList);
		}

		return null;
	}

	/**
	 * 分页查询用量列表(后台管理)
	 *
	 * @return
	 */
	public String list() {

		Map<String, Object> resMap = new HashMap<String, Object>();

		try {

			int intNum = Integer.parseInt(StringTool.isNull(number) ? "20" : number);
			int intPage = Integer.parseInt(StringTool.isNull(page) ? "1" : page);

			int start = (intPage - 1) * intNum;

			resMap.put("rows", this.dosageInfoService.queryPageList(start, intNum));
			resMap.put("total", this.dosageInfoService.queryCount());

		} catch (Exception e) {
			logger.error("分页查询用量列表失败", e);
			resMap.put("rows", new Object[] {});
			resMap.put("total", 0);
		}

		OutTool.writeJsonObject(resMap);

		return null;
	}

	/**
	 * 保存用量信息
	 *
	 * @return
	 */
	public String save() {

		MessageInfoEntity messageInfoEntity = new MessageInfoEntity();

		if (model.getUserId() == null || StringTool.isNull(model.getType()) || model.getCount() == null
				|| StringTool.isNull(model.getDate())) {
			messageInfoEntity.setStatus(Const.RETURN_STATUS_FAIL);
			messageInfoEntity.setDesc("请填写完整用量信息");
			OutTool.writeJsonObject(messageInfoEntity);
			return null;
		}

		try {

			this.dosageInfoService.insert(model);

			logger.info("保存用量信息成功 -> {}", model);

			messageInfoEntity.setStatus(Const.RETURN_STATUS_OK);
			messageInfoEntity.setDesc("保存成功");

		} catch (Exception e) {
			logger.error("保存用量信息失败", e);
			messageInfoEntity.setStatus(Const.RETURN_STATUS_FAIL);
			messageInfoEntity.setDesc("保存失败");
		}

		OutTool.writeJsonObject(messageInfoEntity);

		return null;
	}

	/**
	 * 批量删除用量信息
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

			for (String id : idArr) {

				boolean res = this.dosageInfoService.deleteById(Integer.parseInt(id));

				logger.info("删除ID为 -> {} 的用量数据成功 -> {}", id, res);
			}

			messageInfoEntity.setStatus(Const.RETURN_STATUS_OK);
			messageInfoEntity.setDesc("删除成功");

		} catch (Exception e) {
			logger.error("批量删除用量信息失败", e);
			messageInfoEntity.setStatus(Const.RETURN_STATUS_FAIL);
			messageInfoEntity.setDesc("删除失败");
		}

		OutTool.writeJsonObject(messageInfoEntity);

		return null;
	}
}
