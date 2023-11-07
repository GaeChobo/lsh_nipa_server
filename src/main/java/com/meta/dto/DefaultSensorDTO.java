package com.meta.dto;

import java.util.Date;

public class DefaultSensorDTO {

	private String area_code; 
	private String zone_name;
	private String section_name;
	private String sensor_name;
	private String model_name;
	private String detection_target;
	private double x;
	private double y;
	private double z;
	private String sensor_type;
	private double threshold_value;
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
	public String getSensor_name() {
		return sensor_name;
	}
	public void setSensor_name(String sensor_name) {
		this.sensor_name = sensor_name;
	}
	public String getModel_name() {
		return model_name;
	}
	public void setModel_name(String model_name) {
		this.model_name = model_name;
	}
	public String getDetection_target() {
		return detection_target;
	}
	public void setDetection_target(String detection_target) {
		this.detection_target = detection_target;
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

	public String getSensor_type() {
		return sensor_type;
	}
	public void setSensor_type(String sensor_type) {
		this.sensor_type = sensor_type;
	}
	public double getThreshold_value() {
		return threshold_value;
	}
	public void setThreshold_value(double threshold_value) {
		this.threshold_value = threshold_value;
	}
	public Date getManufacturing_date() {
		return manufacturing_date;
	}
	public void setManufacturing_date(Date manufacturing_date) {
		this.manufacturing_date = manufacturing_date;
	}
	public Date getInstallation_date() {
		return installation_date;
	}
	public void setInstallation_date(Date installation_date) {
		this.installation_date = installation_date;
	}
	public String getSs_full_id() {
		return ss_full_id;
	}
	public void setSs_full_id(String ss_full_id) {
		this.ss_full_id = ss_full_id;
	}
	private Date manufacturing_date;
	private Date installation_date;
	private String ss_full_id;
}
