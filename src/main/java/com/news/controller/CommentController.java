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
import com.news.entity.Comment;
import com.news.service.CommentService;

import net.sf.json.JSONObject;

/**  
 * @author: husong
 * @date:   2017年12月25日 上午11:26:32   
 */
@Controller
@RequestMapping("/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@RequestMapping(value = "select",produces = {"application/json;charset=UTF-8"},method =RequestMethod.GET)
	@ResponseBody
	public String select(@RequestParam Map<String,Object> criteria){
		System.out.println(criteria);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", true);
		try{
			if(criteria==null||!criteria.containsKey("page")||!criteria.containsKey("size")){
				throw new IllegalArgumentException("请补全页面下标和每页大小参数！");
			}
			Integer total = commentService.count(criteria);
			PageTemp pageTemp = new PageTemp(Integer.valueOf(criteria.get("page").toString()), Integer.valueOf(criteria.get("size").toString()), total);
			result.put("total", total);
			result.put("data", commentService.selectWithPage(criteria, pageTemp));
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", false);
			result.put("error", e.getMessage());
		}
		return JSONObject.fromObject(result).toString();
	}
	
	@RequestMapping(value = "save",produces = {"application/json;charset=UTF-8"},method =RequestMethod.POST)
	@ResponseBody
	public String save(Comment comment){
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", true);
		try{
			if(comment == null){
				throw new IllegalArgumentException("请传值！");
			}
			commentService.saveOne(comment);
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
		try{
			if(ids == null){
				throw new IllegalArgumentException("请传值！");
			}
			commentService.deleteMore(ids,false);
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", false);
			result.put("error", e.getMessage());
		}
		return JSONObject.fromObject(result).toString();
	}
	
	@RequestMapping(value = "deleteWithChild",produces = {"application/json;charset=UTF-8"},method =RequestMethod.POST)
	@ResponseBody
	public String deleteWithChild(String ids){
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", true);
		try{
			if(ids == null){
				throw new IllegalArgumentException("请传值！");
			}
			commentService.deleteMore(ids,true);
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", false);
			result.put("error", e.getMessage());
		}
		return JSONObject.fromObject(result).toString();
	}

}
