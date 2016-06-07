package com.timesinternet.alive.search.beans;

import java.io.Serializable;

public class Result implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  String imageLink;
	private  String title;
	private  String source;
	private  String link;
	private String master_category1;
	private String master_category2;
	
	public String getImageLink() {
		return imageLink;
	}
	public  void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}
	public  String getTitle() {
		return title;
	}
	public  void setTitle(String title) {
		this.title = title;
	}
	public String getSource() {
		return source;
	}
	public  void setSource(String source) {
		this.source = source;
	}
	
	public void setLink(String link){
		this.link=link;
	}
	public String getLink(){
		return link;
	}
	public String getMaster_category1() {
		return master_category1;
	}
	public void setMaster_category1(String master_category1) {
		this.master_category1 = master_category1;
	}
	public String getMaster_category2() {
		return master_category2;
	}
	public void setMaster_category2(String master_category2) {
		this.master_category2 = master_category2;
	}
	
	
}

