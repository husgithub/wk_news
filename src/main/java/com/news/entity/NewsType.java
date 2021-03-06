package com.news.entity;
// Generated 2017-12-18 9:55:06 by Hibernate Tools 3.4.0.CR1

/**
 * NewsType generated by hbm2java
 */
public class NewsType implements java.io.Serializable {

	private Integer id;
	private String code;
	private String name;
	private Integer parentid;

	public NewsType() {
	}

	public NewsType(String code, String name, Integer parentid) {
		this.code = code;
		this.name = name;
		this.parentid = parentid;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParentid() {
		return this.parentid;
	}

	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}

}
