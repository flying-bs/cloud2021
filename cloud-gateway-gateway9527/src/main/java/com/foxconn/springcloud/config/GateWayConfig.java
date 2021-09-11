package com.foxconn.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GateWayConfig {
    /**
     * 配置了一个id为route-name的路由规则，
     * 当访问地址 http://localhost:9527/guonei时会自动转发到地址：http://news.baidu.com/guonei
     * @param builder
     * @return
     * Gateway网关路由有两种配置方式:1.在yml配置/2.自定义配置类，如GateWayConfig
     * 
     */
	@Bean
	public RouteLocator customerRouteLocator(RouteLocatorBuilder builder) {
		RouteLocatorBuilder.Builder locatorBuilder = builder.routes();
		locatorBuilder.route("path_route_foxconn", r ->r.path("/guonei").uri("http://news.baidu.com/guonei")).build();
		return locatorBuilder.build();
		
	}
}
