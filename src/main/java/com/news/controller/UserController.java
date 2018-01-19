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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.news.security.shiro.MyUsernamePasswordToken;
import com.news.service.ManagerService;

import net.sf.json.JSONObject;

/**  
 * @author: husong
 * @date:   2017年12月18日 下午8:27:37   
 */

@Controller
@RequestMapping("/user")
public class UserController {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	private static final String USER_TYPE = "user";
	
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
	    		    session.setAttribute("curUser", JSONObject.fromObject(userMessage).toString());
	                map.put("success", true);
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
	
	@RequestMapping(value = "/logout",produces = {"application/json;charset=UTF-8"},method = RequestMethod.GET)
	@ResponseBody
	public String logout(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		try{
			Subject currentUser = SecurityUtils.getSubject();
			currentUser.logout();
		}catch(Exception e){
			e.printStackTrace();
			map.put("success", false);
			map.put("error", e.getStackTrace());
		}
		return JSONObject.fromObject(map).toString();
		
	}
	
	@RequestMapping(value = "getLoginedMessage",produces = {"application/json;charset=UTF-8"},method =RequestMethod.GET)
	@ResponseBody
	public String getLoginedMessage(String userName){
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", true);
		try{
			if(userName==null||"".equals(userName)){
				throw new IllegalArgumentException("参数异常！");
			}
			JSONObject curUser = JSONObject.fromObject(SecurityUtils.getSubject().getSession(false).getAttribute("curUser"));
			if(!userName.equals(curUser.get("userName"))){
				throw new IllegalArgumentException("没有查询该用户信息的权限！");
			}
			Map<String,Object> userMessage = managerService.getLoginedMessage(userName, USER_TYPE);
			result.put("data", userMessage);
		}catch(Exception e){
			e.printStackTrace();
			result.put("success", false);
			result.put("error", e.getMessage());
		}
		return JSONObject.fromObject(result).toString();
	}
	
	@RequestMapping(value = "/update",produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
	@ResponseBody
	public String update(@RequestParam Map<String,Object> params){
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", false);
		try{
			Object id = params.get("id");
			if(params==null||params.isEmpty()||id==null){
				throw new IllegalArgumentException("参数错误！");
			}
			params.remove("id");
			Integer count = managerService.update(params, id);
			map.put("success", true);
			map.put("count", count);
		}catch(Exception e){
			e.printStackTrace();
			map.put("error", e.getMessage());
		}
		return JSONObject.fromObject(map).toString();
		
	}
	@RequestMapping(value = "/changePwd",produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
	@ResponseBody
	public String changePwd(@RequestParam Map<String,Object> params){
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", false);
		try{
			Object id = params.get("id");
			if(params==null||params.isEmpty()||id==null||params.get("old")==null||params.get("news")==null){
				throw new IllegalArgumentException("参数错误！");
			}
			managerService.changePwd(id, params.get("old").toString(), params.get("news").toString());
			map.put("success", true);
		}catch(Exception e){
			e.printStackTrace();
			map.put("error", e.getMessage());
		}
		return JSONObject.fromObject(map).toString();
		
	}

}
