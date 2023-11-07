package com.meta.dto;

import java.util.Date;

public class AdminAreaDTO {

	private String company_admin;
	private String area_code;
	private String construction_name;
	private int area_type;
	private String construction_detail;
	private String area_address;
	private String owner_name;
	private String path_img;
	private String owner_contact;
	private Date creation_datetime;
	private String company_name;

	public Date getCreation_datetime() {
		return creation_datetime;
	}
	public void setCreation_datetime(Date creation_datetime) {
		this.creation_datetime = creation_datetime;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getCompany_admin() {
		return company_admin;
	}
	public void setCompany_admin(String company_admin) {
		this.company_admin = company_admin;
	}
	public String getArea_code() {
		return area_code;
	}
	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	public String getConstruction_name() {
		return construction_name;
	}
	public void setConstruction_name(String construction_name) {
		this.construction_name = construction_name;
	}
	public int getArea_type() {
		return area_type;
	}
	public void setArea_type(int area_type) {
		this.area_type = area_type;
	}
	public String getConstruction_detail() {
		return construction_detail;
	}
	public void setConstruction_detail(String construction_detail) {
		this.construction_detail = construction_detail;
	}
	public String getArea_address() {
		return area_address;
	}
	public void setArea_address(String area_address) {
		this.area_address = area_address;
	}
	public String getOwner_name() {
		return owner_name;
	}
	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}
	public String getPath_img() {
		return path_img;
	}
	public void setPath_img(String path_img) {
		this.path_img = path_img;
	}
	public String getOwner_contact() {
		return owner_contact;
	}
	public void setOwner_contact(String owner_contact) {
		this.owner_contact = owner_contact;
	}
}
