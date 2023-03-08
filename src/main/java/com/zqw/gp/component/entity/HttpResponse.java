package com.zqw.gp.component.entity;

/**
 * @author zhangqianwei
 * @date 2023/3/6 18:45
 */
public class HttpResponse {

	private Integer httpstatus;
	private DataResult data;
	private String messages;
	private boolean status;

	public Integer getHttpstatus() {
		return httpstatus;
	}

	public void setHttpstatus(Integer httpstatus) {
		this.httpstatus = httpstatus;
	}

	public DataResult getData() {
		return data;
	}

	public void setData(DataResult data) {
		this.data = data;
	}

	public String getMessages() {
		return messages;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}
