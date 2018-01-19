package com.news.dao;

import java.util.List;
import java.util.Map;

import com.news.entity.Manager;

/**  
 * @author: husong
 * @date:   2017年12月18日 下午9:11:16   
 */
public interface ManagerDao {
	
	Manager selectOne(String userName);
	
	List<Map<String,Object>> selectWithSqlAndPage(Map<String, Object> criteria,Integer rowStart,Integer pageSize);
	Integer countWithSql(Map<String, Object> criteria);
	
	Integer update(Map<String, Object> criteria,Object id);

}
