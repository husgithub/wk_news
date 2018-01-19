package com.news.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.news.common.PageTemp;
import com.news.dao.CommentDao;
import com.news.entity.Comment;
import com.news.service.CommentService;

/**  
 * @author: husong
 * @date:   2017年12月25日 上午11:13:54   
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentDao commentDao;

	@Override
	public List<Map<String, Object>> selectWithPage(Map<String, Object> criteria, PageTemp page) {
		List<Map<String, Object>> result = null;
		try{
			result = commentDao.selectWithSqlAndPage(criteria, page.getRowIndex(), page.getPageSize());
			if(result==null){
				return result;
			}
			if(criteria.containsKey("parentid")){
				for(int i=0;i<result.size();i++){
					Map<String, Object> map = result.get(i);
					criteria.put("parentid", map.get("id"));
					map.put("children", commentDao.selectWithSqlAndPage(criteria, 0, 100000));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Integer count(Map<String, Object> criteria) {
		Integer count = null;
		try{
			count = commentDao.countWithSql(criteria);
		}catch(Exception e){
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public void saveOne(Comment comment) {
		try{
			if(comment == null){
				throw new IllegalArgumentException("请输入参数！");
			}
			comment.setUpvote(0);  //默认点赞量为0
			comment.setTime(new Date());
			commentDao.insertOne(comment);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void deleteMore(String ids,boolean deleteChild) {
		if(ids == null){
			throw new IllegalArgumentException("请输入参数！");
		}
		Integer count = commentDao.deleteMore(ids,deleteChild);
		if(count==null){
			throw new IllegalArgumentException("删除异常！");
		}
	}

}
