package com.zqw.gp.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * gson的工具类
 * 
 * @author lengon
 *
 */
public class JsonUtils {

	private static Gson gson = null;

	private JsonUtils() {
	}

	/**
	 * 自定的gson
	 * 
	 * @return gson对象
	 */
	public static Gson getGson() {
		if (null == gson) {
			GsonBuilder builder = new GsonBuilder();
			builder.registerTypeAdapter(java.util.Date.class, new DateSerializer()).setDateFormat(DateFormat.LONG);
			builder.registerTypeAdapter(java.util.Date.class, new DateDeserializer()).setDateFormat(DateFormat.LONG);
			builder.serializeSpecialFloatingPointValues();
			gson = builder.create();
		}
		return gson;
	}

	/**
	 * 将java bean的指定字段系列化
	 * 
	 * @param obj
	 *            对象
	 * @param contains
	 *            指定的字段列表
	 * @return 系列化的json对象
	 */
	public static JsonObject buildContain(Object obj, String... contains) {
		// 如果包含了多个层级，那么需要将对应的string转为List;
		Map<String, String[]> containMaps = new HashMap<>();
		for (String contain : contains) {
			String[] tmps = contain.split("\\.");
			containMaps.put(tmps[0], tmps);
		}

		return buildContain(obj, containMaps);
	}

	// 包含需要的字段。 当前只支持第一层的包含。
	private static JsonObject buildContain(Object obj, Map<String, String[]> contains) {
		if (null == obj) {
			return null;
		}
		String str = gson.toJson(obj);
		JsonObject json = gson.fromJson(str, JsonObject.class);
		Set<String> removeKeys = new HashSet<String>();
		for (String key : json.keySet()) {
			boolean isContain = false;
			for (String containKey : contains.keySet()) {
				String[] containValues = contains.get(containKey);

				if (key.equals(containKey)) {
					if (containValues.length > 1) {
						String nextKey = containValues[1];
						Map<String, String[]> cm = new HashMap<>();
						String[] nextValue = strCopy(containValues, 1, containValues.length);
						cm.put(nextKey, nextValue);
						// 将新的放入到当前的json中。
						JsonObject value = buildContain(json.get(containKey), cm);
						json.add(containKey, value);
					}
					isContain = true;
					break;
				}

			}
			if (!isContain) {
				removeKeys.add(key);
			}
		}

		for (String removeKey : removeKeys) {
			json.remove(removeKey);
		}
		return json;
	}

	private static String[] strCopy(String[] input, int from, int to) {
		String[] result = new String[to - from];
		for (int i = from; i < to; i++) {
			result[i - from] = input[i];
		}

		return result;
	}

	public static JsonObject buildObj(Object... input) {
		JsonObject data = new JsonObject();
		for (int i = 0; i < input.length; i += 2) {
			String key = String.valueOf(input[i]);
			Object value = input[i + 1];
			if (value.getClass().equals(String.class)) {
				data.addProperty(key, (String) value);
				continue;
			}

			if (value.getClass().equals(int.class)||value.getClass().equals(Integer.class)) {
				data.addProperty(key, (int) value);
				continue;
			}

			if (value.getClass().equals(float.class)||value.getClass().equals(Float.class)) {
				data.addProperty(key, (float) value);
				continue;
			}

			if (value.getClass().equals(double.class)||value.getClass().equals(Double.class)) {
				data.addProperty(key, (double) value);
				continue;
			}

			if (value.getClass().equals(long.class)||value.getClass().equals(Long.class)) {
				data.addProperty(key, (long) value);
				continue;
			}

			if (value.getClass().equals(boolean.class)) {
				data.addProperty(key, (boolean) value);
				continue;
			}
		}
		return data;

	}
}
