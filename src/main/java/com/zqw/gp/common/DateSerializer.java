package com.zqw.gp.common;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Date;


/**
 * Gson日期反系列化，long转为date类型
 * @author chenlong
 * @date 2021年3月26日
 */
public class DateSerializer implements JsonSerializer<Date> {
	
	@Override
	public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(src.getTime());
	}
}
