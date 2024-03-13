package com.hanyang.dprecated.state;

import com.hanyang.utils.RedisCommonProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeprecatedReceiveOrder extends DeprecatedAbstractOrderState {

    @Autowired
    private RedisCommonProcessor redisCommonProcessor;

    // 订单签收
    protected DeprecatedOrder receiveOrder(String orderId, DeprecatedOrderContext context) {
        DeprecatedOrder order = (DeprecatedOrder) redisCommonProcessor.get(orderId);
        if (!order.getState().equals(ORDER_WAIT_RECEIVE)) {
            throw new UnsupportedOperationException("Order state should be ORDER_WAIT_RECEIVE" + ".But now it's state is : " + order.getState());
        }
        order.setState(ORDER_FINISH);
        // 观察者模式
        super.notifyObserver(orderId, ORDER_FINISH);
        redisCommonProcessor.remove(orderId);
        return order;
    }
}
