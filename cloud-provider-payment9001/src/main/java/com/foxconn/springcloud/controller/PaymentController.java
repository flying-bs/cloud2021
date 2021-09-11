package com.foxconn.springcloud.controller;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.foxconn.springcloud.entities.CommonResult;
import com.foxconn.springcloud.entities.Payment;
import com.foxconn.springcloud.service.IPaymentService;

@RestController
public class PaymentController {

	Logger  log = LoggerFactory.getLogger(PaymentController.class);
	@Autowired
	private IPaymentService iPaymentService;
	
    @Value("${server.port}")
    private String serverPort;

	@PostMapping("payment/add")
	//前端传过来的为json字符串，所以必须加注解@RequestBody才能解析为pojo存入数据库
	public CommonResult create(@RequestBody  Payment payment) {
		int res = iPaymentService.create(payment);
		if (res > 0) {
			return new CommonResult(200,"数据新增成功!且服务端口号为："+serverPort+"且结果为:"+res);
		}else {
			return new CommonResult(444,"数据新增失败!且服务端口号为："+serverPort+"且结果为" + res);
		}
	}
	
	@GetMapping("payment/get/{id}")
	public CommonResult<Payment>  findById(@PathVariable("id") Long id) {
		Payment pt = iPaymentService.getPaymentById(id);
		log.info("*****查询结果："+pt.toString());
		if (pt != null) {
			return new CommonResult(200,"查询成功,server port:"+serverPort+"且结果为:"+pt);
		}else {
			return new CommonResult(200,"查询失败，对应id: " +id);
		}
	}   
	@GetMapping(value = "/payment/lb")
    public String getPaymentLB()
    {
        return serverPort;
    }
	
    //*******************Hystirx 服务降级测试方法开始****************************
	@GetMapping("/payment/hystrix/ok/{id}")
	public String paymentInfo_Ok(@PathVariable("id") Integer id) {
			String rs = iPaymentService.paymentInfo_Ok(id);
			return rs;
	}

	@GetMapping("/payment/hystrix/error/{id}")
	public String paymentIInfo_Error(Integer id) {
		String rs = iPaymentService.paymentIInfo_Error(id);
		return rs;
	}
    //*******************Hystirx 服务降级测试方法End****************************
	
    //*******************Hystirx 服务熔断测试方法开始****************************
	@GetMapping("/payment/hystrix-circuitBreaker/{id}")
	public String paymentCircuitBreaker(@PathVariable("id")  Integer id) {
		String res =  iPaymentService.paymentCircuitBreaker(id);
		return res;
	}
    //*******************Hystirx 服务熔断测试方法End****************************
}
