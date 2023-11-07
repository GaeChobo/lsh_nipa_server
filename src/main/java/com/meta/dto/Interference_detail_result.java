package com.meta.dto;

public class Interference_detail_result {

	public String request_title;
	public String version_interference;
	public int final_interference;
	public interference_data interference_data;
	
	public String getRequest_title() {
		return request_title;
	}
	public void setRequest_title(String request_title) {
		this.request_title = request_title;
	}
	public String getVersion_interference() {
		return version_interference;
	}
	public void setVersion_interference(String version_interference) {
		this.version_interference = version_interference;
	}
	public int getFinal_interference() {
		return final_interference;
	}
	public void setFinal_interference(int final_interference) {
		this.final_interference = final_interference;
	}
	public interference_data getInterference_data() {
		return interference_data;
	}
	public void setInterference_data(interference_data interference_data) {
		this.interference_data = interference_data;
	}
}
