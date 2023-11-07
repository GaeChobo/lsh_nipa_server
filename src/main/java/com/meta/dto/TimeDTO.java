package com.meta.dto;

import java.util.Date;

public class TimeDTO {

	private String area_code;
	private String user_id;
	
	private Date expiration_datetime;

	public String getArea_code() {
		return area_code;
	}

	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Date getExpiration_datetime() {
		return expiration_datetime;
	}

	public void setExpiration_datetime(Date expiration_datetime) {
		this.expiration_datetime = expiration_datetime;
	}
}
