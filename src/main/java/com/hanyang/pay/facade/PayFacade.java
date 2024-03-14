package com.hanyang.pay.facade;

import com.hanyang.pay.strategy.context.PayContext;
import com.hanyang.pay.strategy.factory.PayContextFactory;
import com.hanyang.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PayFacade {
    // 注入工厂类
    @Autowired
    private PayContextFactory contextFactory;

    public String pay(Order order, Integer payType) {
        // 获取 PayContext
        PayContext context = contextFactory.getContext(payType);
        // 调用支付方法
        return context.execute(order);
    }
}
