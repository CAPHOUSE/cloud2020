package com.atguigu.cloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    /**
     *
     * @param id
     * @return
     */

    public String paymentInfoOk(Integer id){
        return "线程池：" + Thread.currentThread().getName() + "paymentInfo_ok, id:" + id +"\t" +"^_^";
    }


    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000")
    })  //超过三秒调用paymentInfo_TimeOutHandler方法,运行异常或超时异常都会降级
    public String paymentInfo_Timeout(Integer id){

         int timeNumber = 3;
//        int a = 10 / 0;
        try { TimeUnit.SECONDS.sleep(timeNumber); }catch (InterruptedException e){ e.printStackTrace(); }
        return "线程池：" + Thread.currentThread().getName() + "paymentInfo_Timeout, id:" + id +"\t" +"^_^, " + "耗时:"+ timeNumber;
    }

    public String paymentInfo_TimeOutHandler(Integer id){
        return "线程池：" + Thread.currentThread().getName() + "8001系统繁忙,请稍后再试!, id:" + id +"\t" +"o(╥﹏╥)o";
    }



    //服务熔断
//服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),   //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),  //请求词数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),    //时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),    //失败率到达多少后跳闸
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if(id < 0){
            throw new RuntimeException("******id 不能为负数");
        }
        String serialNumber = IdUtil.simpleUUID();  //UUID.randomUUID();

        return Thread.currentThread().getName()+"\t"+"调用成功，流水号："+serialNumber;
    }


    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return "id 不能负数，请稍后再试，o(╥﹏╥)o  id："+id;
    }

}
