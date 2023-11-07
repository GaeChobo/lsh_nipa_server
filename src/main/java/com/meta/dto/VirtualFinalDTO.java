package com.meta.dto;

import java.util.Date;

public class VirtualFinalDTO {

	private String user_id;
	private String area_code;
	private String version_virtual;
	private Date update_datetime;
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getArea_code() {
		return area_code;
	}
	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	public String getVersion_virtual() {
		return version_virtual;
	}
	public void setVersion_virtual(String version_virtual) {
		this.version_virtual = version_virtual;
	}
	public Date getUpdate_datetime() {
		return update_datetime;
	}
	public void setUpdate_datetime(Date update_datetime) {
		this.update_datetime = update_datetime;
	}
}
