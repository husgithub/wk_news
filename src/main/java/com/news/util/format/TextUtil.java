package com.news.util.format;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**  
 * @author: husong
 * @date:   2017年11月28日 下午7:56:06   
 * 解析富文本内容
 */
public class TextUtil {
	
	public static Pattern p_img = Pattern.compile("(<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>)");
	
	public static String getImgsByText(String  rich_text,String contextPath){
		String imgs = "";
		Matcher m_img = p_img.matcher(rich_text);
		while (m_img.find()) {
		    String src = m_img.group(2); //m_img.group(1) 为获得整个img标签  m_img.group(2) 为获得src的值
		    if(contextPath!=null&&!"".equals(contextPath)){
		    	src = src.replaceAll("/*"+contextPath+"/", "");
		    }
		    imgs +=src+",";
		}
		if("".equals(imgs)){
			return null;
		}
		if(imgs.contains(",")){
			imgs = imgs.substring(0,imgs.lastIndexOf(","));
			return imgs;
		}
		return null;
	}
	
	public static void main(String[] args) {
		String str ="<p><img src=\"/wk_news/upload/image/20171129/1511922028998012227.jpg\" title=\"1511922028998012227.jpg\" alt=\"bg.jpg\"/></p>";
		System.out.println(getImgsByText(str,"wk_news"));
	}

}
