package com.meta.dto;

import java.util.Date;

public class Request_preprocessingDTO {

	private String user_id;
	private String area_code;
	private String request_title;
	private int request_status;
	private String description;
	private String path_drawing;
	private String path_csv;
	private Date creation_datetime;
	private Date update_datetime;
	private String version_drawing;
	
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
	public String getRequest_title() {
		return request_title;
	}
	public void setRequest_title(String request_title) {
		this.request_title = request_title;
	}
	public int getRequest_status() {
		return request_status;
	}
	public void setRequest_status(int request_status) {
		this.request_status = request_status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPath_drawing() {
		return path_drawing;
	}
	public void setPath_drawing(String path_drawing) {
		this.path_drawing = path_drawing;
	}
	public String getPath_csv() {
		return path_csv;
	}
	public void setPath_csv(String path_csv) {
		this.path_csv = path_csv;
	}
	public Date getCreation_datetime() {
		return creation_datetime;
	}
	public void setCreation_datetime(Date creation_datetime) {
		this.creation_datetime = creation_datetime;
	}
	public Date getUpdate_datetime() {
		return update_datetime;
	}
	public void setUpdate_datetime(Date update_datetime) {
		this.update_datetime = update_datetime;
	}
	public String getVersion_drawing() {
		return version_drawing;
	}
	public void setVersion_drawing(String version_drawing) {
		this.version_drawing = version_drawing;
	}
}
