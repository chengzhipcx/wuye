package com.onlymvp.action;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.naming.factory.SendMailFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onlymvp.entity.AdminInfoEntity;
import com.onlymvp.entity.MessageInfoEntity;
import com.onlymvp.entity.UserInfoEntity;
import com.onlymvp.service.UserInfoService;
import com.onlymvp.tool.Const;
import com.onlymvp.tool.MailUtils;
import com.onlymvp.tool.OutTool;
import com.onlymvp.tool.SendMail;
import com.onlymvp.tool.StringTool;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 业主管理访问控制器 提供EasyUI访问接口并返回Json数据
 * 
 * chengzhi
 *
 */
@ParentPackage(value = "json-default")
@Action(value = "user", results = { @Result(name = "success", type = "json"),
				@Result(name="userLogin",location="/index.jsp") })
public class UserAction extends ActionSupport implements ModelDriven<UserInfoEntity>, ServletRequestAware {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private UserInfoEntity model = new UserInfoEntity();// 对象模型
	private HttpServletRequest request;

	@Resource
	private UserInfoService userInfoService;

	private String number;// 每页展示条数
	private String page;// 当前页数
	private String ids;// 批量删除的id数组,自行用","分割
	
	private String tomail;//目标邮箱地址
	private String mailcontent;//邮件的内容

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

	@Override
	public UserInfoEntity getModel() {

		return model;
	}

	// ------ SET AND GET ------
	
	
	public String getNumber() {
		return number;
	}


	public String getMailcontent() {
		return mailcontent;
	}

	public void setMailcontent(String mailcontent) {
		this.mailcontent = mailcontent;
	}

	public void setNumber(String number) {
		this.number = number;
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
	// ------ SET AND GET END ------

	/**
	 * 通过用户名查询业主
	 * 
	 * @return
	 */
	public String queryByUserName() {

		MessageInfoEntity messageInfoEntity = new MessageInfoEntity();

		try {
			UserInfoEntity userInfoEntity = this.userInfoService.queryByUserName(model.getUserName());
			messageInfoEntity.setStatus(Const.RETURN_STATUS_OK);
			messageInfoEntity.setDetail(userInfoEntity);

		} catch (Exception e) {
			e.printStackTrace();
			messageInfoEntity.setStatus(Const.RETURN_STATUS_FAIL);
			messageInfoEntity.setDesc("查询失败");
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

		int intPage = Integer.parseInt(StringTool.isNull(page) ? "1" : page);

		int intNum = Integer.parseInt(StringTool.isNull(number) ? "1" : number);

		int start = (intPage - 1) * intNum;

		Map<String, Object> resMap = this.userInfoService.queryUserInfoListAndSize(model, start, intNum);

		OutTool.writeJsonObject(resMap);

		return null;
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

			for (String id : idArr) {

				boolean res = this.userInfoService.deleteById(Integer.parseInt(id));// 循环删除数据库业主数据并更新房产状态

				logger.info("删除ID为 -> {} 的业主数据成功 -> {}", id, res);

			}

			messageInfoEntity.setStatus(Const.RETURN_STATUS_OK);
			messageInfoEntity.setDesc("删除成功");

		} catch (Exception e) {
			e.printStackTrace();
			messageInfoEntity.setStatus(Const.RETURN_STATUS_FAIL);
			messageInfoEntity.setDesc("删除失败");
		}

		OutTool.writeJsonObject(messageInfoEntity);

		return null;
	}

	/**
	 * 更新业主信息
	 * 
	 * @return
	 */
	public String update() {

		MessageInfoEntity messageInfoEntity = new MessageInfoEntity();

		try {
			UserInfoEntity queryById = this.userInfoService.queryById(model.getId());
			queryById.setEmail(model.getEmail());
			queryById.setIdCard(model.getIdCard());
			queryById.setPhone(model.getPhone());
			queryById.setQq(model.getQq());
			queryById.setRealName(model.getRealName());
			queryById.setUserPwd(model.getUserPwd());
			this.userInfoService.update(queryById);

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
	 * 保存业主信息
	 * 
	 * @return
	 */
	public String save() {
		MessageInfoEntity messageInfoEntity = new MessageInfoEntity();

		try {

			this.userInfoService.saveUserInfoAndUpdatePropertySaleStatus(model);

			messageInfoEntity.setStatus(Const.RETURN_STATUS_OK);
			messageInfoEntity.setDesc("保存成功");

		} catch (ConstraintViolationException e) {
			e.printStackTrace();
			messageInfoEntity.setStatus(Const.RETURN_STATUS_FAIL);
			messageInfoEntity.setDesc("业主用户名重复／房产已售");
		} catch (Exception e) {
			e.printStackTrace();
			messageInfoEntity.setStatus(Const.RETURN_STATUS_FAIL);
			messageInfoEntity.setDesc("保存失败");
		}

		OutTool.writeJsonObject(messageInfoEntity);

		return null;
	}

	/**
	 * 业主登录
	 * 
	 * @return
	 */
	public String login() {
		MessageInfoEntity messageInfoEntity = new MessageInfoEntity();
		try {

			UserInfoEntity userInfoEntity = this.userInfoService.queryByUserNameAndUserPwd(model.getUserName(),
					model.getUserPwd());

			if (userInfoEntity != null) {
				this.request.getSession().setAttribute(Const.SESSION_USER_BEAN, userInfoEntity);// 把登录信息添加到session
				messageInfoEntity.setStatus(Const.RETURN_STATUS_OK);
				messageInfoEntity.setDetail(userInfoEntity);
			} else {
				messageInfoEntity.setStatus(Const.RETURN_STATUS_FAIL);
				messageInfoEntity.setDesc("用户名/密码错误");
			}

		} catch (Exception e) {
			e.printStackTrace();
			messageInfoEntity.setStatus(Const.RETURN_STATUS_FAIL);
			messageInfoEntity.setDesc("服务器错误");
		}

		OutTool.writeJsonObject(messageInfoEntity);

		return null;
	}
	
	public String logout() {
		this.request.getSession().setAttribute(Const.SESSION_USER_BEAN, null);
		return "userLogin";
	}
	
	public String sendMail(){
		MessageInfoEntity messageInfoEntity = new MessageInfoEntity();
		SendMail.simpleEmail(this.tomail,"缴费通知", this.mailcontent);
		messageInfoEntity.setStatus(Const.RETURN_STATUS_OK);
		return null;
	}
}
