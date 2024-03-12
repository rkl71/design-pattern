package com.hanyang.dprecated.state;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeprecatedOrder {

    // 订单唯一编号
    private String orderId;

    // 商品信息
    private String productId;

    // 订单状态
    private String state;
}
