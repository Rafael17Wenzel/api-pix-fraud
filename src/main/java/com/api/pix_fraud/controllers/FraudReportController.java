package com.api.pix_fraud.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.pix_fraud.models.FraudReport;
import com.api.pix_fraud.models.FraudReportStatus;
import com.api.pix_fraud.services.FraudReportService;



@RestController
@RequestMapping("/api/reports")
public class FraudReportController {

    private final FraudReportService reportService;

    public FraudReportController(FraudReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public ResponseEntity<FraudReport> createReport(@RequestBody Map<String, Object> payload) {
        Long pixCodeId = Long.valueOf(payload.get("pix_code_id").toString());
        Long userId = Long.valueOf(payload.get("user_id").toString());
        Long fraudType = Long.parseLong(payload.get("fraudType").toString());
        return ResponseEntity.status(201).body(reportService.createReport(pixCodeId, userId, fraudType));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<FraudReport>> getReportsByUser(@PathVariable Long userId) {
        List<FraudReport> reports = reportService.getReportsByUserId(userId);
        if (reports.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reports);
    }

    @PutMapping("/{reportId}")
    public ResponseEntity<FraudReport> updateStatus(@PathVariable Long reportId, @RequestBody Map<String, String> payload) {
        FraudReportStatus status = FraudReportStatus.valueOf(payload.get("status"));
        return ResponseEntity.ok(reportService.updateReportStatus(reportId, status));
    }
}
