package com.zc.news.model.entity;

import java.util.List;

/**
 * 	用户中心
 * @author Administrator
 *
 * @param <T>
 */
public class User<T> {
	
	private String uid;//用户名
	private String portrait;//用户图标
	private int integration;//用户积分票总数
	private int comnum;//评论总数
	private List<T> loginlog;
	
	public User() {
		super();
	}
	
	public User(String uid, String portrait, int integration, int comnum,
			List<T> loginlog) {
		super();
		this.uid = uid;
		this.portrait = portrait;
		this.integration = integration;
		this.comnum = comnum;
		this.loginlog = loginlog;
	}

	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPortrait() {
		return portrait;
	}
	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}
	public int getIntegration() {
		return integration;
	}
	public void setIntegration(int integration) {
		this.integration = integration;
	}
	public int getComnum() {
		return comnum;
	}
	public void setComnum(int comnum) {
		this.comnum = comnum;
	}
	public List<T> getLoginlog() {
		return loginlog;
	}
	public void setLoginlog(List<T> loginlog) {
		this.loginlog = loginlog;
	}
	

}
