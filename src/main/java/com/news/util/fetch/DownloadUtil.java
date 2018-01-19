package com.news.util.fetch;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**  
 * @author: husong
 * @date:   2017年11月28日 下午8:13:13   
 */
public class DownloadUtil {
	public static void downloadImg(String path,String imgSrc,String imgName){
		String src = "http://www.chinanews.com"+imgSrc;
		if(imgSrc.contains(".com")){
			src = imgSrc;
		}
		if(path==null){
			path = "D://NEWS/upload/image";
		}
		try {
			URL url = new URL(src);
			InputStream inputStream = url.openConnection().getInputStream();
			
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			byte []b = new byte[1024];
			int len = -1;
			while((len = inputStream.read(b, 0, b.length))!=-1){
				byteArrayOutputStream.write(b, 0, len);
			}
			File directory = new File(path);
			if(!directory.exists()){
				directory.mkdirs();
			}
			System.out.println(src.substring(src.lastIndexOf(".")));
			File file = new File(directory, imgName+src.substring(src.lastIndexOf(".")));
			if(!file.exists()){
				if(!file.createNewFile()){
					return ;
				}
			}
			FileOutputStream fileOutputStream =  new FileOutputStream(file);
			fileOutputStream.write(byteArrayOutputStream.toByteArray());
			fileOutputStream.flush();
			fileOutputStream.close();
			inputStream.close();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
