package com.zc.news.activity;



import cn.jpush.android.api.JPushInterface;

import com.feicui.news.R;
import com.zc.news.Util.LogUtil;
import com.zc.news.base.activity.BaseActivity;
import com.zc.news.base.adapter.BasePagerAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 引导界面
 * @author
 *
 */
public class LeadActivity extends BaseActivity {
	private ViewPager mViewPager;
	private BasePagerAdapter mBasePagerAdapter;
	private ImageView[] points = new ImageView[3];
	private Intent it;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lead);
		
		initView();
		
		
		//判断是不是第一次打开应用
		SharedPreferences shared = getSharedPreferences("first", Context.MODE_PRIVATE);
		Boolean isFirst = shared.getBoolean("first", true);
		if(!isFirst){
			startActivity(this, LogoActivity.class);
			finish();
		}else{//如果是第一次登录
			Editor edit = shared.edit();
			edit.putBoolean("first", false);
			edit.commit();
			
		}
		
	
		
		setListener();
	}

	

	@Override
	protected void initView() {
		mViewPager = (ViewPager) findViewById(R.id.vp_lead);
		mBasePagerAdapter = new BasePagerAdapter(this);
		mViewPager.setAdapter(mBasePagerAdapter);
		//获取4个圆点对象
		points[0] = (ImageView) findViewById(R.id.iv_lead_left);
		points[1] = (ImageView) findViewById(R.id.iv_lead_middle1);
		points[2] = (ImageView) findViewById(R.id.iv_lead_right);
		
	}



	@Override
	protected void setListener() {
		
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			//当页面切换的时候调用
			@Override
			public void onPageSelected(int position) {
				//设置图片
				for(int x = 0; x < points.length; x++){
					points[x].setImageResource(R.drawable.lead_point_normal);
				}
				points[position].setImageResource(R.drawable.lead_point_selected);
				
				if(position == 2){
					new Thread(){
						public void run() {
							try {
								sleep(500);
								runOnUiThread(new Runnable() {
									public void run() {
										startActivity(LeadActivity.this, LogoActivity.class);
										finish();
									}
								});
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						};
					}.start();
				}
			}
			//当页面滚动的时候会多次调用
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			//当滚动状态切换的时候调用（向左或向右）
			@Override
			public void onPageScrollStateChanged(int arg0) {
				LogUtil.d("onPageScrollStateChanged", arg0+"");
				
				
			}
		});
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		JPushInterface.onResume(this);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		JPushInterface.onPause(this);
	}

}

