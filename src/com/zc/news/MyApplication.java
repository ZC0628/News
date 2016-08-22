package com.zc.news;

import java.util.HashMap;

import cn.jpush.android.api.JPushInterface;

import com.zc.news.Util.LogUtil;

import android.app.Application;
import android.content.res.Configuration;

/**
 * ��Ӧ�ù����ࣨ������
 *
 */
public class MyApplication extends Application{
	/**����ģʽ*/ 
	private static MyApplication application;
	
	public static MyApplication getInstance(){ 
		LogUtil.d(LogUtil.TAG, "MyApplication onCreate"); 
		return application; 
		}
	@Override 
	public void onCreate() { 
		super.onCreate(); 
		application=this; 
		JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
		}
	/**������������Ӧ�ó��������*/ 
	private HashMap<String, Object> allData=new HashMap<String, Object>(); 
	/**������ */ 
	public void addAllData(String key,Object value){ 
		allData.put(key, value); 
		} 
	/**ȡ����*/ 
	public Object getAllData(String key){ 
		if(allData.containsKey(key)){ 
			return allData.get(key); 
			} 
		return null; 
		} 
	/**ɾ��һ������*/ 
	public void delAllDataBykey(String key){ 
		if(allData.containsKey(key)){ 
			allData.remove(key); 
			} 
		} 
	/**ɾ����������*/ 
	public void delAllData(){ 
		allData.clear(); 
		}
	
	/**�ڴ治���ʱ��*/ 
	@Override 
	public void onLowMemory() { 
		// TODO Auto-generated method stub super.onLowMemory(); 
		LogUtil.d(LogUtil.TAG, "MyApplication onLowMemory"); 
		} 
	/** * ������ʱ�� */ 
	@Override 
	public void onTerminate() { 
		// TODO Auto-generated method stub super.onTerminate(); 
		LogUtil.d(LogUtil.TAG, "MyApplication onTerminate"); 
		} 
	/**���øı��ʱ��*/ 
	@Override 
	public void onConfigurationChanged(Configuration newConfig) { 
		// TODO Auto-generated method stub 
		super.onConfigurationChanged(newConfig); 
		LogUtil.d(LogUtil.TAG, "MyApplication onConfigurationChanged"); 
		} 
}
