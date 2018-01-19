package com.news.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;

/**  
 * @author: husong
 * @date:   2017年12月24日 下午3:46:09   
 */

@Controller
@RequestMapping("/sys")
public class SystemController {
	
	/**
	 * 查找系统自带的表情包
	 * @return
	 */
	@RequestMapping(value = "getEmotion",produces = {"application/json;charset=UTF-8"},method = RequestMethod.GET)
	@ResponseBody
	public String getEmotion(HttpServletRequest request){
		String rootPath = "img/emotion/";
		Map<String,Object> emojs = new HashMap<String,Object>();
		emojs.put("success", false);
		try{
			String emojPath = request.getSession().getServletContext().getRealPath(rootPath);
			if(emojPath==null){
				throw new Exception("表情包文件夹不存在！");
			}
			File e_file = new File(emojPath);
			if(!e_file.isDirectory()){
				throw new Exception("表情包文件夹格式错误！");
			}
			Collection<Object> data = new ArrayList<Object>();
			File[] files = e_file.listFiles();
			for(int i=0;i<files.length;i++){
				if(files[i].isDirectory()){
					Map<String,Object> single = new HashMap<String,Object>();
					single.put("name", files[i].getName());
					single.put("rootPath", rootPath+files[i].getName()+"/");
					single.put("value", files[i].list());
					data.add(single);
				}
			}
			emojs.put("data", data);
			emojs.put("success", true);
		}catch(Exception e){
			e.printStackTrace();
			emojs.put("error", "系统异常！");
		}
		return JSONObject.fromObject(emojs).toString();
	}

}
