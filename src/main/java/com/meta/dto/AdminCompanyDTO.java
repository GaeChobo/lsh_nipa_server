package com.meta.dto;

import java.util.List;

public class AdminCompanyDTO {
	
	private String domain_name;
	private String company_name;
	private String company_admin;
	private List<String> area_code;
	private String company_name_kr;
	
	public String getDomain_name() {
		return domain_name;
	}
	public void setDomain_name(String domain_name) {
		this.domain_name = domain_name;
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


	public List<String> getArea_code() {
		return area_code;
	}
	public void setArea_code(List<String> area_code) {
		this.area_code = area_code;
	}
	public String getCompany_name_kr() {
		return company_name_kr;
	}
	public void setCompany_name_kr(String company_name_kr) {
		this.company_name_kr = company_name_kr;
	}
}
