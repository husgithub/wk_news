package com.news.security.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**  
 * 在原基础上加上用户类型
 * @author: husong
 * @date:   2017年12月22日 下午4:19:13   
 */
public class MyUsernamePasswordToken extends UsernamePasswordToken {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//用户类型
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public MyUsernamePasswordToken(String username, String password , String type) {
		super(username,password);
		this.type = type;
	}
	
	

}
