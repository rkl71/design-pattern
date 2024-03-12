package com.hanyang.dprecated.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeprecatedOrderContext {

    @Autowired
    private DeprecatedCreateOrder deprecatedCreateOrder;

    @Autowired
    private DeprecatedPayOrder deprecatedPayOrder;

    @Autowired
    private DeprecatedSendOrder deprecatedSendOrder;

    @Autowired
    private DeprecatedReceiveOrder deprecatedReceiveOrder;

    // 创建订单
    public DeprecatedOrder createOrder(String orderId, String productId) {
        DeprecatedOrder order = deprecatedCreateOrder.createOrder(orderId, productId, this);
        return order;
    }

    // 支付订单
    public DeprecatedOrder payOrder(String orderId) {
        DeprecatedOrder order = deprecatedPayOrder.payOrder(orderId, this);
        return order;
    }

    // 发送订单
    public DeprecatedOrder sendOrder(String orderId) {
        DeprecatedOrder order = deprecatedSendOrder.sendOrder(orderId, this);
        return order;
    }

    // 接收订单
    public DeprecatedOrder receiveOrder(String orderId) {
        DeprecatedOrder order = deprecatedReceiveOrder.receiveOrder(orderId, this);
        return order;
    }
}
