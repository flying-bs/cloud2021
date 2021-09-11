package com.foxconn.springcloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxconn.springcloud.dao.PaymentDao;
import com.foxconn.springcloud.entities.Payment;
import com.foxconn.springcloud.service.IPaymentService;

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

}
