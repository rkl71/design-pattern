package com.hanyang.ordermanagement.listener;

import com.hanyang.ordermanagement.command.OrderCommand;
import com.hanyang.ordermanagement.command.invoker.OrderCommandInvoker;
import com.hanyang.ordermanagement.state.OrderState;
import com.hanyang.ordermanagement.state.OrderStateChangeAction;
import com.hanyang.pojo.Order;
import com.hanyang.utils.RedisCommonProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;

@Component
@WithStateMachine(name = "orderStateMachine")
public class OrderStateListener {
    @Autowired
    private RedisCommonProcessor redisCommonProcessor;

    @Autowired
    private OrderCommand orderCommand;

    @OnTransition(source = "ORDER_WAIT_PAY", target = "ORDER_WAIT_SEND")
    public boolean payToSend(Message<OrderStateChangeAction> message) {
        // 从Redis中获取订单，并判断当前订单状态是否为待支付
        Order order = (Order) message.getHeaders().get("order");
        if (order.getOrderState() != OrderState.ORDER_WAIT_PAY) {
            throw new UnsupportedOperationException("Order state error!");
        }
        order.setOrderState(OrderState.ORDER_WAIT_SEND);
        redisCommonProcessor.set(order.getOrderId(), order);
        // 命令模式
        OrderCommandInvoker invoker = new OrderCommandInvoker();
        invoker.invoke(orderCommand, order);
        return true;
    }

    @OnTransition(source = "ORDER_WAIT_SEND", target = "ORDER_WAIT_RECEIVE")
    public boolean sendToReceive(Message<OrderStateChangeAction> message) {
        // 从Redis中获取订单，并判断当前订单状态是否为待支付
        Order order = (Order) message.getHeaders().get("order");
        if (order.getOrderState() != OrderState.ORDER_WAIT_SEND) {
            throw new UnsupportedOperationException("Order state error!");
        }
        order.setOrderState(OrderState.ORDER_WAIT_RECEIVE);
        redisCommonProcessor.set(order.getOrderId(), order);
        // 命令模式
        OrderCommandInvoker invoker = new OrderCommandInvoker();
        invoker.invoke(orderCommand, order);
        return true;
    }

    @OnTransition(source = "ORDER_WAIT_RECEIVE", target = "ORDER_FINISH")
    public boolean receiveToFinish(Message<OrderStateChangeAction> message) {
        // 从Redis中获取订单，并判断当前订单状态是否为待支付
        Order order = (Order) message.getHeaders().get("order");
        if (order.getOrderState() != OrderState.ORDER_WAIT_RECEIVE) {
            throw new UnsupportedOperationException("Order state error!");
        }
        order.setOrderState(OrderState.ORDER_FINISH);
        redisCommonProcessor.remove(order.getOrderId());
        redisCommonProcessor.remove(order.getOrderId() + "STATE");
        // 命令模式
        OrderCommandInvoker invoker = new OrderCommandInvoker();
        invoker.invoke(orderCommand, order);
        return true;
    }
}
