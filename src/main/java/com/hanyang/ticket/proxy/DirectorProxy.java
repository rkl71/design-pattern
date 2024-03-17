package com.hanyang.ticket.proxy;

import com.hanyang.ticket.director.AbstractDirector;
import com.hanyang.ticket.director.Director;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DirectorProxy extends AbstractDirector {
    @Autowired
    private Director director;

    @Override
    public Object buildTicket(String type, String productId, String content, String title, String bankInfo, String taxId) {
        String product = this.getProduct(productId);
        if (bankInfo != null && !this.validateBankInfo(bankInfo)) {
            return null;
        }
        return director.buildTicket(type, product, content, title, bankInfo, taxId);
    }

    public String getProduct(String productId) {
        return "通过productId获取商品信息";
    }

    private boolean validateBankInfo(String bankInfo) {
        System.out.println("银行卡校验逻辑");
        return true;
    }
}
