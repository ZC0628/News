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
		//ע�ᰴť
		regist.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String s1 = email.getText().toString();
				String s2 = nickName.getText().toString().trim();
				String s3 = password.getText().toString().trim();
				
				//�ж��Ƿ�ͬ��Э������ 
				if(!checkBox.isChecked()){ 
					Toast.makeText(getActivity(), "û��ͬ��Э�����", Toast.LENGTH_SHORT).show(); 
					return; 
					}
				if(!CommonUtil.isEmail(s1)){ 
					Toast.makeText(getActivity(), "����������ȷ�������ʽ", Toast.LENGTH_SHORT).show();
					return; 
					}
				if(TextUtils.isEmpty(s2) ){ 
					Toast.makeText(getActivity(), "�������û��ǳ�", Toast.LENGTH_SHORT).show(); 
					return ; 
					}
				if(s3.length() < 6 || s3.length() > 16 ){ 
					Toast.makeText(getActivity(), "���볤�ȴ���", Toast.LENGTH_SHORT).show(); 
					return ; 
					}
				if(!CommonUtil.verifyPassword(s3)){ 
					Toast.makeText(getActivity(), "������6-16λ���ֺ���ĸ��ϵ�����", Toast.LENGTH_SHORT).show(); 
					return; 
					}
				if(userMgr == null){
					userMgr = UserManager.getInstance();
				}
				//ʹ���û�����������ע��
				userMgr.regist(getActivity(), new MyTextResponseHandler(), new MyTextError(), 
						s2,s1,s3);
			}
		});
		
		return view;
	}
	
	public class MyTextResponseHandler implements Listener<String>{

		@Override
		public void onResponse(String response) {
			//����
			Log.i("response", response);
			Entity<Register> entity = ParserUser.parserRegist(response);
			Register register =  entity.getData();
			int result = register.getResult();
			if(result == 0){
				//�����û���Ϣ
				SharedPreferencesUtil.saveRegister(getActivity(), register);
				//��ת���û�����
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
			Toast.makeText(getActivity(), "ע��ʧ�ܣ�", Toast.LENGTH_SHORT).show();
		}
		
	}
	
}
