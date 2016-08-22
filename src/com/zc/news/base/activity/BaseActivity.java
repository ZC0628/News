package com.zc.news.base.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public abstract class BaseActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	protected abstract void initView();
	protected abstract void setListener();
	
	/**
	 * ��һ��������ת����һ������
	 * @param act  ���ĸ����濪ʼ��ת
	 * @param cls  ��ת���ĸ�����
	 */
protected void startActivity(Activity act, Class<?> cls){
		startActivity(new Intent(act, cls));
	}

/**
 * ��һ��������ת��ĳ��ϵͳ����
 * @param action  ��ת���ĸ�ϵͳ����
 */
protected void startActivity(String action){
	
	Intent it = new Intent(action);
	startActivity(it);
}

/**
 * ��һ��������ת����һ�����沢Я������
 * @param cls  ��ת���ĸ�����
 * @param bundle ���ݰ�
 */
protected void startActivity(Class<?> cls, Bundle bundle){
	//��ת��TelListActivity����
	Intent it = new Intent(this, cls);
	//��position���ݹ�ȥ
	it.putExtra("bundle", bundle);
	startActivity(it);
}

/**
 * ��һ��������ת����һ������,�����ж���Ч��
 * 
 * @param cls  ��ת���ĸ�����
 * @param in   �����Ķ����ļ�
 * @param out  ��ȥ�Ķ����ļ�
 * 
 */
protected void startActivity(Class<?> cls, int in, int out){

	Intent it = new Intent(this, cls);
	startActivity(it);
	//�������ת�������������startActivity��finish����
	overridePendingTransition(in, out);
}
/**
 * ��һ��������ת����һ������,�����ж���Ч��,��Я������
 * @param act  ���ĸ����濪ʼ��ת
 * @param cls  ��ת���ĸ�����
 * @param in   �����Ķ����ļ�
 * @param out  ��ȥ�Ķ����ļ�
 * @param bundle ���ݰ�
 * 
 */
protected void startActivity(Class<?> cls, int in, int out, Bundle bundle){

	Intent it = new Intent(this, cls);
	it.putExtra("bundle", bundle);
	startActivity(it);
	//�������ת�������������startActivity��finish����
	overridePendingTransition(in, out);
}

}
