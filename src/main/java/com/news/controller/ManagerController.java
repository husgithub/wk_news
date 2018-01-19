package com.news.controller;

import java.util.HashMap;
import java.util.Map;


import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.jboss.logging.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.news.common.PageTemp;
import com.news.security.shiro.MyUsernamePasswordToken;
import com.news.service.ManagerService;

import net.sf.json.JSONObject;

/**  
 * @author: husong
 * @date:   2017年12月18日 下午8:27:37   
 */

@Controller
@RequestMapping("/manager")
public class ManagerController {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	private static final String USER_TYPE = "admin";
	
	@Autowired
	private ManagerService managerService;
	
	@RequestMapping(value = "/login",produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
	@ResponseBody
	public String login(String userName,String password,boolean autoLogin){
		log.info(userName+"  "+password+"  "+"  "+autoLogin);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", false);
		try{
			if(userName==null||password==null){
				map.put("error", "系统中没有该账号！");
				throw new Exception("参数格式错误!");
			}
			Subject currentUser = SecurityUtils.getSubject();
			//用户没有登录
	        if (!currentUser.isAuthenticated()) {
	        	MyUsernamePasswordToken token = new MyUsernamePasswordToken(userName,password,USER_TYPE);
	            token.setRememberMe(autoLogin);
	            try {
	                currentUser.login(token);
	                Session session = currentUser.getSession(false);
	                Map<String,Object> userMessage = managerService.getLoginedMessage(userName, USER_TYPE);
	    		    session.setAttribute("curManager", JSONObject.fromObject(userMessage).toString());
	                map.put("success", true);
	                map.put("url", "admin-index.jsp");
	            } catch (UnknownAccountException uae) {
	                log.info("There is no user with username of " + token.getPrincipal());
	                map.put("error", "系统中没有该账号！");
	            } catch (IncorrectCredentialsException ice) {
	                log.info("Password for account " + token.getPrincipal() + " was incorrect!");
	                map.put("error", "密码错误！");
	            } catch (LockedAccountException lae) {
	                log.info("The account for username " + token.getPrincipal() + " is locked.  " +
	                        "Please contact your administrator to unlock it.");
	                map.put("error", lae.getMessage());
	            }catch (AuthenticationException ae) {
	            	map.put("error", ae.getMessage());
	            }
	        }else{
	        	map.put("error", "用户已登录！");
	        }
		}catch(Exception e){
			e.printStackTrace();
			map.put("error", e.getMessage());
		}
		return JSONObject.fromObject(map).toString();
		
	}
	
	@RequestMapping(value = "/logout",method = RequestMethod.GET)
	public String logout(String userName,String password,boolean autoLogin){
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		return "redirect:admin-login.jsp";
		
	}
	
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
			Integer total = managerService.count(criteria);
			PageTemp pageTemp = new PageTemp(Integer.valueOf(criteria.get("page").toString()), Integer.valueOf(criteria.get("size").toString()), total);
			result.put("total", total);
			result.put("data", managerService.selectWithPage(criteria, pageTemp));
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", false);
			result.put("error", e.getMessage());
		}
		return JSONObject.fromObject(result).toString();
	}
	
	@RequestMapping(value = "/lock",produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
	@ResponseBody
	public String lock(@RequestParam("status")String status,@RequestParam("id")String id){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", false);
		try{
			if(status==null||"".equals("status")||id==null||"".equals(id))throw new IllegalArgumentException("参数格式错误！");
			Map<String,Object> criteria = new HashMap<String,Object>();
			criteria.put("status", status);
			managerService.update(criteria, id);
			map.put("success", true);
		}catch(Exception e){
			e.printStackTrace();
			map.put("error", e.getMessage());
		}
		return JSONObject.fromObject(map).toString();
		
	}
}
