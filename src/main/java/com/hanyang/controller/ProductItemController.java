package com.hanyang.controller;

import com.hanyang.items.composite.ProductComposite;
import com.hanyang.pojo.ProductItem;
import com.hanyang.service.ProductItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductItemController {
    @Autowired
    private ProductItemService productItemService;

    @PostMapping("/fetchAllItems")
    public ProductComposite fetchAllItems() {
        return productItemService.fetchAllItems();
    }

    // 添加商品类目
    @PostMapping("/addItems")
    public ProductComposite addItems(@RequestBody ProductItem item){
        return productItemService.addItem(item);
    }

    // 删除商品类目
    @PostMapping("/delItems")
    public ProductComposite delItems(@RequestBody ProductItem item){
        return productItemService.delItems(item);
    }
}
