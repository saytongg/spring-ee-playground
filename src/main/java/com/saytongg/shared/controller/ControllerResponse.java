package com.saytongg.shared.controller;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
public class ControllerResponse<T>{
	
	private boolean success;
	private String message;
	private long timestamp;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T data;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private ErrorInfo error;
	
	@XmlRootElement(name = "error")
	public static class ErrorInfo{
		private int errorCode;
		private String errorMessage;
		
		public ErrorInfo() {}
		
		public ErrorInfo(Exception e) {
			this.errorCode = 500;
			this.errorMessage = e.getMessage();
		}
		
		public int getErrorCode() {
			return errorCode;
		}
		
		public void setErrorCode(int errorCode) {
			this.errorCode = errorCode;
		}
		
		public String getErrorMessage() {
			return errorMessage;
		}
		
		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}
	}
	
	public ControllerResponse(){}
	
	public ControllerResponse(T data, String message){
		this.success = true;
		this.message = message;
		this.timestamp = Instant.now().getEpochSecond();
		this.data = data;
		this.error = null;
	}
	
	public ControllerResponse(Exception e){
		this.success = false;
		this.message = "An error occurred";
		this.timestamp = Instant.now().getEpochSecond();
		this.data = null;
		
		ErrorInfo errorInfo = new ErrorInfo(e);
		this.error = errorInfo;
	}
	

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public ErrorInfo getError() {
		return error;
	}

	public void setError(ErrorInfo error) {
		this.error = error;
	}
}
