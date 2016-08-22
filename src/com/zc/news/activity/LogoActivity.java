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
 * ������ʾ��ӭ����
 * @author ����
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
		//Logo���濪ʼ��ʾͼƬ�Ķ���
	    iv = (ImageView) findViewById(R.id.img_iv);
	}

	@Override
	protected void setListener() {
		/*
		 * ������������
		 * ����
		 * 1.������
		 * 2.������ʾ�����Ĳ����ļ�id
		 * 
		 */
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
		
		
		//���ö����ļ���
		anim.setAnimationListener(new AnimationListener() {
			//������ʼ��ʱ����ô˺���
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			//�����ظ����ŵ�ʱ����ô˺���
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			//����������ʱ����ôκ���
			@Override
			public void onAnimationEnd(Animation animation) {
				//startActivity(LogoActivity.this, TelActivity.class);
				startActivity(HomeActivity.class, R.anim.in_right, R.anim.exit_right);
				finish();
			}
		});
		
		//���ö���
		iv.startAnimation(anim);
	}

	
	

}
