package com.hanyang.ordermanagement.audit;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class SendOrderLog extends AbstractAuditLogProcessor {
    @Override
    protected OrderAuditLog buildDetails(OrderAuditLog auditLog) {
        // 增加快递公司和快递编号
        HashMap<String, String> extraLog = new HashMap<>();
        extraLog.put("快递公司", "X 速递");
        extraLog.put("快递编号", "100100100");
        auditLog.setDetails(extraLog);
        return auditLog;
    }
}
