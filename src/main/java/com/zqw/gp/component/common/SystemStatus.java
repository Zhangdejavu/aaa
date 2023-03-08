package com.zqw.gp.component.common;

/**
 * 系统运行状态
 */
public class SystemStatus {

	private static final int STATUS_LOAD = 1;
	private static final int STATUS_FINISH = 2;

	private static int status = STATUS_LOAD;
	private static boolean stop = false;

	public static void loadFinish() {
		status = STATUS_FINISH;
	}

	public static boolean isLoadFinish() {
		return status == STATUS_FINISH;
	}

	public static boolean isStop() {
		return stop;
	}

	public static void setStop() {
		SystemStatus.stop = true;
	}
}
