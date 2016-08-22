package com.zc.news.activity;

import com.feicui.news.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * �����˵������
 * @author Administrator
 *
 */
public class FragmentLeft extends Fragment implements OnClickListener{
	
	private RelativeLayout[] rel = new RelativeLayout[5];
	private HomeActivity homeActivity;
	
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	View view = inflater.inflate(R.layout.fragment_menu_left, null);
	//���ü���
	rel[0] = (RelativeLayout) view.findViewById(R.id.rl_news);
	rel[1] = (RelativeLayout) view.findViewById(R.id.rl_reading);
	rel[2] = (RelativeLayout) view.findViewById(R.id.rl_local);
	rel[3] = (RelativeLayout) view.findViewById(R.id.rl_commnet);
	rel[4] = (RelativeLayout) view.findViewById(R.id.rl_photo);
	
	for(RelativeLayout rl : rel){
		rl.setOnClickListener(this);
	}
	return view;
}
@Override
public void onClick(View v) {
	for(RelativeLayout rl : rel){
	   //��������ɫ�ĳ�ͳһ
		rl.setBackgroundColor(Color.TRANSPARENT);
	}
	v.setBackgroundColor(getResources().getColor(R.color.gray));
	homeActivity = (HomeActivity) getActivity();
	switch(v.getId()){
	case R.id.rl_news:
		//�����������ʾ����������
		homeActivity.showNewsContent();
		break;
	case R.id.rl_reading:
		//�����������ʾ����������
		homeActivity.showLoveNews();
		break;
	case R.id.rl_local:
		//�����������ʾ����������
		//homeActivity.showNewsContent();
		break;
	case R.id.rl_commnet:
		//�����������ʾ����������
		//homeActivity.showNewsContent();
		break;
	case R.id.rl_photo:
		//�����������ʾ����������
		//homeActivity.showNewsContent();
		break;
	} 
	
}
}
