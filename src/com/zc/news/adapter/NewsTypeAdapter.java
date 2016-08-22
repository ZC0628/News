package com.zc.news.adapter;

import java.util.ArrayList;

















import com.feicui.news.R;
import com.zc.news.Util.LogUtil;
import com.zc.news.base.adapter.MyBaseAdapter;
import com.zc.news.model.entity.SubType;
import com.zc.news.view.HorizontalListView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 	水平列表适配器   （军事，社会，股票，基金，手机，探索，英超，NBA） 
 *
 */
public class NewsTypeAdapter extends MyBaseAdapter<SubType> {

	private TextView tv;
	public NewsTypeAdapter(Context context) {
		super(context);
		LogUtil.d("NewsTypeAdapter", "context");
		// TODO Auto-generated constructor stub
	}

	/*private ArrayList<SubType> subTypes = new ArrayList<SubType>();
	
	public void addData(ArrayList<SubType> data){
		if(!subTypes.isEmpty()){
			subTypes.clear();
		}
		this.subTypes.addAll(data);
	}

	public ArrayList<SubType> getSubTypes() {
		return subTypes;
	}*/

	@Override
	public View getMyView(int position, View convertView, ViewGroup parent) {
		LogUtil.d("getMyView","getMyView");
		convertView = mLayoutInflater.inflate(R.layout.activity_horizontallistview_item, null);
		tv = (TextView) convertView.findViewById(R.id.horizontal_tv);
		tv.setText(infos.get(position).getSubgroup());
		LogUtil.d("getMyView", infos.toString());
		return convertView;
	}
	
	

}
