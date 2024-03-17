package com.hanyang.transaction.colleague;

import com.hanyang.transaction.mediator.AbstractMediator;

public class Buyer extends AbstractCustomer {
    public Buyer(String orderId, AbstractMediator mediator, String customerName) {
        super(orderId, mediator, customerName);
    }

    @Override
    public void messageTransfer(String orderId, String targetCustomer, String payResult) {
        // 调用中介者的messageTransfer方法
        super.mediator.messageTransfer(orderId, targetCustomer, this, payResult);
    }
}
