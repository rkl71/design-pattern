package com.hanyang.dprecated.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeprecatedOrderContext {

    private DeprecatedAbstractOrderState currentState;

    @Autowired
    private DeprecatedCreateOrder deprecatedCreateOrder;

    // 设置当前订单状态
    public void setCurrentState(DeprecatedAbstractOrderState currentState) {
        this.currentState = currentState;
    }

    // 创建订单
    public DeprecatedOrder createOrder(String orderId, String productId) {
        this.currentState = this.deprecatedCreateOrder;
        DeprecatedOrder order = currentState.createOrder(orderId, productId, this);
        return order;
    }

    // 支付订单
    public DeprecatedOrder payOrder(String orderId) {
        DeprecatedOrder order = currentState.payOrder(orderId, this);
        return order;
    }

    // 发送订单
    public DeprecatedOrder sendOrder(String orderId) {
        DeprecatedOrder order = currentState.sendOrder(orderId, this);
        return order;
    }

    // 接收订单
    public DeprecatedOrder receiveOrder(String orderId) {
        DeprecatedOrder order = currentState.receiveOrder(orderId, this);
        return order;
    }
}
