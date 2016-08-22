package com.zc.news.Util;

import java.io.InputStream;
import java.net.HttpURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/*
 * 用于网络上发送请求的工具类
 * */
public class HttpUtil {
    /**
     * 用于获取图片数据
     */
	public static Bitmap httpGetBitmap(String path) throws Exception{
		HttpClient client = HttpClientUtil.getInstance();
		path = path.replace("localhost", "118.244.212.82");
		//创建get请求对象
		HttpGet hg = new HttpGet(path);
		//通过客户端对象发送请求
		//服务器返回的响应,包含响应头和响应体
		synchronized(client){
			HttpResponse response = client.execute(hg);
			
			//判断服务器是否连接成功
			if(response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK){
				HttpEntity entity = response.getEntity();
				InputStream is = entity.getContent();
				Bitmap result = BitmapFactory.decodeStream(is);
				is.close();
				return result;
			}else{
				throw new Exception();
			}
		}
		
	}
	/**
     * 用于获取字符串
     */
	public static String httpGetString(String path) throws Exception{
		HttpClient client = HttpClientUtil.getInstance();
		//创建get请求对象
		HttpGet hg = new HttpGet(path);
		//通过客户端对象发送请求
		//服务器返回的响应,包含响应头和响应体
		HttpResponse response = client.execute(hg);
	
		//判断服务器是否连接成功
		if(response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK){
			HttpEntity entity = response.getEntity();
			String data = EntityUtils.toString(entity, "utf-8");
			return data;
		}else{
			throw new Exception();
		}
	}
}
