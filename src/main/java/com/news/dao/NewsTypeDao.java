package com.news.dao;

import java.util.List;
import java.util.Map;

import com.news.entity.News;
import com.news.entity.NewsType;

public interface NewsTypeDao {
	
	void saveAll(List<NewsType> list);
	
	List<NewsType> select(Map<String, Object> criteria);
	
	Integer deleteOne(Integer id);
	
	Integer saveOrUpdate(NewsType newsType);

}
