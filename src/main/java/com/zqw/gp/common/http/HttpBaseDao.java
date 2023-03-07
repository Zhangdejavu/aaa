package com.zqw.gp.common.http;

import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class HttpBaseDao {

	private static final String HTTP = "http";
	private static final String HTTPS = "https";
	private boolean isResend = false;
	private HttpClient client;
	private HttpClient httpsClient;
	private int failTimes;
	private static HttpBaseDao instance = new HttpBaseDao();

	public void setFail() {
		this.isResend = true;
		this.failTimes++;
	}

	public int getFail() {
		return failTimes;
	}

	private HttpBaseDao() {
		client = buildHttpClient(false, null);
	}

	public static HttpBaseDao getInstantce() {
		return instance;
	}

	private void refreshClient() {
		client = buildHttpClient(false, null);
	}

	public String doGet(String url) throws ClientProtocolException, URISyntaxException, IOException {
		RequestModel request = new RequestModel(url);
		return doGet(request);
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
		ConnectionUtils clientUtils = getClient(request.getUrl());
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
		ConnectionUtils clientUtils = getClient(request.getUrl());
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
		ConnectionUtils clientUtils = getClient(request.getUrl());
		return clientUtils.simpleImageGetInvoke(request, path);
	}

	public byte[] imgToBytes(RequestModel request) throws ClientProtocolException, IOException, URISyntaxException {
		if (isResend) {
			refreshClient();
		}
		System.out.println("begin img down task " + request.getUrl());
		ConnectionUtils clientUtils = getClient(request.getUrl());
		return clientUtils.simpleImageGetInvoke(request);
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
			if (isMultiThread) {
				client = HttpClientBuilder.create().setConnectionManager(new PoolingHttpClientConnectionManager())
						.build();
			} else {
				client = HttpClientBuilder.create().build();
			}
		}
		return client;
	}

	private ConnectionUtils getClient(String url) {
		if (url.startsWith(HTTPS)) {
			if (null == httpsClient) {
				httpsClient = buildHttsClient();
			}
			return new ConnectionUtils(httpsClient);
		}

		return new ConnectionUtils(client);
	}

	private HttpClient buildHttsClient() {
		SSLContextBuilder builder = null;
		SSLConnectionSocketFactory sslsf = null;
		PoolingHttpClientConnectionManager cm = null;
		try {
			builder = new SSLContextBuilder();
			// 全部信任 不做身份鉴定
			builder.loadTrustMaterial(null, new TrustStrategy() {
				@Override
				public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
					return true;
				}
			});
			sslsf = new SSLConnectionSocketFactory(builder.build(),
					new String[] { "SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2" }, null, NoopHostnameVerifier.INSTANCE);
			Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register(HTTP, new PlainConnectionSocketFactory()).register(HTTPS, sslsf).build();
			cm = new PoolingHttpClientConnectionManager(registry);
			cm.setMaxTotal(200);// max connection
		} catch (Exception e) {
			e.printStackTrace();
		}
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).setConnectionManager(cm)
				.setConnectionManagerShared(true).build();
		return httpClient;
	}
}