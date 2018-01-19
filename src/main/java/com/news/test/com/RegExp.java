package com.news.test.com;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

/**  
 * @author: husong
 * @date:   2017年11月18日 下午10:41:45   
 */
public class RegExp {
	@Test
	public void test(){
		String regex = "^AND";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher("AND ...");
		System.out.println(matcher.matches());
	}
}
