package com.onlymvp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 菜单角色实体类
 * 
 * chengzhi
 *
 */
@Entity
@Table(name = "MenuAndRole")
public class MenuAndRoleEntity {
	private Integer id;
	private Integer menuId;
	private Integer adminId;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	@Column(name = "adminId")
	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	@Override
	public String toString() {
		return "MenuAndRoleEntity [id=" + id + ", menuId=" + menuId + ", adminId=" + adminId + "]";
	}

}
