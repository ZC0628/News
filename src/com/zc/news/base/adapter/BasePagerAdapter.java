package com.zc.news.base.adapter;

import java.util.ArrayList;

import com.feicui.news.R;



import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class BasePagerAdapter extends PagerAdapter {
	//ViewPager����Դһ��ֻ����������   1.View  2.Fragment
	private ArrayList<View> views = new ArrayList<View>();
	private LayoutInflater mLayoutInflater;
	private Context context;
	private int[] pics = {
			R.drawable.lead1, R.drawable.lead2, R.drawable.lead3};

	public BasePagerAdapter(Context context){
		this.context = context;
		mLayoutInflater = LayoutInflater.from(context);
		//��������ͼ���س�View����,��������ͼƬ
		for(int x = 0; x < pics.length ;x++){
			View v = mLayoutInflater.inflate(R.layout.activity_lead_viewpager_item, null);
			ImageView iv = (ImageView) v.findViewById(R.id.iv_lead_viewpager_item);
			iv.setImageResource(pics[x]);
			addViewToAdapter(v);
		}
	}
	/**
	 * �����ͼ����������
	 * @param v     ��ͼ
	 */
	public void addViewToAdapter(View v){
		views.add(v);
	}
	
	public ArrayList<View> getViews(){
		return views;
	}
	@Override
	public int getCount() {
		
		return views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		
		return arg0 == arg1;
	}
	//����ͼ��ӽ���ͼ��
	/*
	 * ����
	 * 1. ��ͼ�����
	 * 2. ����
	 * 
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View v = views.get(position);
		container.addView(v);
		return v;
	}
	//����ͼ�Ƴ���ͼ��
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(views.get(position));
	}

}
