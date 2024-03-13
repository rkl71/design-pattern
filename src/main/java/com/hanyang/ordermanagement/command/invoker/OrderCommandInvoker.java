package com.hanyang.ordermanagement.command.invoker;

import com.hanyang.ordermanagement.command.OrderCommandInterface;
import com.hanyang.pojo.Order;

public class OrderCommandInvoker {
    public void invoke(OrderCommandInterface command, Order order){
        command.execute(order);
    }
}
