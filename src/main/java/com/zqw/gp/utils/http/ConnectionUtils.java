package com.zqw.gp.utils.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * detail client with get/ post
 */
public class ConnectionUtils {

	protected HttpClient client;

	/**
	 * 连接超时时间
	 */
	public static final int CONNECTION_TIMEOUT_MS = 3000;

	/**
	 * 读取数据超时时间
	 */
	public static final int SO_TIMEOUT_MS = 3000;

	// 最大的重定向次数
	public static final int MAX_REDIRT_TIMES = 100;

	public ConnectionUtils(HttpClient client) {
		this.client = client;
	}

	/**
	 * 简单post调用
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String simplePostInvoke(RequestModel config)
			throws URISyntaxException, ClientProtocolException, IOException {
		HttpPost postMethod = null;
		if (null != config.getBody()) {
			postMethod = buildHttpPost(config.getUrl(), config.getBody());
		}
		RequestConfig rConfig = buildRequestConfig();
		postMethod.setConfig(rConfig);

		if (null != config.getHeaders()) {
			Iterator<String> headIt = config.getHeaders().keySet().iterator();
			while (headIt.hasNext()) {
				String key = headIt.next();
				String value = config.getHeaders().get(key);
				postMethod.addHeader(key, value);
			}
		}

		HttpResponse response = client.execute(postMethod);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			String returnStr = EntityUtils.toString(entity, config.getDecored());
			return returnStr;
		}
		return null;
	}

	/**
	 * 简单get调用
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public String simpleGetInvoke(RequestModel config) {

		String url = config.getUrl();
		HttpGet get = null;
		try {
			get = buildHttpGet(url, config.getParas());
			RequestConfig rConfig = buildRequestConfig();
			get.setConfig(rConfig);
			if (null != config.getHeaders()) {
				Iterator<String> headIt = config.getHeaders().keySet().iterator();
				while (headIt.hasNext()) {
					String key = headIt.next();
					String value = config.getHeaders().get(key);
					get.setHeader(key, value);
				}
			}
			HttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String returnStr = EntityUtils.toString(entity, config.getDecored());
				return returnStr;
			}
		} catch (Exception e) {
			if (null != get) {
				get.abort();
				get.releaseConnection();
			}
		}

		return null;
	}

	/**
	 * 构建httpGet对象
	 * 
	 * @param url
	 * @param headers
	 * @return
	 * @throws URISyntaxException
	 */
	private HttpGet buildHttpGet(String url, Map<String, String> params) throws URISyntaxException {
		HttpGet get = new HttpGet(buildGetUrl(url, params));
		return get;
	}

	/**
	 * 构建公用RequestConfig
	 * 
	 * @return
	 */
	private RequestConfig buildRequestConfig() {
		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom().setCircularRedirectsAllowed(true)
				.setMaxRedirects(MAX_REDIRT_TIMES).setSocketTimeout(SO_TIMEOUT_MS)
				.setConnectTimeout(CONNECTION_TIMEOUT_MS).build();

		return requestConfig;
	}

