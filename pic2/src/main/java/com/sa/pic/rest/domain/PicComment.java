package com.sa.pic.rest.domain;

public class PicComment {
	 private Long uid;
	 private String uname;
	 private String content;
	 
	  public PicComment(){
		  
	  }
	  
	  public PicComment(Long uid, String uname, String content){
		  this.uid = uid;
		  this.uname=uname;
		  this.content=content;
	  }

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	  

}
