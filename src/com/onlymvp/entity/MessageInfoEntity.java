package com.onlymvp.entity;

/**
 * 返回信息实体类
 * 
 * @describe 对Ajax请求返回的Json数据
 * chengzhi
 *
 */
public class MessageInfoEntity {
	private String status;// 响应信息
	private String desc;// 描述
	private Object detail;// 返回数据

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Object getDetail() {
		return detail;
	}

	public void setDetail(Object detail) {
		this.detail = detail;
	}

	@Override
	public String toString() {
		return "MessageInfoEntity [status=" + status + ", desc=" + desc + ", detail=" + detail + "]";
	}

}
