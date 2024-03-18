package com.hanyang.ordermanagement.audit;

import java.util.Date;

public abstract class AbstractAuditLogProcessor {
    private final OrderAuditLog basicAuditLog(String account, String action, String orderId) {
        OrderAuditLog auditLog = new OrderAuditLog();
        auditLog.setAccount(account);
        auditLog.setAction(action);
        auditLog.setOrderId(orderId);
        auditLog.setDate(new Date());
        return auditLog;
    }

    // 定义抽象模板方法
    protected abstract OrderAuditLog buildDetails(OrderAuditLog auditLog);

    // 定义订单审计日志的创建步骤
    public final OrderAuditLog createAuditLog(String account, String action, String orderId) {
        // 设置审计日志的基本信息
        OrderAuditLog orderAuditLog = basicAuditLog(account, action, orderId);
        // 设置审计日志的额外信息
        return buildDetails(orderAuditLog);
    }
}
