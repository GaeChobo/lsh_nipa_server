package com.meta.vo;

public class HomeMessage {

	private StatusEnum status;
	
	private int movements; 
	
	public int getMovements() {
		return movements;
	}

	public void setMovements(int movements) {
		this.movements = movements;
	}
	private String message;
	
	private String result;
	
	private ErrorEnum error_code;
	
	private Object Companydata;
	
	private Object Sitedata;

	public Object getSitedata() {
		return Sitedata;
	}

	public void setSitedata(Object sitedata) {
		Sitedata = sitedata;
	}

	public ErrorEnum getError_code() {
		return error_code;
	}

	public void setError_code(ErrorEnum error_code) {
		this.error_code = error_code;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	
	public HomeMessage() {
		this.status = StatusEnum.OK;
		this.message = null;
	}
	
	public StatusEnum getStatus() {
		return status;
	}
	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public Object getCompanydata() {
		return Companydata;
	}

	public void setCompanydata(Object companydata) {
		Companydata = companydata;
	}

}
