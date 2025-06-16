package com.api.pix_fraud.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.pix_fraud.models.AuditLog;
import com.api.pix_fraud.services.AuditService;

@RestController
@RequestMapping("/api/audit_logs")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping
    public ResponseEntity<List<AuditLog>> getAuditLogs() {
        List<AuditLog> auditLogs = auditService.getAuditLogs();
        return ResponseEntity.ok(auditLogs);
    }
}
