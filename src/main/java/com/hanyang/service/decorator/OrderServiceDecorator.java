package com.hanyang.service.decorator;

import com.hanyang.pojo.Order;
import com.hanyang.pojo.Products;
import com.hanyang.repo.ProductsRepository;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceDecorator extends AbstractOrderServiceDecorator {
    // 引入Apollo配置中心的消息超时时间
    @Value("${delay.service.time}")
    private String delayServiceTime;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 具体实现积分更新和红包发放
    @Override
    protected void updateScoreAndSendRedPaper(String productId, int serviceLevel, float price) {
        switch (serviceLevel) {
            case 0:
                //根据价格的百分之一更新积分
                int score = Math.round(price) / 100;
                System.out.println("正常处理，为用户更新积分！score = " + score);
                //根据商品属性发放红包
                Products product = productsRepository.findByProductId(productId);
                if (product != null && product.getSendRedBag() == 1) {
                    System.out.println("正常处理，为用户发放红包！productId = " + productId);
                }
                break;
            case 1:
                MessageProperties properties = new MessageProperties();
                // 设置消息过期时间
                Message msg = new Message(productId.getBytes(), properties);
                // 向正常队列中发送消息
                rabbitTemplate.send("normalExcahnge", "myKey", msg);
                System.out.println("延迟处理，时间 =" + delayServiceTime);
                break;
            case 2:
                System.out.println("暂停服务！");
                break;
            default:
                throw new UnsupportedOperationException("不支持的服务级别！");
        }
    }

    // 将pay方法与updateScoreAndSendRedPaper方法进行逻辑结合
    public Order decoratorPay(String orderId, int serviceLevel, float price) {
        // 调用原有OrderService逻辑
        Order order = super.pay(orderId);
        // 新的逻辑，更新积分、发放用户红包
        try {
            this.updateScoreAndSendRedPaper(order.getProductId(), serviceLevel, price);
        } catch (Exception e) {
            // 重试机制
        }
        return order;
    }
}
