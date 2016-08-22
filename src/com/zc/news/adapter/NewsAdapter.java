package com.zc.news.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.feicui.news.R;
import com.zc.news.Util.ImageLoaderUtil;
import com.zc.news.activity.FragmentNews;
import com.zc.news.base.adapter.MyBaseAdapter;
import com.zc.news.model.entity.News;
/**
 * ��������������
 *
 */
public class NewsAdapter extends MyBaseAdapter<News> {

	private Context context;
	private boolean flag = true; 
	private ImageLoaderUtil mImageLoaderUtil;
	public NewsAdapter(Context context) {
		super(context);
		this.context  = context;
	
	}
	
	//ͨ�����������������״̬
	public void setFlag(boolean flag) {
		this.flag = flag;
	}


	@Override
	public View getMyView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if(convertView == null){
			vh = new ViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.activity_home_item, null);
			vh.tv1 = (TextView) convertView.findViewById(R.id.item_tv1);
			vh.tv2 = (TextView) convertView.findViewById(R.id.item_tv2);
			convertView.setTag(vh);
		}else{
			vh = (ViewHolder) convertView.getTag();
		}
		vh.iv = (ImageView) convertView.findViewById(R.id.item_iv);
		//��ÿ��ImageView����TAG
		vh.iv.setTag(infos.get(position).icon);
		//Ĭ��ͼƬ
		vh.iv.setImageResource(R.drawable.loading_01);
		
		//
		if(flag){
		mImageLoaderUtil = new ImageLoaderUtil(FragmentNews.getImageLoadListener(), context);
		Bitmap bitmap = mImageLoaderUtil.getBitmap(infos.get(position).icon);
		if(bitmap != null){
			vh.iv.setImageBitmap(bitmap);
		}
	}
		vh.tv1.setText(infos.get(position).title);//���ű���
		vh.tv2.setText(infos.get(position).summary);//��������
		
		return convertView;
	}
	
	class ViewHolder{
		ImageView iv;
		TextView tv1,tv2;
	}
	
	

}
