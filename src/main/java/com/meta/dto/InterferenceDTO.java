package com.meta.dto;

public class InterferenceDTO {

	private String user_id;
	private String area_code;
	private String version_drawing;
	private String path_result_interference;
	private String version_interference;
	private int interference_point;
	private String request_title;
	
	public String getRequest_title() {
		return request_title;
	}
	public void setRequest_title(String request_title) {
		this.request_title = request_title;
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
	public String getVersion_drawing() {
		return version_drawing;
	}
	public void setVersion_drawing(String version_drawing) {
		this.version_drawing = version_drawing;
	}
	public String getPath_result_interference() {
		return path_result_interference;
	}
	public void setPath_result_interference(String path_result_interference) {
		this.path_result_interference = path_result_interference;
	}
	public String getVersion_interference() {
		return version_interference;
	}
	public void setVersion_interference(String version_interference) {
		this.version_interference = version_interference;
	}
	public int getInterference_point() {
		return interference_point;
	}
	public void setInterference_point(int interference_point) {
		this.interference_point = interference_point;
	}
}
