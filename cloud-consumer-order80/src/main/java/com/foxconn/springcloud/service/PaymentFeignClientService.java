package com.foxconn.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "CLOUD-PROVIDER-PAYMENT",fallback = PaymentFallbackService.class)
public interface PaymentFeignClientService {
	   @GetMapping("/payment/hystrix/error/{id}")
	    public String getPaymentInfo(@PathVariable("id") Integer id);
}
