package com.meta.dto;

public class VirtualFinalChoiceDTO {

	private String user_id;
	private String area_code;
	private String version_virtual;
	private int final_virtual;
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
	public int getFinal_virtual() {
		return final_virtual;
	}
	public void setFinal_virtual(int final_virtual) {
		this.final_virtual = final_virtual;
	}
}
