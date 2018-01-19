package com.news.security.shiro;

import java.io.UnsupportedEncodingException;

import org.apache.shiro.crypto.hash.SimpleHash;

/**  
 * @author: husong
 * @date:   2017年12月22日 下午2:54:01   
 */
public class ShiroMD5 {
	
	private final static String DEFAULT_algorithmName = "MD5";
	private final static Integer DEFAULT_hashIterations = 1;
	
	public static String getMD5Code(String str,Integer hashIterations){
		String md5Code = null;
		try {
			if(hashIterations==null){
				hashIterations = DEFAULT_hashIterations;
			}
			SimpleHash simpleHash = new SimpleHash(DEFAULT_algorithmName, str, null, hashIterations);
			md5Code = simpleHash.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return md5Code;
	}
	
	public static void main(String[] args) {
		String str = ShiroMD5.getMD5Code("user", 1);
		str = ShiroMD5.getMD5Code("root", 1);
		System.out.println(str);
	}

}
