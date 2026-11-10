package com.onlymvp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 房产信息实体类
 * 
 * chengzhi
 *
 */
@Entity
@Table(name = "PropertyInfo")
public class PropertyInfoEntity {
	private Integer id;
	private String address;
	private Integer area;
	private String layout;
	private Integer saleStatus;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public Integer getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(Integer saleStatus) {
		this.saleStatus = saleStatus;
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

	@Override
	public String toString() {
		return "PropertyInfoEntity [id=" + id + ", address=" + address + ", area=" + area + ", layout=" + layout
				+ ", saleStatus=" + saleStatus + ", delStatus=" + delStatus + ", createTime=" + createTime + "]";
	}

}
