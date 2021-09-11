package com.foxconn.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//虽然不用重启客户端端微服务，但仍需要运维人员发送Post请求刷新3355，如：curl -X POST "http://localhost:3355/actuator/refresh"
//所以后续需要依赖bus总线技术
@RefreshScope
public class ConfigClientController {

    @Value("${config.info}")
    private String configInfo;
	
	@GetMapping("/getConfigInfo")
	public String getConfigInfo() {
		return configInfo;
	}
}
