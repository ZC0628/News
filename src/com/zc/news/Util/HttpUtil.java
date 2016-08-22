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
 * ���������Ϸ�������Ĺ�����
 * */
public class HttpUtil {
    /**
     * ���ڻ�ȡͼƬ����
     */
	public static Bitmap httpGetBitmap(String path) throws Exception{
		HttpClient client = HttpClientUtil.getInstance();
		path = path.replace("localhost", "118.244.212.82");
		//����get�������
		HttpGet hg = new HttpGet(path);
		//ͨ���ͻ��˶���������
		//���������ص���Ӧ,������Ӧͷ����Ӧ��
		synchronized(client){
			HttpResponse response = client.execute(hg);
			
			//�жϷ������Ƿ����ӳɹ�
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
     * ���ڻ�ȡ�ַ���
     */
	public static String httpGetString(String path) throws Exception{
		HttpClient client = HttpClientUtil.getInstance();
		//����get�������
		HttpGet hg = new HttpGet(path);
		//ͨ���ͻ��˶���������
		//���������ص���Ӧ,������Ӧͷ����Ӧ��
		HttpResponse response = client.execute(hg);
	
		//�жϷ������Ƿ����ӳɹ�
		if(response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK){
			HttpEntity entity = response.getEntity();
			String data = EntityUtils.toString(entity, "utf-8");
			return data;
		}else{
			throw new Exception();
		}
	}
}
