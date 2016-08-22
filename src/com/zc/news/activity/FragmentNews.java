package com.zc.news.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;









import java.util.Date;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.feicui.news.R;
import com.zc.news.Util.CommonUtil;
import com.zc.news.Util.HttpUtil;
import com.zc.news.Util.ImageLoaderUtil;
import com.zc.news.Util.LogUtil;
import com.zc.news.Util.ParserNewsUtil;
import com.zc.news.Util.SystemUtil;
import com.zc.news.adapter.NewsAdapter;
import com.zc.news.adapter.NewsTypeAdapter;
import com.zc.news.model.biz.NewsDBManager;
import com.zc.news.model.biz.NewsManager;
import com.zc.news.model.entity.News;
import com.zc.news.model.entity.SubType;
import com.zc.news.view.HorizontalListView;
import com.zc.news.xlistview.XListView;
import com.zc.news.xlistview.XListView.IXListViewListener;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * 侧拉菜单左边点击“新闻”，跳转到显示新闻内容的界面
 * @author Administrator
 *
 */
public class FragmentNews extends Fragment {

	private HorizontalListView hlv;//水平列表
	private NewsTypeAdapter adapter;//新闻类型适配器
	private static XListView mListView;//上拉加载和下拉刷新
	private NewsAdapter mNewsAdapter;//新闻主界面适配器
	private NewsDBManager mNewsDBManager;
	private HomeActivity acticity;
	private ImageLoaderUtil mImageLoaderUtil;//图片下载缓存
	private int startIndex;
	private int endIndex;
	private ProgressBar mProgressBar;//圆形进度条
	private ArrayList<News> lists = new ArrayList<News>();
	private static ImageLoaderUtil.ImageLoadListener imageLoadListener = new ImageLoaderUtil.ImageLoadListener() {
		public void imageLoadOK(android.graphics.Bitmap bitmap, String url) {
			// 如果是下载的也要设置图片
			ImageView iv = (ImageView) mListView.findViewWithTag(url);
			if(iv != null && bitmap != null){
				iv.setImageBitmap(bitmap);
			}
		}
	};

	public static ImageLoaderUtil.ImageLoadListener getImageLoadListener() {
		return imageLoadListener;
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			if (msg.what == 100) {
				// 存在数据库中
				mNewsAdapter.addDataToAdapter(lists);
				mListView.setAdapter(mNewsAdapter);
				mProgressBar.setVisibility(View.INVISIBLE);
				mListView.setVisibility(View.VISIBLE);
			} else if (msg.what == 200) {
			
			}
			// dialog.dismiss();
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_news, null);
		hlv = (HorizontalListView) view.findViewById(R.id.hlistview);
		mListView = (XListView) view.findViewById(R.id.main_lv);
		mProgressBar = (ProgressBar) view.findViewById(R.id.progressBarLarge);
		acticity = (HomeActivity) getActivity();
		adapter = new NewsTypeAdapter(acticity);
		hlv.setAdapter(adapter);
		mNewsAdapter = new NewsAdapter(acticity);
		mNewsDBManager = NewsDBManager.getNewsDBManager(acticity);
		//发送请求给服务器要求下载新闻类型
		NewsManager.loadNewsType(new MyErrorHandler(),new MyResponseHandler(),getActivity());
		
		// 查看数据库是否有数据，没有从网上下载
				mNewsDBManager = NewsDBManager.getNewsDBManager(getActivity());
				if (mNewsDBManager.getCount() > 0) {
					new Thread() {
						@Override
						public void run() {
							// 查询数据库获取数据
							lists = mNewsDBManager.queryNews();
							handler.sendEmptyMessage(100);
						}
					}.start();

				} else {
					// 下载获取数据
					new Thread() {
						public void run() {
							try {
								String data = HttpUtil.httpGetString(CommonUtil.NETPATH
										+ CommonUtil.NETPATH1);
								lists = ParserNewsUtil.getParserData(data);
								// 存到数据库
								mNewsDBManager.addNews(lists);
								handler.sendEmptyMessage(100);
								//
							} catch (Exception e) {
								e.printStackTrace();
							}
						};
					}.start();
				}
				
				//设置上拉和下拉
				mListView.setPullLoadEnable(true);
				mListView.setPullRefreshEnable(true);

				setListener();
		return view;
	}
	
	
	protected void setListener() {
		
		hlv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				for (int i = 0; i < hlv.getChildCount(); i++) {
					hlv.getChildAt(i).setSelected(false);
				}
				arg1.setSelected(true);
				 
			}
		});
		
		//上拉加载和下拉刷新的监听
		mListView.setXListViewListener(new IXListViewListener() {
					
					@Override
					public void onRefresh() {
						Toast.makeText(acticity, "下拉刷新", 0).show();
						//停止刷新
						mListView.stopRefresh();
						mListView.setRefreshTime(CommonUtil.getTime());
					}
					
					@Override
					public void onLoadMore() {
						Toast.makeText(acticity, "上拉加载", 0).show();
						//停止加载
						mListView.stopLoadMore();
					}
				});

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				Bundle bundle = new Bundle();
				bundle.putSerializable("news", mNewsAdapter.getInfos().get(arg2 -1));
				Intent it = new Intent(acticity, TwoActivity.class);
				it.putExtras(bundle);
				startActivity(it);

			}
		});

		// 监听ListView的滚动状态
		mListView.setOnScrollListener(new OnScrollListener() {
			// 当滚动状态改变的时候
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				mNewsAdapter.setFlag(false);
				switch (scrollState) {
				// 快速滑动
				case OnScrollListener.SCROLL_STATE_FLING:

					break;
				// 结束滚动
				case OnScrollListener.SCROLL_STATE_IDLE:
				
					//下载
					for(int x = startIndex;  x < endIndex; x++){
					mImageLoaderUtil = new ImageLoaderUtil(imageLoadListener, acticity);
					Bitmap bitmap = mImageLoaderUtil.getBitmap(mNewsAdapter.getItem(x-1).icon);
					ImageView iv = (ImageView) mListView.findViewWithTag(mNewsAdapter.getItem(x-1).icon);
					if(bitmap != null){
						iv.setImageBitmap(bitmap);
					}
					}
					break;
				// 开始滚动
				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:

					break;
				}
			}
			/*当列表滚动时会不停的调用
			 * firstVisibleItem -- 滚动过程中屏幕最上方图片的索引
			 * visibleItemCount -- 屏幕上有几个Item
			 * totalItemCount -- 总共有多少Item*/
			 
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				startIndex = Math.max(firstVisibleItem, 1);
				endIndex = Math.min(firstVisibleItem + visibleItemCount,
						totalItemCount - 1);

			}
		});

		
	}
	
	public class MyErrorHandler implements ErrorListener{

		@Override
		public void onErrorResponse(VolleyError error) {
			Log.i("error", "网络连接失败");
		}
		
	}
	
	public class MyResponseHandler implements Listener<String>{

		@Override
		public void onResponse(String response) {//服务器的回调
			//解析...
			ArrayList<SubType> lists = ParserNewsUtil.getGsonParser(response);
			adapter.addDataToAdapter(lists);
			adapter.notifyDataSetChanged();
			//LogUtil.d("adapter", adapter.getSubTypes().toString());
		}
	}
	
}
