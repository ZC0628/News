package com.zc.news.activity;

import com.feicui.news.R;
import com.zc.news.slidingmenu.SlidingMenu;

import android.os.Bundle;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
/**
 *	新闻主界面
 * @author	
 *
 */
public class HomeActivity extends FragmentActivity {
	private SlidingMenu slidingMenu;//侧拉菜单
	private FragmentNews fragmentNews;///新闻内容界面
	private FragmentLogin login;
	private Dialog dialog;
	private TextView title;
	private FragmentFavorite favorite;//收藏新闻界面
	private FragmentRegist regist;
	private FragmentForget forget;
	private FragmentLeft left;
	private FragmentRight right;
	private long waitTime = 1000;
	private long time = 0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		slidingMenu = new SlidingMenu(this);
		// 设置为左右两边菜单栏——侧拉菜单的模式
		slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
		// 设置全屏范围都可以打开菜单栏
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		// 设置菜单拖出后剩余的屏幕宽度
		slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// 设置菜单栏不类的关联：当前类显示的为菜单栏的中间界面
		slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		// 设置左菜单栏样式
		slidingMenu.setMenu(R.layout.activity_menu_left);
		// 设置右菜单栏样式
		slidingMenu.setSecondaryMenu(R.layout.activity_menu_right);

		// 替换fragment
		left = new FragmentLeft();
		right = new FragmentRight();
		fragmentNews = new FragmentNews();
		// 开启事务才能替换，而且事务必须提交
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_left, left).commit();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_right, right).commit();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.home_fragment, fragmentNews).commit();

		title = (TextView) findViewById(R.id.title);

	}

	private void loadDataFromDB() {
		// ArrayList<News> data = mNewsDBManager.queryNews();
		// mNewsAdapter.addDataToAdapter(data);
		// mNewsAdapter.notifyDataSetChanged();
	}

	// 异步加载数据
	private void loadData() {
		dialog = ProgressDialog.show(this, null, "加载中,请稍后...");
	}

	// 显示新闻
	public void showNewsContent() {
		// 关闭菜单并显示内容
		slidingMenu.showContent();
		if (fragmentNews == null) {
			fragmentNews = new FragmentNews();
		}
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.home_fragment, fragmentNews).commit();
		//设置activity上方导航条中间的标题
		title.setText("资讯");

	}

	// 登录
	public void goLogin() {
		// 关闭菜单并显示内容
		slidingMenu.showContent();
		if (login == null) {
			login = new FragmentLogin();
		}
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.home_fragment, login).commit();
		title.setText("登录");

	}
	
	
	//新闻收藏
	public void showLoveNews() {
		// 关闭菜单并显示内容
		slidingMenu.showContent();
		if (favorite == null) {
			favorite = new FragmentFavorite();
		}
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.home_fragment, favorite).commit();
		title.setText("收藏");
	}

	public void showRegistContent() {
		// 关闭菜单并显示内容
		slidingMenu.showContent();
		if (regist == null) {
			regist = new FragmentRegist();
		}
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.home_fragment, regist).commit();
		title.setText("注册");

	}

	public void showForgetpass() {
		// 关闭菜单并显示内容
		slidingMenu.showContent();
		if (forget == null) {
			forget = new FragmentForget();
		}
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.home_fragment, forget).commit();
		title.setText("忘记密码");

	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		right.login();
	}
	
	//监听返回键
			/*
			 * 参数
			 * 1.按键码
			 * 2.按键的信息
			 * 
			 */
			@Override
			public boolean onKeyDown(int keyCode, KeyEvent event) {
				switch(keyCode){
				case KeyEvent.KEYCODE_BACK:
					//获取当前毫秒值
					long currentTimeMillis = System.currentTimeMillis();
					if(currentTimeMillis - time <= waitTime){
						HomeActivity.this.finish();
					}else{
						Toast.makeText(this, "双击退出", Toast.LENGTH_SHORT)
						.show();
					}
					time = currentTimeMillis;
					return true;
				}
				//表示调用父类中默认的按键处理行为
				return super.onKeyDown(keyCode, event);
			}
			
			
			
			//主界面的导航条，左边点击跳转到侧拉菜单左边，右边点击跳转到侧拉菜单右边
			public void doClick(View view) {
				switch (view.getId()) {
				case R.id.title_bar_ib1:
					slidingMenu.showMenu();
					break;
				case R.id.title_bar_ib2:
					slidingMenu.showSecondaryMenu();
					break;
				}
			}

}
