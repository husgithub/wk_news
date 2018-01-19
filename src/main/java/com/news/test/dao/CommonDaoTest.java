package com.news.test.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.news.dao.common.CommonDao;
import com.news.entity.News;

@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class CommonDaoTest {
	
	@Autowired
	private CommonDao<News> commonDao;
	
	@Test
	@Transactional
	public void selectWithSQL(){
		String sql = "select * from news";
		List<News> list = commonDao.selectWithSQL(News.class,sql, null);
		for(int i=0;i<list.size();i++){
			News news = list.get(i);
			System.out.println(news);
		}
		
	}
	
	@Test
	@Transactional
	public void saveAll(){
		List<News> list = new ArrayList<News>();
		for(int i=0;i<10;i++){
			News news = new News();
			news.setTitle(Integer.toString(i));
			list.add(news);
		}
		commonDao.saveAll(list, 5);
	}
}
