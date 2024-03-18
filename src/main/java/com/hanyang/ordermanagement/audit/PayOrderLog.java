package com.hanyang.ordermanagement.audit;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;

@Component
public class PayOrderLog extends AbstractAuditLogProcessor {
    @Override
    protected OrderAuditLog buildDetails(OrderAuditLog auditLog) {
        // 添加支付类型和实际支付金额
        HashMap<String, String> extraLog = new HashMap<>();
        extraLog.put("payType", "支付宝");
        extraLog.put("price", "100");
        auditLog.setDetails(extraLog);
        return auditLog;
    }
}
