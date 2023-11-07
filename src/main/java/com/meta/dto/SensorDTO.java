package com.meta.dto;

import java.util.Date;

public class SensorDTO {

	public String ss_id;
	public String ss_Stat;
	public String ss_Value;
	public String ss_date;
	public Date Mdfy_Dttm;
	
	private String Hour1time;
	private String Hour2time;
	
	public void setHour1time(String hour1time) {
		Hour1time = hour1time;
	}
	public void setHour2time(String hour2time) {
		Hour2time = hour2time;
	}
	public String getSs_id() {
		return ss_id;
	}
	public void setSs_id(String ss_id) {
		this.ss_id = ss_id;
	}
	public String getSs_Stat() {
		return ss_Stat;
	}
	public void setSs_Stat(String ss_Stat) {
		this.ss_Stat = ss_Stat;
	}
	public String getSs_Value() {
		return ss_Value;
	}
	public void setSs_Value(String ss_Value) {
		this.ss_Value = ss_Value;
	}
	public String getSs_date() {
		return ss_date;
	}
	public void setSs_date(String ss_date) {
		this.ss_date = ss_date;
	}
	public Date getMdfy_Dttm() {
		return Mdfy_Dttm;
	}
	public void setMdfy_Dttm(Date mdfy_Dttm) {
		Mdfy_Dttm = mdfy_Dttm;
	}

	
}
