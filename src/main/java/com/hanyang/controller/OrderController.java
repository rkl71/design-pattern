package com.hanyang.controller;

import com.hanyang.pojo.Order;
import com.hanyang.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public Order createOrder(@RequestParam String productId){
        return orderService.createOrder(productId);
    }

    @PostMapping("/pay")
    public Order payOrder(@RequestParam String orderId){
        return orderService.pay(orderId);
    }

    @PostMapping("/send")
    public Order send(@RequestParam String orderId){
        return orderService.send(orderId);
    }

    @PostMapping("/receive")
    public Order receive(@RequestParam String orderId){
        return orderService.receive(orderId);
    }
}
