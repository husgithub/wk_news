package com.news.entity.complex;

import com.news.entity.Comment;
import com.news.entity.Manager;

/**  
 * @author: husong
 * @date:   2017年12月25日 上午9:22:31   
 */
public class CommentComplex extends Comment {
	
	private Manager manager;

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}
	
	

}
