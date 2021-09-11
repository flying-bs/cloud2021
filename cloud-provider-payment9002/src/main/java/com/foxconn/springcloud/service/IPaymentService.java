package com.foxconn.springcloud.service;

import org.apache.ibatis.annotations.Param;

import com.foxconn.springcloud.entities.Payment;

public interface IPaymentService {
    public int create(Payment payment);

    public Payment getPaymentById(@Param("id") Long id);
}
