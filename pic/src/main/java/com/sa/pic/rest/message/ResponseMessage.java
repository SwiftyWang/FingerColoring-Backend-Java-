/**
 * 
 */
package com.sa.pic.rest.message;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ResponseMessage {

	public final static String SUCCESS = "Success";
	public final static String FAIL = "Fail";

	private Integer errorCode;
	private String message;
	private Object result;

	/**
	 * 
	 */
	public ResponseMessage() {
	}

	/**
	 * @param isError
	 * @param message
	 * @param result
	 */
	public ResponseMessage(String message, Object result) {
		this.message = message;
		this.result = result;
	}

	/**
	 * @param isError
	 * @param message
	 * @param result
	 * @param token
	 */
	public ResponseMessage(String message, Object result, String token) {
		this.message = message;
		this.result = result;
	}

	@JsonIgnore
	public boolean isError() {
		if (getErrorCode() == null) {
			return false;
		}
		return true;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the result
	 */
	public Object getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(Object result) {
		this.result = result;
	}

	/**
	 * @return the errorCode
	 */
	public Integer getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode
	 *            the errorCode to set
	 */
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

}
