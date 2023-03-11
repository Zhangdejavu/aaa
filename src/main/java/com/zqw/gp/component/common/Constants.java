package com.zqw.gp.component.common;

/**
 * 常量类
 */
public class Constants {

	/**
	 * 任务状态码
	 */
	public static final int STATUS_INIT = 0;
	public static final int STATUS_PATCH = 1;
	public static final int STATUS_DOING = 2;
	public static final int STATUS_FINISH = 3;
	public static final int STATUS_PAUSE = 10;
	public static final int STATUS_ERROR = 20;

	/**
	 * 外部API接口通信状态码
	 */
	public static final String HTTP_RESPONSE_MSG_OK = "ok";
	public static final String HTTP_RESPONSE_MSG_SUCCESS = "success";
	public static final int HTTP_RESPONSE_CODE_OK = 0;
	public static final int HTTP_RESPONSE_CODE_SUCCESS = 200;

	/**
	 * API来源
	 */
	public static final String URL_RESOURCE_12306 = "12306";

	public static final String WEB_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36";

	/**
	 * 服务器类别
	 */
	public static final String SERVER_LOCAL = "本地";
	public static final String SERVER_ALI = "阿里云";
	public static final String SERVER_TENCENT = "腾讯云";

	public static final int SEAT_PRICE_STR_LENGTH = 40;
	public static final int TRAIN_F_DATA_LENGTH = 30;
	public static final int TRAIN_OTHER_DATA_LENGTH = 30;

	public static final String TRAIN_TYPE_F = "9MO";
	public static final String TRAIN_TYPE_G = "9MOO";
	public static final String TRAIN_TYPE_D= "";
	public static final String TRAIN_TYPE_Z= "";
	public static final String TRAIN_TYPE_T= "";
	public static final String TRAIN_TYPE_K= "1341";
}
