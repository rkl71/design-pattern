package com.hanyang.dprecated.state;

import com.hanyang.utils.RedisCommonProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeprecatedSendOrder extends DeprecatedAbstractOrderState {

    @Autowired
    private RedisCommonProcessor redisCommonProcessor;

    @Autowired
    private DeprecatedReceiveOrder deprecatedReceiveOrder;

    // 订单发送
    protected DeprecatedOrder sendOrder(String orderId, DeprecatedOrderContext context) {
        DeprecatedOrder order = (DeprecatedOrder) redisCommonProcessor.get(orderId);
        if (!order.getState().equals(ORDER_WAIT_SEND)) {
            throw new UnsupportedOperationException("Order state should be ORDER_WAIT_SEND" + ".But now it's state is : " + order.getState());
        }
        order.setState(ORDER_WAIT_RECEIVE);
        redisCommonProcessor.set(orderId, order);
        // 观察者模式
        super.notifyObserver(orderId, ORDER_WAIT_RECEIVE);
        return order;
    }
}
