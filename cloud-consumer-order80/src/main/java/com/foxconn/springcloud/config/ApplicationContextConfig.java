package com.foxconn.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {

	@Bean
	//@LoadBalanced//如果自己实现LoasBalancer,则需要屏蔽此注解
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
