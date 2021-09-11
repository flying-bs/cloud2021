package com.foxconn.springcloud.service;

import org.springframework.stereotype.Component;

@Component //必须加 //必须加 //必须加
public class PaymentFallbackService implements PaymentFeignClientService
{
    @Override
    public String getPaymentInfo(Integer id)
    {
        return "服务调用失败，提示来自：cloud-consumer-feign-order80";
    }
}