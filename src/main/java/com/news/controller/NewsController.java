package com.news.controller;

import java.util.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.news.common.PageTemp;
import com.news.entity.News;
import com.news.service.NewsService;

import net.sf.json.JSONObject;

/**  
 * @author: husong
 * @date:   2017年11月12日 下午11:01:34   
 */
@Controller
@RequestMapping("/news")
public class NewsController {
	
	@Autowired
	private NewsService newsService;
	
	@RequestMapping("/test")
	@ResponseBody
	public String test(){
		return "test";
	}
	
	@RequestMapping(value = "selectOne",produces = {"application/json;charset=UTF-8"},method =RequestMethod.GET)
	@ResponseBody
	public String selectOne(String id){
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", true);
		try{
			result.put("data", newsService.selectOne(Integer.valueOf(id)));
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", false);
			result.put("error", e.getMessage());
		}
		return JSONObject.fromObject(result).toString();
	}
	
	@RequestMapping(value = "select",produces = {"application/json;charset=UTF-8"},method =RequestMethod.GET)
	@ResponseBody
	public String select(@RequestParam Map<String,Object> criteria){
		System.out.println(criteria);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", true);
		try{
			if(criteria.isEmpty()){
				criteria.put("page", 1);
				criteria.put("size", 10);
			}
			Integer total = newsService.count(criteria);
			PageTemp pageTemp = new PageTemp(Integer.valueOf(criteria.get("page").toString()), Integer.valueOf(criteria.get("size").toString()), total);
			result.put("total", total);
			result.put("data", newsService.selectWithPage(criteria, pageTemp));
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", false);
			result.put("error", e.getMessage());
		}
		return JSONObject.fromObject(result).toString();
	}
	
	@RequestMapping(value = "delete",produces = {"application/json;charset=UTF-8"},method =RequestMethod.POST)
	@ResponseBody
	public String delete(String ids){
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", true);
		if(ids==null||"".equals(ids)){
			result.put("success", false);
			result.put("error", "请传入参数");
			return JSONObject.fromObject(result).toString();
		}
		try{
			
			String[] idArray = ids.split(",");
			List<Integer> int_ids = new ArrayList<Integer>();
			for(int i =0;i<idArray.length;i++){
				int_ids.add(Integer.valueOf(idArray[i]));
			}
			result.put("data", newsService.deleteMulti(int_ids));
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", false);
			result.put("error", e.getMessage());
		}
		return JSONObject.fromObject(result).toString();
	}
	@RequestMapping(value = "add",produces = {"application/json;charset=UTF-8"},method =RequestMethod.POST)
	@ResponseBody
	public String add(News news,HttpServletRequest request){
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", true);
		if(news==null){
			result.put("success", false);
			result.put("error", "请传入参数");
			return JSONObject.fromObject(result).toString();
		}
		try{
			newsService.save(news,request.getContextPath());
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", false);
			result.put("error", e.getMessage());
		}
		return JSONObject.fromObject(result).toString();
	}
	@RequestMapping(value = "update",produces = {"application/json;charset=UTF-8"},method =RequestMethod.POST)
	@ResponseBody
	public String update(News news,HttpServletRequest request){
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", true);
		if(news==null){
			result.put("success", false);
			result.put("error", "请传入参数");
			return JSONObject.fromObject(result).toString();
		}
		try{
			newsService.updateOne(news,request.getContextPath());
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", false);
			result.put("error", e.getMessage());
		}
		return JSONObject.fromObject(result).toString();
	}
	@RequestMapping(value = "selectResource",produces = {"application/json;charset=UTF-8"},method =RequestMethod.GET)
	@ResponseBody
	public String selectResource(){
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", true);
		try{
			result.put("data", newsService.querySource());
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", false);
			result.put("error", e.getMessage());
		}
		return JSONObject.fromObject(result).toString();
	}
	
	

}
