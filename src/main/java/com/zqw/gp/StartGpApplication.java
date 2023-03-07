package com.zqw.gp;

import com.zqw.gp.common.JsonUtils;
import com.zqw.gp.common.SystemStatus;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import com.google.gson.Gson;

@MapperScan("com.zqw.gp.component.dao")
@SpringBootApplication
@ComponentScan("com.zqw.gp.component")
@PropertySource(value = { "application.properties", "mysql.properties" }, encoding = "UTF-8")
public class StartGpApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder(StartGpApplication.class).
				headless(false).run(args);
		SystemStatus.loadFinish();
	}

	@Bean
	public Gson buildGson() {
		return JsonUtils.getGson();
	}
}
