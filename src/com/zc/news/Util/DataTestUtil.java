package com.zc.news.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import android.test.AndroidTestCase;

public class DataTestUtil extends AndroidTestCase {

	public void textHttpGetString(){
		new Thread(){
			public void run() {
				try {
					String data = HttpUtil.httpGetString(CommonUtil.NETPATH+CommonUtil.NETPATH1);
					LogUtil.d("DataTest", data);
					
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
	}
}
