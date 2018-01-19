package com.news.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.news.common.PageTemp;
import com.news.entity.NewsType;
import com.news.service.NewsTypeService;

import net.sf.json.JSONObject;

/**  
 * @author: husong
 * @date:   2017年11月19日 下午2:52:34   
 */
@Controller
@RequestMapping("/type")
public class NewsTypeController {
	
	@Autowired
	private NewsTypeService newsTypeService;
	
	
	
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
			result.put("data", newsTypeService.select(criteria));
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", false);
			result.put("error", e.getMessage());
		}
		return JSONObject.fromObject(result).toString();
	}
	
	@RequestMapping(value = "deleteOne",produces = {"application/json;charset=UTF-8"},method =RequestMethod.POST)
	@ResponseBody
	public String deleteOne(@RequestParam("id") Integer id){
		System.out.println(id);
		Map<String,Object> map = null;
		try{
			map = newsTypeService.deleteOne(id);
		}catch(Exception e){
			e.printStackTrace();
			map.put("error", "系统异常！");
		}
		return JSONObject.fromObject(map).toString();
	}
	
	@RequestMapping(value = "saveOrUpdate",produces = {"application/json;charset=UTF-8"},method =RequestMethod.POST)
	@ResponseBody
	public String saveOrUpdate(NewsType newsType){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", false);
		try{
			Integer num = newsTypeService.saveOrUpdate(newsType);
			if(num==null){
				map.put("error", "系统异常！");
			}else{
				map.put("success", true);
			}
		}catch(Exception e){
			e.printStackTrace();
			map.put("error", "系统异常！");
		}
		return JSONObject.fromObject(map).toString();
	}

}
