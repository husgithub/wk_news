package com.news.service;

import java.util.List;
import java.util.Map;

import com.news.entity.NewsType;

/**  
 * @author: husong
 * @date:   2017年11月19日 下午3:39:23   
 */
public interface NewsTypeService {
	
	void saveAll(List<NewsType> list);
	List<NewsType> select(Map<String,Object> criteria);
	
	Map<String,Object> deleteOne(Integer id);
	
	Integer saveOrUpdate(NewsType newsType);
	

}
