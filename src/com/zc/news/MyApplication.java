package com.zc.news;

import java.util.HashMap;

import cn.jpush.android.api.JPushInterface;

import com.zc.news.Util.LogUtil;

import android.app.Application;
import android.content.res.Configuration;

/**
 * 本应用共享类（单例）
 *
 */
public class MyApplication extends Application{
	/**单例模式*/ 
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
	/**用来保存整个应用程序的数据*/ 
	private HashMap<String, Object> allData=new HashMap<String, Object>(); 
	/**存数据 */ 
	public void addAllData(String key,Object value){ 
		allData.put(key, value); 
		} 
	/**取数据*/ 
	public Object getAllData(String key){ 
		if(allData.containsKey(key)){ 
			return allData.get(key); 
			} 
		return null; 
		} 
	/**删除一条数据*/ 
	public void delAllDataBykey(String key){ 
		if(allData.containsKey(key)){ 
			allData.remove(key); 
			} 
		} 
	/**删除所有数据*/ 
	public void delAllData(){ 
		allData.clear(); 
		}
	
	/**内存不足的时候*/ 
	@Override 
	public void onLowMemory() { 
		// TODO Auto-generated method stub super.onLowMemory(); 
		LogUtil.d(LogUtil.TAG, "MyApplication onLowMemory"); 
		} 
	/** * 结束的时候 */ 
	@Override 
	public void onTerminate() { 
		// TODO Auto-generated method stub super.onTerminate(); 
		LogUtil.d(LogUtil.TAG, "MyApplication onTerminate"); 
		} 
	/**配置改变的时候*/ 
	@Override 
	public void onConfigurationChanged(Configuration newConfig) { 
		// TODO Auto-generated method stub 
		super.onConfigurationChanged(newConfig); 
		LogUtil.d(LogUtil.TAG, "MyApplication onConfigurationChanged"); 
		} 
}
