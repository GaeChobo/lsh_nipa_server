package com.meta.dto;

import java.util.Date;

public class GasSensorDTO {

	private String area_code;
	private String sensor_name;
	private String ss_full_id;
	private String sensor_status;
	private Date time; 
	private String value;
	
	public String getArea_code() {
		return area_code;
	}
	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	public String getSensor_name() {
		return sensor_name;
	}
	public void setSensor_name(String sensor_name) {
		this.sensor_name = sensor_name;
	}
	public String getSs_full_id() {
		return ss_full_id;
	}
	public void setSs_full_id(String ss_full_id) {
		this.ss_full_id = ss_full_id;
	}
	public String getSensor_status() {
		return sensor_status;
	}
	public void setSensor_status(String sensor_status) {
		this.sensor_status = sensor_status;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	
}
