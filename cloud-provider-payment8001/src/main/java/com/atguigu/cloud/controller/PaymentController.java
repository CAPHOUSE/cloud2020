package com.atguigu.cloud.controller;

import com.atguigu.cloud.entity.CommonResult;
import com.atguigu.cloud.entity.Payment;
import com.atguigu.cloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping( value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("----插入结果："+result);
        if (result > 0){
            return new CommonResult(200,"插入数据库成功!serverPort:"+serverPort,result);
        }else {
            return new CommonResult(444,"插入数据库识别!",null);
        }
    }

    @GetMapping( value = "/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);

        if (payment != null){
            return new CommonResult(200,"查询数据库成功!serverPort:"+serverPort,payment);
        }else {
            return new CommonResult(444,"没有对应的数据,查询ID:"+ id,null);
        }
    }

    @GetMapping("/payment/discovery")
    public Object discovery(){

        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("-----service："+ service);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PRYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getServiceId()+"\t"+instance.getHost() + "\t" +  instance.getPort()+"\t"+instance.getUri());
        }

        return this.discoveryClient;
    }

    @GetMapping("/payment/lb")
    public String getPayment(){
        return serverPort;
    }

    @GetMapping("/payment/feign/timeout")
    public String paymentFeignTimeout(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }
}
