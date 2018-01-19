package com.news.service;

import java.util.*;

import com.news.common.PageTemp;
import com.news.entity.News;

public interface NewsService {
	
	void saveAll(List<News> list);
	List<News> selectWithPage(Map<String,Object> criteria,PageTemp page);
	Integer count(Map<String,Object> criteria);
	
	Integer deleteMulti(List<Integer>ids);
	
	void save(News news,String contextPath);
	
	News selectOne(Integer id);
	
	void updateOne(News news,String contextPath);
	
	/**
	 * 查询新闻来源
	 * @return
	 */
	List<String> querySource();
	
	void updateNewsBrowser(Integer id);

}
