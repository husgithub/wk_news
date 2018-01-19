package com.news.test.service;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.news.service.ManagerService;

/**  
 * @author: husong
 * @date:   2017年12月22日 下午5:24:58   
 */
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ManagerServiceTest {
	
	@Autowired
	private ManagerService managerService;
	
	@Test
	public void getLoginedMessage(){
		Map<String, Object> result = managerService.getLoginedMessage("root", "admin");
		System.out.println(result+"-");
	}

}
