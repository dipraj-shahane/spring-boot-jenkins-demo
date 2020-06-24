package com.in28minutes.rest.webservices.restfulwebservices.user.exception;

import java.util.Date;


public class ExceptionResponse {
	private Date time;
	private String message;
	private String description;

	public ExceptionResponse() {
	}

	public ExceptionResponse(Date time, String message, String description) {
		this.time = time;
		this.message = message;
		this.description = description;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
