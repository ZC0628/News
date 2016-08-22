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
 *	����������
 * @author	
 *
 */
public class HomeActivity extends FragmentActivity {
	private SlidingMenu slidingMenu;//�����˵�
	private FragmentNews fragmentNews;///�������ݽ���
	private FragmentLogin login;
	private Dialog dialog;
	private TextView title;
	private FragmentFavorite favorite;//�ղ����Ž���
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
		// ����Ϊ�������߲˵������������˵���ģʽ
		slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
		// ����ȫ����Χ�����Դ򿪲˵���
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		// ���ò˵��ϳ���ʣ�����Ļ���
		slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// ���ò˵�������Ĺ�������ǰ����ʾ��Ϊ�˵������м����
		slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		// ������˵�����ʽ
		slidingMenu.setMenu(R.layout.activity_menu_left);
		// �����Ҳ˵�����ʽ
		slidingMenu.setSecondaryMenu(R.layout.activity_menu_right);

		// �滻fragment
		left = new FragmentLeft();
		right = new FragmentRight();
		fragmentNews = new FragmentNews();
		// ������������滻��������������ύ
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

	// �첽��������
	private void loadData() {
		dialog = ProgressDialog.show(this, null, "������,���Ժ�...");
	}

	// ��ʾ����
	public void showNewsContent() {
		// �رղ˵�����ʾ����
		slidingMenu.showContent();
		if (fragmentNews == null) {
			fragmentNews = new FragmentNews();
		}
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.home_fragment, fragmentNews).commit();
		//����activity�Ϸ��������м�ı���
		title.setText("��Ѷ");

	}

	// ��¼
	public void goLogin() {
		// �رղ˵�����ʾ����
		slidingMenu.showContent();
		if (login == null) {
			login = new FragmentLogin();
		}
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.home_fragment, login).commit();
		title.setText("��¼");

	}
	
	
	//�����ղ�
	public void showLoveNews() {
		// �رղ˵�����ʾ����
		slidingMenu.showContent();
		if (favorite == null) {
			favorite = new FragmentFavorite();
		}
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.home_fragment, favorite).commit();
		title.setText("�ղ�");
	}

	public void showRegistContent() {
		// �رղ˵�����ʾ����
		slidingMenu.showContent();
		if (regist == null) {
			regist = new FragmentRegist();
		}
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.home_fragment, regist).commit();
		title.setText("ע��");

	}

	public void showForgetpass() {
		// �رղ˵�����ʾ����
		slidingMenu.showContent();
		if (forget == null) {
			forget = new FragmentForget();
		}
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.home_fragment, forget).commit();
		title.setText("��������");

	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		right.login();
	}
	
	//�������ؼ�
			/*
			 * ����
			 * 1.������
			 * 2.��������Ϣ
			 * 
			 */
			@Override
			public boolean onKeyDown(int keyCode, KeyEvent event) {
				switch(keyCode){
				case KeyEvent.KEYCODE_BACK:
					//��ȡ��ǰ����ֵ
					long currentTimeMillis = System.currentTimeMillis();
					if(currentTimeMillis - time <= waitTime){
						HomeActivity.this.finish();
					}else{
						Toast.makeText(this, "˫���˳�", Toast.LENGTH_SHORT)
						.show();
					}
					time = currentTimeMillis;
					return true;
				}
				//��ʾ���ø�����Ĭ�ϵİ���������Ϊ
				return super.onKeyDown(keyCode, event);
			}
			
			
			
			//������ĵ���������ߵ����ת�������˵���ߣ��ұߵ����ת�������˵��ұ�
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
