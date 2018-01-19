package com.news.util.fetch;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.news.entity.NewsType;

/**  
 * @author: husong
 * @date:   2017年12月16日 下午11:41:31   
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class NewsTypeTest {
	
	@Autowired
	private NewsTypeFactory newsTypeFactory;
	
	@Test
	public void getTypeList(){
		List<NewsType> list = newsTypeFactory.getTypeList();
		System.out.println(list.size());
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i));
		}
	}

}
