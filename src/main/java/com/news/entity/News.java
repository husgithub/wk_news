package com.news.entity;
// Generated 2017-12-18 9:55:06 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * News generated by hbm2java
 */
public class News implements java.io.Serializable {

	private Integer id;
	private String title;
	private String editor;
	private String source;
	private String detail;
	private String imgsrc;
	private String videosrc;
	private Integer browsenum;
	private Date time;
	private Date modifiedtime;
	private String type;
	private Integer isdelete;
	
	private NewsType newsType;
	
	private Integer commentCount;  //评论数量

	public News() {
	}

	public News(String title, String editor, String source, String detail, String imgsrc, String videosrc,
			Integer browsenum, Date time, Date modifiedtime, String type, Integer isdelete) {
		this.title = title;
		this.editor = editor;
		this.source = source;
		this.detail = detail;
		this.imgsrc = imgsrc;
		this.videosrc = videosrc;
		this.browsenum = browsenum;
		this.time = time;
		this.modifiedtime = modifiedtime;
		this.type = type;
		this.isdelete = isdelete;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEditor() {
		return this.editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getImgsrc() {
		return this.imgsrc;
	}

	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}

	public String getVideosrc() {
		return this.videosrc;
	}

	public void setVideosrc(String videosrc) {
		this.videosrc = videosrc;
	}

	public Integer getBrowsenum() {
		return this.browsenum;
	}

	public void setBrowsenum(Integer browsenum) {
		this.browsenum = browsenum;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Date getModifiedtime() {
		return this.modifiedtime;
	}

	public void setModifiedtime(Date modifiedtime) {
		this.modifiedtime = modifiedtime;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getIsdelete() {
		return this.isdelete;
	}

	public void setIsdelete(Integer isdelete) {
		this.isdelete = isdelete;
	}

	public NewsType getNewsType() {
		return newsType;
	}

	public void setNewsType(NewsType newsType) {
		this.newsType = newsType;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	
    
}
