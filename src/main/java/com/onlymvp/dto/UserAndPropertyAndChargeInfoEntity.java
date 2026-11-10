package com.onlymvp.dto;

/**
 * 数据传输对象,包含业主,住址,缴费信息
 * 
 * chengzhi
 *
 */
public class UserAndPropertyAndChargeInfoEntity {

	private Integer chargeInfoId;
	private Integer propertyId;
	private Integer userId;
	private Integer chargeType;
	private Integer payment;
	private String remark;
	private String createTime;
	private String realName;
	private String address;
	private String phone;
	private String status;

	public UserAndPropertyAndChargeInfoEntity() {
	}

	/**
	 * JPA 构造器表达式使用(缴费+房产+业主三表联查投影)
	 */
	public UserAndPropertyAndChargeInfoEntity(Integer chargeInfoId, Integer propertyId, Integer userId,
			Integer chargeType, Integer payment, String remark, String createTime, String address, String realName,
			String phone, String status) {
		this.chargeInfoId = chargeInfoId;
		this.propertyId = propertyId;
		this.userId = userId;
		this.chargeType = chargeType;
		this.payment = payment;
		this.remark = remark;
		this.createTime = createTime;
		this.address = address;
		this.realName = realName;
		this.phone = phone;
		this.status = status;
	}

	public Integer getChargeInfoId() {
		return chargeInfoId;
	}

	public void setChargeInfoId(Integer chargeInfoId) {
		this.chargeInfoId = chargeInfoId;
	}

	public Integer getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(Integer propertyId) {
		this.propertyId = propertyId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getChargeType() {
		return chargeType;
	}

	public void setChargeType(Integer chargeType) {
		this.chargeType = chargeType;
	}

	public Integer getPayment() {
		return payment;
	}

	public void setPayment(Integer payment) {
		this.payment = payment;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "UserAndPropertyAndChargeInfoEntity [chargeInfoId=" + chargeInfoId + ", propertyId=" + propertyId
				+ ", userId=" + userId + ", chargeType=" + chargeType + ", payment=" + payment + ", remark=" + remark
				+ ", createTime=" + createTime + ", realName=" + realName + ", address=" + address + ", phone=" + phone
				+ ", status=" + status + "]";
	}


}
