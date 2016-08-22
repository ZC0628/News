package com.zc.news.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * �������洢���ݹ�����
 *
 */
public class CommonUtil {
		public static final int VERSION_CODE = 1;
		/** ������ip */ 
		public static String NETIP = "118.244.212.82"; 
		/** ������·�� */ 
		public static String NETPATH = "http://" + NETIP + ":9092/newsClient/"; 
		public static String NETPATH1 = "news_list?ver=1&subid=1&dir=1&nid=1&stamp=20140321&cnt=20";
		/** SharedPreferences�����û����� */ 
		public static final String SHARE_USER_NAME = "userName"; 
		/** SharedPreferences�����û������� */ 
		public static final String SHARE_USER_PWD = "userPwd"; 
		/** SharedPreferences�����Ƿ��һ�ε�½ */ 
		public static final String SHARE_IS_FIRST_RUN = "isFirstRun"; 
		/** SharedPreferences�洢·�� */ 
		public static final String SHAREPATH = "news_share";
		//�ж�email��ʽ�Ƿ���ȷ
		public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);

		return m.matches();
		} 
		
		// ˢ��ʱ��
		public static String getTime() {
			long timeNumber = System.currentTimeMillis();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date(timeNumber);
			String time = format.format(date);
			return time;
		}
		//�����Ƿ�淶
		public static boolean verifyPassword(String psw) {
			String str = "^(?=.*?[A-Z])(?=.*?[0-9])[a-zA-Z0-9]{7,16}$";
			Pattern p = Pattern.compile(str);
			Matcher m = p.matcher(psw);
			return m.matches();
		}
		
		/* * 
		 * @param context �����Ķ��� 
		 * @return ��ǰ�汾 */ 
		//��ȡ�汾��(�ڲ�ʶ���)
		public static int getVersionCode(Context context) { 
			try { PackageInfo pi=context.getPackageManager().getPackageInfo(context.getPackageName(), 0); 
			return pi.versionCode; 
			} catch (NameNotFoundException e) { 
				e.printStackTrace(); 
				return 0; 
				} 
			}
}
