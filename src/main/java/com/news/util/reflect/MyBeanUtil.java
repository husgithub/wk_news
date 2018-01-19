package com.news.util.reflect;

import java.lang.reflect.Field;
import java.util.*;

/**  
 * @author: husong
 * @date:   2017年11月28日 上午9:43:59   
 */
public class MyBeanUtil {
	
	public static String join(Object[] obj,String splitStr){
		if(obj==null||obj.length<=0){
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<obj.length;i++){
			if(i<obj.length-1){
				sb.append(String.valueOf(obj[i])+splitStr);
			}else{
				sb.append(String.valueOf(obj[i]));
			}
		}
		return sb.toString();
	}
	
	public static List<Object> getValueByFields(String[] fieldNames,Object obj){
		List<Object> list = new ArrayList<Object>();
		try{
			if(fieldNames==null||fieldNames.length<=0||obj == null){
				return null;
			}
			Field[] fields = obj.getClass().getDeclaredFields();
			for(int h=0;h<fieldNames.length;h++){
				for(int i=0;i<fields.length;i++){
					fields[i].setAccessible(true);
					//System.out.println(fields[i].getName()+" : "+fields[i].get(obj)+" "+fields[i].getType());
					if(fieldNames[h].equals(fields[i].getName())){
						list.add(fields[i].get(obj));
					}
				}
			}
			if(list.size()<=0){
				return null;
			}
			return list;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static void main(String[] args) {
		
	}

}
