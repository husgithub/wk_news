package com.news.util.fetch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewsDateFormat {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
	private static SimpleDateFormat imgSDF= new SimpleDateFormat("yyyyMMddHHmmsssss");
	
	public static Date strToDate(String str){
		Date  date = null;
		try {
		    date =sdf.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	public static String getImgName(int num){
		String str = imgSDF.format(new Date())+"-"+num;
		return str;
	}

}
