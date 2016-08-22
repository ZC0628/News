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
 * 新闻的解析
 * @author Administrator
 *
 */
public class ParserNewsUtil {
	
	private static ArrayList<News> lists = new ArrayList<News>();
	public static ArrayList<News> getParserData(String data) throws Exception{
		//1.将获取到的字符串转换成JSONObject
		JSONObject json = new JSONObject(data);
		
			//2.获取外层的3个属性
	    	String result = json.getString("message");
	    	int status = json.getInt("status");
	    	JSONArray arr = json.getJSONArray("data");
	    	//获取JSONArray中的数据
	    	for(int x = 0 ; x < arr.length() ; x++){
	    		//先获取JSONObject
	    		JSONObject obj = arr.getJSONObject(x);
	    		String summary = obj.getString("summary");
	    		String icon = obj.getString("icon");// 图片的路径
	    		String stamp = obj.getString("stamp");
	    		String title = obj.getString("title");
	    		int nid = obj.getInt("nid");
	    		String link = obj.getString("link");
	    		int type = obj.getInt("type");
	    		
	    		News news = new News();
	    		//保存数据
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
	
	//Gson解析水平列表 
	public static ArrayList<SubType> getGsonParser(String data){
		ArrayList<SubType> subTypes = new ArrayList<SubType>();
		Gson gson = new Gson();
		Type type = new TypeToken<BaseEntity<SubGroup<SubType>>>(){}.getType();
		BaseEntity<SubGroup<SubType>> entity = gson.fromJson(data, type);
		ArrayList<SubGroup<SubType>> lists = (ArrayList<SubGroup<SubType>>)entity.getData();
		for(int x = 0; x < lists.size(); x++){
			SubGroup<SubType> group = lists.get(x);//先获取SubGroup
			List<SubType> types = group.getSubgrp();//再获取SubType
			for(SubType s : types){
				subTypes.add(s);
			}
		}
		LogUtil.d("subTypes", subTypes.toString());
		return subTypes;
	}
	
	//Gson解析
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
