package com.zc.news.model.entity;

/**
 * 		登录日志中的登录信息
 * @author Administrator
 *
 */
public class LoginLog {

	private String time;//登录时间
	private String address;//登录地址
	private int device;
	
	public LoginLog() {
		super();
	}
	public LoginLog(String time, String address, int device) {
		super();
		this.time = time;
		this.address = address;
		this.device = device;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getDevice() {
		return device;
	}
	public void setDevice(int device) {
		this.device = device;
	}
	@Override
	public String toString() {
		return "LoginLog [time=" + time + ", address=" + address + ", device="
				+ device + "]";
	}
	
}
