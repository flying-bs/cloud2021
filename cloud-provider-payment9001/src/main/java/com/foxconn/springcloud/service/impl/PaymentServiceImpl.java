package com.foxconn.springcloud.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxconn.springcloud.dao.PaymentDao;
import com.foxconn.springcloud.entities.Payment;
import com.foxconn.springcloud.service.IPaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import cn.hutool.core.util.IdUtil;

@Service
public class PaymentServiceImpl implements IPaymentService {
	@Autowired
	private PaymentDao dao;
	
	@Override
	public int create(Payment payment) {
		// TODO Auto-generated method stub
		return dao.add(payment);
	}

	@Override
	public Payment getPaymentById(Long id) {
		// TODO Auto-generated method stub
		return dao.getPaymentById(id);
	}
	
    //*******************Hystirx 服务降级测试方法开始****************************
	@Override
	public String paymentInfo_Ok(Integer id) {
		// TODO Auto-generated method stub
		return  "ThreadPool- " + Thread.currentThread().getName() +" paymentInfo_Ok：业务正常 ";
	}

	//服务超时导致服务降级测试
	@Override
	@HystrixCommand(fallbackMethod = "paymentIInfo_ErrorHandler",commandProperties = {
			@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="3000")
	})
	public String paymentIInfo_Error(Integer id) {
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  "ThreadPool- " + Thread.currentThread().getName() +" paymentIInfo_Error：业务异常 ";
	}
	
public String paymentIInfo_ErrorHandler(Integer id){
    return "/(ㄒoㄒ)/调用支付接口超时或异常：\t"+ "\t当前线程池名字" + Thread.currentThread().getName();
}
    //*******************Hystirx 服务降级测试方法End****************************

//*******************Hystirx 服务熔断测试方法开始****************************
//其实服务熔断过程=服务降级+熔断+恢复调用链路
@HystrixCommand(fallbackMethod = "paymentFallback",commandProperties = {
		@HystrixProperty(name = "circuitBreaker.enabled" ,value = "true"),//是否开启断路器
		@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value  = "10000"),//时间窗口期
		@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value  = "10"),//调用端请求次数
		@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value  = "50")//失败率达到50%后进行断路
})
@Override
public String paymentCircuitBreaker(Integer id) {
	if (id < 0) {
		throw new RuntimeException("输入Id不能为负数！");
	}
	String serialNumber = IdUtil.simpleUUID();//根据Hutool 工具生成唯一字符串 
	//线程池数默认为10个
	return Thread.currentThread().getName() + "\t 调用成功，流水号为=  " + serialNumber;
}

public String paymentFallback(Integer id) {
	return "id 不能负数，请稍后再试，/(ㄒoㄒ)/~~   id: " +id;
}
//*******************Hystirx 服务熔断测试方法End****************************

}
