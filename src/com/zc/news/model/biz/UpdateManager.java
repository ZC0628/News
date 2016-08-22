package com.zc.news.model.biz;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;

import com.android.volley.RequestQueue;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.StringRequest;
import com.zc.news.Util.CommonUtil;
import com.zc.news.activity.FragmentRight;
import com.zc.news.volley.VolleySingleton;

public class UpdateManager {

	//���Ͱ汾��������
	public static void judgeUpdate(Context context, FragmentRight.MyTextResponseHandler2 handler, FragmentRight.MyTextError2 error, String s1,String s2,int s3){ 
		VolleySingleton vs = VolleySingleton.getVolleySingleton(context);
		RequestQueue queue = vs.getRequestQueue();
		StringRequest sr = new StringRequest(Method.GET, CommonUtil.NETPATH
				+ "/update?imei="+s1+"&pkg="+s2+"&ver="+s3, handler,
				error);
		vs.addToRequestQueue(sr);
	}
	//�������°汾
	public static void downLoad(Context context, String url) { 
		// ��ʼ�����ع����� 
		DownloadManager manager = (DownloadManager) context .getSystemService(Context.DOWNLOAD_SERVICE); 
		// �������� 
		DownloadManager.Request request = new DownloadManager.Request( Uri.parse(url)); 
		// ��������ʹ�õ��������ͣ�wifi 
		request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI); 
		// ��֪ͨ����ʾ����������API 11�б�setNotificationVisibility()ȡ�� 
		request.setShowRunningNotification(true); 
		// ��ʾ���ؽ��� request.setVisibleInDownloadsUi(true); 
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-ddhh-mm-ss"); 
		String date = dateformat.format(new Date()); 
		//�������غ��ļ���ŵ�λ��--���Ŀ��λ���Ѿ���������ļ�������ִ�����أ�������date�������ȡ���� 
		request.setDestinationInExternalFilesDir(context, null, date + ".apk");
		manager.enqueue(request);// ����������������
	}
}
