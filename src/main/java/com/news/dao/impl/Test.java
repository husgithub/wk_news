package com.news.dao.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**  
 * @author: husong
 * @date:   2017年12月15日 下午3:40:34   
 */
public class Test {
	
	public static void main(String[] args) {
		//"[-+]{0,1}\\d+\\.?\\d*";
		String regex = "[-+]{0,1}\\d+(\\.\\d+)*";
		Pattern p = Pattern.compile(regex);
		String str = "113.35046448";
		str = "0";
		Matcher m = p.matcher(str);
		System.out.println(m.matches());
	}

}
