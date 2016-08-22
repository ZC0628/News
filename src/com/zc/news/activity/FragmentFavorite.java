package com.zc.news.activity;

import java.util.ArrayList;

import com.feicui.news.R;
import com.zc.news.Util.LogUtil;
import com.zc.news.adapter.NewsAdapter;
import com.zc.news.model.biz.NewsDBManager;
import com.zc.news.model.entity.News;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentFavorite extends Fragment {

	private ListView listView;
	private NewsAdapter adapter;
	private OnItemClickListener itemListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) { 
			//打开显示当前选中的新闻  
			Bundle bundle = new Bundle();
			bundle.putSerializable("news", adapter.getInfos().get(position));
			Intent it = new Intent(getActivity(), TwoActivity.class);
			it.putExtras(bundle);
			startActivity(it);
			}
		};

	
	@Override
	public void onResume() {
		super.onResume();
		adapter.notifyDataSetChanged();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_favorite, null);
		listView = (ListView) view.findViewById(R.id.read_lv);
		adapter = new NewsAdapter(getActivity());
		loadLoveNews();
		listView.setAdapter(adapter); 
		listView.setOnItemClickListener(itemListener);
		return view;
	}
	
	/*
	 * 从数据库中加载保存的新闻
	 */
	public void loadLoveNews() { 
		ArrayList<News> data = NewsDBManager.getNewsDBManager(getActivity()).queryLoveNews();
		adapter.addDataToAdapter(data);
		}
}
