package com.api.pix_fraud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.pix_fraud.models.FraudReport;

import java.util.List;

public interface FraudReportRepository extends JpaRepository<FraudReport, Long> {
    List<FraudReport> findByUserId(Long userId);
}