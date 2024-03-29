package com.hanyang.service;

import com.hanyang.ordermanagement.audit.CreateOrderLog;
import com.hanyang.ordermanagement.audit.PayOrderLog;
import com.hanyang.ordermanagement.audit.ReceiveOrderLog;
import com.hanyang.ordermanagement.audit.SendOrderLog;
import com.hanyang.ordermanagement.command.OrderCommand;
import com.hanyang.ordermanagement.command.invoker.OrderCommandInvoker;
import com.hanyang.ordermanagement.state.OrderState;
import com.hanyang.ordermanagement.state.OrderStateChangeAction;
import com.hanyang.pay.facade.PayFacade;
import com.hanyang.pojo.Order;
import com.hanyang.service.inter.OrderServiceInterface;
import com.hanyang.transaction.colleague.AbstractCustomer;
import com.hanyang.transaction.colleague.Buyer;
import com.hanyang.transaction.colleague.Payer;
import com.hanyang.transaction.mediator.Mediator;
import com.hanyang.utils.RedisCommonProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class OrderService implements OrderServiceInterface {
    @Autowired
    private StateMachine<OrderState, OrderStateChangeAction> orderStateMachine;

    @Autowired
    private StateMachinePersister<OrderState, OrderStateChangeAction, String> stateMachineRedisPersister;

    @Autowired
    private RedisCommonProcessor redisCommonProcessor;

    @Autowired
    private OrderCommand orderCommand;

    @Autowired
    private PayFacade payFacade;

    // 注入中介者对象
    @Autowired
    private Mediator mediator;

    @Autowired
    private CreateOrderLog createOrderLog;

    @Autowired
    private PayOrderLog payOrderLog;

    @Autowired
    private SendOrderLog sendOrderLog;

    @Autowired
    private ReceiveOrderLog receiveOrderLog;

    // 订单创建
    public Order createOrder(String productId) {
        String orderId = "OID" + productId;
        Order order = Order.builder()
                .orderId(orderId)
                .productId(productId)
                .orderState(OrderState.ORDER_WAIT_PAY)
                .build();
        redisCommonProcessor.set(order.getOrderId(), order, 900);
        OrderCommandInvoker invoker = new OrderCommandInvoker();
        invoker.invoke(orderCommand, order);
        createOrderLog.createAuditLog("userAccount", "CREATE", orderId);
        return order;
    }

    // 订单支付
    public Order pay(String orderId) {
        // 从Redis中获取订单
        Order order = (Order) redisCommonProcessor.get(orderId);
        Message message = MessageBuilder
                .withPayload(OrderStateChangeAction.PAY_ORDER)
                .setHeader("order", order)
                .build();
        // 将Message传递给spring状态机
        if (changeStateAction(message, order)) {
            createOrderLog.createAuditLog("userAccount", "PAY", orderId);
            return order;
        }
        return null;
    }

    // 订单发送
    public Order send(String orderId) {
        // 从Redis中获取订单
        Order order = (Order) redisCommonProcessor.get(orderId);
        Message message = MessageBuilder
                .withPayload(OrderStateChangeAction.SEND_ORDER)
                .setHeader("order", order)
                .build();
        // 将Message传递给spring状态机
        if (changeStateAction(message, order)) {
            createOrderLog.createAuditLog("userAccount", "SEND", orderId);
            return order;
        }
        return null;
    }

    // 订单签收
    public Order receive(String orderId) {
        // 从Redis中获取订单
        Order order = (Order) redisCommonProcessor.get(orderId);
        Message message = MessageBuilder
                .withPayload(OrderStateChangeAction.RECEIVE_ORDER)
                .setHeader("order", order)
                .build();
        // 将Message传递给spring状态机
        if (changeStateAction(message, order)) {
            createOrderLog.createAuditLog("userAccount", "RECEIVE", orderId);
            return order;
        }
        return null;
    }

    // 状态机的相关操作
    private boolean changeStateAction(Message<OrderStateChangeAction> message, Order order) {
        try {
            // 启动状态机
            orderStateMachine.start();
            stateMachineRedisPersister.restore(orderStateMachine, order.getOrderId() + "STATE");
            boolean res = orderStateMachine.sendEvent(message);
            stateMachineRedisPersister.persist(orderStateMachine, order.getOrderId() + "STATE");
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            orderStateMachine.stop();
        }
        return false;
    }

    public String getPayUrl(String orderId, Float price, Integer payType) {
        Order order = (Order) redisCommonProcessor.get(orderId);
        order.setPrice(price);
        return payFacade.pay(order, payType);
    }

    public void friendPay(String sourceCustomer, String orderId, String targetCustomer, String payResult, String role) {
        // 创建中介者
        Buyer buyer = new Buyer(orderId, mediator, sourceCustomer);
        Payer payer = new Payer(orderId, mediator, sourceCustomer);
        HashMap<String, AbstractCustomer> map = new HashMap<>();
        map.put("buyer", buyer);
        map.put("payer", payer);
        mediator.customerInstances.put(orderId, map);
        if (role.equals("B")) {
            buyer.messageTransfer(orderId, targetCustomer, payResult);
        } else if (role.equals("P")) {
            payer.messageTransfer(orderId, targetCustomer, payResult);
        }
    }


}
