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
 * �����˵����ұ�
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
					//�����������ʾ����������
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
					//�����������ʾ����������
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
					if (mIconLink.equals(mIconLinkOld)) {// �ж�ͷ�������Ƿ�ı�
					} else {
						LogUtil.d("4", "4");
						LogUtil.d("mIconLink", mIconLink);
							Bitmap bitmap = imageLoaderUtil.getBitmap(mIconLink);
							if (bitmap != null) {
								mImageView.setImageBitmap(bitmap);
							}
					}
				
			}
			}else {// ����Ӧ���Զ���½
				LogUtil.d("7", "7");  
				
				userMgr.loginRight(getActivity(), new MyTextResponseHandler1(), new MyTextError1(), name, password);
			}
			mIconLinkOld = mIconLink;// �����µ�ͷ������
		} else {
			LogUtil.d("6", "6");
			isLogin = false;
			mImageView.setImageResource(R.drawable.biz_pc_main_info_profile_avatar_bg_dark);
			mTextView.setText("���̵�½");
		}
	}
	
	public class MyTextResponseHandler implements Listener<String> {

		@Override
		public void onResponse(String response) {
			// ����
			Log.i("response", response);
			user = ParserUser.getUserParser(response);
			
		}
	}
	
	public class MyTextError implements ErrorListener {

		@Override
		public void onErrorResponse(VolleyError error) {
			Toast.makeText(getActivity(), "��ȡ�����쳣��", Toast.LENGTH_SHORT)
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
			//����
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
			Toast.makeText(homeActivity, "��¼�쳣��", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	public class MyTextResponseHandler2 implements Listener<String>{

		@Override
		public void onResponse(String response) {
			//����
			Version version = ParserVersion.parserJson(response);
			//�жϱ��ذ汾��������汾 
			if (CommonUtil.getVersionCode(FragmentRight.this .getActivity()) < Integer.parseInt(version.getVersion())) { 
				// ִ���������� 
				Toast.makeText(getActivity(), "�����������°汾." ,0).show();
				UpdateManager.downLoad(getActivity(), version.getLink());
			}else{
				Toast.makeText(getActivity(), "��ǰ�������°汾", 0) .show();
			}
			}
			
		
	}
	
	public class MyTextError2 implements ErrorListener{

		@Override
		public void onErrorResponse(VolleyError error) {
			Toast.makeText(homeActivity, "����ʧ�ܣ�", Toast.LENGTH_SHORT).show();
		}
		
	}
}
