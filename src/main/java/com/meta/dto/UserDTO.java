package com.meta.dto;

import java.util.Date;

public class UserDTO {

	public String user_id;
	public String user_pw;
	public String company_name;
	public String creation_datetime;
	public String temp_pw;
	public int password_change;
	public Integer super_admin;
	public Integer data_admin;
	public Integer company_admin;
	
	public int getPassword_change() {
		return password_change;
	}
	public void setPassword_change(int password_change) {
		this.password_change = password_change;
	}
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
	
	public int getPasswod_change() {
		return password_change;
	}
	public void setPasswod_change(int password_change) {
		this.password_change = password_change;
	}
	public String getTemp_pw() {
		return temp_pw;
	}
	public void setTemp_pw(String temp_pw) {
		this.temp_pw = temp_pw;
	}
	public String getCreation_datetime() {
		return creation_datetime;
	}
	public void setCreation_datetime(String creation_datetime) {
		this.creation_datetime = creation_datetime;
	}
	public Date expiration_datetime;
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_pw() {
		return user_pw;
	}
	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public Date getExpiration_datetime() {
		return expiration_datetime;
	}
	public void setExpiration_datetime(Date expiration_datetime) {
		this.expiration_datetime = expiration_datetime;
	}


	
}
