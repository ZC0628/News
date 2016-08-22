package com.zc.news.base.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
/**
 * 所有适配器的父类
 * @param <T>  数据的类型
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
     * 返回数据源
     */
    public ArrayList<T> getInfos(){
    	return infos;
    }
    
    
    
    /**
	 * 添加数据到适配器最后的方法
	 * @param info 要添加的数据
	 */
	public void addDataToAdapter(T info){
		infos.add(info);
	}
	
	/**
	 * 添加数据到适配器指定位置的方法
	 * @param info 要添加的数据
	 * @param index 要添加数据的位置
	 */
	public void addDataToAdapter(T info,int index){
		infos.add(index,info);
	}
	
	/**
	 * 清空所有数据
	 */
	public void clearAllData(){
		infos.clear();
	}
	
	/**
	 * 添加集合中数据到适配器的方法
	 * @param info 要添加的数据
	 */
	public void addDataToAdapter(ArrayList<T> infos){
		this.infos.addAll(infos);
	}
	
	/** 
	 * 封装添加一条记录方法 
	 *   t         一条数据的对象 
	 * isClearOld  是否清除原数据 
	 */ 
	public void appendData(T t,boolean isClearOld){ 
		if(t==null){ 
			//非空验证 
			return; 
			} 
		if(isClearOld){
			//如果true 清空原数据 
			infos.clear(); 
			}
		//添加一条新数据到最后 
		infos.add(t); 
	}
	
	/** 
	 * 添加多条记录 
	 * @param alist      数据集合
     * @param isClearOld 是否清空原数据 
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
