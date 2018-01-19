package com.news.test.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.news.dao.NewsDao;
import com.news.entity.News;

@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class NewsDaoTest {
	
	@Autowired
	private NewsDao newsDao;
	
	@Transactional
	@Test
	public void saveAll(){
		List<News> list = new ArrayList<News>();
		for(int i=0;i<10;i++){
			News news = new News();
			news.setTitle(Integer.toString(i));
			list.add(news);
		}
		newsDao.saveAll(list);
	}
	@Transactional
	@Test
	public void countWithHsql(){
		Map<String,Object> criteria = new HashMap<String,Object>();
		criteria.put("page", 1);
		criteria.put("size", 10);
		criteria.put("title", "f");
		int c = newsDao.countWithHsql(criteria);
		System.out.println(c);
	}
	@Transactional
	@Test
	public void selectWithHsqlAndPage(){
		Map<String,Object> criteria = new HashMap<String,Object>();
		criteria.put("title", "f");
		List<News> list = newsDao.selectWithHsqlAndPage(criteria, 0, 1);
		System.out.println(list.size());
	}
	@Transactional
	@Test
	public void updateMulti(){
		List<Integer> ids = new ArrayList<Integer>();
		ids.addAll(Arrays.asList(1,2,3));
		newsDao.updateMulti(ids);
	}

}
