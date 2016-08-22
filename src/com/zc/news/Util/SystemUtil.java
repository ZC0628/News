package com.zc.news.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class SystemUtil {
	private static SystemUtil systemUtils;
	private Context context;
	private TelephonyManager telManager;
	private static ConnectivityManager connManager;
	private LocationManager locationManager;
	private String position;
	private static TelephonyManager tm;

	private SystemUtil(Context context) {
		this.context = context;
		telManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
	}

	public static SystemUtil getInstance(Context context) {
		if (systemUtils == null) {
			systemUtils = new SystemUtil(context);
		}
		return systemUtils;
	}

	// ¼ì²âÍøÂç
	public static boolean isNetConn(Context context) {
		NetworkInfo info = connManager.getActiveNetworkInfo();
		if (info != null && info.isConnected()) {
			return true;
		}
		return false;
	}

	public static String getImei(Context context){
		tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = tm.getDeviceId();
		return imei;
		
	} 
	

	

}
