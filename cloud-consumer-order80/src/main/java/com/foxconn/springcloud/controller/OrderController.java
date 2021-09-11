package com.foxconn.springcloud.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.foxconn.springcloud.entities.CommonResult;
import com.foxconn.springcloud.entities.Payment;
import com.foxconn.springcloud.lb.MyLoadBalancer;
import com.foxconn.springcloud.service.PaymentFeignClientService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class OrderController {

	//public static final String PAYMENT_URL = "http://localhost:9001";
	public static final String PAYMENT_URL = "http://CLOUD-PROVIDER-PAYMENT";
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private MyLoadBalancer mybalancer;
	@Autowired
    private DiscoveryClient discoveryClient;
	
	@Autowired
	private PaymentFeignClientService paymentFeignClientService;
	@GetMapping("/consumer/payment/add")
	public CommonResult<Payment> add(Payment payment){
		return restTemplate.postForObject(PAYMENT_URL+"/payment/add/", payment, CommonResult.class);
	}
	
	@GetMapping("/consumer/payment/get/{id}")
	public CommonResult<Payment> getPayment(@PathVariable("id") Long id){
		return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id, CommonResult.class);
	}
	
	@GetMapping("/consumer/get/mylb")
	public String testMylB() {
		List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PROVIDER-PAYMENT");
		if (instances == null ||  instances.size()<=0) {
			return null;
		}
		ServiceInstance serviceInstance = mybalancer.getInstances(instances);
		URI uri = serviceInstance.getUri();
		
		return restTemplate.getForObject(uri+"/payment/lb",String.class);
	}
	
	//调用端Hystrix服务降级测试
	@GetMapping("/payment/hystrix/error/{id}")
	public String paymentIInfo_Error(Integer id) {
		String rs = paymentFeignClientService.getPaymentInfo(id);
		return rs;
	}
}
