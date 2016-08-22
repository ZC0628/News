package com.zc.news.model.biz;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zc.news.Util.LogUtil;
import com.zc.news.model.db.DBOpenHelper;
import com.zc.news.model.entity.News;

/**
 * 数据库管理类——对数据库进行增删改查
 * 
 */
public class NewsDBManager {
	// 单例
	private static NewsDBManager dbManager;
	private DBOpenHelper helper;

	private NewsDBManager(Context context) {
		helper = new DBOpenHelper(context);
	}

	public static NewsDBManager getNewsDBManager(Context context) {
		if (dbManager == null) {
			synchronized (NewsDBManager.class) {
				if (dbManager == null) {
					dbManager = new NewsDBManager(context);
				}
			}
		}
		return dbManager;
	}

	// 添加新闻数据
	public void addNews(ArrayList<News> news) {
		SQLiteDatabase db = helper.getWritableDatabase();
		for (int x = 0; x < news.size(); x++) {
			News s = news.get(x);
			ContentValues values = new ContentValues();
			values.put("nid", s.nid);
			values.put("title", s.title);
			values.put("summary", s.summary);
			values.put("stamp", s.stamp);
			values.put("icon", s.icon);
			values.put("link", s.link);
			values.put("type", s.type);
			db.insert(DBOpenHelper.TABLE_NAME, null, values);
		}
		db.close();

	}
	//添加水平列表数据

	/*
	 * 数据数量
	 */
	public long getCount() {
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select count(*) from news", null);
		long length = 0;
		if (cursor.moveToFirst()) {
			length = cursor.getLong(0);
		}
		cursor.close();
		db.close();
		return length;
	}

	/*
	 * 查询数据
	 */
	public ArrayList<News> queryNews() {
		ArrayList<News> lists = new ArrayList<News>();
		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "select * from news";
		Cursor cursor = db.rawQuery(sql, null);
		cursor.moveToFirst();
		do {
			LogUtil.d("qw", "123");
			News news = new News();
			int nid = cursor.getInt(cursor.getColumnIndex("nid"));
			String title = cursor.getString(cursor.getColumnIndex("title"));
			String summary = cursor.getString(cursor.getColumnIndex("summary"));
			String stamp = cursor.getString(cursor.getColumnIndex("stamp"));
			String icon = cursor.getString(cursor.getColumnIndex("icon"));
			String link = cursor.getString(cursor.getColumnIndex("link"));
			int type = cursor.getInt(cursor.getColumnIndex("type"));
			news.nid = nid;
			news.title = title;
			news.summary = summary;
			news.icon = icon;
			news.stamp = stamp;
			news.link = link;
			news.type = type;
			lists.add(news);
		} while (cursor.moveToNext());
		cursor.close();
		db.close();
		return lists;
	}

	/*
	 * 收藏新闻
	 */
	public boolean saveLoveNews(News news) {
		try {
			SQLiteDatabase db = helper.getWritableDatabase();
			Cursor cursor = db.rawQuery("select * from lovenews where nid="
					+ news.nid, null);
			if (cursor.moveToFirst()) {
				cursor.close();
				return false;
			}
			cursor.close();
			ContentValues values = new ContentValues();
			values.put("type", news.type);
			values.put("nid", news.nid);
			values.put("stamp", news.stamp);
			values.put("icon", news.icon);
			values.put("title", news.title);
			values.put("summary", news.summary);
			values.put("link", news.link);
			db.insert("lovenews", null, values);
			db.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/*
	 * 获取收藏新闻的列表
	 */
	public ArrayList<News> queryLoveNews(){
		ArrayList<News> newsList = new ArrayList<News>(); 
		SQLiteDatabase db = helper.getReadableDatabase(); 
		String sql= "select * from lovenews order by _id desc"; 
		Cursor cursor=db.rawQuery(sql, null);
		if (cursor.moveToFirst()) { 
			do { 
				News news = new News();
				int type = cursor.getInt(cursor.getColumnIndex("type")); 
				int nid = cursor.getInt(cursor.getColumnIndex("nid")); 
				String stamp = cursor.getString(cursor.getColumnIndex("stamp")); 
				String icon = cursor.getString(cursor.getColumnIndex("icon")); 
				String title = cursor.getString(cursor.getColumnIndex("title")); 
				String summary = cursor.getString(cursor.getColumnIndex("summary")); 
				String link = cursor.getString(cursor.getColumnIndex("link")); 
				news.nid = nid;
				news.title = title;
				news.summary = summary;
				news.icon = icon;
				news.stamp = stamp;
				news.link = link;
				news.type = type;
				newsList.add(news); 
				} while (cursor.moveToNext()); 
			cursor.close(); 
			db.close(); 
			} 
		return newsList;
	}
}
