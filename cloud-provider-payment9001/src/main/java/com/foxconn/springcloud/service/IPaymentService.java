package com.foxconn.springcloud.service;

import org.apache.ibatis.annotations.Param;

import com.foxconn.springcloud.entities.Payment;

public interface IPaymentService {
    public int create(Payment payment);

    public Payment getPaymentById(@Param("id") Long id);
    
    //*******************Hystirx 测试方法开始****************************
    //hystrix -增加服务降级、熔断，限流
    //模拟正常业务方法
    public String paymentInfo_Ok(Integer id);
    
    //模拟异常业务方法-超时或者程序异常
    public String paymentIInfo_Error(Integer id);
    
    //模拟服务熔断业务接口
    public String paymentCircuitBreaker(Integer id);
  //*******************Hystirx 测试方法End****************************
}
