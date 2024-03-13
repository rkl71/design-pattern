package com.hanyang.dprecated.observer;

import com.hanyang.dprecated.DeprecatedConstants;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DeprecatedReceiveObserver extends DeprecatedAbstractObserver{

    @PostConstruct
    public void init(){
        DeprecatedConstants.OBSERVER_LIST.add(this);
    }

    public void orderStateHandle(String orderId, String orderState){
        if (!orderState.equals("ORDER_FINISH")) {
            return;
        }
        // 通过命令模式进行后续处理
        System.out.println("监听到：订单签收成功。通过命令模式作后续处理。");
    }
}