package com.news.test.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.news.dao.NewsTypeDao;
import com.news.entity.NewsType;

/**  
 * @author: husong
 * @date:   2017年11月19日 下午3:02:15   
 */
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class NewsTypeDaoTest {
	
	@Autowired
	private NewsTypeDao newsTypeDao;
	
	@Test
	@Transactional
	public void select(){
		List<NewsType> list = newsTypeDao.select(null);
		System.out.println(list.size());
	}

}
