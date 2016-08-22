package com.zc.news.model.entity;

import java.util.List;

public class BaseEntity<T> {
	
	private String message;
	private int status;
	private List<T> data;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public BaseEntity(String message, int status, List<T> data) {
		super();
		this.message = message;
		this.status = status;
		this.data = data;
	}
	@Override
	public String toString() {
		return "BaseEntity [message=" + message + ", status=" + status
				+ ", data=" + data + "]";
	}
	
	
}
