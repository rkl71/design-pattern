package com.hanyang.ordermanagement.command;

import com.hanyang.pojo.Order;

public interface OrderCommandInterface {
    // 执行命令
    void execute(Order order);
}
