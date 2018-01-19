package com.news.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.news.dao.CommentDao;
import com.news.dao.common.CommonDaoImpl;
import com.news.entity.Comment;

/**  
 * @author: husong
 * @date:   2017年12月25日 上午9:14:09   
 */

@Repository
public class CommentDaoImpl extends CommonDaoImpl<Comment> implements CommentDao {
	
	private Map<String,Object> concatCriteria(Map<String, Object> criteria){
		Map<String,Object> result = new HashMap<String,Object>();
		if(criteria ==null||criteria.isEmpty()){
			return null;
		}
		StringBuffer strBuffer = new StringBuffer("");
		List<Object> param = new ArrayList<Object>();
		if(criteria.containsKey("newsid")){
			strBuffer.append("AND c.`newsid` = ? ");
			param.add(criteria.get("newsid"));
		}
		if(criteria.containsKey("parentid")){
			strBuffer.append("AND c.`parentid` = ? ");
			param.add(criteria.get("parentid"));
		}
		if(criteria.containsKey("title")){
			strBuffer.append("AND n.`title` like ? ");
			param.add("%"+criteria.get("title")+"%");
		}
		if(criteria.containsKey("ufrom")){
			strBuffer.append("AND c.`ufrom` = ? ");
			param.add(criteria.get("ufrom"));
		}
		if(criteria.containsKey("uto")){
			strBuffer.append("AND c.`uto` = ? ");
			param.add(criteria.get("uto"));
		}
		if(criteria.containsKey("from_name")){
			strBuffer.append("AND m1.`user_name` = ? ");
			param.add(criteria.get("from_name"));
		}
		if(criteria.containsKey("to_name")){
			strBuffer.append("AND m2.`user_name` = ? ");
			param.add(criteria.get("to_name"));
		}
		if(criteria.containsKey("startTime")){
			strBuffer.append("AND c.`time` >= '"+criteria.get("startTime")+"'");
		}
		if(criteria.containsKey("endTime")){
			strBuffer.append("AND c.`time` <= '"+criteria.get("endTime")+"'");
		}
		String orderby = "c.`time` DESC ";   //time DESC , modifiedtime DESC
		if(criteria.containsKey("orderby")){
			strBuffer.append("ORDER BY "+criteria.get("orderby"));
		}else{
			strBuffer.append("ORDER BY "+orderby);
		}
		String str = strBuffer.toString();
		if(str == null||"".equals(str)){
			return null;
		}
		result.put("str", str);
		result.put("param", param);
		return result;
	}
    /**
     *  SELECT c.`id`,c.`content`,c.`ufrom`,c.`uto`,c.`img`,c.`newsid`,c.`upvote`,c.`time`,c.`parentid`,
		m1.`user_name` from_name ,m1.`avatar` f_avatar,
		m2.`user_name` to_name, m2.`avatar` t_avatar,
		n.`title`
		FROM COMMENT c 
		LEFT JOIN manager m1 ON c.`ufrom` = m1.`id` 
		LEFT JOIN manager m2 ON c.`uto` = m2.`id`
		LEFT JOIN news n ON c.`newsid` = n.`id` 
		WHERE m1.`type` = 'user'
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> selectWithSqlAndPage(Map<String, Object> criteria, Integer rowStart, Integer pageSize) {
		List<Map<String,Object>> result = null;
		try{
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT c.`id`,c.`content`,c.`ufrom`,c.`uto`,c.`img`,c.`newsid`,c.`upvote`,c.`time`,c.`parentid`, ");
			sb.append("m1.`user_name` from_name ,m2.`user_name` to_name, m1.`avatar` f_avatar,m2.`avatar` t_avatar, ");
			sb.append("n.`title` ");
			sb.append("FROM COMMENT c LEFT JOIN manager m1 ON c.`ufrom` = m1.`id` ");
			sb.append("LEFT JOIN manager m2 ON c.`uto` = m2.`id` ");
			sb.append("LEFT JOIN news n ON c.`newsid` = n.`id` ");
			sb.append("WHERE m1.`type` = 'user' ");
			Map<String,Object> c_Map = concatCriteria(criteria);
			List<Object> param = null;
			if(c_Map!=null&&!c_Map.isEmpty()){
				String str = (String) c_Map.get("str");
				sb.append(str);
				param = (List<Object>) c_Map.get("param");
			}
			result = super.selectWithSQL(sb.toString(), param,rowStart,pageSize,true);
			if(result==null||result.isEmpty()){
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer countWithSql(Map<String, Object> criteria) {
		Integer count = null;
		try{
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT COUNT(*) ");
			sb.append("FROM COMMENT c LEFT JOIN manager m1 ON c.`ufrom` = m1.`id` ");
			sb.append("LEFT JOIN manager m2 ON c.`uto` = m2.`id` ");
			sb.append("LEFT JOIN news n ON c.`newsid` = n.`id` ");
			sb.append("WHERE m1.`type` = 'user' ");
			Map<String,Object> c_Map = concatCriteria(criteria);
			List<Object> param = null;
			if(c_Map!=null&&!c_Map.isEmpty()){
				String str = (String) c_Map.get("str");
				sb.append(str);
				param = (List<Object>) c_Map.get("param");
			}
			count = super.countWithSql(sb.toString(), param);
		}catch(Exception e){
			e.printStackTrace();
		}
		return count;
	}
	@Override
	public void insertOne(Comment comment) {
		try{
			List<Comment> list = new ArrayList<Comment>();
			list.add(comment);
			super.saveAll(list, 1);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@Override
	public Integer deleteMore(String ids,boolean deleteChild) {
		try{
			String str = "";
			String [] arr = ids.split(",");
			for(int i=0;i<arr.length;i++){
				str += "'"+arr[i]+"',";
			}
			str = str.substring(0,str.lastIndexOf(","));
			String sql = "DELETE FROM COMMENT WHERE id in ("+str+")";
			if(deleteChild){
				sql +=" OR parentid in ("+str+")";
			}
			return super.updateWithSql(sql, null);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
		
	}

	
}
