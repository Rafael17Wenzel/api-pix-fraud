package com.api.pix_fraud.services;

import com.api.pix_fraud.models.AuditLog;
import com.api.pix_fraud.models.User;
import com.api.pix_fraud.repositories.AuditLogRepository;
import org.springframework.stereotype.Service;

@Service
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    public AuditService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public void log(User user, String action, String details) {
        AuditLog log = new AuditLog();
        log.setUser(user);
        log.setAction(action);
        log.setActionDetails(details);
        auditLogRepository.save(log);
    }
}
