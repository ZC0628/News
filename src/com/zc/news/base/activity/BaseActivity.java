package com.zc.news.base.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public abstract class BaseActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	protected abstract void initView();
	protected abstract void setListener();
	
	/**
	 * 从一个界面跳转到另一个界面
	 * @param act  从哪个界面开始跳转
	 * @param cls  跳转到哪个界面
	 */
protected void startActivity(Activity act, Class<?> cls){
		startActivity(new Intent(act, cls));
	}

/**
 * 从一个界面跳转到某个系统界面
 * @param action  跳转到哪个系统界面
 */
protected void startActivity(String action){
	
	Intent it = new Intent(action);
	startActivity(it);
}

/**
 * 从一个界面跳转到另一个界面并携带数据
 * @param cls  跳转到哪个界面
 * @param bundle 数据包
 */
protected void startActivity(Class<?> cls, Bundle bundle){
	//跳转到TelListActivity界面
	Intent it = new Intent(this, cls);
	//将position传递过去
	it.putExtra("bundle", bundle);
	startActivity(it);
}

/**
 * 从一个界面跳转到另一个界面,并带有动画效果
 * 
 * @param cls  跳转到哪个界面
 * @param in   进来的动画文件
 * @param out  出去的动画文件
 * 
 */
protected void startActivity(Class<?> cls, int in, int out){

	Intent it = new Intent(this, cls);
	startActivity(it);
	//界面的跳转动画，必须加在startActivity和finish后面
	overridePendingTransition(in, out);
}
/**
 * 从一个界面跳转到另一个界面,并带有动画效果,并携带数据
 * @param act  从哪个界面开始跳转
 * @param cls  跳转到哪个界面
 * @param in   进来的动画文件
 * @param out  出去的动画文件
 * @param bundle 数据包
 * 
 */
protected void startActivity(Class<?> cls, int in, int out, Bundle bundle){

	Intent it = new Intent(this, cls);
	it.putExtra("bundle", bundle);
	startActivity(it);
	//界面的跳转动画，必须加在startActivity和finish后面
	overridePendingTransition(in, out);
}

}
