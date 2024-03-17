package com.hanyang.ticket.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalTicket implements Cloneable {
    // 发票固定不变的信息
    private String finalInfo;
    // 发票抬头
    private String title;
    // 商品信息
    private String product;
    // 税率、发票代码、校验码等信息
    private String content;
}
