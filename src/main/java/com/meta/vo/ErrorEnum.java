package com.meta.vo;

public enum ErrorEnum {
	
	NONE(000, "NONE"),
	FILE_ERROR(001, "FILE_ERROR"),
	EXCEL_ERROR(002, "EXCEL_ERROR"),
	OS_ERROR(003, "OS_ERROR"),
	INTERNAL_SERVER_ERROR(004, "INTERNAL_SERVER_ERROR");
	
	int ErrorCode;
	String code;

	ErrorEnum(int ErrorCode, String code) {
		this.ErrorCode = ErrorCode;
		this.code = code;
	}
	
}
