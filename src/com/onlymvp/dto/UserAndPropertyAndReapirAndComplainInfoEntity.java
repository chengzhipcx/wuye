package com.onlymvp.dto;

/**
 * 数据传输对象,包含业主,住址,报修或投诉信息
 * 
 * chengzhi
 *
 */
public class UserAndPropertyAndReapirAndComplainInfoEntity {
	private Integer repairAndComplainInfoId;
	private Integer userId;
	private Integer propertyId;
	private String content;
	private String dealUser;
	private Integer infoType;
	private Integer solveStatus;
	private Integer delStatus;
	private String createTime;
	private String solveTime;

	private String realName;
	private String phone;
	private String address;

	public Integer getRepairAndComplainInfoId() {
		return repairAndComplainInfoId;
	}

	public void setRepairAndComplainInfoId(Integer repairAndComplainInfoId) {
		this.repairAndComplainInfoId = repairAndComplainInfoId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(Integer propertyId) {
		this.propertyId = propertyId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getInfoType() {
		return infoType;
	}

	public void setInfoType(Integer infoType) {
		this.infoType = infoType;
	}

	public Integer getSolveStatus() {
		return solveStatus;
	}

	public void setSolveStatus(Integer solveStatus) {
		this.solveStatus = solveStatus;
	}

	public Integer getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(Integer delStatus) {
		this.delStatus = delStatus;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getSolveTime() {
		return solveTime;
	}

	public void setSolveTime(String solveTime) {
		this.solveTime = solveTime;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDealUser() {
		return dealUser;
	}

	public void setDealUser(String dealUser) {
		this.dealUser = dealUser;
	}

	@Override
	public String toString() {
		return "UserAndPropertyAndReapirAndComplainInfoEntity [repairAndComplainInfoId=" + repairAndComplainInfoId
				+ ", userId=" + userId + ", propertyId=" + propertyId + ", content=" + content + ", dealUser="
				+ dealUser + ", infoType=" + infoType + ", solveStatus=" + solveStatus + ", delStatus=" + delStatus
				+ ", createTime=" + createTime + ", solveTime=" + solveTime + ", realName=" + realName + ", phone="
				+ phone + ", address=" + address + "]";
	}

}
