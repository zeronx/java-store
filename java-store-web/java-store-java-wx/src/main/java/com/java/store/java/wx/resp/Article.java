package com.java.store.java.wx.resp;

import java.io.Serializable;

public class Article implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String title; // 图文消息标题
	private String description; // 图文消息内容
	private String picUrl; // 图文消息插图url
	private String detailUrl; // 图文消息点击跳转后详情url
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getDetailUrl() {
		return detailUrl;
	}
	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}
	
}
