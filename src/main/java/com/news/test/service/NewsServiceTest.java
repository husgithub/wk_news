package com.news.test.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.news.common.PageTemp;
import com.news.entity.News;
import com.news.service.NewsService;

@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class NewsServiceTest {
	
	@Autowired
	private NewsService newsService;
	
	@Test
	public void saveAll(){
		List<News> list = new ArrayList<News>();
		for(int i=0;i<10;i++){
			News news = new News();
			news.setTitle(Integer.toString(i));
			list.add(news);
		}
		newsService.saveAll(list);
	}
	@Test
	public void selectWithPage(){
		
		List<News> list = newsService.selectWithPage(null, new PageTemp(1, 10, 20));
		System.out.println(list.size());
		for(int i=0;i<list.size();i++){
			News news = list.get(i);
			System.out.println(news.getNewsType().getName());
		}
	}
	@Test
	public void querySource(){
		
		List<String> list = newsService.querySource();
		for(int i=0;i<list.size();i++){
			
			System.out.println(list.get(i));
		}
	}
	

}
