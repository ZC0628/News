package com.zc.news.activity;

import java.util.ArrayList;











import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.feicui.news.R;
import com.zc.news.Util.CommonUtil;
import com.zc.news.Util.ImageLoaderUtil;
import com.zc.news.Util.LogUtil;
import com.zc.news.Util.ParserUser;
import com.zc.news.Util.ParserVersion;
import com.zc.news.Util.SharedPreferencesUtil;
import com.zc.news.Util.SystemUtil;
import com.zc.news.model.biz.UpdateManager;
import com.zc.news.model.biz.UserManager;
import com.zc.news.model.entity.Entity;
import com.zc.news.model.entity.LoginLog;
import com.zc.news.model.entity.Register;
import com.zc.news.model.entity.User;
import com.zc.news.model.entity.Version;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 侧拉菜单的右边
 * @author Administrator
 *
 */
public class FragmentRight extends Fragment implements ImageLoaderUtil.ImageLoadListener{

	private ImageView mImageView;
	private HomeActivity homeActivity;
	private TextView mTextView;
	private boolean isLogin;
	private String name,password,token;
	private String mIconLink, mIconLinkOld;
	private ImageLoaderUtil imageLoaderUtil;
	private UserManager userMgr;
	private SharedPreferences sp;
	private User<LoginLog> user;
	private TextView update;

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_menu_right, null);
		mImageView = (ImageView) view.findViewById(R.id.unlogin_iv);
		update = (TextView) view.findViewById(R.id.update_tv);
		mTextView = (TextView) view.findViewById(R.id.unlogin_tv);
		homeActivity = (HomeActivity) getActivity();
		imageLoaderUtil = new ImageLoaderUtil(this, getActivity());
		userMgr = UserManager.getInstance();
		userMgr.userRight(getActivity(), new MyTextResponseHandler(), new MyTextError(),
				SystemUtil.getImei(getActivity()), token);
		mImageView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if (isLogin) {
					getActivity().startActivity(
							new Intent(getActivity(), UserActivity.class));
					homeActivity.showNewsContent();
				} else {
					//点击将内容显示在主界面中
					homeActivity.goLogin();
				}
				
			}
			
		});
		
		update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UpdateManager.judgeUpdate(getActivity(), new MyTextResponseHandler2(), new MyTextError2(),SystemUtil.getImei(getActivity()),"com.feicui.news",CommonUtil.getVersionCode(getActivity()));
				
			}
		});
		
		mTextView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if (isLogin) {
					getActivity().startActivity(
							new Intent(getActivity(), UserActivity.class));
					homeActivity.showNewsContent();
				} else {
					//点击将内容显示在主界面中
					homeActivity.goLogin();
				}
			}
			
		});
		login();
		return view;
	}
	
	public void login() {
		sp = getActivity().getSharedPreferences("user",
				Context.MODE_PRIVATE);
		name = sp.getString("name", null);
		password = sp.getString("password", null);
		token = sp.getString("token", null);
		mIconLink = SharedPreferencesUtil.getIconLink(getActivity());
		if (name != null && password != null && token != null) {
			LogUtil.d("1", "1");
			if (isLogin) {
				LogUtil.d("2", "2");
				if (mIconLink != null){
					LogUtil.d("3", "3");
					if (mIconLink.equals(mIconLinkOld)) {// 判断头像链接是否改变
					} else {
						LogUtil.d("4", "4");
						LogUtil.d("mIconLink", mIconLink);
							Bitmap bitmap = imageLoaderUtil.getBitmap(mIconLink);
							if (bitmap != null) {
								mImageView.setImageBitmap(bitmap);
							}
					}
				
			}
			}else {// 进入应用自动登陆
				LogUtil.d("7", "7");  
				
				userMgr.loginRight(getActivity(), new MyTextResponseHandler1(), new MyTextError1(), name, password);
			}
			mIconLinkOld = mIconLink;// 保存新的头像链接
		} else {
			LogUtil.d("6", "6");
			isLogin = false;
			mImageView.setImageResource(R.drawable.biz_pc_main_info_profile_avatar_bg_dark);
			mTextView.setText("立刻登陆");
		}
	}
	
	public class MyTextResponseHandler implements Listener<String> {

		@Override
		public void onResponse(String response) {
			// 解析
			Log.i("response", response);
			user = ParserUser.getUserParser(response);
			
		}
	}
	
	public class MyTextError implements ErrorListener {

		@Override
		public void onErrorResponse(VolleyError error) {
			Toast.makeText(getActivity(), "获取数据异常！", Toast.LENGTH_SHORT)
					.show();
		}

	}

	@Override
	public void imageLoadOK(Bitmap bitmap, String url) {
		if (bitmap != null) {
			mImageView.setImageBitmap(bitmap);
		}
		
	}
	
	public class MyTextResponseHandler1 implements Listener<String>{

		@Override
		public void onResponse(String response) {
			//解析
			Entity<Register> entity = ParserUser.parserRegist(response);
			if(entity.getStatus() == 0){
				Register register =  entity.getData();
				int result = register.getResult();
				if(result == 0){
					LogUtil.d("11", "11");
					mTextView.setText(name);
					mIconLink = SharedPreferencesUtil.getIconLink(getActivity());
					if (mIconLink != null) {
						Bitmap bitmap = imageLoaderUtil.getBitmap(mIconLink);
						LogUtil.d("12", "12");
						if (bitmap != null) {
							LogUtil.d("13", "13");
							mImageView.setImageBitmap(bitmap);
						} 
					}
					isLogin = true;
					
				}
			}else{
				Toast.makeText(homeActivity, entity.getMessage(), Toast.LENGTH_SHORT).show();
			}
			}
			
		
	}
	
	public class MyTextError1 implements ErrorListener{

		@Override
		public void onErrorResponse(VolleyError error) {
			Toast.makeText(homeActivity, "登录异常！", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	public class MyTextResponseHandler2 implements Listener<String>{

		@Override
		public void onResponse(String response) {
			//解析
			Version version = ParserVersion.parserJson(response);
			//判断本地版本与服务器版本 
			if (CommonUtil.getVersionCode(FragmentRight.this .getActivity()) < Integer.parseInt(version.getVersion())) { 
				// 执行下载请求 
				Toast.makeText(getActivity(), "正在下载最新版本." ,0).show();
				UpdateManager.downLoad(getActivity(), version.getLink());
			}else{
				Toast.makeText(getActivity(), "当前已是最新版本", 0) .show();
			}
			}
			
		
	}
	
	public class MyTextError2 implements ErrorListener{

		@Override
		public void onErrorResponse(VolleyError error) {
			Toast.makeText(homeActivity, "更新失败！", Toast.LENGTH_SHORT).show();
		}
		
	}
}
