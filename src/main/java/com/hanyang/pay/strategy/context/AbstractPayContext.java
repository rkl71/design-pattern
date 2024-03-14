package com.hanyang.pay.strategy.context;

import com.hanyang.pojo.Order;

public abstract class AbstractPayContext {
    public abstract String execute(Order order);
}
