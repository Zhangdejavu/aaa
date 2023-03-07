package com.zqw.gp.common;

public class Constants {

	/**
	 * 任务状态码
	 */
	public static final int STATUS_INIT = 0;
	public static final int STATUS_PAUSE = 1;
	public static final int STATUS_DOING = 2;
	public static final int STATUS_FINISH = 3;
	public static final int STATUS_ERROR = 21;

	/**
	 * 外部API接口通信状态码
	 */
	public static final String HTTP_RESPONSE_MSG_OK = "ok";
	public static final String HTTP_RESPONSE_MSG_SUCCESS = "success";
	public static final int HTTP_RESPONSE_CODE_OK = 0;
	public static final int HTTP_RESPONSE_CODE_SUCCESS = 200;
}
