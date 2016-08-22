package com.zc.news.activity;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.feicui.news.R;








import com.zc.news.Util.ParserUser;
import com.zc.news.Util.SharedPreferencesUtil;
import com.zc.news.activity.FragmentRegist.MyTextError;
import com.zc.news.activity.FragmentRegist.MyTextResponseHandler;
import com.zc.news.model.biz.UserManager;
import com.zc.news.model.entity.Entity;
import com.zc.news.model.entity.Register;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FragmentLogin extends Fragment implements OnClickListener{

	private EditText name,pwd;
	private Button regist,forgetPwd,login;
	private HomeActivity homeActivity;
	private UserManager userMgr;
	private String s1,s2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_login, null);
		name = (EditText) view.findViewById(R.id.login_et1);
		pwd = (EditText) view.findViewById(R.id.login_et2);
		regist = (Button) view.findViewById(R.id.login_register);
		forgetPwd = (Button) view.findViewById(R.id.login_forgetpsw);
		login = (Button) view.findViewById(R.id.login_input);
		
		regist.setOnClickListener(this); 
		forgetPwd.setOnClickListener(this); 
		login.setOnClickListener(this);
		return view;
	}
	@Override
	public void onClick(View v) {
		s1 = name.getText().toString().trim();
		s2 = pwd.getText().toString().trim();
		homeActivity = (HomeActivity) getActivity();
		switch (v.getId()) {
		case R.id.login_register:
			//切换到FragmentRegist
			homeActivity.showRegistContent();
			break;
		case R.id.login_forgetpsw:
			//切换到FragmentRegist
			homeActivity.showForgetpass();
			break;
		case R.id.login_input:
			
			//判断用户名和密码是否为空，密码长度是否符合规范 
			if(TextUtils.isEmpty(s1)){ 
				Toast.makeText(getActivity(), "请输入用户名", 0).show(); 
				return; 
				}
			if(TextUtils.isEmpty(s2)){ 
				Toast.makeText(getActivity(), "密码不能为空", Toast.LENGTH_SHORT).show(); 
				return ; 
				}
			if(s2.length() < 6 || s2.length() > 16 ){ 
				Toast.makeText(getActivity(), "密码长度错误", Toast.LENGTH_SHORT).show(); 
				return ; 
				}
			if(userMgr == null){
				userMgr = UserManager.getInstance();
			}
			//使用用户管理器帮助登录
			userMgr.login(homeActivity, new MyTextResponseHandler(), new MyTextError(), 
					s1,s2);
			break;
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
					info = "登录成功";
					SharedPreferencesUtil.saveRegister(homeActivity, register);
					SharedPreferences sp = getActivity().getSharedPreferences(
							"user", Context.MODE_PRIVATE);
					Editor editor = sp.edit();
					editor.putString("name", s1);
					editor.putString("token", register.getToken());
					editor.putString("password",s2);
					editor.commit();
					//跳转到用户中心
					startActivity(new Intent(getActivity(), UserActivity.class));
					homeActivity.showNewsContent();
				}else if(result == -3){
					info = "用户名或密码错误";
				}else{
					info = "登录失败";
				}
				Toast.makeText(homeActivity, info, Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(homeActivity, entity.getMessage(), Toast.LENGTH_SHORT).show();
			}
			}
			
		
	}
	
	public class MyTextError implements ErrorListener{

		@Override
		public void onErrorResponse(VolleyError error) {
			Toast.makeText(homeActivity, "登录异常！", Toast.LENGTH_SHORT).show();
		}
		
	}

}
