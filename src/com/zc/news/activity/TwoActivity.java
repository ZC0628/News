package com.zc.news.activity;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.feicui.news.R;
import com.feicui.news.R.layout;
import com.feicui.news.R.menu;
import com.zc.news.Util.LogUtil;
import com.zc.news.base.activity.BaseActivity;
import com.zc.news.model.biz.NewsDBManager;
import com.zc.news.model.entity.News;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 *   ��������ҳ
 */
public class TwoActivity extends BaseActivity {
	private ProgressBar progressBar;
	private WebView webView;
	private News news;
	private ImageButton ivback,ivmenu;
	private PopupWindow popupWindow;
	private View popview;
	private TextView tv;
	private TextView mTextView;
	private TextView share;
	private WebViewClient viewclient = new WebViewClient() {
		// �ڵ�������������ʱ�Ż���ã���д�˷�������true���������ҳ��������ӻ����ڵ�ǰ��webview����ת��
		// ������������Ǳߡ�
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			
			return false;
		};
	};

	/* ��������ʹ��,���ݽ��� */
	private WebChromeClient chromeClient = new WebChromeClient() {
		public void onProgressChanged(WebView view, int newProgress) {
			super.onProgressChanged(view, newProgress);
			progressBar.setProgress(newProgress);
			if (progressBar.getProgress() == 100) {
				progressBar.setVisibility(View.GONE);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_two);
		initView();

		
		news = (News) getIntent().getSerializableExtra("news");
		LogUtil.d("news",news.toString());
		//����webview�����Ի���ģʽ���� 
		webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		// �����Ƿ������
		webView.getSettings().setBuiltInZoomControls(true);
		//��������
		webView.getSettings().setUseWideViewPort(true);
		//����ʱ����Ӧ��Ļ
		webView.getSettings().setLoadWithOverviewMode(true);
		//���ü���ȫ����ļ��� 
		webView.setWebViewClient(viewclient);
		//���õ�����ʱ�ļ��� 
		webView.setWebChromeClient(chromeClient);
		// ����WebView���ԣ��ܹ�ִ��Javascript�ű�
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl(news.link);
		
		initpopwindow();
		setListener();

	}
    
	public void initpopwindow() {
		//�Ե������ڽ������� 
		popupWindow = new PopupWindow(popview, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT); 
		popupWindow.setFocusable(true); 
		popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.loading_01)); 
		tv = (TextView) popview.findViewById(R.id.saveLocal);
	}

	@Override
	protected void initView() {
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		webView = (WebView) findViewById(R.id.webView1);
		ivback = (ImageButton) findViewById(R.id.title_bar_ib1);
		ivmenu = (ImageButton) findViewById(R.id.title_bar_ib2);
		//��ʾR.layout.item_pop_save����
		popview = getLayoutInflater().inflate(R.layout.activity_two_popwindow, null);
		mTextView = (TextView) findViewById(R.id.comment_tv);
		share = (TextView) findViewById(R.id.comment_share);
	}

	@Override
	protected void setListener() {
		ivback.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (webView.canGoBack()) {
					webView.goBack();
					LogUtil.d("ivback", "true");
				}else{
					TwoActivity.this.finish();
					LogUtil.d("ivback", "false");
				}
			}
		});
		
		//���õ�������������������ŵ����ݿ� 
		tv.setOnClickListener(new View.OnClickListener() { 
			@Override 
			public void onClick(View v) { 
				popupWindow.dismiss(); 
				NewsDBManager manager = NewsDBManager.getNewsDBManager(TwoActivity.this);
				if (manager.saveLoveNews(news)) { 
					Toast.makeText(TwoActivity.this, "�ղسɹ���\n��������໬�˵��в鿴", 0).show(); 
					}else{ 
						Toast.makeText(TwoActivity.this, "�Ѿ��ղع����������ˣ�\n��������໬�˵��в鿴", 0).show(); 
				}
						
						
			}
		});
		
		ivmenu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(popupWindow != null && popupWindow.isShowing()){
					popupWindow.dismiss();
				}else if(popupWindow != null){
					popupWindow.showAsDropDown(ivmenu, 0, 15);
				}
				
			}
		});
		
		mTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				Bundle bundle = new Bundle();
				bundle.putSerializable("news", news);
				Intent it = new Intent(TwoActivity.this, CommentActivity.class);
				it.putExtras(bundle);
				startActivity(it);
				
			}
		});
		
		share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				showShare();
			}
		});

	}

	// ���û��ˣ�����Activity���onKeyDown����
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
			webView.goBack();// goBack()��ʾ����WebView����һҳ��
			LogUtil.d("onKeyDown", "true");
			return true;
		}
		LogUtil.d("onKeyDown", "false");
		return super.onKeyDown(keyCode, event);
	}
	
	private void showShare() {
		 ShareSDK.initSDK(this);
		 OnekeyShare oks = new OnekeyShare();
		 //�ر�sso��Ȩ
		 oks.disableSSOWhenAuthorize(); 

		// ����ʱNotification��ͼ�������  2.5.9�Ժ�İ汾�����ô˷���
		 //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
		 // title���⣬ӡ��ʼǡ����䡢��Ϣ��΢�š���������QQ�ռ�ʹ��
		 oks.setTitle(news.title);
		 // titleUrl�Ǳ�����������ӣ�������������QQ�ռ�ʹ��
		 oks.setTitleUrl(news.link);
		 // text�Ƿ����ı�������ƽ̨����Ҫ����ֶ�
		 oks.setText(news.summary);
		 // imagePath��ͼƬ�ı���·����Linked-In�����ƽ̨��֧�ִ˲���
		 //oks.setImagePath("/sdcard/test.jpg");//ȷ��SDcard������ڴ���ͼƬ
		 oks.setImageUrl(news.icon);
		 // url����΢�ţ��������Ѻ�����Ȧ����ʹ��
		 oks.setUrl(news.link);
		 // comment���Ҷ�������������ۣ�������������QQ�ռ�ʹ��
		 oks.setComment("���ǲ��������ı�");
		 // site�Ƿ�������ݵ���վ���ƣ�����QQ�ռ�ʹ��
		 oks.setSite(getString(R.string.app_name));
		 // siteUrl�Ƿ�������ݵ���վ��ַ������QQ�ռ�ʹ��
		 oks.setSiteUrl(news.link);

		// ��������GUI
		 oks.show(this);
		 }
	
}
