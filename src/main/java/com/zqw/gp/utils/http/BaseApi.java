package com.zqw.gp.utils.http;

import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.IOException;
import java.net.URISyntaxException;

public abstract class BaseApi {
	// private static final long serialVersionUID = 1L;

	protected String seperate = "-";

	private boolean isResend = false;
	private HttpClient client;
//	private String domain;
	private int failTimes;

	public void setFail() {
		this.isResend = true;
		this.failTimes++;
	}

	public int getFail() {
		return failTimes;
	}

	public BaseApi() {
		client = buildHttpClient(false, null);
	}
//	public BaseApi(String domain) {
//		ClientConfig config = ClientConfigManager.getByDomain(domain);
//		this.domain = domain;
//		if (config == null) {
//			throw new NullPointerException("null config");
//		} else {
//			client = buildHttpClient(false, null);
//		}
//	}

	private void refreshClient() {
//		ClientConfig config = ClientConfigManager.getByDomain(domain);
		client = buildHttpClient(false, null);
	}

	/**
	 * 实现get的处理
	 * 
	 * @return get返回的信息
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 */
	public String doGet(RequestModel request) throws ClientProtocolException, URISyntaxException, IOException {
		if (isResend) {
			refreshClient();
		}
		System.out.println("begin get task " + request.getUrl());
		ConnectionUtils clientUtils = new ConnectionUtils(client);
		return clientUtils.simpleGetInvoke(request);
	}

	/**
	 * 实现post
	 * 
	 * @return
	 * @throws ClientProtocolException
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public String doPost(RequestModel request) throws ClientProtocolException, URISyntaxException, IOException {
		if (isResend) {
			refreshClient();
		}
		System.out.println("begin post task " + request.getUrl());
		ConnectionUtils clientUtils = new ConnectionUtils(client);
		return clientUtils.simplePostInvoke(request);
	}

	/**
	 * 实现图片的下载
	 * 
	 * @param path 下载之后的保存路径
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public boolean imgDown(RequestModel request, String path)
			throws ClientProtocolException, IOException, URISyntaxException {
		if (isResend) {
			refreshClient();
		}
		System.out.println("begin img down task " + request.getUrl());
		ConnectionUtils clientUtils = new ConnectionUtils(client);
		return clientUtils.simpleImageGetInvoke(request, path);
	}

	/**
	 * 实现图片的上传
	 * 
	 * @param path 上传的本地的路径
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	// public boolean imgUpload(RequestModel request, String path) throws
	// ClientProtocolException, IOException {
	// if (isResend) {
	// refreshClient();
	// }
	// System.out.println("begin img upload task " + request.getUrl());
	// ConnectionUtils clientUtils = new ConnectionUtils(client);
	// return clientUtils.uploadMedia(request, path);
	// }

	/**
	 * 创建HttpClient *
	 * 
	 * @param isMultiThread 是否需要开启多个请求，如果需要，需要重写PoolingHttpClientConnectionManager，
	 *                      赋予最大请求数等
	 * @param host          代理地址， null表示不使用代理
	 * @return
	 */
	private HttpClient buildHttpClient(boolean isMultiThread, HttpHost host) {
		CloseableHttpClient client;
		if (null != host) {
			System.out.println("begin to use the proxy.." + host.toHostString());
			DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(host);
			if (isMultiThread) {
				client = HttpClientBuilder.create().setConnectionManager(new PoolingHttpClientConnectionManager())
						.setRoutePlanner(routePlanner).build();
			} else {
				client = HttpClientBuilder.create().setRoutePlanner(routePlanner).build();
			}

		} else {
			if (isMultiThread)
				client = HttpClientBuilder.create().setConnectionManager(new PoolingHttpClientConnectionManager())
						.build();
			else {
				client = HttpClientBuilder.create().build();
			}
		}
		return client;
	}
}