package com.hanyang.ticket.builder;

import com.hanyang.ticket.product.CompanyTicket;

public class CompanyTicketBuilder extends TicketBuilder<CompanyTicket> {
    private CompanyTicket companyTicket = new CompanyTicket();

    @Override
    public void setCommentInfo(String title, String product, String content) {
        companyTicket.setTitle(title);
        companyTicket.setProduct(product);
        companyTicket.setContent(content);
    }

    public void setTaxId(String taxId) {
        companyTicket.setTaxId(taxId);
    }

    public void setBankInfo(String bankInfo) {
        companyTicket.setBankInfo(bankInfo);
    }

    // 返回企业电子发票
    @Override
    public CompanyTicket buildTicket() {
        return companyTicket;
    }
}
