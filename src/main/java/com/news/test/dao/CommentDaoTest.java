package com.news.test.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.news.dao.CommentDao;

/**  
 * @author: husong
 * @date:   2017年12月25日 上午9:58:36   
 */
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class CommentDaoTest {
	
	@Autowired
	private CommentDao commentDao;
	
	@Transactional
	@Test
	public void selectWithSqlAndPage(){
		List<Map<String,Object>> list = commentDao.selectWithSqlAndPage(null, null, null);
		if(list!=null){
			for(int i=0;i<list.size();i++){
				Map<String,Object> map = list.get(i);
				System.out.println(map);
				System.out.println(map.get("time") instanceof Date);
			}
		}
	}

}
