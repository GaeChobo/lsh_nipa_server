package com.meta.dto;

import java.util.Date;

public class Mv_SensorDTO {

	public String area_code;
	public String sensor_name;
	public String sensor_status;
	public Date time;
	public String ss_full_id;
	public String value;
	
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
	public String getSs_full_id() {
		return ss_full_id;
	}
	public void setSs_full_id(String ss_full_id) {
		this.ss_full_id = ss_full_id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
