package com.hanyang.pay.strategy.factory;

public enum StrategyEnum {
    // 定义支付宝支付策略类
    alipay("com.hanyang.pay.strategy.AlipayStrategy"),
    // 定义微信支付策略类
    wechat("com.hanyang.pay.strategy.WechatStrategy");

    String value = "";

    StrategyEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
