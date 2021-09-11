package com.foxconn.springcloud.filter;

import java.util.Date;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

//自定义全局GlobalFilter
@Component
public class MyLogGateWayFilter implements GlobalFilter,Ordered {

	@Override
	public int getOrder() {
		return 0;//表示首先会执行此过滤器
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("time:"+new Date()+"\t 执行了自定义的全局过滤器: "+"MyLogGateWayFilter"+"hello");
        String uname = exchange.getRequest().getQueryParams().getFirst("uname");
        if (uname == null) {
			System.out.println("&&&&&&&&&&&用户名为空，不允许登录！");
			exchange.getResponse().setStatusCode(HttpStatus.METHOD_NOT_ALLOWED);
			 return exchange.getResponse().setComplete();
		}
		return chain.filter(exchange);
	}

}
