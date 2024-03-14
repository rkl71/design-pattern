package com.hanyang.pay.strategy;

import com.hanyang.pojo.Order;

public interface PayStrategyInterface {
    // 定义公共的支付方法
    String pay(Order order);
}
