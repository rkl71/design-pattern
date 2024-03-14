package com.hanyang.pay.strategy.factory;

import com.hanyang.pay.strategy.PayStrategyInterface;
import com.hanyang.pay.strategy.context.PayContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PayContextFactory extends AbstractPayContextFactory<PayContext> {

    // 创建Map作为缓存存储PayContext
    private static final Map<String, PayContext> payContexts = new ConcurrentHashMap();

    @Override
    public PayContext getContext(Integer payType) {
        // 根据payType定位枚举类
        StrategyEnum strategyEnum =
                payType == 1 ? StrategyEnum.alipay :
                payType == 2 ? StrategyEnum.wechat :
                null;
        if (strategyEnum == null) {
            throw new UnsupportedOperationException("payType not supported!");
        }
        // 尝试从Map中获取PayContext
        PayContext context = payContexts.get(strategyEnum.name());
        // 第一次调用，Context为空
        if (context == null) {
            try {
                // 通过反射，创建具体策略类
                PayStrategyInterface payStrategy = (PayStrategyInterface) Class.forName(strategyEnum.getValue()).newInstance();
                PayContext payContext = new PayContext(payStrategy);
                payContexts.put(strategyEnum.name(), payContext);
            } catch (Exception e) {
                throw new UnsupportedOperationException("Get payStrategy failed!");
            }
        }
        return payContexts.get(strategyEnum.name());
    }
}
