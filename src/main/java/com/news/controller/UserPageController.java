package com.news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.news.service.NewsService;

/**  
 * @author: husong
 * @date:   2017年12月11日 下午9:51:49   
 */

@Controller
public class UserPageController {
	
	@Autowired
	private NewsService newsService;
	
	private String pagePath = "/WEB-INF/views/user/";
	
	@RequestMapping(value = "/index/{type}",method = RequestMethod.GET)
	public String index(@PathVariable("type") String code){
		System.out.println(code);
		return pagePath+"user-index.jsp";
	}
	
	@RequestMapping(value = "/detail/{id}",method = RequestMethod.GET)
	public String detail(@PathVariable("id") String id){
		try{
			System.out.println(id);
			newsService.updateNewsBrowser(Integer.valueOf(id));
			return pagePath+"user-detail.jsp";
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:/index/all";
	}
	
	@RequestMapping(value = "/user/mine",method = RequestMethod.GET)
	public String mine(){
		try{
			return pagePath+"user-mine.jsp";
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:/index/all";
	}

}
