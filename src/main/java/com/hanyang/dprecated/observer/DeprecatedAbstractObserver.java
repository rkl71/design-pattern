package com.hanyang.dprecated.observer;

public abstract class DeprecatedAbstractObserver {
    // 订单状态发生变更时，调用此方法
    public abstract void orderStateHandle(String orderId, String orderState);
}
