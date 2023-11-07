package com.meta.dto;

public class AssignListDTO {

	public String user_id;
	public String area_code;
	public int company_admin;
	
	public int getCompany_admin() {
		return company_admin;
	}
	public void setCompany_admin(int company_admin) {
		this.company_admin = company_admin;
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

}
