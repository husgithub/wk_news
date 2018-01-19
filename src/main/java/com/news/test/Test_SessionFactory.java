package com.news.test;



import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.news.entity.News;

@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class Test_SessionFactory {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Test
	public void select(){
		System.out.println("test:"+sessionFactory);
		Session session  = sessionFactory.openSession();
		Transaction transaction =  session.beginTransaction();
		String sql = "select * from news";
		SQLQuery sqlQuery = session.createSQLQuery(sql).addEntity(News.class);
		@SuppressWarnings("unchecked")
		List<News> list = sqlQuery.list();
		for(int i=0;i<list.size();i++){
			News news = list.get(i);
			System.out.println(news);
		}
		transaction.commit();
	}
	
	@Test
	public void save(){
		Session session  = null;
		Transaction transaction = null;
		try{
			session  = sessionFactory.openSession();
			transaction =  session.beginTransaction();
			News news = new News();
			news.setTitle("测试"); 
			session.save(news);
			transaction.commit();
		}catch(Exception e){
			e.printStackTrace();
			transaction.rollback();
		}finally {
			session.close();
		}
	}

}
