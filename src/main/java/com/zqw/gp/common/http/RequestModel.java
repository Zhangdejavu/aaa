package com.zqw.gp.common.http;

import org.apache.http.HttpHost;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * http的请求信息
 */
public class RequestModel {

	private String url;
	private Map<String, String> paras;
	private Map<String, String> headers;
	private String decored = "utf-8";// utf-8 gb2312...
	private String body;
	private boolean isResend;
	private HttpHost host;

	// 如果存在文件， 那么将会是multiple form的提交表单的形式。
	private Map<String, File> files;

	private String method;

	/**
	 * @param url 请求网址
	 */
	public RequestModel(String url) {
		this.url = url;
	}

	public RequestModel(String url, String body) {
		this.url = url;
		this.body = body;
	}

	/**
	 * 
	 * @param url   请求网址
	 * @param paras 请求的参数
	 */
	public RequestModel(String url, Map<String, String> paras) {
		this.url = url;
		this.paras = paras;
	}

	/**
	 * 
	 * @param url     请求网址
	 * @param paras   请求的参数
	 * @param headers 请求头文件配置
	 */
	public RequestModel(String url, Map<String, String> paras, Map<String, String> headers) {
		this.url = url;
		this.paras = paras;
		this.headers = headers;
	}

	/**
	 * 
	 * @param url     请求网址
	 * @param paras   请求的参数
	 * @param headers 请求头文件配置
	 * @param body    请求的实体
	 */
	public RequestModel(String url, Map<String, String> paras, Map<String, String> headers, String body) {
		this.url = url;
		this.paras = paras;
		this.headers = headers;
		this.body = body;
	}

	/**
	 * 
	 * @param url
	 * @param host 代理地址
	 */
	public RequestModel(String url, HttpHost host) {
		this.url = url;
		this.host = host;
	}

	/**
	 * 
	 * @param url
	 * @param paras
	 */
	public RequestModel(String url, Map<String, String> paras, HttpHost host) {
		this.url = url;
		this.paras = paras;
		this.host = host;
	}

	public RequestModel(String url, Map<String, String> paras, Map<String, String> headers, HttpHost host) {
		this.url = url;
		this.paras = paras;
		this.headers = headers;
		this.host = host;
	}

	public RequestModel(String url, Map<String, String> paras, Map<String, String> headers, String body,
			HttpHost host) {
		this.url = url;
		this.paras = paras;
		this.headers = headers;
		this.body = body;
		this.host = host;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getParas() {
		return paras;
	}

	public void setParas(Map<String, String> paras) {
		this.paras = paras;
	}

	public void addPara(String key, String value) {
		if (null == this.getParas()) {
			Map<String, String> paras = new HashMap<>();
			this.paras = paras;
		}
		this.paras.put(key, value);
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public void addHeader(String key, String value) {
		if (null == this.getHeaders()) {
			Map<String, String> headers = new HashMap<>();
			this.headers = headers;
		}
		this.headers.put(key, value);
	}

	public void addHeader(String keyValue) {
		if (null == this.getHeaders()) {
			Map<String, String> headers = new HashMap<>();
			this.headers = headers;
		}

		String key = keyValue.substring(0, keyValue.indexOf(":"));
		String value = keyValue.substring(keyValue.indexOf(":") + 1);
		this.headers.put(key, value);
	}

	public String getDecored() {
		return decored;
	}

	public void setDecored(String decored) {
		this.decored = decored;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public HttpHost getHost() {
		return host;
	}

	public void setHost(HttpHost host) {
		this.host = host;
	}

	public boolean isResend() {
		return isResend;
	}

	public void setResend(boolean isResend) {
		this.isResend = isResend;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Map<String, File> getFiles() {
		return files;
	}

	public void addFiles(String key, File file) {
		if (null == this.getFiles()) {
			Map<String, File> files = new HashMap<>();
			this.files = files;
		}

		this.files.put(key, file);
	}

}
