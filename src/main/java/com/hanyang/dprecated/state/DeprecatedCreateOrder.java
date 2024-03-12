package com.hanyang.dprecated.state;

import com.hanyang.utils.RedisCommonProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeprecatedCreateOrder extends DeprecatedAbstractOrderState {

    @Autowired
    private RedisCommonProcessor redisCommonProcessor;

    @Autowired
    private DeprecatedPayOrder deprecatedPayOrder;

    // 创建订单
    protected DeprecatedOrder createOrder(String orderId, String productId, DeprecatedOrderContext context) {
        // 创建订单对象
        DeprecatedOrder order = DeprecatedOrder.builder()
                .orderId(orderId)
                .productId(productId)
                .state(ORDER_WAIT_PAY)
                .build();
        // 将新订单存入redis缓存，15分钟后失效
        redisCommonProcessor.set(orderId, order, 900);
        return order;
    }
}
