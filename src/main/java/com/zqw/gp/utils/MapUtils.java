package com.zqw.gp.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 快速构建Map
 */
public class MapUtils {

	private Map<Object, Object> map = null;

	private static final int MAP_GAP_SIZE = 2;

	public static Map<Object, Object> buildHashMap(Object... input) {
		Map<Object, Object> map = new HashMap<>();
		for (int i = 0; i < input.length; i += MAP_GAP_SIZE) {
			if (input[i + 1] != null) {
				map.put(input[i], input[i + 1]);
			}
		}
		return map;
	}

	public MapUtils put(Object key, Object value) {
		if (null == map) {
			map = new HashMap<Object, Object>();
		}
		map.put(key, value);
		return this;
	}
}
