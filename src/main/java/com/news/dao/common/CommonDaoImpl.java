package com.news.dao.common;


import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CommonDaoImpl<T> implements CommonDao<T>{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getCurrentSession(){
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> selectWithSQL(Class<T> c,String sql,List<Object> param){
		Session session = getCurrentSession();
		SQLQuery sqlQuery = session.createSQLQuery(sql).addEntity(c.getName());
		if(param!=null){
			for(int i=0;i<param.size();i++){
				sqlQuery.setParameter(i, param.get(i));
			}
		}
		return sqlQuery.list();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List selectWithSQL(String sql, List<Object> param,Integer rowStart,Integer pageSize,boolean transformMap) {
		Session session = getCurrentSession();
		SQLQuery sqlQuery = session.createSQLQuery(sql);
		if(param!=null){
			for(int i=0;i<param.size();i++){
				sqlQuery.setParameter(i, param.get(i));
			}
		}
		if(rowStart!=null){
			sqlQuery.setFirstResult(rowStart);
		}
		if(pageSize!=null){
			sqlQuery.setMaxResults(pageSize);
		}
		if(transformMap){
			sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		}
		return sqlQuery.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> selectWithHQL(String hql, List<Object> param) {
		Session session = getCurrentSession();
		Query query = session.createQuery(hql);
		if(param!=null){
			for(int i=0;i<param.size();i++){
				query.setParameter(i, param.get(i));
			}
		}
		return query.list();
	}
	
	@Override
	public Integer countWithSql(String sql, List<Object> param) {
		Session session = getCurrentSession();
		Query query = session.createSQLQuery(sql);
		if(param!=null){
			for(int i=0;i<param.size();i++){
				Object obj= param.get(i);
				if(obj instanceof Integer){
					query.setInteger(i, Integer.valueOf(obj.toString()));
				}else{
					query.setParameter(i, obj);
				}
			}
		}
		return Integer.valueOf(query.uniqueResult().toString());
	}
	
	@Override
	public Integer countWithHsql(String hsql, List<Object> param) {
		Session session = getCurrentSession();
		Query query = session.createQuery(hsql);
		if(param!=null){
			for(int i=0;i<param.size();i++){
				Object obj= param.get(i);
				if(obj instanceof Integer){
					query.setInteger(i, Integer.valueOf(obj.toString()));
				}else{
					query.setParameter(i, obj);
				}
			}
		}
		return Integer.valueOf(query.uniqueResult().toString()) ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> selectWithHsqlAndPage(String hsql, List<Object> param,int rowStart,int pageSize) {
		Session session = getCurrentSession();
		Query query = session.createQuery(hsql);
		if(param!=null){
			for(int i=0;i<param.size();i++){
				Object obj= param.get(i);
				if(obj instanceof Integer){
					query.setInteger(i, Integer.valueOf(obj.toString()));
				}else{
					query.setParameter(i, obj);
				}
			}
		}
		query.setFirstResult(rowStart);
		query.setMaxResults(pageSize);
		return query.list();
	}
	/**
	 * 批量新增数据
	 * @param list
	 * @param num   每新增mum条数据提交一次
	 * @return
	 */
	@Override
	public void saveAll(List<T> list,Integer num){
		Session session = getCurrentSession();
		if(list==null||list.size()<=0){
			return ;
		}
		Iterator<T> iterator = list.iterator();
		int i = 0;
		while(iterator.hasNext()){
			session.save(iterator.next());
			i++;
			if(i%num == 0){
				session.flush();
				session.clear();
			}
		}
		session.flush();
		session.clear();
	}
	

	@Override
	public void updateAll(List<T> list, Integer num) {
		Session session = getCurrentSession();
		if(list==null||list.size()<=0){
			return ;
		}
		Iterator<T> iterator = list.iterator();
		int i = 0;
		while(iterator.hasNext()){
			session.update(iterator.next());
			i++;
			if(i%num == 0){
				session.flush();
				session.clear();
			}
		}
		session.flush();
		session.clear();
	}

	@Override
	public Integer updateWithSql(String sql, List<Object> param)throws Exception {
		Session session = getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		if(param!=null){
			for(int i=0;i<param.size();i++){
				Object obj= param.get(i);
				if(obj instanceof Integer){
					query.setInteger(i, Integer.valueOf(obj.toString()));
				}else{
					query.setParameter(i, obj);
				}
			}
		}
		return query.executeUpdate();
	}

}
