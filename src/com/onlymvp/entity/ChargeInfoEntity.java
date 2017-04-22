package com.onlymvp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 缴费信息实体类
 * 
 * chengzhi
 *
 */
@Entity
@Table(name = "ChargeInfo")
public class ChargeInfoEntity {

	private Integer id;
	private Integer propertyId;
	private Integer userId;
	private Integer chargeType;
	private Integer payment;
	private String remark;
	private Integer delStatus;
	private String createTime;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(Integer delStatus) {
		this.delStatus = delStatus;
	}

	@Override
	public String toString() {
		return "ChargeInfoEntity [id=" + id + ", propertyId=" + propertyId + ", userId=" + userId + ", chargeType="
				+ chargeType + ", payment=" + payment + ", remark=" + remark + ", delStatus=" + delStatus
				+ ", createTime=" + createTime + "]";
	}

}
