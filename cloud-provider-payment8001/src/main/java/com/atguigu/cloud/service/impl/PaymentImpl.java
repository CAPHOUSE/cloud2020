package com.atguigu.cloud.service.impl;

import com.atguigu.cloud.dao.PaymentDao;
import com.atguigu.cloud.entity.Payment;
import com.atguigu.cloud.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentImpl implements PaymentService{

    @Autowired
    private PaymentDao paymentDao;

    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }
}
