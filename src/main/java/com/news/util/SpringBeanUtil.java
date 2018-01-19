package com.news.util;


import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.news.common.PageTemp;
import com.news.entity.News;
import com.news.service.NewsService;

/**  
 * @author: husong
 * @date:   2017年11月20日 下午8:05:55   
 */
public class SpringBeanUtil{
	private static ApplicationContext applicationContext = (ApplicationContext)new ClassPathXmlApplicationContext("applicationContext.xml");
	
	public static Object getBeanByName(String beanName) {
		if (applicationContext == null){
			return null;
		}
		return applicationContext.getBean(beanName);
	}
	
	public static void main(String[] args) {
		NewsService newsService = (NewsService) SpringBeanUtil.getBeanByName("newsServiceImpl");
		List<News> list = newsService.selectWithPage(null, new PageTemp(1, 10, 20));
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i));
		}
	}

}
