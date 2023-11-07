package com.meta.dto;

public class User_verificationDTO {

	private String user_id;
	private String verification_code;
	private String creation_datetime;
	private int verification_status;
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getVerification_code() {
		return verification_code;
	}
	public int getVerification_status() {
		return verification_status;
	}
	public void setVerification_status(int verification_status) {
		this.verification_status = verification_status;
	}
	public void setVerification_code(String verification_code) {
		this.verification_code = verification_code;
	}
	public String getCreation_datetime() {
		return creation_datetime;
	}
	public void setCreation_datetime(String creation_datetime) {
		this.creation_datetime = creation_datetime;
	}

}
