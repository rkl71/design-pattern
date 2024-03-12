package com.hanyang.items.composite;

import com.hanyang.pojo.ProductItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductComposite extends AbstractProductItem {
    private int id;
    private int pid;
    private String name;
    private List<AbstractProductItem> child = new ArrayList<>();

    // 添加商品类目
    public void addProductItem(AbstractProductItem item) {
        this.child.add(item);
    }

    // 移除商品类目
    public void delProductChild(AbstractProductItem item) {
        ProductComposite removeItem = (ProductComposite) item;
        Iterator iterator = child.iterator();
        while (iterator.hasNext()) {
            ProductComposite composite = (ProductComposite) iterator.next();

            // 移除ID相同的商品类目
            if (composite.getId() == removeItem.getId()) {
                iterator.remove();
                break;
            }
        }
    }
}
