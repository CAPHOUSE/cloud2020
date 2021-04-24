package com.atguigu.cloud;

import com.atguigu.cloud.dao.PaymentDao;
import com.atguigu.cloud.entity.Payment;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Test1 {

    @Autowired
    private PaymentDao paymentDao;

    @Test
   public void test(){
        Payment paymentById = paymentDao.getPaymentById((long) 21);
        System.out.println(paymentById);
    }
}
