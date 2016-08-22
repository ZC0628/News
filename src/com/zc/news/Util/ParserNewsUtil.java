package com.zc.news.Util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Path;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zc.news.model.entity.BaseEntity;
import com.zc.news.model.entity.Comment;
import com.zc.news.model.entity.News;
import com.zc.news.model.entity.SubGroup;
import com.zc.news.model.entity.SubType;

/**
 * ���ŵĽ���
 * @author Administrator
 *
 */
public class ParserNewsUtil {
	
	private static ArrayList<News> lists = new ArrayList<News>();
	public static ArrayList<News> getParserData(String data) throws Exception{
		//1.����ȡ�����ַ���ת����JSONObject
		JSONObject json = new JSONObject(data);
		
			//2.��ȡ����3������
	    	String result = json.getString("message");
	    	int status = json.getInt("status");
	    	JSONArray arr = json.getJSONArray("data");
	    	//��ȡJSONArray�е�����
	    	for(int x = 0 ; x < arr.length() ; x++){
	    		//�Ȼ�ȡJSONObject
	    		JSONObject obj = arr.getJSONObject(x);
	    		String summary = obj.getString("summary");
	    		String icon = obj.getString("icon");// ͼƬ��·��
	    		String stamp = obj.getString("stamp");
	    		String title = obj.getString("title");
	    		int nid = obj.getInt("nid");
	    		String link = obj.getString("link");
	    		int type = obj.getInt("type");
	    		
	    		News news = new News();
	    		//��������
	    		news.summary = summary;
	    		news.icon = icon;
	    		news.stamp = stamp;
	    		news.nid = nid;
	    		news.link = link;
	    		news.title = title;
	    		news.type = type;
	    		lists.add(news);
	    	}	
	    	LogUtil.d("news", lists.toString());
	    	return lists;
	}
	
	//Gson����ˮƽ�б� 
	public static ArrayList<SubType> getGsonParser(String data){
		ArrayList<SubType> subTypes = new ArrayList<SubType>();
		Gson gson = new Gson();
		Type type = new TypeToken<BaseEntity<SubGroup<SubType>>>(){}.getType();
		BaseEntity<SubGroup<SubType>> entity = gson.fromJson(data, type);
		ArrayList<SubGroup<SubType>> lists = (ArrayList<SubGroup<SubType>>)entity.getData();
		for(int x = 0; x < lists.size(); x++){
			SubGroup<SubType> group = lists.get(x);//�Ȼ�ȡSubGroup
			List<SubType> types = group.getSubgrp();//�ٻ�ȡSubType
			for(SubType s : types){
				subTypes.add(s);
			}
		}
		LogUtil.d("subTypes", subTypes.toString());
		return subTypes;
	}
	
	//Gson����
		public static ArrayList<Comment> getComment(String data){
			ArrayList<Comment> comments = new ArrayList<Comment>();
			Gson gson = new Gson();
			Type type = new TypeToken<BaseEntity<Comment>>(){}.getType();
			BaseEntity<Comment> comment = gson.fromJson(data, type);
			ArrayList<Comment> lists = (ArrayList<Comment>) comment.getData();
			for(Comment s : lists){
				comments.add(s);
			}

			return comments;
		}
}
