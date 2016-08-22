package com.zc.news.model.entity;

import java.io.Serializable;

public class News implements Serializable{

	public String summary;
	public String icon;
	public String stamp;
	public String title;
	public int nid;
	public String link;
	public int type;
	public News() {
		super();
	}
	
	public News(String summary, String icon, String stamp, String title,
			int nid, String link, int type) {
		super();
		this.summary = summary;
		this.icon = icon;
		this.stamp = stamp;
		this.title = title;
		this.nid = nid;
		this.link = link;
		this.type = type;
	}

	@Override
	public String toString() {
		return "News [summary=" + summary + ", icon=" + icon + ", stamp="
				+ stamp + ", title=" + title + ", nid=" + nid + ", link="
				+ link + ", type=" + type + "]";
	}

	
	
}
