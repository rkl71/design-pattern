package com.hanyang.items.vistor;

import com.hanyang.items.composite.AbstractProductItem;

public interface ItemVisitor <T>{
    // 定义公共的visitor方法供子类实现
    T visitor(AbstractProductItem productItem);
}
