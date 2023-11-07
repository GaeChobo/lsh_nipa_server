package com.meta.dto;

import java.util.Date;

public class VirtualDTO {

	private String user_id;
	private String area_code;
	private String version_drawing;
	private String version_interference;
	private Date creation_datetime;
	private String path_result_virtual;
	private String version_virtual;
	private String virtual_title;
	private int  danger_point;
	
	
	public String getVersion_drawing() {
		return version_drawing;
	}
	public void setVersion_drawing(String version_drawing) {
		this.version_drawing = version_drawing;
	}
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
	public String getVersion_interference() {
		return version_interference;
	}
	public void setVersion_interference(String version_interference) {
		this.version_interference = version_interference;
	}
	public Date getCreation_datetime() {
		return creation_datetime;
	}
	public void setCreation_datetime(Date creation_datetime) {
		this.creation_datetime = creation_datetime;
	}
	public String getPath_result_virtual() {
		return path_result_virtual;
	}
	public void setPath_result_virtual(String path_result_virtual) {
		this.path_result_virtual = path_result_virtual;
	}
	public String getVersion_virtual() {
		return version_virtual;
	}
	public void setVersion_virtual(String version_virtual) {
		this.version_virtual = version_virtual;
	}
	public String getVirtual_title() {
		return virtual_title;
	}
	public void setVirtual_title(String virtual_title) {
		this.virtual_title = virtual_title;
	}
	public int getDanger_point() {
		return danger_point;
	}
	public void setDanger_point(int danger_point) {
		this.danger_point = danger_point;
	}
}
