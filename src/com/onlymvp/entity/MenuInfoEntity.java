package com.onlymvp.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 菜单信息实体类
 * 
 * chengzhi
 *
 */
@Entity
@Table(name = "MenuInfo")
public class MenuInfoEntity implements Comparable<MenuInfoEntity> {
	private Integer id;
	private Integer parentId;
	private String name;
	private String url;
	private Integer switchStatus;
	private Integer delStatus;
	private Integer orderBy;
	private Date createTime;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getSwitchStatus() {
		return switchStatus;
	}

	public void setSwitchStatus(Integer switchStatus) {
		this.switchStatus = switchStatus;
	}

	public Integer getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(Integer delStatus) {
		this.delStatus = delStatus;
	}

	public Integer getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "MenuInfoEntity [id=" + id + ", parentId=" + parentId + ", name=" + name + ", url=" + url
				+ ", switchStatus=" + switchStatus + ", delStatus=" + delStatus + ", orderBy=" + orderBy
				+ ", createTime=" + createTime + "]";
	}

	@Override
	public int compareTo(MenuInfoEntity o) {

		return this.orderBy - o.getOrderBy();
	}

}
