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
 * �����˵���ߵ�������š�����ת����ʾ�������ݵĽ���
 * @author Administrator
 *
 */
public class FragmentNews extends Fragment {

	private HorizontalListView hlv;//ˮƽ�б�
	private NewsTypeAdapter adapter;//��������������
	private static XListView mListView;//�������غ�����ˢ��
	private NewsAdapter mNewsAdapter;//����������������
	private NewsDBManager mNewsDBManager;
	private HomeActivity acticity;
	private ImageLoaderUtil mImageLoaderUtil;//ͼƬ���ػ���
	private int startIndex;
	private int endIndex;
	private ProgressBar mProgressBar;//Բ�ν�����
	private ArrayList<News> lists = new ArrayList<News>();
	private static ImageLoaderUtil.ImageLoadListener imageLoadListener = new ImageLoaderUtil.ImageLoadListener() {
		public void imageLoadOK(android.graphics.Bitmap bitmap, String url) {
			// ��������ص�ҲҪ����ͼƬ
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
				// �������ݿ���
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
		//���������������Ҫ��������������
		NewsManager.loadNewsType(new MyErrorHandler(),new MyResponseHandler(),getActivity());
		
		// �鿴���ݿ��Ƿ������ݣ�û�д���������
				mNewsDBManager = NewsDBManager.getNewsDBManager(getActivity());
				if (mNewsDBManager.getCount() > 0) {
					new Thread() {
						@Override
						public void run() {
							// ��ѯ���ݿ��ȡ����
							lists = mNewsDBManager.queryNews();
							handler.sendEmptyMessage(100);
						}
					}.start();

				} else {
					// ���ػ�ȡ����
					new Thread() {
						public void run() {
							try {
								String data = HttpUtil.httpGetString(CommonUtil.NETPATH
										+ CommonUtil.NETPATH1);
								lists = ParserNewsUtil.getParserData(data);
								// �浽���ݿ�
								mNewsDBManager.addNews(lists);
								handler.sendEmptyMessage(100);
								//
							} catch (Exception e) {
								e.printStackTrace();
							}
						};
					}.start();
				}
				
				//��������������
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
		
		//�������غ�����ˢ�µļ���
		mListView.setXListViewListener(new IXListViewListener() {
					
					@Override
					public void onRefresh() {
						Toast.makeText(acticity, "����ˢ��", 0).show();
						//ֹͣˢ��
						mListView.stopRefresh();
						mListView.setRefreshTime(CommonUtil.getTime());
					}
					
					@Override
					public void onLoadMore() {
						Toast.makeText(acticity, "��������", 0).show();
						//ֹͣ����
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

		// ����ListView�Ĺ���״̬
		mListView.setOnScrollListener(new OnScrollListener() {
			// ������״̬�ı��ʱ��
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				mNewsAdapter.setFlag(false);
				switch (scrollState) {
				// ���ٻ���
				case OnScrollListener.SCROLL_STATE_FLING:

					break;
				// ��������
				case OnScrollListener.SCROLL_STATE_IDLE:
				
					//����
					for(int x = startIndex;  x < endIndex; x++){
					mImageLoaderUtil = new ImageLoaderUtil(imageLoadListener, acticity);
					Bitmap bitmap = mImageLoaderUtil.getBitmap(mNewsAdapter.getItem(x-1).icon);
					ImageView iv = (ImageView) mListView.findViewWithTag(mNewsAdapter.getItem(x-1).icon);
					if(bitmap != null){
						iv.setImageBitmap(bitmap);
					}
					}
					break;
				// ��ʼ����
				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:

					break;
				}
			}
			/*���б����ʱ�᲻ͣ�ĵ���
			 * firstVisibleItem -- ������������Ļ���Ϸ�ͼƬ������
			 * visibleItemCount -- ��Ļ���м���Item
			 * totalItemCount -- �ܹ��ж���Item*/
			 
			
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
			Log.i("error", "��������ʧ��");
		}
		
	}
	
	public class MyResponseHandler implements Listener<String>{

		@Override
		public void onResponse(String response) {//�������Ļص�
			//����...
			ArrayList<SubType> lists = ParserNewsUtil.getGsonParser(response);
			adapter.addDataToAdapter(lists);
			adapter.notifyDataSetChanged();
			//LogUtil.d("adapter", adapter.getSubTypes().toString());
		}
	}
	
}
