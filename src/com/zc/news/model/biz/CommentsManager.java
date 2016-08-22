package com.zc.news.model.biz;

import com.android.volley.RequestQueue;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.zc.news.Util.CommonUtil;
import com.zc.news.Util.LogUtil;
import com.zc.news.activity.CommentActivity;
import com.zc.news.volley.VolleySingleton;

import android.content.Context;

public class CommentsManager {

	/*
	 * ��������
	 * 
	 * @paramver �汾
	 * 
	 * @paramargs ˳��Ҫ�����¡�����nid (����id) dir ( ˢ�·���1 ��ʾ������ˢ�£������ظ����xx���� 2
	 * ��ʾ����ˢ�£��������µ�����) cid����id
	 * cmt?ver=�汾��&nid=���ű��&token=�û�����&imei=�ֻ���ʶ��&ctx=�������ݷ�������
	 */
	//������������
	public static void loadComments(Context context, String ver,
			Listener<String> listener, ErrorListener errorListener, int... args) {
		LogUtil.d("loadComments","loadComments");
		String url = CommonUtil.NETPATH + "cmt_list?ver=" + ver + "&nid="
				+ args[0] + "&dir=" + args[1] + "&cid=" + args[2] + "&type="
				+ 1 + "&stamp=" + "20140707";
		LogUtil.d("url", url);
		// ����
		VolleySingleton vs = VolleySingleton.getVolleySingleton(context);
		RequestQueue queue = vs.getRequestQueue();
		StringRequest sr = new StringRequest(Method.GET, url, listener,
				errorListener);
		queue.add(sr);
	}
	//������������
	public static void sendCommnet(Context context, int nid,
			CommentActivity.MyTextResponseHandler handler, ErrorListener errorListener,
			String... args) {
		String url = CommonUtil.NETPATH + "cmt_commit?nid=" + nid + "&ver=1"
				 + "&token=" + args[0] + "&imei=" + args[1] + "&ctx="
				+ args[2];
		// ����
		VolleySingleton vs = VolleySingleton.getVolleySingleton(context);
		RequestQueue queue = vs.getRequestQueue();
		StringRequest sr = new StringRequest(Method.GET, url, handler,
				errorListener);
		queue.add(sr);
	}

}
