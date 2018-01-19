package com.news.service;

import java.util.List;
import java.util.Map;

import com.news.common.PageTemp;
import com.news.entity.Manager;

/**  
 * @author: husong
 * @date:   2017年12月22日 下午5:10:31   
 */
public interface ManagerService {
	
	Map<String,Object> getLoginedMessage(String userName,String type);
	
	List<Map<String,Object>> selectWithPage(Map<String,Object> criteria,PageTemp page);
	Integer count(Map<String,Object> criteria);
	
	Integer update(Map<String, Object> criteria,Object id);
	
	void changePwd(Object id,String old,String news);

}
