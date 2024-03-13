package com.hanyang.ordermanagement.command;

import com.hanyang.ordermanagement.command.receiver.OrderCommandReceiver;
import com.hanyang.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderCommand implements OrderCommandInterface{
    @Autowired
    private OrderCommandReceiver receiver;

    @Override
    public void execute(Order order) {
        this.receiver.action(order);
    }
}
