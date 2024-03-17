package com.hanyang.ticket.builder;

import com.hanyang.ticket.product.PersonalTicket;


public class PersonalTicketBuilder extends TicketBuilder<PersonalTicket>{
    private PersonalTicket personalTicket = new PersonalTicket();

    @Override
    public void setCommentInfo(String title, String product, String content) {
        personalTicket.setTitle(title);
        personalTicket.setProduct(product);
        personalTicket.setContent(content);
    }

    // 返回个人电子发票
    @Override
    public PersonalTicket buildTicket() {
        return personalTicket;
    }
}
