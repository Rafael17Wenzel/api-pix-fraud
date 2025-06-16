package com.api.pix_fraud.services;

import com.api.pix_fraud.models.AuditLog;
import com.api.pix_fraud.models.Person;
import com.api.pix_fraud.repositories.AuditLogRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    public AuditService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public void log(Person user, String action, String details) {
        AuditLog log = new AuditLog();
        log.setUser(user);
        log.setAction(action);
        log.setActionDetails(details);
        auditLogRepository.save(log);
    }

    public List<AuditLog> getAuditLogs() {
        return auditLogRepository.findAll();
    }
}
