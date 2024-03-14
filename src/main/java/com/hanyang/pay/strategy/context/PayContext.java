package com.hanyang.pay.strategy.context;

import com.hanyang.pay.strategy.PayStrategyInterface;
import com.hanyang.pojo.Order;

public class PayContext {
    private PayStrategyInterface payStrategy;

    // 设计具体策略
    public PayContext(PayStrategyInterface payStrategy){
        this.payStrategy = payStrategy;
    }

    // 执行策略
    public String execute(Order order){
        return this.payStrategy.pay(order);
    }
}
