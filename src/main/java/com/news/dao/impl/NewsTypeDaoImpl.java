package com.news.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.news.dao.NewsDao;
import com.news.dao.NewsTypeDao;
import com.news.dao.common.CommonDaoImpl;
import com.news.entity.News;
import com.news.entity.NewsType;

@Repository
public class NewsTypeDaoImpl extends CommonDaoImpl<NewsType> implements NewsTypeDao {

	@Override
	public void saveAll(List<NewsType> list) {
		super.saveAll(list, 100);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<NewsType> select(Map<String, Object> criteria) {
		String sql = "SELECT * FROM news_type ";
		Map<String,Object> cMap = concatCriteria(criteria);
		List<Object> param = null;
		if(cMap!=null&&cMap.containsKey("str")){
			sql = sql+cMap.get("str");
			param = (List<Object> )cMap.get("param");
		}
		
		
		return super.selectWithSQL(NewsType.class, sql, param);
	}
	private Map<String,Object> concatCriteria(Map<String, Object> criteria){
		Map<String,Object> result = new HashMap<String,Object>();
		if(criteria ==null||criteria.isEmpty()){
			return null;
		}
		StringBuffer strBuffer = new StringBuffer("");
		List<Object> param = new ArrayList<Object>();
		if(criteria.containsKey("id")){
			strBuffer.append("AND id = ? ");
			param.add(criteria.get("id"));
		}
		if(criteria.containsKey("code")){
			strBuffer.append("AND code = ? ");
			param.add(criteria.get("code"));
		}
		if(criteria.containsKey("name")){
			strBuffer.append("AND name = ? ");
			param.add(criteria.get("name"));
		}
		String str = strBuffer.toString();
		if(str == null||"".equals(str)){
			return null;
		}
		str = str.replaceFirst("AND", "WHERE");
		result.put("str", str);
		result.put("param", param);
		return result;
	}

	@Override
	public Integer deleteOne(Integer id) {
		try{
			String sql = "DELETE FROM news_type WHERE news_type.`id` = '"+id+"' ";
			return super.updateWithSql(sql, null);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Integer saveOrUpdate(NewsType newsType) {
		try{
			if(newsType == null){
				return 0;
			}
			List<NewsType> list = new ArrayList<NewsType>();
			list.add(newsType);
			if(newsType.getId()==null){
				super.saveAll(list, 1);
			}else{
				super.updateAll(list, 1);
			}
			return 1;
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
		
	}

}
