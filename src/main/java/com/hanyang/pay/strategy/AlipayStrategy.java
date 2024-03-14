package com.hanyang.pay.strategy;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.hanyang.pojo.Order;
import com.hanyang.utils.Constants;


// 支付宝支付策略类
public class AlipayStrategy implements PayStrategyInterface {
    @Override
    public String pay(Order order) {
        // 创建AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(Constants.ALIPAY_GATEWAY, Constants.APP_ID, Constants.APP_PRIVATE_KEY, "JSON", "UTF-8", Constants.ALIPAY_PUBLIC_KEY, Constants.SIGN_TYPE);
        // 设置请求参数
        AlipayTradePagePayRequest payRequest = new AlipayTradePagePayRequest();
        payRequest.setReturnUrl(Constants.CALLBACK_URL);
        payRequest.setBizContent("{\"out_trade_no\":\"" + order.getOrderId() + "\","
                + "\"total_amount\":\"" + order.getPrice() + "\","
                + "\"subject\":\"" + " 程序员 " + "\","
                + "\"body\":\"" + " 商品描述 " + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        // 请求
        try {
            String result = alipayClient.pageExecute(payRequest, "GET").getBody();
            return result;
        } catch (Exception e) {
            throw new UnsupportedOperationException("Alipay failed! " + e);
        }
    }

}
