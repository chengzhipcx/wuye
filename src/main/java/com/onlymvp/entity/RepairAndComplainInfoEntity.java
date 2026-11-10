package com.onlymvp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 报修,投诉信息实体类
 * 
 * chengzhi
 *
 */
@Entity
@Table(name = "RepairAndComplainInfo")
public class RepairAndComplainInfoEntity {

	private Integer id;
	private Integer userId;
	private Integer propertyId;
	private String content;
	private String dealUser;
	private Integer infoType;
	private Integer solveStatus;
	private Integer delStatus;
	private String createTime;
	private String solveTime;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public Integer getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(Integer propertyId) {
		this.propertyId = propertyId;
	}

	public String getDealUser() {
		return dealUser;
	}

	public void setDealUser(String dealUser) {
		this.dealUser = dealUser;
	}

	@Override
	public String toString() {
		return "RepairAndComplainInfoEntity [id=" + id + ", userId=" + userId + ", propertyId=" + propertyId
				+ ", content=" + content + ", dealUser=" + dealUser + ", infoType=" + infoType + ", solveStatus="
				+ solveStatus + ", delStatus=" + delStatus + ", createTime=" + createTime + ", solveTime=" + solveTime
				+ "]";
	}

}
