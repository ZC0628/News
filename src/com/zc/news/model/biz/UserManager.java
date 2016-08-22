package com.zc.news.model.biz;

import java.io.File;
import java.io.FileNotFoundException;

import android.content.Context;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.zc.news.Util.CommonUtil;
import com.zc.news.activity.FragmentForget;
import com.zc.news.activity.FragmentLogin;
import com.zc.news.activity.FragmentRegist;
import com.zc.news.activity.FragmentRight;
import com.zc.news.activity.UserActivity;
import com.zc.news.activity.FragmentLogin.MyTextError;
import com.zc.news.activity.FragmentRight.MyTextError1;
import com.zc.news.model.httpclient.AsyncHttpClient;
import com.zc.news.model.httpclient.RequestParams;
import com.zc.news.model.httpclient.ResponseHandlerInterface;
import com.zc.news.volley.VolleySingleton;

/**
 * �����û�ע��,��¼,��������Ȳ���
 * @author Administrator
 *
 */
public class UserManager {

	private static UserManager userManager;

	private UserManager() {
	}

	public static UserManager getInstance() {
		if (userManager == null) {
			userManager = new UserManager();
		}
		return userManager;
	}

	// ����ע������
	public void regist(Context context, FragmentRegist.MyTextResponseHandler handler, ErrorListener error,
			String... s) {
		VolleySingleton vs = VolleySingleton.getVolleySingleton(context);
		RequestQueue queue = vs.getRequestQueue();
		StringRequest sr = new StringRequest(Method.GET, CommonUtil.NETPATH
				+ "user_register?ver=1&uid=" + s[0] + "&email=" + s[1]
				+ "&pwd=" + s[2], handler, error);
		vs.addToRequestQueue(sr);
	}
	
	// ����������������
		public void forget(Context context, FragmentForget.MyTextResponseHandler handler, ErrorListener error,
				String... s) {
			VolleySingleton vs = VolleySingleton.getVolleySingleton(context);
			RequestQueue queue = vs.getRequestQueue();
			StringRequest sr = new StringRequest(Method.GET, CommonUtil.NETPATH
					+ "user_forgetpass?ver=1&email="+s[0], handler, error);
			vs.addToRequestQueue(sr);
		}

	// ���͵�¼����
	public void login(Context context, FragmentLogin.MyTextResponseHandler handler, MyTextError error,
			String... s) {
		VolleySingleton vs = VolleySingleton.getVolleySingleton(context);
		RequestQueue queue = vs.getRequestQueue();
		StringRequest sr = new StringRequest(Method.GET,
				CommonUtil.NETPATH + "user_login?ver=1&uid=" + s[0] + "&pwd="
						+ s[1] + "&device=0", handler, error);
		vs.addToRequestQueue(sr);
	}
	
	// �Ҳ˵����͵�¼����
		public void loginRight(Context context, FragmentRight.MyTextResponseHandler1 handler, FragmentRight.MyTextError1 error,
				String... s) {
			VolleySingleton vs = VolleySingleton.getVolleySingleton(context);
			RequestQueue queue = vs.getRequestQueue();
			StringRequest sr = new StringRequest(Method.GET,
					CommonUtil.NETPATH + "user_login?ver=1&uid=" + s[0] + "&pwd="
							+ s[1] + "&device=0", handler, error);
			vs.addToRequestQueue(sr);
		}

	// �����û���Ϣ����
	public void user(Context context, UserActivity.MyTextResponseHandler handler, ErrorListener error,
			String... s) {
		VolleySingleton vs = VolleySingleton.getVolleySingleton(context);
		RequestQueue queue = vs.getRequestQueue();
		StringRequest sr = new StringRequest(Method.GET, CommonUtil.NETPATH
				+ "user_home?ver=1&imei=" + s[0] + "&token=" + s[1], handler,
				error);
		vs.addToRequestQueue(sr);
	}
	
	// �����û���Ϣ����
		public void userIcon(Context context, UserActivity.MyTextResponseHandler1 handler, ErrorListener error,
				String... s) {
			VolleySingleton vs = VolleySingleton.getVolleySingleton(context);
			RequestQueue queue = vs.getRequestQueue();
			StringRequest sr = new StringRequest(Method.GET, CommonUtil.NETPATH
					+ "user_home?ver=1&imei=" + s[0] + "&token=" + s[1], handler,
					error);
			vs.addToRequestQueue(sr);
		}
	
	// �����û���Ϣ����
		public void userRight(Context context, FragmentRight.MyTextResponseHandler handler, ErrorListener error,
				String... s) {
			VolleySingleton vs = VolleySingleton.getVolleySingleton(context);
			RequestQueue queue = vs.getRequestQueue();
			StringRequest sr = new StringRequest(Method.GET, CommonUtil.NETPATH
					+ "user_home?ver=1&imei=" + s[0] + "&token=" + s[1], handler,
					error);
			vs.addToRequestQueue(sr);
		}

	/** �ϴ�ͷ�񵽷�����        �����û�ͷ��*/
	// user_image 	post����
	public void uploadIcon(Context context, String token, File portrait,
			ResponseHandlerInterface in) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams param = new RequestParams();
		param.put("token", token);
		try {
			param.put("portrait", portrait);
			client.post(context,  CommonUtil.NETPATH + "user_image", param, in);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
