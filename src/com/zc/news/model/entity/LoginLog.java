package com.zc.news.model.entity;

/**
 * 		��¼��־�еĵ�¼��Ϣ
 * @author Administrator
 *
 */
public class LoginLog {

	private String time;//��¼ʱ��
	private String address;//��¼��ַ
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
