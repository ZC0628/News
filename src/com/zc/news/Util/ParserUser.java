package com.zc.news.Util;

import java.lang.reflect.Type;
import java.util.ArrayList;



import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zc.news.model.entity.Entity;
import com.zc.news.model.entity.LoginLog;
import com.zc.news.model.entity.Register;
import com.zc.news.model.entity.User;

/**
 * 		用户解析
 * @author Administrator
 *
 */
public class ParserUser {
	
	//服务器返回的注册信息解析
	public static Entity<Register> parserRegist(String json){
		Gson gson = new Gson();
		Type type = new TypeToken<Entity<Register>>(){}.getType();
		Entity<Register> entity = gson.fromJson(json, type);
		return entity;
	}
	
	//解析用户中心返回的数据
	public static User<LoginLog> getUserParser(String data){
		Gson gson = new Gson();
		Type type = new TypeToken<Entity<User<LoginLog>>>(){}.getType();
		Entity<User<LoginLog>> login = gson.fromJson(data, type);
		User<LoginLog> user = (User<LoginLog>)login.getData();
		return user;
	}
	
}
