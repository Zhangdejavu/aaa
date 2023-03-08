package com.zqw.gp.component.common;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


/**
 * 异常码
 */
public class ErrorCode {

	// 系统级别的错误放在这边。

	public static final int SUCCESS = 200;

	public static final int SYSTEM_ERROR = 20;

	public static final int SYSTEM_INIT = 21;

	public static final int HTTP_PARA_WRONG = 110;
	public static final int HTTP_TOKEN_WRONG = 120;
	public static final int HTTP_PARAM_MISS = 130;

	/** 任务相关的错误码 */
	public static final int TASK_EXIST = 300;
	public static final int TASK_CAN_NOT_CANCEL = 301;
	public static final int TASK_DONE = 303;

	/**
	 * 通信相关的错误码
	 */
	public static final int URL_NOT_EXIT = 350;


	// 获得所有的错误码关系的对应表。
	private static Map<Integer, String> errorCodes = new HashMap<Integer, String>();

	protected static void addErrorCodeClz(Class<?> clz) {
		Field[] fields = clz.getDeclaredFields();
		for (Field f : fields) {
			try {
				Object obj = f.get(clz);
				if (obj instanceof Number) {
					int value = Integer.parseInt(String.valueOf(f.get(clz)));
					String msg = f.getName();
					msg = msg.toLowerCase();
					msg = msg.replace("_", " ");
					if (errorCodes.containsKey(value)) {
						throw new IllegalArgumentException("error code " + value + " duplicate");
					}
					errorCodes.put(value, msg);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	static {
		addErrorCodeClz(ErrorCode.class);
	}

	public static String getMsg(int errorCode) {
		return errorCodes.get(errorCode);
	}

	private static Map<Integer, String> getErrors() {
		return errorCodes;
	}

	public static void main(String args[]) {
		Map<Integer, String> msgs = getErrors();
		for (Integer key : msgs.keySet()) {
			System.out.println(key + "=" + msgs.get(key));
		}
	}
}
