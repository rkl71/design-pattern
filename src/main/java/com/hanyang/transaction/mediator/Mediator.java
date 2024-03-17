package com.hanyang.transaction.mediator;

import com.hanyang.transaction.colleague.AbstractCustomer;
import com.hanyang.transaction.colleague.Buyer;
import com.hanyang.transaction.colleague.Payer;

public class Mediator extends AbstractMediator {
    // 关联同事类
    private AbstractCustomer buyer;
    private AbstractCustomer payer;

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public void setPayer(Payer payer) {
        this.payer = payer;
    }

    @Override
    public void messageTransfer(String orderId, String targetCustomer, AbstractCustomer customer, String payResult) {
        // 如果当前同事类为购买者
        if (customer instanceof Buyer) {
            System.out.println("朋友代付：" + buyer.getCustomerName() + "转发OrderId" + orderId + "到用户" + targetCustomer + "进行支付.");
        } else if (customer instanceof Payer) {
            System.out.println("代付完成：" + payer.getCustomerName() + "完成orderId" + orderId + "的支付。通知" + targetCustomer + ", 支付结果：" + payResult);
        }
    }
}
