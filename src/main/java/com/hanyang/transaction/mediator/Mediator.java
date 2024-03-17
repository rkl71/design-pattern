package com.hanyang.transaction.mediator;

import com.hanyang.transaction.colleague.AbstractCustomer;
import com.hanyang.transaction.colleague.Buyer;
import com.hanyang.transaction.colleague.Payer;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Mediator extends AbstractMediator {
    // 关联多个对应的同事类
    public static Map<String, Map<String, AbstractCustomer>> customerInstances = new ConcurrentHashMap<>();

    @Override
    public void messageTransfer(String orderId, String targetCustomer, AbstractCustomer customer, String payResult) {
        // 如果当前同事类为购买者
        if (customer instanceof Buyer) {
            AbstractCustomer buyer = customerInstances.get(orderId).get("buyer");
            System.out.println("朋友代付：" + buyer.getCustomerName() + "转发OrderId" + orderId + "到用户" + targetCustomer + "进行支付.");
        } else if (customer instanceof Payer) {
            AbstractCustomer payer = customerInstances.get(orderId).get("payer");
            System.out.println("代付完成：" + payer.getCustomerName() + "完成orderId" + orderId + "的支付。通知" + targetCustomer + ", 支付结果：" + payResult);
            customerInstances.remove(orderId);
        }
    }
}
