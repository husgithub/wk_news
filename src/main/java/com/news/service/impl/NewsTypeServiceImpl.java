package com.news.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.news.common.PageTemp;
import com.news.dao.NewsTypeDao;
import com.news.entity.News;
import com.news.entity.NewsType;
import com.news.service.NewsService;
import com.news.service.NewsTypeService;

/**  
 * @author: husong
 * @date:   2017年11月19日 下午3:39:50   
 */
@Service
@Transactional
public class NewsTypeServiceImpl implements NewsTypeService {
	@Autowired
	private NewsTypeDao newsTypeDao;
	
	@Autowired
	private NewsService newsService;

	@Override
	public void saveAll(List<NewsType> list) {
		try{
			newsTypeDao.saveAll(list);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public List<NewsType> select(Map<String, Object> criteria) {
		List<NewsType> list = null;
		try{
			list = newsTypeDao.select(criteria);
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Map<String, Object> deleteOne(Integer id) {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", false);
		try{
			if(id == null){
				result.put("error", "新闻类型id有误！");
				return result;
			}
			Map<String,Object> criteria = new HashMap<String,Object>();
			criteria.put("type", id);
			Integer count  = newsService.count(criteria);
			if(count ==null){
				result.put("error", "系统异常！");
				return result;
			}
			if(count>0){    //该类型下有新闻
				result.put("error", "无法进行删除操作，因为该类型下有新闻！");
				return result;
			}
			//删除类型
			Integer num = newsTypeDao.deleteOne(id);
			if(num==null){
				result.put("error", "系统异常！");
				return result;
			}
			if(num<=0){
				result.put("error", "没有此类型！");
				return result;
			}
			result.put("success", true);
		}catch(Exception e){
			e.printStackTrace();
			result.put("error", "系统异常！");
		}
		return result;
	}

	@Override
	public Integer saveOrUpdate(NewsType newsType) {
		try{
			return newsTypeDao.saveOrUpdate(newsType);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
