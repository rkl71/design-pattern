package com.hanyang.service.decorator;

import com.hanyang.pojo.Order;
import com.hanyang.service.inter.OrderServiceInterface;

public abstract class AbstractOrderServiceDecorator implements OrderServiceInterface {
    private OrderServiceInterface orderServiceInterface;

    public void setOrderServiceInterface(OrderServiceInterface orderServiceInterface) {
        this.orderServiceInterface = orderServiceInterface;
    }

    public Order createOrder(String productId) {
        return this.orderServiceInterface.createOrder(productId);
    }

    public Order send(String orderId) {
        return this.orderServiceInterface.send(orderId);
    }

    public Order receive(String orderId) {
        return this.orderServiceInterface.receive(orderId);
    }

    public String getPayUrl(String orderId, Float price, Integer payType) {
        return this.orderServiceInterface.getPayUrl(orderId, price, payType);
    }

    @Override
    public Order pay(String orderId) {
        return this.orderServiceInterface.pay(orderId);
    }

    protected abstract void updateScoreAndSendRedPaper(String productId, int serviceLevel, float price);
}
