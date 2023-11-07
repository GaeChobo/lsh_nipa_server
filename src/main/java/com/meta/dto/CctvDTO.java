package com.meta.dto;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

public class CctvDTO {

	private String area_code;
	private String zone_name;
	private String section_name;
	
	@ApiModelProperty(hidden = true)
	private String cctv_no;
	
	private String path_still_cut;
	private double x;
	private double y;
	private double z;
	
	@ApiModelProperty(hidden = true)
	private Date create_datetime;
	
	@ApiModelProperty(hidden = true)
	private String object_key;
	
	public String getArea_code() {
		return area_code;
	}
	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}
	public String getZone_name() {
		return zone_name;
	}
	public void setZone_name(String zone_name) {
		this.zone_name = zone_name;
	}
	public String getSection_name() {
		return section_name;
	}
	public void setSection_name(String section_name) {
		this.section_name = section_name;
	}
	public String getCctv_no() {
		return cctv_no;
	}
	public void setCctv_no(String cctv_no) {
		this.cctv_no = cctv_no;
	}
	public String getPath_still_cut() {
		return path_still_cut;
	}
	public void setPath_still_cut(String path_still_cut) {
		this.path_still_cut = path_still_cut;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getZ() {
		return z;
	}
	public void setZ(double z) {
		this.z = z;
	}
	public Date getCreate_datetime() {
		return create_datetime;
	}
	public void setCreate_datetime(Date create_datetime) {
		this.create_datetime = create_datetime;
	}
	public String getObject_key() {
		return object_key;
	}
	public void setObject_key(String object_key) {
		this.object_key = object_key;
	}
}
