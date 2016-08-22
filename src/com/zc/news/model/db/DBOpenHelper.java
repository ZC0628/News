package com.zc.news.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Êý¾Ý¿â
 * @author Administrator
 *
 */
public class DBOpenHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "news";
	public DBOpenHelper(Context context) {
		super(context, "newsdb.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table news(_id integer primary key autoincrement,nid integer,title text,"
				+ "summary text,icon text,link text,type integer,stamp text)");
		db.execSQL("create table newstype(_id integer primary key autoincrement,subgroup text,subid integer)");
		db.execSQL("create table lovenews(_id integer primary key autoincrement,nid integer,title text,"
				+ "summary text,icon text,link text,type integer,stamp text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
