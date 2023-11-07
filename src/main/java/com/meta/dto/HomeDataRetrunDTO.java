package com.meta.dto;

public class HomeDataRetrunDTO {

	private String company_name;
	
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	private String owner_name;
	
	public String getOwner_name() {
		return owner_name;
	}
	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}
	
	private String area_code;
	private String construction_name;
	private int area_type;
	private String construction_detail;
	private Integer[] facility_type;
	private String area_address;
	private String owner_contact;
	private String supervisor_name;
	private String supervisor_contact;
	private String contractor_name;
	private String contractor_contact;
	private String period_start;
	private String period_end;
	private int status;
	private String path_img;
	private String creation_datetime;

	public Integer[] getFacility_type() {
		return facility_type;
	}
	public void setFacility_type(Integer[] facility_type) {
		this.facility_type = facility_type;
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
	public String getOwner_contact() {
		return owner_contact;
	}
	public void setOwner_contact(String owner_contact) {
		this.owner_contact = owner_contact;
	}
	public String getSupervisor_name() {
		return supervisor_name;
	}
	public void setSupervisor_name(String supervisor_name) {
		this.supervisor_name = supervisor_name;
	}
	public String getSupervisor_contact() {
		return supervisor_contact;
	}
	public void setSupervisor_contact(String supervisor_contact) {
		this.supervisor_contact = supervisor_contact;
	}
	public String getContractor_name() {
		return contractor_name;
	}
	public void setContractor_name(String contractor_name) {
		this.contractor_name = contractor_name;
	}
	public String getContractor_contact() {
		return contractor_contact;
	}
	public void setContractor_contact(String contractor_contact) {
		this.contractor_contact = contractor_contact;
	}
	public String getPeriod_start() {
		return period_start;
	}
	public void setPeriod_start(String period_start) {
		this.period_start = period_start;
	}
	public String getPeriod_end() {
		return period_end;
	}
	public void setPeriod_end(String period_end) {
		this.period_end = period_end;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getPath_img() {
		return path_img;
	}
	public void setPath_img(String path_img) {
		this.path_img = path_img;
	}
	public String getCreation_datetime() {
		return creation_datetime;
	}
	public void setCreation_datetime(String creation_datetime) {
		this.creation_datetime = creation_datetime;
	}
}
