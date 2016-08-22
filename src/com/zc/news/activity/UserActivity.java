package com.zc.news.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import org.apache.http.Header;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.feicui.news.R;
import com.zc.news.Util.ImageLoaderUtil;
import com.zc.news.Util.LogUtil;
import com.zc.news.Util.ParserUser;
import com.zc.news.Util.SharedPreferencesUtil;
import com.zc.news.Util.SystemUtil;
import com.zc.news.Util.ImageLoaderUtil.ImageLoadListener;
import com.zc.news.adapter.UserAdapter;
import com.zc.news.base.activity.BaseActivity;
import com.zc.news.model.biz.UserManager;
import com.zc.news.model.entity.Entity;
import com.zc.news.model.entity.LoginLog;
import com.zc.news.model.entity.Register;
import com.zc.news.model.entity.User;
import com.zc.news.model.httpclient.TextHttpResponseHandler;

import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 	用户界面   
 * @author Administrator
 *
 */
public class UserActivity extends BaseActivity implements
		ImageLoaderUtil.ImageLoadListener, OnClickListener {

	private ImageView mImageView;//返回键
	private UserManager userMgr;
	private String token;//用户令牌
	private UserAdapter mUserAdapter;
	private ListView mListView;//登录日志——显示登录信息列表
	private User<LoginLog> user;
	private TextView userName;//登录名
	private TextView integral;//积分
	private TextView count;//跟帖数统计
	private Button exit;//退出登录
	private ImageView icon;//头像
	private ImageLoaderUtil imageLoaderUtil;
	private PopupWindow pw;//更改头像的对话框
	private File photo;
	private LinearLayout root;//根布局
	private Bitmap bitmap;
	private LinearLayout linear;
	private ProgressBar mProgressBar;
	private FragmentRight right;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		initView();
		setListener();
		// 使用用户管理器帮助登录
		userMgr.user(this, new MyTextResponseHandler(), new MyTextError(),
				SystemUtil.getImei(this), token);
		mListView.setAdapter(mUserAdapter);
	}

	@Override
	protected void initView() {
		mImageView = (ImageView) findViewById(R.id.imageView_back);
		token = SharedPreferencesUtil.getRegister(this).getToken();
		mUserAdapter = new UserAdapter(this);
		mListView = (ListView) findViewById(R.id.list);
		right = new FragmentRight();
		userName = (TextView) findViewById(R.id.name);
		icon = (ImageView) findViewById(R.id.icon);
		integral = (TextView) findViewById(R.id.integral);
		count = (TextView) findViewById(R.id.comment_count);
		exit = (Button) findViewById(R.id.btn_exit);
		linear = (LinearLayout) findViewById(R.id.user_liner);
		mProgressBar = (ProgressBar) findViewById(R.id.user_pb);
		userMgr = UserManager.getInstance();
		imageLoaderUtil = new ImageLoaderUtil(this, this);
		root = (LinearLayout) findViewById(R.id.layout);
		// 创建PopupWindow
		View view = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
				.inflate(R.layout.item_pop_selectpic, null);
		// 获取两个LinearLayout设置点击
		//调用照相机
		LinearLayout photoTake = (LinearLayout) view
				.findViewById(R.id.photo_take);
		//从照片库中选择
		LinearLayout photoSel = (LinearLayout) view
				.findViewById(R.id.photo_sel);
		photoTake.setOnClickListener(this);
		photoSel.setOnClickListener(this);
		pw = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, true);
		// 设置属性
		pw.setBackgroundDrawable(new BitmapDrawable());

	}

	@Override
	protected void setListener() {
		//返回键的监听
		mImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				right.setLogin(true);
			}
		});

		//退出登录的监听
		exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				SharedPreferencesUtil.clearUserInfo(UserActivity.this);
			}
		});

		// 点击弹出popupWindow对话框
		icon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pw.showAtLocation(root, Gravity.TOP, 0, 50);
			}
		});

	}

	public class MyTextResponseHandler implements Listener<String> {

		@Override
		public void onResponse(String response) {
			// 解析
			Log.i("response", response);
			user = ParserUser.getUserParser(response);
			String userIcon = user.getPortrait();
			SharedPreferencesUtil.saveIconLink(UserActivity.this, userIcon);
			//设置界面视图
			userName.setText(user.getUid());
			integral.setText(user.getIntegration() + "");
			count.setText(user.getComnum() + "");
			ArrayList<LoginLog> logins = (ArrayList<LoginLog>) user
					.getLoginlog();
			mUserAdapter.addDataToAdapter(logins);

			// 获取头像,先从缓存中找,没有则下载,下载完保存到缓存
			String localPath = SharedPreferencesUtil
					.getPhoto(UserActivity.this);
			if (TextUtils.isEmpty(localPath)) {// "" null
				Bitmap bitmap = imageLoaderUtil.getBitmap(user.getPortrait());
				if (bitmap != null) {
					icon.setImageBitmap(bitmap);
				}
			} else {
				// 从SD卡中获取头像
				Bitmap bitmap = BitmapFactory.decodeFile(Environment
						.getExternalStorageDirectory() + "/newsphoto/photo.jpg");
				icon.setImageBitmap(bitmap);
			}
			linear.setVisibility(View.VISIBLE);
			mProgressBar.setVisibility(View.INVISIBLE);
		}
	}

	public class MyTextError implements ErrorListener {

		@Override
		public void onErrorResponse(VolleyError error) {
			Toast.makeText(UserActivity.this, "获取数据异常！", Toast.LENGTH_SHORT)
					.show();
		}

	}

	//当头像下载完成回调
	@Override
	public void imageLoadOK(Bitmap bitmap, String url) {
		if (bitmap != null) {
			icon.setImageBitmap(bitmap);
		}
	}

	@Override
	public void onClick(View v) {
		// 关闭PopupWindow
		pw.dismiss();
		switch (v.getId()) {

		// 用相机拍
		case R.id.photo_take:
			Intent take = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(take, 1);//请求码  拍照的是1
			break;
		// 进入系统的相册中选择
		case R.id.photo_sel:
			// 开启自带的功能（图片的剪裁）
			Intent sel = new Intent(Intent.ACTION_PICK);
			//要获取的数据类型
			sel.setType("image/*");
			sel.putExtra("crop", "true");// 设置裁剪功能
			sel.putExtra("aspectX", 1); // 宽高比例
			sel.putExtra("aspectY", 1);
			sel.putExtra("outputX", 80);//宽高值
			sel.putExtra("outputY", 80);
			sel.putExtra("return-data", true); // 返回裁剪结果
			startActivityForResult(sel, 2);//请求码  拍照的是2
			break;
		}
	}
	
	//接收拍照或者相册选择返回的结果
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(data != null){
			//获取照片
			bitmap = data.getParcelableExtra("data");//头像
			if(bitmap != null){
				//存到SD卡——获取外部存储卡的路径
				File file = new File(Environment.getExternalStorageDirectory(),"newsphoto");
				if(!file.exists()){
					file.mkdirs();//如果不存在就创建文件夹
				}
				//mnt/sdcard/newsphoto/photo.jpg
				photo = new File(file,"photo.jpg");
				OutputStream os = null;
				try {
					os = new FileOutputStream(photo);
					//先将图片保存起来，并且转换成File类型——保存头像到本地
					bitmap.compress(CompressFormat.JPEG, 90, os);
					//发送请求给服务器上传头像
					userMgr.uploadIcon(this, token, photo, new MyResponseHanlder());
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally{
					if(os != null){
						try {
							os.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	class MyResponseHanlder extends TextHttpResponseHandler{

		@Override
		public void onFailure(int statusCode, Header[] headers,
				String responseString, Throwable throwable) {
			Log.i("onFailure", "图片上传失败---"+responseString);
		}

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				String responseString) {
			//解析
			Entity<Register> entity = ParserUser.parserRegist(responseString);
			Register register = entity.getData();
			Log.i("onSuccess", responseString);
			int result = register.getResult();
			if(result ==0){//上传成功
				//将本地头像路径保存在文件中
				SharedPreferencesUtil.savePhoto(UserActivity.this, photo.getAbsolutePath());
				icon.setImageBitmap(bitmap);
				userMgr.userIcon(UserActivity.this, new MyTextResponseHandler1(), new MyTextError(),
						SystemUtil.getImei(UserActivity.this), token);
			}
		}

	
	} 
	
	public class MyTextResponseHandler1 implements Listener<String> {

		@Override
		public void onResponse(String response) {
			// 解析
			user = ParserUser.getUserParser(response);
			String iconLink = user.getPortrait();
			SharedPreferencesUtil.saveIconLink(UserActivity.this, iconLink);
	}
	}
}
