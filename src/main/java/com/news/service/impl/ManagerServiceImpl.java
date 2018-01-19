package com.news.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.news.common.PageTemp;
import com.news.dao.ManagerDao;
import com.news.entity.Manager;
import com.news.security.shiro.ShiroMD5;
import com.news.service.ManagerService;

/**  
 * @author: husong
 * @date:   2017年12月22日 下午5:11:51   
 */
@Service
@Transactional
public class ManagerServiceImpl implements ManagerService{
	
	@Autowired
	private ManagerDao managerDao;

	@Override
	public Map<String, Object> getLoginedMessage(String userName, String type) {
		if(userName==null){
			throw new IllegalArgumentException("用户名userName不能为空。");
		}
		Map<String,Object> result = null;
		try{
			Manager manager = managerDao.selectOne(userName);
			if(manager != null){
				if(!type.equals(manager.getType())){
					throw new IllegalArgumentException("用户类型不匹配。");
				}
				result = new HashMap<String,Object>();
				result.put("userId", manager.getId());
				result.put("userName", manager.getUserName());
				result.put("name", manager.getName());
				result.put("avatar", manager.getAvatar());
				result.put("regtime", manager.getRegtime());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	
	@Override
	public List<Map<String, Object>> selectWithPage(Map<String, Object> criteria, PageTemp page) {
		List<Map<String, Object>> result = null;
		try{
			result = managerDao.selectWithSqlAndPage(criteria, page.getRowIndex(), page.getPageSize());
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Integer count(Map<String, Object> criteria) {
		Integer count = null;
		try{
			count = managerDao.countWithSql(criteria);
		}catch(Exception e){
			e.printStackTrace();
		}
		return count;
	}


	@Override
	public Integer update(Map<String, Object> criteria,Object id) {
		if(criteria == null||criteria.isEmpty()||id==null) throw new IllegalArgumentException("参数错误！");
		if(criteria.containsKey("password")){     //密码md5编码
			criteria.put("password",ShiroMD5.getMD5Code(criteria.get("password").toString(), 1));
		}
		Integer count = managerDao.update(criteria, id);
		if(count == null)throw new IllegalArgumentException("更新异常！");
		return count;
	}


	@Override
	public void changePwd(Object id, String old, String news) {
		if(id==null||"".equals(id)||old==null||"".equals(old)||news==null||"".equals(news)){
			throw new IllegalArgumentException("参数错误！");
		}
		//检验原始密码
		Map<String, Object> criteria = new HashMap<String,Object>();
		criteria.put("id", id);
		criteria.put("password", ShiroMD5.getMD5Code(old, 1));
		Integer count = count(criteria);
		if(count == null){
			throw new RuntimeException("操作失败！");
		}
		if(count<=0){
			throw new RuntimeException("原始密码错误！");
		}
		criteria.put("password", news);
		Integer u_count = update(criteria, id);
		if(u_count == null){
			throw new RuntimeException("操作失败！");
		}
	}

}
