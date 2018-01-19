package com.news.dao.common;

import java.util.*;


public interface CommonDao<T> {
	
	/**
	 * 原生sql查询单表
	 * @param c
	 * @param sql
	 * @param param
	 * @return
	 */
	List<T> selectWithSQL(Class<T> c,String sql,List<Object> param);
	/**
	 * 原生sql查询
	 * @param c
	 * @param sql
	 * @param param
	 * @return
	 */
	List<Object> selectWithSQL(String sql,List<Object> param,Integer rowStart,Integer pageSize,boolean transformMap);
	/**
	 * hql查询单表
	 * @param c
	 * @param sql
	 * @param param
	 * @return
	 */
	List<T> selectWithHQL(String hql,List<Object> param);
	/**
	 * 使用原生sql统计数据
	 * @param hsql
	 * @param param
	 * @param rowStart
	 * @param pageSize
	 * @return
	 */
	Integer countWithSql(String sql,List<Object> param);
	/**
	 * 使用hsql统计数据
	 * @param hsql
	 * @param param
	 * @param rowStart
	 * @param pageSize
	 * @return
	 */
	Integer countWithHsql(String hsql,List<Object> param);
	/**
	 * 使用hsql分页查询
	 * @param hsql
	 * @param param
	 * @param rowStart
	 * @param pageSize
	 * @return
	 */
	List<T> selectWithHsqlAndPage(String hsql,List<Object> param,int rowStart,int pageSize);
	/**
	 * 批量保存
	 * @param list
	 * @param num
	 * @return
	 */
	void saveAll(List<T> list,Integer num);
	/**
	 * 批量更新
	 * @param list
	 * @param num
	 * @return
	 */
	void updateAll(List<T> list,Integer num);
	
	Integer updateWithSql(String sql,List<Object> param)throws Exception;
	
}
