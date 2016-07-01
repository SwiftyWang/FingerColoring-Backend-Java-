package com.sa.pic.dao.domain;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

@Entity
public class BuzzPostData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8088252442615391685L;

	@Id
	@Column(nullable = false)
	private String postId;

	@Column(nullable = false)
	private String pageId;

	@Column(nullable = true)
	private String title;

	@Column(nullable = true)
	private String content;

	@Column(nullable = true, columnDefinition = "blob")
	private byte[] contentByte;

	@Column(nullable = false)
	private Long engagementNumber;

	@Column(nullable = true, length = 512)
	private String url;

	@Column(nullable = true, length = 1024)
	private String picUrl;

	@Column(nullable = false)
	private Date publishDate;

	@Column(nullable = true)
	private Date updateDate;

	@Column(nullable = true)
	private Integer isComment;

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getEngagementNumber() {
		return engagementNumber;
	}

	public void setEngagementNumber(Long engagementNumber) {
		this.engagementNumber = engagementNumber;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getIsComment() {
		return isComment;
	}

	public void setIsComment(Integer isComment) {
		this.isComment = isComment;
	}

	/**
	 * @return the contentByte
	 */
	public byte[] getContentByte() {
		return contentByte;
	}

	/**
	 * @param contentByte
	 *            the contentByte to set
	 */
	public void setContentByte(byte[] contentByte) {
		this.contentByte = contentByte;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
