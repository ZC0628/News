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
 * 	�û�����   
 * @author Administrator
 *
 */
public class UserActivity extends BaseActivity implements
		ImageLoaderUtil.ImageLoadListener, OnClickListener {

	private ImageView mImageView;//���ؼ�
	private UserManager userMgr;
	private String token;//�û�����
	private UserAdapter mUserAdapter;
	private ListView mListView;//��¼��־������ʾ��¼��Ϣ�б�
	private User<LoginLog> user;
	private TextView userName;//��¼��
	private TextView integral;//����
	private TextView count;//������ͳ��
	private Button exit;//�˳���¼
	private ImageView icon;//ͷ��
	private ImageLoaderUtil imageLoaderUtil;
	private PopupWindow pw;//����ͷ��ĶԻ���
	private File photo;
	private LinearLayout root;//������
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
		// ʹ���û�������������¼
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
		// ����PopupWindow
		View view = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
				.inflate(R.layout.item_pop_selectpic, null);
		// ��ȡ����LinearLayout���õ��
		//���������
		LinearLayout photoTake = (LinearLayout) view
				.findViewById(R.id.photo_take);
		//����Ƭ����ѡ��
		LinearLayout photoSel = (LinearLayout) view
				.findViewById(R.id.photo_sel);
		photoTake.setOnClickListener(this);
		photoSel.setOnClickListener(this);
		pw = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, true);
		// ��������
		pw.setBackgroundDrawable(new BitmapDrawable());

	}

	@Override
	protected void setListener() {
		//���ؼ��ļ���
		mImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				right.setLogin(true);
			}
		});

		//�˳���¼�ļ���
		exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				SharedPreferencesUtil.clearUserInfo(UserActivity.this);
			}
		});

		// �������popupWindow�Ի���
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
			// ����
			Log.i("response", response);
			user = ParserUser.getUserParser(response);
			String userIcon = user.getPortrait();
			SharedPreferencesUtil.saveIconLink(UserActivity.this, userIcon);
			//���ý�����ͼ
			userName.setText(user.getUid());
			integral.setText(user.getIntegration() + "");
			count.setText(user.getComnum() + "");
			ArrayList<LoginLog> logins = (ArrayList<LoginLog>) user
					.getLoginlog();
			mUserAdapter.addDataToAdapter(logins);

			// ��ȡͷ��,�ȴӻ�������,û��������,�����걣�浽����
			String localPath = SharedPreferencesUtil
					.getPhoto(UserActivity.this);
			if (TextUtils.isEmpty(localPath)) {// "" null
				Bitmap bitmap = imageLoaderUtil.getBitmap(user.getPortrait());
				if (bitmap != null) {
					icon.setImageBitmap(bitmap);
				}
			} else {
				// ��SD���л�ȡͷ��
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
			Toast.makeText(UserActivity.this, "��ȡ�����쳣��", Toast.LENGTH_SHORT)
					.show();
		}

	}

	//��ͷ��������ɻص�
	@Override
	public void imageLoadOK(Bitmap bitmap, String url) {
		if (bitmap != null) {
			icon.setImageBitmap(bitmap);
		}
	}

	@Override
	public void onClick(View v) {
		// �ر�PopupWindow
		pw.dismiss();
		switch (v.getId()) {

		// �������
		case R.id.photo_take:
			Intent take = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(take, 1);//������  ���յ���1
			break;
		// ����ϵͳ�������ѡ��
		case R.id.photo_sel:
			// �����Դ��Ĺ��ܣ�ͼƬ�ļ��ã�
			Intent sel = new Intent(Intent.ACTION_PICK);
			//Ҫ��ȡ����������
			sel.setType("image/*");
			sel.putExtra("crop", "true");// ���òü�����
			sel.putExtra("aspectX", 1); // ��߱���
			sel.putExtra("aspectY", 1);
			sel.putExtra("outputX", 80);//���ֵ
			sel.putExtra("outputY", 80);
			sel.putExtra("return-data", true); // ���زü����
			startActivityForResult(sel, 2);//������  ���յ���2
			break;
		}
	}
	
	//�������ջ������ѡ�񷵻صĽ��
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(data != null){
			//��ȡ��Ƭ
			bitmap = data.getParcelableExtra("data");//ͷ��
			if(bitmap != null){
				//�浽SD��������ȡ�ⲿ�洢����·��
				File file = new File(Environment.getExternalStorageDirectory(),"newsphoto");
				if(!file.exists()){
					file.mkdirs();//��������ھʹ����ļ���
				}
				//mnt/sdcard/newsphoto/photo.jpg
				photo = new File(file,"photo.jpg");
				OutputStream os = null;
				try {
					os = new FileOutputStream(photo);
					//�Ƚ�ͼƬ��������������ת����File���͡�������ͷ�񵽱���
					bitmap.compress(CompressFormat.JPEG, 90, os);
					//����������������ϴ�ͷ��
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
			Log.i("onFailure", "ͼƬ�ϴ�ʧ��---"+responseString);
		}

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				String responseString) {
			//����
			Entity<Register> entity = ParserUser.parserRegist(responseString);
			Register register = entity.getData();
			Log.i("onSuccess", responseString);
			int result = register.getResult();
			if(result ==0){//�ϴ��ɹ�
				//������ͷ��·���������ļ���
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
			// ����
			user = ParserUser.getUserParser(response);
			String iconLink = user.getPortrait();
			SharedPreferencesUtil.saveIconLink(UserActivity.this, iconLink);
	}
	}
}
