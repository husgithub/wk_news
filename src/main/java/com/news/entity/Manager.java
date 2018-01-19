package com.news.entity;
// Generated 2017-12-27 22:23:09 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * Manager generated by hbm2java
 */
public class Manager implements java.io.Serializable {

	private Integer id;
	private String userName;
	private String password;
	private String name;
	private String status;
	private String type;
	private String role;
	private String avatar;
	private Date regtime;

	public Manager() {
	}

	public Manager(String userName, String password, String name, String status, String type, String role,
			String avatar, Date regtime) {
		this.userName = userName;
		this.password = password;
		this.name = name;
		this.status = status;
		this.type = type;
		this.role = role;
		this.avatar = avatar;
		this.regtime = regtime;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAvatar() {
		return this.avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Date getRegtime() {
		return this.regtime;
	}

	public void setRegtime(Date regtime) {
		this.regtime = regtime;
	}

}
