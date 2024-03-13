package com.hanyang.pojo;

import com.hanyang.ordermanagement.state.OrderState;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {
    private String orderId;
    private String productId;
    private OrderState orderState;  // 订单状态
    private Float price;            // 商品价格
}
