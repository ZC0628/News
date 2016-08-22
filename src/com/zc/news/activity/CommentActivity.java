package com.zc.news.activity;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.feicui.news.R;
import com.feicui.news.R.layout;
import com.feicui.news.R.menu;
import com.zc.news.Util.CommonUtil;
import com.zc.news.Util.LogUtil;
import com.zc.news.Util.ParserNewsUtil;
import com.zc.news.Util.ParserUser;
import com.zc.news.Util.SharedPreferencesUtil;
import com.zc.news.Util.SystemUtil;
import com.zc.news.adapter.CommentsAdapter;
import com.zc.news.base.activity.BaseActivity;
import com.zc.news.model.biz.CommentsManager;
import com.zc.news.model.biz.NewsManager;
import com.zc.news.model.entity.Comment;
import com.zc.news.model.entity.Entity;
import com.zc.news.model.entity.News;
import com.zc.news.model.entity.Register;
import com.zc.news.model.httpclient.TextHttpResponseHandler;
import com.zc.news.xlistview.XListView;
import com.zc.news.xlistview.XListView.IXListViewListener;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class CommentActivity extends BaseActivity {

	private XListView xListView;
	private CommentsAdapter adapter;
	private ImageButton mImageButton;
	private ImageView mImageView;
	private EditText mEditText;
	private News news;
	private Comment comment;
	private int curId;
	private ProgressBar mProgressBar;
	private SystemUtil systemUtil;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		news = (News) getIntent().getSerializableExtra("news");
		initView();
		setListener();
	}

	@Override
	protected void initView() {
		xListView = (XListView) findViewById(R.id.comment_lv);
		adapter = new CommentsAdapter(this);
		xListView.setAdapter(adapter);
		mImageButton = (ImageButton) findViewById(R.id.comment_title_bar_ib);
		mImageView = (ImageView) findViewById(R.id.comment_iv);
		mEditText = (EditText) findViewById(R.id.comment_edittext);
		mProgressBar = (ProgressBar) findViewById(R.id.comment_pb);
		curId = adapter.getCount() <= 0 ? 0 : adapter.getItem(0).getCid();
		systemUtil = SystemUtil.getInstance(this);
		CommentsManager.loadComments(this, CommonUtil.VERSION_CODE + "",
				listener, errorListener, news.nid,1, curId);
		
	}

	@Override
	protected void setListener() {
		// 监听返回按钮
		mImageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();

			}
		});
		//
		mImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String ccontent = mEditText.getText().toString();
				if (ccontent == null || ccontent.equals("")) {
					Toast.makeText(CommentActivity.this, "要先写评论内容哦，亲！",
							Toast.LENGTH_SHORT).show();
				}else{
					Register  register = SharedPreferencesUtil.getRegister(CommentActivity.this);
					String token = register.getToken();
					String imei = SystemUtil.getImei(CommentActivity.this);
					sp = getSharedPreferences("user",
							Context.MODE_PRIVATE);
					String name = sp.getString("name", null); 
					String password = sp.getString("password", null);
					if(name != null && password != null){
						CommentsManager.sendCommnet(CommentActivity.this, news.nid, new MyTextResponseHandler(),new MyTextError(),token,imei,ccontent);
					}else{
						Toast.makeText(CommentActivity.this, "请先登录", 0).show();
					}
				}
			}
		});

		// 上拉加载和下拉刷新的监听
		xListView.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				Toast.makeText(CommentActivity.this, "下拉刷新", 0).show();
				// 停止刷新
				xListView.stopRefresh();
				xListView.setRefreshTime(CommonUtil.getTime());
				adapter.clearAllData();
				loadNextComment();
			}

			@Override
			public void onLoadMore() {
				loadPreComment();
				Toast.makeText(CommentActivity.this, "上拉加载", 0).show();
				// 停止加载
				xListView.stopLoadMore();
			}
		});

	}

	/*
	 * 加载下面的评论
	 */
	protected void loadPreComment() {
		
		if (systemUtil.isNetConn(this)) {
			CommentsManager.loadComments(this, CommonUtil.VERSION_CODE + "",
					listener, errorListener, news.nid,2, comment.getCid());
		}
	}

	/*
	 * 请求最新的评论
	 */
	protected void loadNextComment() {
		if(systemUtil.isNetConn(this)){
		LogUtil.d("loadNextComment","loadNextComment");
		CommentsManager.loadComments(this, CommonUtil.VERSION_CODE + "",
				listener, errorListener, news.nid,1, curId);
		}
	}

	private Listener<String> listener = new Listener<String>() {
		@Override
		public void onResponse(String response) {
			LogUtil.d("onResponse", response);
			ArrayList<Comment> comments = ParserNewsUtil.getComment(response);
			if (comments == null || comments.size() < 1) {
				return;
			}
			adapter.addDataToAdapter(comments);
			adapter.notifyDataSetChanged();
			mProgressBar.setVisibility(View.INVISIBLE);
			xListView.setVisibility(View.VISIBLE);
		}
	};

	private ErrorListener errorListener = new ErrorListener() {
		@Override
		public void onErrorResponse(VolleyError error) {
			// TODO Auto-generated method stub
			Toast.makeText(CommentActivity.this, "服务器连接错误！", Toast.LENGTH_SHORT)
					.show();
		}

	};
	
	public class MyTextError implements ErrorListener {

		@Override
		public void onErrorResponse(VolleyError error) {
			Toast.makeText(CommentActivity.this, "发送评论失败！", Toast.LENGTH_SHORT)
					.show();
		}
	}
	
	public class MyTextResponseHandler implements Listener<String>{

		@Override
		public void onResponse(String response) {
			//解析
			Entity<Register> entity = ParserUser.parserRegist(response);
			if(entity.getStatus() == 0){
				Register register =  entity.getData();
				String info = "";
				int result = register.getResult();
				if(result == 0){
					info = "评论成功";
					mEditText.setText(null); 
					mEditText.clearFocus();
				}else{
					info = "评论失败";
					}
				Toast.makeText(CommentActivity.this, info, Toast.LENGTH_SHORT).show();
				}
		}
}

}
