package com.hanyang.transaction.mediator;

import com.hanyang.transaction.colleague.AbstractCustomer;

public abstract class AbstractMediator {
    public abstract void messageTransfer(String orderId, String targetCustomer, AbstractCustomer customer, String payResult);
}
