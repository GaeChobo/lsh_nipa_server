package com.meta.dto;

public class UpdateInterferenceDetailDTO {

	private String user_id;
	private String area_code;
	private String version_interference;
	private int interference_point;
	private double[] x;
	private double[] y;
	private double[] z;		
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getArea_code() {
		return area_code;
	}
	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	public String getVersion_interference() {
		return version_interference;
	}
	public void setVersion_interference(String version_interference) {
		this.version_interference = version_interference;
	}
	public int getInterference_point() {
		return interference_point;
	}
	public void setInterference_point(int interference_point) {
		this.interference_point = interference_point;
	}
	public double[] getX() {
		return x;
	}
	public void setX(double[] x) {
		this.x = x;
	}
	public double[] getY() {
		return y;
	}
	public void setY(double[] y) {
		this.y = y;
	}
	public double[] getZ() {
		return z;
	}
	public void setZ(double[] z) {
		this.z = z;
	}
}
