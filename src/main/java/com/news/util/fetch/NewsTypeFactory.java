package com.news.util.fetch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.news.entity.NewsType;
import com.news.service.NewsTypeService;

/**  
 * @author: husong
 * @date:   2017年12月16日 下午11:16:21   
 */

@Service
public class NewsTypeFactory {
	
	@Autowired
	private NewsTypeService newsTypeService;
	
	
	private static final String[][] array = new String[][]{
		{"gn","时政"},
		{"sh", "社会"},
		{"cj", "财经"},
		{"fortune", "金融"},
		{"yl", "娱乐"},
		{"ga", "港澳"},
		{"tw", "台湾"},
		{"gj", "国际"},
		{"auto", "汽车"},
		{"hr", "华人"},
		{"ty", "体育"},
		{"cul", "文化"},
		{"business", "产经"}
	};
	
	public List<NewsType> getTypeList(){
		List<NewsType> list = new ArrayList<NewsType>();
		for(int i=0;i<array.length;i++){
			NewsType newsType = new NewsType(array[i][0], array[i][1], 0);
			Map<String,Object> criteria = new HashMap<String,Object>();
			criteria.put("code", newsType.getCode());
			criteria.put("name", newsType.getName());
			List<NewsType> temp_list = newsTypeService.select(criteria);
			if(temp_list==null||temp_list.size()<=0){                          //保存
				newsTypeService.saveOrUpdate(newsType);
				list.add(newsType);
			}else{
				list.add(temp_list.get(0));
			}
			
		}
		return list;
	}

}
