package com.news.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.news.dao.ManagerDao;
import com.news.dao.common.CommonDaoImpl;
import com.news.entity.Manager;

/**  
 * @author: husong
 * @date:   2017年12月18日 下午9:13:36   
 */
@Repository
public class ManagerDaoImpl extends CommonDaoImpl<Manager> implements ManagerDao {
    
	@Transactional
	@Override
	public Manager selectOne(String userName) {
		String sql = "SELECT * FROM manager WHERE manager.`user_name` = '"+userName+"' ";
		List<Manager> list = super.selectWithSQL(Manager.class, sql, null);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
    
	/**
	 * SELECT m.id,m.`user_name`,m.`name`,m.`status`,m.`type`,m.`role`,m.`avatar` FROM manager m
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> selectWithSqlAndPage(Map<String, Object> criteria, Integer rowStart, Integer pageSize) {
		List<Map<String,Object>> result = null;
		try{
			StringBuffer sb = new StringBuffer("SELECT m.id,m.`user_name`,m.`name`,m.`status`,m.`type`,m.`role`,m.`avatar`, m.`regtime` FROM manager m ");
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
			StringBuffer sb = new StringBuffer("SELECT count(*) FROM manager m ");
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
		if(criteria.containsKey("user_name")){
			strBuffer.append("AND user_name like ? ");
			param.add("%"+criteria.get("user_name")+"%");
		}
		if(criteria.containsKey("password")){
			strBuffer.append("AND password = ? ");
			param.add(criteria.get("password"));
		}
		if(criteria.containsKey("name")){
			strBuffer.append("AND name like ? ");
			param.add("%"+criteria.get("name")+"%");
		}
		if(criteria.containsKey("status")){
			strBuffer.append("AND status = ? ");
			param.add(criteria.get("status"));
		}
		if(criteria.containsKey("type")){
			strBuffer.append("AND type = ? ");
			param.add(criteria.get("type"));
		}
		if(criteria.containsKey("role")){
			strBuffer.append("AND role = ? ");
			param.add(criteria.get("role"));
		}
		if(criteria.containsKey("startTime")){
			strBuffer.append("AND regtime >= '"+criteria.get("startTime")+"'");
		}
		if(criteria.containsKey("endTime")){
			strBuffer.append("AND regtime <= '"+criteria.get("endTime")+"'");
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
	public Integer update(Map<String, Object> criteria,Object id) {
		try{
			if(criteria == null||criteria.isEmpty()||id==null) throw new IllegalArgumentException("参数错误！");
			List<Object> param = new ArrayList<Object>();
			StringBuffer sb = new StringBuffer("UPDATE manager  SET ");
			Iterator<Map.Entry<String,Object>> iterator =  criteria.entrySet().iterator();
			while(iterator.hasNext()){
				Map.Entry<String,Object> entry = iterator.next();
				if(entry.getValue()!=null){
					sb.append(",  "+entry.getKey() +" = ? ");
					param.add(entry.getValue());
				}
			}
			sb.append(" where id = ? ");
			param.add(id);
			String sql =  sb.toString();
			sql = sql.replaceFirst(",", "");
			return super.updateWithSql(sql, param);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}


	
}
