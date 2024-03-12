package com.hanyang.service;

import com.hanyang.items.composite.AbstractProductItem;
import com.hanyang.items.composite.ProductComposite;
import com.hanyang.pojo.ProductItem;
import com.hanyang.repo.ProductItemRepository;
import com.hanyang.utils.RedisCommonProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductItemService {

    @Autowired
    private RedisCommonProcessor redisProcessor;

    @Autowired
    private ProductItemRepository productItemRepository;

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
}
