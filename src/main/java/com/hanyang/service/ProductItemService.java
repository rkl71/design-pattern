package com.hanyang.service;

import com.hanyang.items.composite.AbstractProductItem;
import com.hanyang.items.composite.ProductComposite;
import com.hanyang.items.vistor.AddItemVisitor;
import com.hanyang.items.vistor.DelItemVisitor;
import com.hanyang.pojo.ProductItem;
import com.hanyang.repo.ProductItemRepository;
import com.hanyang.utils.RedisCommonProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductItemService {

    @Autowired
    private RedisCommonProcessor redisProcessor;

    @Autowired
    private ProductItemRepository productItemRepository;

    @Autowired
    private AddItemVisitor addItemVisitor;

    @Autowired
    private DelItemVisitor delItemVisitor;

    // 获取商品类目信息
    public ProductComposite fetchAllItems() {
        Object cacheItems = redisProcessor.get("items");
        if (cacheItems != null) {
            return (ProductComposite) cacheItems;
        }
        List<ProductItem> fetchDbItems = productItemRepository.findAll();
        ProductComposite items = generateProductTree(fetchDbItems);
        if (items == null) {
            throw new UnsupportedOperationException("Product items should not be empty in DB!");
        }
        redisProcessor.set("items", items);
        return items;
    }

    // 将DB中的商品类目信息组装成树型结构
    private ProductComposite generateProductTree(List<ProductItem> fetchDbItems) {
        List<ProductComposite> composites = new ArrayList<>(fetchDbItems.size());
        fetchDbItems.forEach(dbItem -> composites.add(ProductComposite.builder()
                .id(dbItem.getId())
                .name(dbItem.getName())
                .pid(dbItem.getPid())
                .build()));
        Map<Integer, List<ProductComposite>> groupingList = composites.stream().collect(Collectors.groupingBy(ProductComposite::getPid));
        composites.stream().forEach(item -> {
            List<ProductComposite> list = groupingList.get(item.getId());
            item.setChild(list == null ? new ArrayList<>() : list.stream().map(x -> (AbstractProductItem) x).collect(Collectors.toList()));
        });
        ProductComposite composite = composites.size() == 0 ? null : composites.get(0);
        return composite;
    }

    // 添加商品类目
    public ProductComposite addItem(ProductItem item) {
        // 先更新数据库
        productItemRepository.addItem(item.getName(), item.getPid());
        // 通过访问者模式访问树形数据结构，并添加新的商品类目
        ProductComposite addItem = ProductComposite.builder()
                .id(productItemRepository.findByNameAndPid(item.getName(), item.getPid()).getId())
                .name(item.getName())
                .pid(item.getPid())
                .child(new ArrayList<>())
                .build();
        AbstractProductItem updatedItems = addItemVisitor.visitor(addItem);
        redisProcessor.set("items", updatedItems);
        return (ProductComposite) updatedItems;
    }

    // 删除商品类目
    public ProductComposite delItems(ProductItem item){
        // 先更新数据库
        productItemRepository.delItem(item.getId());
        // 通过访问者模式访问树形数据结构，并删除商品类目
        ProductComposite delItem = ProductComposite.builder()
                .id(item.getId())
                .name(item.getName())
                .pid(item.getPid())
                .build();
        AbstractProductItem updatedItems = delItemVisitor.visitor(delItem);
        redisProcessor.set("items", updatedItems);
        return (ProductComposite) updatedItems;
    }
}
