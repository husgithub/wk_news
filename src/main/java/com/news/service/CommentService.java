package com.news.service;

import java.util.List;
import java.util.Map;

import com.news.common.PageTemp;
import com.news.entity.Comment;

/**  
 * @author: husong
 * @date:   2017年12月25日 上午11:10:43   
 */
public interface CommentService {
	
	List<Map<String,Object>> selectWithPage(Map<String,Object> criteria,PageTemp page);
	Integer count(Map<String,Object> criteria);
	
	void saveOne(Comment comment);
	
	/**
	 * 
	 * @param ids
	 * @param deleteChild  是否删除回复
	 */
	void deleteMore(String ids,boolean deleteChild);

}
