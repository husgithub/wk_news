package com.news.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.news.dao.NewsDao;
import com.news.dao.common.CommonDaoImpl;
import com.news.entity.News;
import com.news.util.reflect.MyBeanUtil;

@SuppressWarnings("unchecked")
@Repository
public class NewsDaoImpl extends CommonDaoImpl<News> implements NewsDao {

	@Override
	public void saveAll(List<News> list) {
		super.saveAll(list, 100);
	}
	
	@Override
	public List<News> selectWithHsqlAndPage(Map<String, Object> criteria,int rowStart,int pageSize) {
		String hsql = "from News ";
		Map<String,Object> cMap = concatCriteria(criteria);
		List<Object> param = null;
		if(cMap !=null&&!cMap.isEmpty()){
			hsql = hsql+cMap.get("str");
			param = (List<Object>) cMap.get("param");
		}
		return super.selectWithHsqlAndPage(hsql, param, rowStart, pageSize);
	}

	@Override
	public Integer countWithHsql(Map<String, Object> criteria) {
		try{
			String hsql = "select count(*) from News ";
			Map<String,Object> cMap = concatCriteria(criteria);
			List<Object> param = null;
			if(cMap !=null&&!cMap.isEmpty()){
				hsql = hsql+cMap.get("str");
				param = (List<Object>) cMap.get("param");
			}
			return super.countWithHsql(hsql, param);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
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
		if(criteria.containsKey("title")){
			strBuffer.append("AND title like ? ");
			param.add("%"+criteria.get("title")+"%");
		}
		if(criteria.containsKey("editor")){
			strBuffer.append("AND editor like ? ");
			param.add("%"+criteria.get("editor")+"%");
		}
		if(criteria.containsKey("source")){
			strBuffer.append("AND source like ? ");
			param.add("%"+criteria.get("source")+"%");
		}
		if(criteria.containsKey("detail")){
			strBuffer.append("AND detail like '%?%' ");
			param.add("%"+criteria.get("detail")+"%");
		}
		if(criteria.containsKey("type")){
			strBuffer.append("AND type = ? ");
			param.add(criteria.get("type"));
		}
		if(criteria.containsKey("imgsrc")){
			strBuffer.append("AND imgsrc is not null AND imgsrc !='' AND imgsrc != '0' ");
		}
		if(criteria.containsKey("startTime")){
			strBuffer.append("AND time >= '"+criteria.get("startTime")+"'");
		}
		if(criteria.containsKey("endTime")){
			strBuffer.append("AND time <= '"+criteria.get("endTime")+"'");
		}
		strBuffer.append("AND isdelete = 0 ");
		
		String orderby = "time DESC ";   //time DESC , modifiedtime DESC
		if(criteria.containsKey("orderby")){
			strBuffer.append("ORDER BY "+criteria.get("orderby"));
		}else{
			strBuffer.append("ORDER BY "+orderby);
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
	public Integer updateMulti(List<Integer> ids) {
		try{
			if(ids==null||ids.isEmpty()){
				return 0;
			}
			String param = "";
			for(int id:ids){
				param+=id+",";
			}
			param = param.substring(0, param.lastIndexOf(","));
			String sql = "UPDATE news SET news.`isdelete` = '1' WHERE news.`id` IN ("+param+")";
			return super.updateWithSql(sql, null);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public News selectOne(Integer id) {
		String hsql = "from News where id= '"+id+"'";
		List<News> list = super.selectWithHQL(hsql, null);
		if(list==null||list.size()<=0){
			return null;
		}
		return list.get(0);
	}

	@Override
	public void updateOne(News news) {
		try {
			String sql = "UPDATE news SET news.`title` = ? ,news.`editor` = ? ,news.`source` =?,news.`detail` = ? ,news.`imgsrc` = ?,news.`videosrc` = ?,news.`modifiedtime` = ? , news.`type` = ? WHERE news.id = ?";
			List<Object> list = MyBeanUtil.getValueByFields(new String[]{"title","editor","source","detail","imgsrc","videosrc","modifiedtime","type","id"}, news);
			super.updateWithSql(sql, list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<String> querySource() {
		String sql = "SELECT DISTINCT(news.`source`) FROM news ";
		return super.selectWithSQL(sql, null,null,null,false);
	}

	@Override
	public Integer updateNewsBrowser(Integer id) {
		try {
			String sql = "UPDATE news SET news.`browsenum` = news.`browsenum`+1 WHERE news.`id` = '"+id+"'";
			return super.updateWithSql(sql, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
