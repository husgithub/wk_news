package com.news.dao;

import java.util.List;
import java.util.Map;

import com.news.entity.News;

public interface NewsDao {
	
	void saveAll(List<News> list);
	
	List<News> selectWithHsqlAndPage(Map<String, Object> criteria,int rowStart,int pageSize);
	Integer countWithHsql(Map<String, Object> criteria);
	
	Integer updateMulti(List<Integer> ids);
	
	News selectOne(Integer id);
	
	void updateOne(News news);
	
	List<String> querySource();
	
	Integer updateNewsBrowser(Integer id);

}
