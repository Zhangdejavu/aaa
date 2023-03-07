package com.zqw.gp.common;

public class SystemStatus {

	private static final int STATUS_LOAD = 1;
	private static final int STATUS_FINISH = 2;

	private static int status = STATUS_LOAD;

	public static void loadFinish() {
		status = STATUS_FINISH;
	}

	public static void shutdown() {
		status = STATUS_LOAD;
	}

	public static boolean isLoadFinish() {
		return status == STATUS_FINISH;
	}

}
