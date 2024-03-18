package com.hanyang.ordermanagement.audit;

import org.springframework.stereotype.Component;

@Component
public class ReceiveOrderLog extends AbstractAuditLogProcessor {
    @Override
    protected OrderAuditLog buildDetails(OrderAuditLog auditLog) {
        return auditLog;
    }
}
