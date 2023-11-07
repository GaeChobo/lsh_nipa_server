package com.meta.dto;

import java.util.Date;

public class Result_SensorDTO {

	private String area_code;
	private String sensor_name;
	private String sensor_status;
	private String detection_target;
	private Date time;
	private String ss_full_id;
	private String value;
	private int sensor_type;
	private String section_name;
	
	public int getSensor_type() {
		return sensor_type;
	}
	public void setSensor_type(int sensor_type) {
		this.sensor_type = sensor_type;
	}
	public String getSection_name() {
		return section_name;
	}
	public void setSection_name(String section_name) {
		this.section_name = section_name;
	}
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
	public String getDetection_target() {
		return detection_target;
	}
	public void setDetection_target(String detection_target) {
		this.detection_target = detection_target;
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
