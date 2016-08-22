package com.zc.news.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.feicui.news.R;
import com.zc.news.Util.CommonUtil;
import com.zc.news.Util.ParserUser;
import com.zc.news.Util.SharedPreferencesUtil;
import com.zc.news.model.biz.UserManager;
import com.zc.news.model.entity.Entity;
import com.zc.news.model.entity.Register;

public class FragmentRegist extends Fragment {

	private EditText email,nickName,password;
	private Button regist;
	private UserManager userMgr;
	private HomeActivity homeActivity;
	private CheckBox checkBox;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_register, null);
		homeActivity = (HomeActivity) getActivity();
		email = (EditText) view.findViewById(R.id.regist_et1);
		nickName = (EditText) view.findViewById(R.id.regist_et2);
		password = (EditText) view.findViewById(R.id.regist_et3);
		regist = (Button) view.findViewById(R.id.button_register);
		checkBox = (CheckBox) view.findViewById(R.id.checkBox1);
		//注册按钮
		regist.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String s1 = email.getText().toString();
				String s2 = nickName.getText().toString().trim();
				String s3 = password.getText().toString().trim();
				
				//判断是否同意协议条款 
				if(!checkBox.isChecked()){ 
					Toast.makeText(getActivity(), "没有同意协议条款！", Toast.LENGTH_SHORT).show(); 
					return; 
					}
				if(!CommonUtil.isEmail(s1)){ 
					Toast.makeText(getActivity(), "请求输入正确的邮箱格式", Toast.LENGTH_SHORT).show();
					return; 
					}
				if(TextUtils.isEmpty(s2) ){ 
					Toast.makeText(getActivity(), "请输入用户昵称", Toast.LENGTH_SHORT).show(); 
					return ; 
					}
				if(s3.length() < 6 || s3.length() > 16 ){ 
					Toast.makeText(getActivity(), "密码长度错误", Toast.LENGTH_SHORT).show(); 
					return ; 
					}
				if(!CommonUtil.verifyPassword(s3)){ 
					Toast.makeText(getActivity(), "请输入6-16位数字和字母组合的密码", Toast.LENGTH_SHORT).show(); 
					return; 
					}
				if(userMgr == null){
					userMgr = UserManager.getInstance();
				}
				//使用用户管理器帮助注册
				userMgr.regist(getActivity(), new MyTextResponseHandler(), new MyTextError(), 
						s2,s1,s3);
			}
		});
		
		return view;
	}
	
	public class MyTextResponseHandler implements Listener<String>{

		@Override
		public void onResponse(String response) {
			//解析
			Log.i("response", response);
			Entity<Register> entity = ParserUser.parserRegist(response);
			Register register =  entity.getData();
			int result = register.getResult();
			if(result == 0){
				//保存用户信息
				SharedPreferencesUtil.saveRegister(getActivity(), register);
				//跳转到用户中心
				startActivity(new Intent(getActivity(), UserActivity.class));
				homeActivity.showNewsContent();
			}else if(result == -2){
				Toast.makeText(getActivity(), register.getExplain(), Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(getActivity(), register.getExplain(), Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	
	public class MyTextError implements ErrorListener{

		@Override
		public void onErrorResponse(VolleyError error) {
			Toast.makeText(getActivity(), "注册失败！", Toast.LENGTH_SHORT).show();
		}
		
	}
	
}
