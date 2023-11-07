package com.meta.dto;

import java.util.Date;

public class RSensorDTO {

	public String ss_ID;
	public String ss_Stat;
	public double ss_Value;
	public Date Mdfy_Dttm;
	public String getSs_ID() {
		return ss_ID;
	}
	public void setSs_ID(String ss_ID) {
		this.ss_ID = ss_ID;
	}
	public String getSs_Stat() {
		return ss_Stat;
	}
	public void setSs_Stat(String ss_Stat) {
		this.ss_Stat = ss_Stat;
	}
	public double getSs_Value() {
		return ss_Value;
	}
	public void setSs_Value(double ss_Value) {
		this.ss_Value = ss_Value;
	}
	public Date getMdfy_Dttm() {
		return Mdfy_Dttm;
	}
	public void setMdfy_Dttm(Date mdfy_Dttm) {
		Mdfy_Dttm = mdfy_Dttm;
	}
	

	
}
