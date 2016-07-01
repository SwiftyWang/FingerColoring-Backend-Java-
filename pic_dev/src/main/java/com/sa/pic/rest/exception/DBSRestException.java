package com.sa.pic.rest.exception;

public class DBSRestException extends Exception{

	private static final long serialVersionUID = 1L;
	private int errorCode;

	public DBSRestException(int errorCode) {
		super();
		this.errorCode = errorCode;
	}

	public DBSRestException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}
	
	
		

}
