package com.zqw.gp.utils.http;

/**
 * 请求的配置项，是否使用代理等信息
 */
public class ClientConfig {
	private String domain;
	private boolean isProxy;

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public boolean isProxy() {
		return isProxy;
	}

	public void setProxy(boolean isProxy) {
		this.isProxy = isProxy;
	}

}
