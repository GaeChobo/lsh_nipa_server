package com.meta.vo;

public class UserMessage {

	private StatusEnum status;
	
	private String message;
	
	private String result;
	
	private ErrorEnum error_code;
	
	private Object data;
	
	private Object users;

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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public ErrorEnum getError_code() {
		return error_code;
	}

	public void setError_code(ErrorEnum error_code) {
		this.error_code = error_code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getUsers() {
		return users;
	}

	public void setUsers(Object users) {
		this.users = users;
	}
	
}
