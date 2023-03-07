package com.zqw.gp.common.http;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.zqw.gp.common.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * http的入口操作类
 *
 */
public class BaseHttp {

	private static Logger logger = LoggerFactory.getLogger(BaseHttp.class);

	private static HttpBaseDao http = HttpBaseDao.getInstantce();
	private static Gson gson = JsonUtils.getGson();

	private static final String JSON_OBJ_START_STRING = "{";
	private static final String JSON_DATA_KEY = "data";

	public static <T> List<T> getPostList(String url, Map<Object, Object> body, Class<T> clz) {
		try {
			// build request
			String bodyStr = gson.toJson(body);
			RequestModel request = new RequestModel(url, bodyStr);
			request.addHeader("connection", "close");
			String response = http.doPost(request);
			return getList(response, clz);
		} catch (Exception ex) {
			logger.error("fail to do post list action to url {}", url, ex);
		}
		return null;
	}

	public static boolean doPost(String url, Map<Object, Object> body) {
		try {
			String bodyStr = gson.toJson(body);
			RequestModel request = new RequestModel(url, bodyStr);
			request.addHeader("connection", "close");
			http.doPost(request);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public static boolean doPostWithCode(String url, Object body) {
		try {
			JsonObject response = getPostObj(url, body, JsonObject.class);
			if (response == null || response.get("code").getAsInt() != 200) {
				return false;
			}
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public static <T> T getPostObj(String url, Object body, Class<T> clz) {
		// build request
		try {
			String bodyStr = gson.toJson(body);
			RequestModel request = new RequestModel(url, bodyStr);
			request.addHeader("connection", "close");
			String response = http.doPost(request);
			return getObj(response, clz);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			logger.error("fail to do post action to url {}", url, ex);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private static <T> List<T> getList(String response, Class<T> clz) {
		JsonArray arrs;
		if (response.startsWith(JSON_OBJ_START_STRING)) {
			arrs = gson.fromJson(response, JsonObject.class).get(JSON_DATA_KEY).getAsJsonArray();
		} else {
			arrs = gson.fromJson(response, new TypeToken<JsonArray>() {
			}.getType());
		}

		List<T> values = new ArrayList<>();
		for (int i = 0; i < arrs.size(); i++) {
			if(clz.equals(String.class)) {
				values.add((T) arrs.get(i).getAsString());
			}else {
				JsonObject res = arrs.get(i).getAsJsonObject();
				values.add(gson.fromJson(res.toString(), clz));
			}
		}
		return values;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getObj(String response, Class<T> clz) {
		if (clz.equals(String.class)) {
			return (T) response;
		}
		Gson gson = JsonUtils.getGson();
		JsonObject obj = gson.fromJson(response, JsonObject.class);
		if (obj.get(JSON_DATA_KEY) != null && obj.get(JSON_DATA_KEY).isJsonObject()) {
			obj = obj.get(JSON_DATA_KEY).getAsJsonObject();
		}

		if (clz.equals(JsonObject.class)) {
			return (T) obj;
		}
		return gson.fromJson(obj.toString(), clz);
	}
}
