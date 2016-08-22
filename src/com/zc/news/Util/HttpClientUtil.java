package com.zc.news.Util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import javax.net.ssl.SSLSocketFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import android.graphics.BitmapFactory;

/*
 * 获取httpClient对象的工具类
 * */
public class HttpClientUtil {
	
	private HttpClientUtil(){}
	private static final String CHARSET = HTTP.UTF_8;
	private static HttpClient client;
	
	public static synchronized HttpClient getInstance(){
		
		if(client == null){
			HttpParams params =new BasicHttpParams();
            // 设置一些基本参数
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params,
                    CHARSET);
            HttpProtocolParams.setUseExpectContinue(params, true);
            HttpProtocolParams.setUserAgent(params,
                            "Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
                                    +"AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
            // 超时设置
            /* 从连接池中取连接的超时时间 */
            ConnManagerParams.setTimeout(params, 2000);
            /* 连接超时 */
            HttpConnectionParams.setConnectionTimeout(params, 3000);
            /* 请求超时 */
            HttpConnectionParams.setSoTimeout(params, 4000);
            
            // 设置我们的HttpClient支持HTTP和HTTPS两种模式
            client = new DefaultHttpClient();
		}
		
		return client;
	}
	
}
