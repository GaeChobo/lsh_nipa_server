package com.meta.dto;

import java.util.Date;
import java.util.List;

public class AssginUserDTO {
	
	private String area_code;
	private String company_admin;

	private List<UserResponseDTO> users;
	
	public List<UserResponseDTO> getUsers() {
		return users;
	}
	public void setUsers(List<UserResponseDTO> users) {
		this.users = users;
	}
	public String getArea_code() {
		return area_code;
	}
	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	public String getCompany_admin() {
		return company_admin;
	}
	public void setCompany_admin(String company_admin) {
		this.company_admin = company_admin;
	}
}
