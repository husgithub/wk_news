package com.news.test;

import org.apache.log4j.Logger;
import org.junit.Test;

/**  
 * @author: husong
 * @date:   2017年12月18日 上午10:57:22   
 */
public class LogTest {
	
	private static Logger logger = Logger.getLogger(LogTest.class);
	
	@Test
	public void test(){
		logger.debug("ff");
	}
	
	public static void main(String[] args) {
		logger.debug("ff");
	}

}
