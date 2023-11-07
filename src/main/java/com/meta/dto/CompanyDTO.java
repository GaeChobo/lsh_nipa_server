package com.meta.dto;

public class CompanyDTO {

	private String company_name_kr;
	
	private String domain_name;
	
	private String creation_datetime;
	
	private String company_name;
	
	private String company_admin;
	
	public String getCompany_admin() {
		return company_admin;
	}
	public void setCompany_admin(String company_admin) {
		this.company_admin = company_admin;
	}
	public String getCreation_datetime() {
		return creation_datetime;
	}
	public void setCreation_datetime(String creation_datetime) {
		this.creation_datetime = creation_datetime;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getCompany_name_kr() {
		return company_name_kr;
	}
	public void setCompany_name_kr(String company_name_kr) {
		this.company_name_kr = company_name_kr;
	}
	public String getDomain_name() {
		return domain_name;
	}
	public void setDomain_name(String domain_name) {
		this.domain_name = domain_name;
	}

}
