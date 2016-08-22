package com.zc.news.model.biz;


import android.content.Context;
import android.telephony.TelephonyManager;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.zc.news.Util.CommonUtil;
import com.zc.news.Util.SystemUtil;
import com.zc.news.activity.FragmentNews.MyErrorHandler;
import com.zc.news.activity.FragmentNews.MyResponseHandler;
import com.zc.news.volley.VolleySingleton;

/**
 *  ������������
 * @author Administrator
 *
 */
public class NewsManager {
	
	public static void loadNewsType(MyErrorHandler myErrorHandler, 
			MyResponseHandler myResponseHandler,Context context){

		//�ж����ݿ�����û��
		if(false){
			//�����ݿ��м���
			
		}else{
			//����
			VolleySingleton vs = VolleySingleton.getVolleySingleton(context);
			RequestQueue queue = vs.getRequestQueue();
			
			/*method ��������ķ�ʽ��GET/POST��
			 * url����
			 * listener����
			 * errorListener����
			 */
			//�������󡪡����ŷ�����������  news_sort?ver=1&imei=1
			StringRequest sr = new StringRequest(Method.GET, 
					CommonUtil.NETPATH+"news_sort?ver="+
					CommonUtil.VERSION_CODE+"&imei="+SystemUtil.getImei(context), 
					myResponseHandler, myErrorHandler);
			queue.add(sr);
		}
		
	}
	
}
