package com.zqw.gp.common.http;

import java.util.HashMap;
import java.util.Map;

/**
 * use the domain to get weather use the proxy
 */
public class ClientConfigManager {
	private static Map<String, ClientConfig> config = new HashMap<String, ClientConfig>();

	static {
		String domain = "bbs.fudan.edu.cn";
		ClientConfig eduConfig = new ClientConfig();
		eduConfig.setDomain(domain);
		eduConfig.setProxy(true);
		config.put(domain, eduConfig);
	}

	public static ClientConfig getByDomain(String domain) {
		// if (null == config.get(domain)) {
		// ClientConfig eduConfig = new ClientConfig();
		// eduConfig.setDomain(domain);
		// eduConfig.setProxy(false);
		// return eduConfig;
		// } else {
		// return config.get(domain);
		// }
		ClientConfig c = config.get(domain);
		if (c == null) {
			c = new ClientConfig();
			c.setDomain(domain);
			c.setProxy(false);
			config.put(domain, c);
		}
		return c;
	}
}
