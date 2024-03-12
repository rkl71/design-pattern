package com.hanyang.items.composite;

public class AbstractProductItem {
    // 添加商品类目
    protected void addProductItem(AbstractProductItem item) {
        throw new UnsupportedOperationException("Not Support child add!");
    }

    // 移除商品类目
    protected void delProductChild(AbstractProductItem item) {
        throw new UnsupportedOperationException("Not Support child remove!");
    }
}
