package com.hanyang.dprecated.state;

import com.hanyang.dprecated.DeprecatedConstants;
import com.hanyang.dprecated.observer.DeprecatedAbstractObserver;

import java.util.List;
import java.util.Vector;

public abstract class DeprecatedAbstractOrderState {
    protected final String ORDER_WAIT_PAY = "ORDER_WAIT_PAY";
    protected final String ORDER_WAIT_SEND = "ORDER_WAIT_SEND";
    protected final String ORDER_WAIT_RECEIVE = "ORDER_WAIT_RECEIVE";
    protected final String ORDER_FINISH = "ORDER_FINISH";
    // 关联抽象观察者
    protected final List<DeprecatedAbstractObserver> observerList = DeprecatedConstants.OBSERVER_LIST;

    // 创建订单
    protected DeprecatedOrder createOrder(String orderId, String productId, DeprecatedOrderContext context) {
        throw new UnsupportedOperationException();
    }

    // 订单支付
    protected DeprecatedOrder payOrder(String orderId, DeprecatedOrderContext context) {
        throw new UnsupportedOperationException();
    }

    // 订单发送
    protected DeprecatedOrder sendOrder(String orderId, DeprecatedOrderContext context) {
        throw new UnsupportedOperationException();
    }

    // 订单签收
    protected DeprecatedOrder receiveOrder(String orderId, DeprecatedOrderContext context) {
        throw new UnsupportedOperationException();
    }

    // 新增观察者
    public void addObserver(DeprecatedAbstractObserver observer) {
        this.observerList.add(observer);
    }

    // 移除观察者
    public void removeObserver(DeprecatedAbstractObserver observer) {
        this.observerList.remove(observer);
    }

    // 通知观察者进行相关操作
    public void notifyObserver(String orderId, String orderState) {
        for (DeprecatedAbstractObserver observer : this.observerList) {
            observer.orderStateHandle(orderId, orderState);
        }
    }

}
