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

	//发送版本更新请求
	public static void judgeUpdate(Context context, FragmentRight.MyTextResponseHandler2 handler, FragmentRight.MyTextError2 error, String s1,String s2,int s3){ 
		VolleySingleton vs = VolleySingleton.getVolleySingleton(context);
		RequestQueue queue = vs.getRequestQueue();
		StringRequest sr = new StringRequest(Method.GET, CommonUtil.NETPATH
				+ "/update?imei="+s1+"&pkg="+s2+"&ver="+s3, handler,
				error);
		vs.addToRequestQueue(sr);
	}
	//下载最新版本
	public static void downLoad(Context context, String url) { 
		// 初始化下载管理器 
		DownloadManager manager = (DownloadManager) context .getSystemService(Context.DOWNLOAD_SERVICE); 
		// 创建请求 
		DownloadManager.Request request = new DownloadManager.Request( Uri.parse(url)); 
		// 设置允许使用的网络类型，wifi 
		request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI); 
		// 在通知栏显示下载详情在API 11中被setNotificationVisibility()取代 
		request.setShowRunningNotification(true); 
		// 显示下载界面 request.setVisibleInDownloadsUi(true); 
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-ddhh-mm-ss"); 
		String date = dateformat.format(new Date()); 
		//设置下载后文件存放的位置--如果目标位置已经存在这个文件名，则不执行下载，所以用date类型随机取名。 
		request.setDestinationInExternalFilesDir(context, null, date + ".apk");
		manager.enqueue(request);// 将下载请求放入队列
	}
}
