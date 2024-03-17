package com.hanyang.ticket.builder;

public abstract class TicketBuilder<T> {
    // 设置通用发票信息
    public abstract void setCommentInfo(String title, String product, String content);

    // 设置企业号
    public void setTaxId(String taxId){
        throw new UnsupportedOperationException();
    }

    // 设置企业银行卡信息
    public void setBankInfo(String bankInfo){
        throw new UnsupportedOperationException();
    }

    // 抽象建造方法
    public abstract T buildTicket();
}
