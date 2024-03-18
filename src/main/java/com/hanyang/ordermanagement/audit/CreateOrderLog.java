package com.hanyang.ordermanagement.audit;

import org.springframework.stereotype.Component;

@Component
public class CreateOrderLog extends AbstractAuditLogProcessor {
    @Override
    protected OrderAuditLog buildDetails(OrderAuditLog auditLog) {
        return auditLog;
    }
}
