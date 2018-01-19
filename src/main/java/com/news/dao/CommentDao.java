package com.news.dao;

import java.util.List;
import java.util.Map;

import com.news.entity.Comment;



/**  
 * @author: husong
 * @date:   2017年12月25日 上午9:10:54   
 */
public interface CommentDao {
	
	List<Map<String,Object>> selectWithSqlAndPage(Map<String, Object> criteria,Integer rowStart,Integer pageSize);
	Integer countWithSql(Map<String, Object> criteria);
	
	void insertOne(Comment comment);
	
	Integer deleteMore(String ids,boolean deleteChild);

}
