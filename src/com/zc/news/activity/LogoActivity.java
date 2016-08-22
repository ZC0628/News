package com.zc.news.activity;


import com.feicui.news.R;
import com.zc.news.base.activity.BaseActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
/**
 * 
 * 用于显示欢迎界面
 * @author 李晟
 * @version v1.0
 * 
 * */
public class LogoActivity extends BaseActivity {
	private ImageView iv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logo);
		
		initView();
		setListener();
	}

	@Override
	protected void initView() {
		//Logo界面开始显示图片的对象
	    iv = (ImageView) findViewById(R.id.img_iv);
	}

	@Override
	protected void setListener() {
		/*
		 * 创建动画对象
		 * 参数
		 * 1.上下文
		 * 2.用于显示动画的布局文件id
		 * 
		 */
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
		
		
		//设置动画的监听
		anim.setAnimationListener(new AnimationListener() {
			//动画开始的时候调用此函数
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			//动画重复播放的时候调用此函数
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			//动画结束的时候调用次函数
			@Override
			public void onAnimationEnd(Animation animation) {
				//startActivity(LogoActivity.this, TelActivity.class);
				startActivity(HomeActivity.class, R.anim.in_right, R.anim.exit_right);
				finish();
			}
		});
		
		//设置动画
		iv.startAnimation(anim);
	}

	
	

}
