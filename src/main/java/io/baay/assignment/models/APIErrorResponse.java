package io.baay.assignment.models;

import org.springframework.http.HttpStatus;

public class APIErrorResponse {
	
	private HttpStatus httpStatus;
	private String message;
	
	public APIErrorResponse(HttpStatus httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}
	
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	
	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
