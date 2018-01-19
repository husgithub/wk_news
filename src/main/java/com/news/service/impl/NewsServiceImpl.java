package com.news.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.news.common.PageTemp;
import com.news.dao.CommentDao;
import com.news.dao.NewsDao;
import com.news.dao.NewsTypeDao;
import com.news.entity.News;
import com.news.service.NewsService;
import com.news.util.format.TextUtil;

@Service
@Transactional
public class NewsServiceImpl implements NewsService {
	
	@Autowired
	private NewsDao newsDao;
	
	@Autowired
	private CommentDao commentDao;
	
	@Autowired
	private NewsTypeDao newsTypeDao;

	@Override
	public void saveAll(List<News> list) {
		newsDao.saveAll(list);
	}

	@Override
	public List<News> selectWithPage(Map<String, Object> criteria, PageTemp page) {
		try{
			List<News> list =  newsDao.selectWithHsqlAndPage(criteria, page.getRowIndex(), page.getPageSize());
			for(int i=0;i<list.size();i++){
				News news = list.get(i);
				//查询评论量
				Map<String,Object> up_criteria = new HashMap<String,Object>();
				up_criteria.put("newsid", news.getId());
				Integer commentCount = commentDao.countWithSql(up_criteria);
				news.setCommentCount(commentCount);
				//查询类别
				Map<String,Object> type_criteria = new HashMap<String,Object>();
				type_criteria.put("id", news.getType());
				news.setNewsType(newsTypeDao.select(type_criteria).get(0));
			}
			return list;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Integer count(Map<String, Object> criteria) {
		try{
			return newsDao.countWithHsql(criteria);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Integer deleteMulti(List<Integer> ids) {
		// TODO Auto-generated method stub
		try{
			return newsDao.updateMulti(ids);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void save(News news,String contextPath) {
		try{
			Date date = new Date();
			news.setBrowsenum(0);
			news.setIsdelete(0);
			news.setTime(date);
			news.setModifiedtime(date);
			news.setImgsrc("0");
			String imgSrcs = TextUtil.getImgsByText(news.getDetail(),contextPath);
			if(imgSrcs!=null){
				news.setImgsrc(imgSrcs);
			}
			List<News> list = new ArrayList<News>();
			list.add(news);
			newsDao.saveAll(list);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public News selectOne(Integer id) {
		try{
			News news = newsDao.selectOne(id);
			//查询评论量
			Map<String,Object> up_criteria = new HashMap<String,Object>();
			up_criteria.put("newsid", news.getId());
			Integer commentCount = commentDao.countWithSql(up_criteria);
			news.setCommentCount(commentCount);
			//查询类别
			Map<String,Object> type_criteria = new HashMap<String,Object>();
			type_criteria.put("id", news.getType());
			news.setNewsType(newsTypeDao.select(type_criteria).get(0));
			return news;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateOne(News news,String contextPath) {
		try{
			news.setImgsrc("0");
			String imgSrcs = TextUtil.getImgsByText(news.getDetail(),contextPath);
			if(imgSrcs!=null){
				news.setImgsrc(imgSrcs);
			}
			news.setModifiedtime(new Date());
			newsDao.updateOne(news);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public List<String> querySource() {
		try{
			
			return newsDao.querySource();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateNewsBrowser(Integer id) {
		try{
			newsDao.updateNewsBrowser(id);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
