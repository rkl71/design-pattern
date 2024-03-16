package com.hanyang.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.hanyang.pojo.Order;
import com.hanyang.service.OrderService;
import com.hanyang.service.decorator.OrderServiceDecorator;
import com.hanyang.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderServiceDecorator orderServiceDecorator;

    @Value("${service.level}")
    private Integer serviceLevel;

    @PostMapping("/create")
    public Order createOrder(@RequestParam String productId) {
        return orderService.createOrder(productId);
    }

    @PostMapping("/pay")
    public String payOrder(@RequestParam String orderId,
                           @RequestParam Float price,
                           @RequestParam Integer payType) {
        return orderService.getPayUrl(orderId, price, payType);
    }

    @PostMapping("/send")
    public Order send(@RequestParam String orderId) {
        return orderService.send(orderId);
    }

    @PostMapping("/receive")
    public Order receive(@RequestParam String orderId) {
        return orderService.receive(orderId);
    }

    @RequestMapping("/alipaycallback")
    public String alipayCallback(HttpServletRequest request) throws AlipayApiException, UnsupportedEncodingException, UnsupportedEncodingException {
        // 获取回调信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
            params.put(name, valueStr);
        }
        // 验证签名
        boolean signVerified = AlipaySignature.rsaCheckV1(params, Constants.ALIPAY_PUBLIC_KEY, "UTF-8", Constants.SIGN_TYPE);
        if (signVerified) {
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
            float total_amount = Float.parseFloat(new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8"));
            // 进行相关的业务操作
            //Order order = orderService.pay(out_trade_no);
            orderServiceDecorator.setOrderServiceInterface(orderService);
            Order order = orderServiceDecorator.decoratorPay(out_trade_no, serviceLevel, total_amount);
            return "支付成功页面跳转，当前订单为：" + order;
        } else {
            throw new UnsupportedOperationException("callback verify failed!");
        }
    }
}
