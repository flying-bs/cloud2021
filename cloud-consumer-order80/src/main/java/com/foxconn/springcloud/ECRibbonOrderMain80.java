package com.foxconn.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.foxconn.myrule.MySelfRule;

@SpringBootApplication
@EnableEurekaClient
//Ribbon默认采用的是轮询算法，如果自定义则加注解如下采用随机算法
//@RibbonClient(name = "CLOUD-PROVIDER-PAYMENT",configuration=MySelfRule.class)
@EnableFeignClients
@EnableHystrix //启用Feign和Hystrix联合使用
public class ECRibbonOrderMain80 {
	public static void main(String[] args) {
		SpringApplication.run(ECRibbonOrderMain80.class, args);
	}
}
