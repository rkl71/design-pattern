package com.hanyang.items.vistor;

import com.hanyang.items.composite.AbstractProductItem;
import com.hanyang.items.composite.ProductComposite;
import com.hanyang.utils.RedisCommonProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DelItemVisitor implements ItemVisitor<AbstractProductItem> {

    @Autowired
    private RedisCommonProcessor redisProcessor;

    @Override
    public AbstractProductItem visitor(AbstractProductItem productItem) {
        ProductComposite currentItem = (ProductComposite) redisProcessor.get("items");
        ProductComposite delItem = (ProductComposite) productItem;
        if (delItem.getId() == currentItem.getId()) {
            throw new UnsupportedOperationException("根节点不能删。");
        }
        if (delItem.getPid() == currentItem.getId()) {
            currentItem.delProductChild(delItem);
            return currentItem;
        }
        delChild(delItem, currentItem);
        return currentItem;
    }

    private void delChild(ProductComposite productItem, ProductComposite currentItem) {
        for (AbstractProductItem abstractItem : currentItem.getChild()) {
            ProductComposite item = (ProductComposite) abstractItem;
            if (item.getId() == productItem.getPid()) {
                item.delProductChild(productItem);
                break;
            } else {
                delChild(productItem, item);
            }
        }
    }
}
