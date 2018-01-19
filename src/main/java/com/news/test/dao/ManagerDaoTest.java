package com.news.test.dao;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.news.dao.ManagerDao;

/**  
 * @author: husong
 * @date:   2017年12月28日 下午8:54:48   
 */
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ManagerDaoTest {
	
	@Autowired
	private ManagerDao managerDao;
	
	
	@Test
	public void update(){
		Map<String, Object> criteria = new HashMap<String,Object>();
	    criteria.put("status", "0");
	    managerDao.update(criteria, 4);
	}
	

}
