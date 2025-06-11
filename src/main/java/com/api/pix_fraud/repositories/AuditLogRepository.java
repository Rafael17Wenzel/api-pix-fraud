package com.api.pix_fraud.repositories;

import com.api.pix_fraud.models.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
