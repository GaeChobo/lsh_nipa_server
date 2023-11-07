package com.meta.dto;

public class UserLoginDTO {

	public String token;
	public int password_change;
	public Integer super_admin;
	public Integer data_admin;
	public Integer company_admin;
	
	public Integer getSuper_admin() {
		return super_admin;
	}
	public void setSuper_admin(Integer super_admin) {
		this.super_admin = super_admin;
	}
	public Integer getData_admin() {
		return data_admin;
	}
	public void setData_admin(Integer data_admin) {
		this.data_admin = data_admin;
	}
	public Integer getCompany_admin() {
		return company_admin;
	}
	public void setCompany_admin(Integer company_admin) {
		this.company_admin = company_admin;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getPassword_change() {
		return password_change;
	}
	public void setPassword_change(int password_change) {
		this.password_change = password_change;
	}
}
