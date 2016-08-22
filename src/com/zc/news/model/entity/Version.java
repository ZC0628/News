package com.zc.news.model.entity;

public class Version {
	
	/*更新软件下载地址 */ 
	private String link ; 
	/*应用包名，用于二次确认 */ 
	private String packageName; 
	/*md5校验，判断安装包是否损坏 */ 
	private String md5 ; 
	/*当前最新版本的版本号码 */ 
	private String version;
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Version() {
		super();
	}
	public Version(String link, String packageName, String md5, String version) {
		super();
		this.link = link;
		this.packageName = packageName;
		this.md5 = md5;
		this.version = version;
	}
	@Override
	public String toString() {
		return "Version [link=" + link + ", packageName=" + packageName
				+ ", md5=" + md5 + ", version=" + version + "]";
	}
	
}
