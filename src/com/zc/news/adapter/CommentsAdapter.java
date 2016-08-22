package com.zc.news.adapter;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.feicui.news.R;
import com.zc.news.Util.ImageLoaderUtil;
import com.zc.news.Util.LogUtil;
import com.zc.news.Util.SharedPreferencesUtil;
import com.zc.news.base.adapter.MyBaseAdapter;
import com.zc.news.model.entity.Comment;
import com.zc.news.xlistview.XListView;

public class CommentsAdapter extends MyBaseAdapter<Comment> implements ImageLoaderUtil.ImageLoadListener{

	private ListView listView;
	private ImageView mImageView;
	private TextView tv1,tv2,tv3;
	private ImageLoaderUtil imageLoaderUtil;
	public CommentsAdapter(Context context) {
		super(context);
	}

	@Override
	public View getMyView(int position, View convertView, ViewGroup parent) {
		convertView = mLayoutInflater.inflate(R.layout.activity_comment_item_list, null);
		mImageView = (ImageView) convertView.findViewById(R.id.comment_item_iv);
		tv1 = (TextView) convertView.findViewById(R.id.comment_item_tv1);
		tv2 = (TextView) convertView.findViewById(R.id.comment_item_tv2);
		tv3 = (TextView) convertView.findViewById(R.id.comment_item_tv3);
		imageLoaderUtil = new ImageLoaderUtil(this, mContext);
		String icon = infos.get(position).getPortrait();
		LogUtil.d("icon", icon);
		Bitmap bitmap = imageLoaderUtil.getBitmap(icon);
		mImageView.setImageBitmap(bitmap);
		tv1.setText(infos.get(position).getUid());
		tv2.setText(infos.get(position).getStamp());
		tv3.setText(infos.get(position).getContent());
		return convertView;
	}

	@Override
	public void imageLoadOK(Bitmap bitmap, String url) {
		
		if(bitmap != null){
			mImageView.setImageBitmap(bitmap);
		}
	}

}
