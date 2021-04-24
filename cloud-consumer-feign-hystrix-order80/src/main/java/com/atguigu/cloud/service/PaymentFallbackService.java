package com.atguigu.cloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService {

    @Override
    public String paymentInfo_ok(Integer id) {
        return "-------PaymentFallbackService fall back paymentInfo_ok  ,o(╥﹏╥)o,服务器异常!";
    }

    @Override
    public String paymentInfo_Timeout(Integer id) {
        return "-------PaymentFallbackService fall paymentInfo_Timeout back ,o(╥﹏╥)o,服务器异常!";
    }
}
