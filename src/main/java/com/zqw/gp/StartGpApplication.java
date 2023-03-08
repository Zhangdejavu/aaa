package com.zqw.gp;

import com.zqw.gp.component.control.server.ServerGroupControl;
import com.zqw.gp.component.dao.ServerDao;
import com.zqw.gp.utils.JsonUtils;
import com.zqw.gp.component.common.SystemStatus;
import com.zqw.gp.component.control.url.UrlInfoControl;
import com.zqw.gp.component.dao.UrlInfoDao;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import com.google.gson.Gson;

/**
 * @author zqw
 * @date 2023/3/8 10:21
 */
@MapperScan("com.zqw.gp.component.dao")
@SpringBootApplication
@ComponentScan("com.zqw.gp.component")
@PropertySource(value = { "application.properties", "mysql.properties" }, encoding = "UTF-8")
public class StartGpApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder(StartGpApplication.class).
				headless(false).run(args);
		UrlInfoDao urlInfoDao = context.getBean(UrlInfoDao.class);
		UrlInfoControl.loadService(urlInfoDao);
		UrlInfoControl.init();
		ServerDao serverDao = context.getBean(ServerDao.class);
		ServerGroupControl.loadService(serverDao);
		ServerGroupControl.init();
		SystemStatus.loadFinish();
	}

	@Bean
	public Gson buildGson() {
		return JsonUtils.getGson();
	}
}
