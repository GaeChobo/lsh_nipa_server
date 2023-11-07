package com.meta.vo;


public class Message {
	
	private StatusEnum status;
	
	private String message;
	
	private String result;
	
	private ErrorEnum error_code;
	
	private Object data;

	public ErrorEnum getError_code() {
		return error_code;
	}

	public void setError_code(ErrorEnum error_code) {
		this.error_code = error_code;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	
	public Message() {
		this.status = StatusEnum.OK;
		this.message = null;
	}
	
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
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
