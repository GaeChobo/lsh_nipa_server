package com.meta.dto;

public class UpdateVirtualDetailDTO {

	private String user_id;
	private String area_code;
	private String version_virtual;
	private String version_drawing;
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
	public String getVersion_virtual() {
		return version_virtual;
	}
	public void setVersion_virtual(String version_virtual) {
		this.version_virtual = version_virtual;
	}
	public String getVersion_drawing() {
		return version_drawing;
	}
	public void setVersion_drawing(String version_drawing) {
		this.version_drawing = version_drawing;
	}
	public int getDanger_point() {
		return danger_point;
	}
	public void setDanger_point(int danger_point) {
		this.danger_point = danger_point;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public double[] getWidth() {
		return width;
	}
	public void setWidth(double[] width) {
		this.width = width;
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
	private int danger_point;
	private String location;
	private double[] width;
	private double[] x;
	private double[] y;
	private double[] z;
}
