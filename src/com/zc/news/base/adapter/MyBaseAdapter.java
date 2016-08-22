package com.zc.news.base.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
/**
 * �����������ĸ���
 * @param <T>  ���ݵ�����
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
	protected ArrayList<T> infos = new ArrayList<T>();
	protected Context mContext;
	protected LayoutInflater mLayoutInflater;
	
	public MyBaseAdapter(Context context){
    	mContext = context;
		mLayoutInflater = LayoutInflater.from(mContext);
    }
    /**
     * ��������Դ
     */
    public ArrayList<T> getInfos(){
    	return infos;
    }
    
    
    
    /**
	 * ������ݵ����������ķ���
	 * @param info Ҫ��ӵ�����
	 */
	public void addDataToAdapter(T info){
		infos.add(info);
	}
	
	/**
	 * ������ݵ�������ָ��λ�õķ���
	 * @param info Ҫ��ӵ�����
	 * @param index Ҫ������ݵ�λ��
	 */
	public void addDataToAdapter(T info,int index){
		infos.add(index,info);
	}
	
	/**
	 * �����������
	 */
	public void clearAllData(){
		infos.clear();
	}
	
	/**
	 * ��Ӽ��������ݵ��������ķ���
	 * @param info Ҫ��ӵ�����
	 */
	public void addDataToAdapter(ArrayList<T> infos){
		this.infos.addAll(infos);
	}
	
	/** 
	 * ��װ���һ����¼���� 
	 *   t         һ�����ݵĶ��� 
	 * isClearOld  �Ƿ����ԭ���� 
	 */ 
	public void appendData(T t,boolean isClearOld){ 
		if(t==null){ 
			//�ǿ���֤ 
			return; 
			} 
		if(isClearOld){
			//���true ���ԭ���� 
			infos.clear(); 
			}
		//���һ�������ݵ���� 
		infos.add(t); 
	}
	
	/** 
	 * ��Ӷ�����¼ 
	 * @param alist      ���ݼ���
     * @param isClearOld �Ƿ����ԭ���� 
     * */ 
	public void addendData(ArrayList<T> alist,boolean isClearOld){ 
		if(alist==null){ 
			return; 
			} 
		if(isClearOld){ 
			infos.clear(); 
			} 
		infos.addAll(alist); 
	}
	
	
	
	@Override
	public int getCount() {
		
		return infos.size();
	}

	@Override
	public T getItem(int position) {

		return infos.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getMyView(position, convertView, parent);
	}
	public abstract View getMyView(int position, View convertView, ViewGroup parent);
}
