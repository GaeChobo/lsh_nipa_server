package com.meta.dto;

import java.util.Date;

public class SensorNotificaitonDTO {
	
	private int no;
	private int notification_type;
	private String target_user_id;
	private int message_type;
	private String title;
	private String contents;
	private String sensor_name;
	private Date creation_datetime;
	private int read;
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getNotification_type() {
		return notification_type;
	}
	public void setNotification_type(int notification_type) {
		this.notification_type = notification_type;
	}
	public String getTarget_user_id() {
		return target_user_id;
	}
	public void setTarget_user_id(String target_user_id) {
		this.target_user_id = target_user_id;
	}
	public int getMessage_type() {
		return message_type;
	}
	public void setMessage_type(int message_type) {
		this.message_type = message_type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getSensor_name() {
		return sensor_name;
	}
	public void setSensor_name(String sensor_name) {
		this.sensor_name = sensor_name;
	}
	public Date getCreation_datetime() {
		return creation_datetime;
	}
	public void setCreation_datetime(Date creation_datetime) {
		this.creation_datetime = creation_datetime;
	}
	public int getRead() {
		return read;
	}
	public void setRead(int read) {
		this.read = read;
	}
}
