package com.news.util.fetch;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.news.entity.NewsType;
import com.news.util.SpringBeanUtil;
import com.news.util.http.HttpRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class NewsFetch extends Thread{
	
	/**
	 * http://channel.chinanews.com/cns/s/channel:gn.shtml?pager=1&pagenum=20&_=1493355240467            时政
	 * http://channel.chinanews.com/cns/s/channel:sh.shtml?pager=1&pagenum=20&_=1493207193816            社会
	 * http://channel.chinanews.com/cns/s/channel:cj.shtml?pager=1&pagenum=20&_=1493355777458            财经
	 * http://channel.chinanews.com/cns/s/channel:fortune.shtml?pager=1&pagenum=8&_=1493356119162    金融
	 * http://channel.chinanews.com/cns/s/channel:yl.shtml?pager=1&pagenum=9&_=1493356203232              娱乐
	 * http://channel.chinanews.com/cns/s/channel:ga.shtml?pager=1&pagenum=20&_=1493356230263            港澳
	 * http://channel.chinanews.com/cns/s/channel:tw.shtml?pager=1&pagenum=20&_=1493356256876            台湾
	 * http://channel.chinanews.com/cns/s/channel:gj.shtml?pager=1&pagenum=20&_=1493356312385            国际
	 * http://channel.chinanews.com/cns/s/channel:auto.shtml?pager=2&pagenum=20&_=1513443384315        汽车
	 * http://channel.chinanews.com/cns/s/channel:hr.shtml?pager=1&pagenum=20&_=1513443483301            华人
	 * http://channel.chinanews.com/cns/s/channel:ty.shtml?pager=1&pagenum=7&_=1513443557072              体育
	 * http://channel.chinanews.com/cns/s/channel:cul.shtml?pager=2&pagenum=12&_=1513443637708          文化
	 * http://channel.chinanews.com/cns/s/channel:business.shtml?pager=1&pagenum=20&_=1513509352828产经
	 */
	
	private static final Map<String,Object> map = new HashMap<String,Object>();
	static{
		map.put("gn", "http://channel.chinanews.com/cns/s/channel:gn.shtml?_=1493355240467");
		map.put("sh", "http://channel.chinanews.com/cns/s/channel:sh.shtml?_=1493207193816");
		map.put("cj", "http://channel.chinanews.com/cns/s/channel:cj.shtml?_=1493355777458");
		map.put("fortune", "http://channel.chinanews.com/cns/s/channel:fortune.shtml?_=1493356119162");
		map.put("yl", "http://channel.chinanews.com/cns/s/channel:yl.shtml?_=1493356203232");
		map.put("ga", "http://channel.chinanews.com/cns/s/channel:ga.shtml?_=1493356230263");
		map.put("tw", "http://channel.chinanews.com/cns/s/channel:tw.shtml?_=1493356256876");
		map.put("gj", "http://channel.chinanews.com/cns/s/channel:gj.shtml?_=1493356312385");
		map.put("auto", "http://channel.chinanews.com/cns/s/channel:auto.shtml?_=1513443384315");
		map.put("hr", "http://channel.chinanews.com/cns/s/channel:hr.shtml?_=1513443483301");
		map.put("ty", "http://channel.chinanews.com/cns/s/channel:ty.shtml?_=1513443557072");
		map.put("cul", "http://channel.chinanews.com/cns/s/channel:cul.shtml?_=1513443637708");
		map.put("business", "http://channel.chinanews.com/cns/s/channel:business.shtml?_=1513509352828");
	}
	private static final NewsTypeFactory newsTypeFactory = (NewsTypeFactory) SpringBeanUtil.getBeanByName("newsTypeFactory");
	private static final List<NewsType> list = newsTypeFactory.getTypeList();
	
	
	private String url; //新闻抓取地址
	private int page;
	private int rows;
	private int num;    //抓取当前类型新闻页数
	
	private int typeId; //新闻类型ID
	
	
	
	public NewsFetch() {}
	
	public NewsFetch(String url,int typeId,int page,int rows,int num){
		this.url =url;
		this.typeId = typeId;
		this.page = page;
		this.rows = rows;
		this.num = num;
	}
	
	@Override
	public void run(){
		String doc;
		for(int i=0;i<num;i++){
			System.out.println(url+"?pager="+page+"&pagenum="+rows);
			doc = HttpRequest.sendGet(url, "pager="+page+"&pagenum="+rows);
			/*System.out.println("第"+page+"页的数据："+doc.toString());*/
			String jsonStr = doc.toString().substring(doc.toString().indexOf("{"),doc.toString().lastIndexOf("}")+1);
			System.out.println("第"+page+"页的数据："+jsonStr);
			JSONObject jsonObject = JSONObject.fromObject(jsonStr);
			JSONArray docs = jsonObject.getJSONArray("docs");
			for(int j=0;j<docs.size();j++){
				JSONObject single =(JSONObject) docs.get(j);
				String url = (String) single.get("url");
				NewsDetail newsDetail = new NewsDetail(url,typeId);
				newsDetail.start();
			}
			try {
				Thread.sleep(20000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			page+=1;
		}
	}
	
	
	public static void  main(String []args){
		for(int i=0;i<list.size();i++){
			NewsType single = list.get(i);
			NewsFetch newsFetch = new NewsFetch(map.get(single.getCode()).toString(), single.getId(), 1, 10, 10);
			newsFetch.start();
		}
	}

}
