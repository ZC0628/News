package com.zc.news.activity;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.feicui.news.R;
import com.zc.news.Util.CommonUtil;
import com.zc.news.Util.ParserUser;
import com.zc.news.Util.SharedPreferencesUtil;
import com.zc.news.activity.FragmentRegist.MyTextError;
import com.zc.news.activity.FragmentRegist.MyTextResponseHandler;
import com.zc.news.model.biz.UserManager;
import com.zc.news.model.entity.Entity;
import com.zc.news.model.entity.Register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FragmentForget extends Fragment{
	
	private UserManager userMgr;
	private Button forget;
	private EditText email;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_forgetpass, null);
		forget = (Button) view.findViewById(R.id.btn_forget);
		email = (EditText) view.findViewById(R.id.edit_email);
		
		forget.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String s1 = email.getText().toString();
				if (!CommonUtil.isEmail(s1)) { 
					Toast.makeText(getActivity(), "请求输入正确的邮箱格式", Toast.LENGTH_SHORT).show(); 
					} 
				if(userMgr == null){
					userMgr = UserManager.getInstance();
				}
				//使用用户管理器帮助注册
				userMgr.forget(getActivity(), new MyTextResponseHandler(), new MyTextError(), s1);
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
				Toast.makeText(getActivity(), "已成功发送到邮箱", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(getActivity(), register.getExplain(), Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	
	public class MyTextError implements ErrorListener{

		@Override
		public void onErrorResponse(VolleyError error) {
		}
		
	}
}
