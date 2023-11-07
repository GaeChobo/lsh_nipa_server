package com.meta.dto;

public class InterferenceFinalChoiceDTO {

	private String user_id;
	private String area_code;
	private String version_interference;
	private int final_interference;
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
	public int getFinal_interference() {
		return final_interference;
	}
	public void setFinal_interference(int final_interference) {
		this.final_interference = final_interference;
	}
}
