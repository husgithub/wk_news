package com.news.util.fetch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.news.common.PageTemp;
import com.news.entity.News;
import com.news.service.NewsService;
import com.news.util.SpringBeanUtil;


public class NewsDetail extends Thread{
	
	private static NewsService newsService = (NewsService) SpringBeanUtil.getBeanByName("newsServiceImpl");
	
	private String url;
	private int typeId;
	
	private Document doc;
	private String newsImg ;
	
	
	public NewsDetail(){}
	
	public NewsDetail(String url,int typeId){
		this.url =url;
		this.typeId = typeId;
	}
	
	@Override
	public void run(){
		try {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			doc = Jsoup.connect(url).get();
			Element cont = doc.select("#cont_1_1_2").get(0);
			String title = cont.select("h1").get(0).text();
			//判断是否已经抓取过该条新闻
			Map<String,Object> criteria = new HashMap<String,Object>();
			criteria.put("title", title);
			List<News> _newsList = newsService.selectWithPage(criteria, new PageTemp(1, 10, 10));
			if(_newsList!=null&&_newsList.size()>0){
				return;
			}
			Element left_t = cont.select(".left-time").get(0).select(".left-t").get(0);
			Date publishDate = NewsDateFormat.strToDate(left_t.text().split("　")[0]);
			String source  = left_t.text().split("　")[1];
			source = source.substring(source.indexOf("：")+1, source.lastIndexOf(" "));
			Element left_name = cont.select(".left_name").get(0);
			String author = left_name.text().substring(left_name.text().indexOf(":")+1,left_name.text().lastIndexOf("】"));
			String content = cont.select(".left_zw").get(0).children().toString();
			//下载文本中的图片并替换地址
			content = changeContent(content);
			News news = new News();
			news.setTitle(title);
			news.setTime(publishDate);
			news.setModifiedtime(new Date());;
			news.setSource(source);
			news.setEditor(author);
			news.setDetail(content);
			news.setType(Integer.toString(typeId));
			//随机设置浏览量
			news.setBrowsenum(new Random().nextInt(2000));
			news.setIsdelete(0);
			if(newsImg!=null){
				news.setImgsrc(newsImg);
			}
			List<News> list = new ArrayList<News>();
			list.add(news);
			newsService.saveAll(list);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String changeContent(String content){
		Document document =  Jsoup.parse(content);
		Elements imgs =  document.select("img");
		for(int i= 0;i<imgs.size();i++){
			String imgSrc = imgs.get(i).attr("src");
			//String imgName = NewsDateFormat.getImgName(i);
			String imgName = UUID.randomUUID().toString();
			DownloadUtil.downloadImg(null,imgSrc,imgName);
			String imgNewPath = "upload/image/"+imgName+imgSrc.substring(imgSrc.lastIndexOf("."));
			imgs.get(i).attr("src", imgNewPath);
			newsImg = imgNewPath;
		}
		content= document.select("body").get(0).children().toString();
		return content;
		
	}
	
	
	
	public static void main(String[] args) {
		NewsDetail shizheng = new NewsDetail("http://www.chinanews.com/sh/2017/04-28/8211034.shtml",6);
		shizheng.start();
	}

}
