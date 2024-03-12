package com.hanyang.dprecated.state;

import com.hanyang.utils.RedisCommonProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeprecatedPayOrder extends DeprecatedAbstractOrderState {

    @Autowired
    private RedisCommonProcessor redisCommonProcessor;

    @Autowired
    private DeprecatedSendOrder deprecatedSendOrder;

    // 订单支付
    protected DeprecatedOrder payOrder(String orderId, DeprecatedOrderContext context) {
        DeprecatedOrder order = (DeprecatedOrder) redisCommonProcessor.get(orderId);
        if (!order.getState().equals(ORDER_WAIT_PAY)) {
            throw new UnsupportedOperationException("Order state should be ORDER_WAIT_PAY" + ".But now it's state is : " + order.getState());
        }
        order.setState(ORDER_WAIT_SEND);
        redisCommonProcessor.set(orderId, order);
        return order;
    }
}
