package com.zc.news.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.feicui.news.R;
import com.zc.news.base.adapter.MyBaseAdapter;
import com.zc.news.model.entity.LoginLog;

/**
 *		µÇÂ¼ÐÅÏ¢ÊÊÅäÆ÷
 * @author Administrator
 *
 */
public class UserAdapter extends MyBaseAdapter<LoginLog> {

	public UserAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getMyView(int position, View convertView, ViewGroup parent) {
		convertView = mLayoutInflater.inflate(R.layout.activity_user_item, null);
		TextView tv1 = (TextView) convertView.findViewById(R.id.login_user_tv1);
		TextView tv2 = (TextView) convertView.findViewById(R.id.login_user_tv2);
		TextView tv3 = (TextView) convertView.findViewById(R.id.login_user_tv3);
		
		tv1.setText(infos.get(position).getTime().split(" ")[0]);
		tv2.setText(infos.get(position).getAddress());
		tv3.setText(infos.get(position).getDevice() == 0 ? "ÒÆ¶¯¶ËµÇÂ¼" : "ÍøÒ³¶ËµÇÂ¼");
		return convertView;
	}

}
