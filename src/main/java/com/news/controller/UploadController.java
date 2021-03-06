package com.news.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import net.sf.json.JSONObject;

/**  
 * @author: husong
 * @date:   2017年11月27日 下午8:29:26   
 */

@Controller
@RequestMapping("/upload")
public class UploadController {
	
	private static String AVATAR_PATH = "img/user/avatar/";
	
	 @RequestMapping(value = "test",produces = {"application/json;charset=UTF-8"})
	 @ResponseBody
	    public String  springUpload(HttpServletRequest request) throws IllegalStateException, IOException
	    {
	         long  startTime=System.currentTimeMillis();
	         //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
	        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
	                request.getSession().getServletContext());
	        //检查form中是否有enctype="multipart/form-data"
	        if(multipartResolver.isMultipart(request))
	        {
	            //将request变成多部分request
	            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
	           //获取multiRequest 中所有的文件名
	            Iterator iter=multiRequest.getFileNames();
	             
	            while(iter.hasNext())
	            {
	                //一次遍历所有文件
	                MultipartFile file=multiRequest.getFile(iter.next().toString());
	                if(file!=null)
	                {
	                    String path="E:/springUpload/"+file.getOriginalFilename();
	                    //上传
	                    file.transferTo(new File(path));
	                }
	                 
	            }
	           
	        }
	        long  endTime=System.currentTimeMillis();
	        System.out.println("方法三的运行时间："+String.valueOf(endTime-startTime)+"ms");
	    return "{\"success\":true}"; 
	    }
	 
	 @RequestMapping(value = "avatar",produces = {"application/json;charset=UTF-8"})
	 @ResponseBody
	 public String  img(HttpServletRequest request){
		 Map<String, Object> result = new HashMap<String, Object>();
		 result.put("success", false);
		 try{
			 String fileNewName = null;
			 String avatarPath = request.getSession().getServletContext().getRealPath(AVATAR_PATH);
			 if(avatarPath==null){
				 
			 }
		     //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
		     CommonsMultipartResolver multipartResolver= new CommonsMultipartResolver( request.getSession().getServletContext());
		     //检查form中是否有enctype="multipart/form-data"
		     if(multipartResolver.isMultipart(request)){
		    	 //将request变成多部分request
		         MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
		         //获取multiRequest 中所有的文件名
		         Iterator<?> iter=multiRequest.getFileNames();
		         while(iter.hasNext()){
		        	 //一次遍历所有文件
		             MultipartFile file=multiRequest.getFile(iter.next().toString());
		             if(file!=null){
		            	 String originalFileName = file.getOriginalFilename();
		            	 String suffix = originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());
		            	 fileNewName = UUID.randomUUID().toString()+suffix;
		            	 String path=avatarPath + fileNewName;
		                 file.transferTo(new File(path));
		             }    
		         }    
		     }
		     result.put("success", true);
		     result.put("data", AVATAR_PATH+fileNewName);
	     }catch(Exception e){
	    	 e.printStackTrace();
	     }
	     return JSONObject.fromObject(result).toString();
	 }

}
