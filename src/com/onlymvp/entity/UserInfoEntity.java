package com.onlymvp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 业主信息实体类
 * 
 * chengzhi
 *
 */
@Entity
@Table(name = "UserInfo")
public class UserInfoEntity {
	private Integer id;
	private Integer propertyId;
	private String userName;
	private String userPwd;
	private String realName;
	private String phone;
	private String idCard;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
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

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
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

	public Integer getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(Integer propertyId) {
		this.propertyId = propertyId;
	}

	@Override
	public String toString() {
		return "UserInfoEntity [id=" + id + ", propertyId=" + propertyId + ", userName=" + userName + ", userPwd="
				+ userPwd + ", realName=" + realName + ", phone=" + phone + ", idCard=" + idCard + ", delStatus="
				+ delStatus + ", createTime=" + createTime + "]";
	}

}
