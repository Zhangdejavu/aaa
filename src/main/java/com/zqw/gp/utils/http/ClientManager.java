package com.zqw.gp.utils.http;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * build client and weather use proxy and also use the pool.
 */
public class ClientManager {

	private static Map<String, List<HttpClient>> proxyList = new HashMap<String, List<HttpClient>>();
	private static Map<String, AtomicLong> proxyIndex = new HashMap<String, AtomicLong>();

	protected HttpClient getClientByDomain(String domain) {
		if (null == proxyList || proxyList.get(domain) == null) {
			return buildHttpClient(false, null);
		} else {
			List<HttpClient> clients = proxyList.get(domain);
			AtomicLong index = proxyIndex.get(domain);
			if (index.get() >= clients.size()) {
				index = new AtomicLong(index.get() % clients.size());
			}
			return clients.get((int) index.getAndIncrement());
		}
	}

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
}
