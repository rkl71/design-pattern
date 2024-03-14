package com.hanyang.pay.strategy.factory;

public abstract class AbstractPayContextFactory<T> {
    public abstract T getContext(Integer payType);
}