	/**
	 * build getUrl str
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	private String buildGetUrl(String url, Map<String, String> params) {
		StringBuffer uriStr = new StringBuffer(url);
		if (params != null) {
			List<NameValuePair> ps = new ArrayList<NameValuePair>();
			for (String key : params.keySet()) {
				ps.add(new BasicNameValuePair(key, params.get(key)));
			}
			uriStr.append("?");
			uriStr.append(URLEncodedUtils.format(ps, "utf-8"));
		}
		// System.out.println("begin get task " + uriStr.toString());
		return uriStr.toString();
	}

	/**
	 * 实现图片的下载。
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public boolean simpleImageGetInvoke(RequestModel config, String path)
			throws ClientProtocolException, IOException, URISyntaxException {
		if (null == config) {
			return false;
		}
		// 文件存在则不存储
		File storeFile = new File(path);
		if (storeFile.exists()) {
			return true;
		}
		HttpGet get = buildHttpGet(config.getUrl(), config.getParas());
		if (null != config.getHeaders()) {
			Iterator<String> headIt = config.getHeaders().keySet().iterator();
			while (headIt.hasNext()) {
				String key = headIt.next();
				String value = config.getHeaders().get(key);
				get.setHeader(key, value);
			}
		}
		RequestConfig rConfig = buildRequestConfig();
		get.setConfig(rConfig);

		HttpResponse response = client.execute(get);

		// 文件夹创建，
		String[] temps = path.split("/");
		String tempPath = "";
		for (int i = 0; i < temps.length - 1; i++) {
			String temp = tempPath + temps[i];
			File f = new File(temp);
			if (!f.exists()) {
				f.mkdir();
			}
			tempPath = tempPath + temps[i] + "/";
		}

		HttpEntity entity = response.getEntity();
		if (entity != null) {
			byte[] bytes = EntityUtils.toByteArray(entity);
			FileOutputStream output = new FileOutputStream(storeFile);
			// 得到网络资源的字节数组,并写入文件
			output.write(bytes);
			output.close();
		}
		return true;
	}

	/**
	 * 实现图片的转为byte数组
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public byte[] simpleImageGetInvoke(RequestModel config)
			throws ClientProtocolException, IOException, URISyntaxException {
		if (null == config) {
			return null;
		}

		HttpGet get = buildHttpGet(config.getUrl(), config.getParas());

		RequestConfig rConfig = buildRequestConfig();
		get.setConfig(rConfig);

		HttpResponse response = client.execute(get);

		HttpEntity entity = response.getEntity();
		if (entity != null) {
			byte[] bytes = EntityUtils.toByteArray(entity);
			return bytes;
		}
		return null;
	}

	/**
	 * 实现上传文件
	 * 
	 * @param request
	 * @return 文件上传的结果和对应的mediaid
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws Exception
	 */
	// protected boolean uploadMedia(RequestModel config, String path)
	// throws ClientProtocolException, IOException {
	//
	// String url = config.getUrl();
	//
	// HttpPost httppost = new HttpPost(url);
	// RequestConfig rConfig = buildRequestConfig();
	// httppost.setConfig(rConfig);
	//
	// if (null != config.getHeaders()) {
	// Iterator<String> headIt = config.getHeaders().keySet().iterator();
	// while (headIt.hasNext()) {
	// String key = headIt.next();
	// String value = config.getHeaders().get(key);
	// httppost.addHeader(key, value);
	// }
	// }
	// setCommonHttpMethod(httppost);
	// FileBody bin = new FileBody(new File(path));
	// MultipartEntity reqEntity = new MultipartEntity();
	// reqEntity.addPart("media", bin);
	// httppost.setEntity(reqEntity);
	//
	// HttpResponse response = client.execute(httppost);
	// int statusCode = response.getStatusLine().getStatusCode();
	// if (statusCode == HttpStatus.SC_OK) {
	// HttpEntity entity = response.getEntity();
	// if (entity != null) {
	// String returnStr = EntityUtils.toString(entity,
	// config.getDecored());
	// System.out.println(returnStr);
	// }
	// return true;
	// }
	// return false;
	// }

	private static void setCommonHttpMethod(HttpRequestBase httpMethod) {
		httpMethod.setHeader(HTTP.CONTENT_ENCODING, "utf-8");// setti
	}

	/**
	 * 构建httpPost对象
	 * 
	 * @param url
	 * @param headers
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws URISyntaxException
	 */
	private static HttpPost buildHttpPost(String url, String body)
			throws UnsupportedEncodingException, URISyntaxException {
		HttpPost post = new HttpPost(url);
		setCommonHttpMethod(post);
		StringEntity jsonEntity = new StringEntity(body, ContentType.APPLICATION_JSON);
		post.setEntity(jsonEntity);
		return post;
	}

}
