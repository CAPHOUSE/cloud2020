package com.atguigu.cloud.service;

import com.atguigu.cloud.entity.Payment;
import org.apache.ibatis.annotations.Param;


public interface PaymentService {

    int create(Payment payment);

    Payment getPaymentById(@Param("id") Long id);
}
