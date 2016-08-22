package com.zc.news.Util;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zc.news.model.entity.Version;

public class ParserVersion {

	//解析版本更新
	public static Version parserJson(String json){ 
		Gson gson = new Gson(); 
		Type type =new TypeToken<Version>(){}.getType(); 
		Version version = gson.fromJson(json, type);
		return version; 
		}
}
