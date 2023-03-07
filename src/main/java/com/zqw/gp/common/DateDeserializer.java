package com.zqw.gp.common;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Gson日期系列化，持久化使用long传输
 * @author chenlong
 * @date 2021年3月26日
 */
public class DateDeserializer implements JsonDeserializer<Date> {
	
	@Override
	public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		return new Date(json.getAsJsonPrimitive().getAsLong());
	}
}
