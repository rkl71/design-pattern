package com.hanyang.ticket.director;

public abstract class AbstractDirector {
    public abstract Object buildTicket(String type, String product, String content, String title, String bankInfo, String taxId);
}
