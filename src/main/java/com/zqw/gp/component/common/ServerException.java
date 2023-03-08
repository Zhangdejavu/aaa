package com.zqw.gp.component.common;


/**
 * 系统异常抛出
 */
public class ServerException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int code;

	public ServerException(int error) {
		super(ErrorCode.getMsg(error));
		this.code = error;
	}

	public ServerException(int error, String msg) {
		super(msg);
		this.code = error;
	}
	
	public int getCode() {
		return this.code;
	}
}
