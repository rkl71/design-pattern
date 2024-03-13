package com.hanyang.ordermanagement.state;

public enum OrderStateChangeAction {
    PAY_ORDER,     // 支付操作
    SEND_ORDER,    // 发货操作
    RECEIVE_ORDER; // 收货操作
}
