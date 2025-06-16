package com.api.pix_fraud.controllers;

import java.util.Collections;
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

import com.api.pix_fraud.exceptions.ValidationException;
import com.api.pix_fraud.models.FraudReport;
import com.api.pix_fraud.models.FraudReportStatus;
import com.api.pix_fraud.models.dto.request.FraudReportDTO;
import com.api.pix_fraud.services.FraudReportService;



@RestController
@RequestMapping("/api/reports")
public class FraudReportController {

    private final FraudReportService reportService;

    public FraudReportController(FraudReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public ResponseEntity<?> createReport(@RequestBody FraudReportDTO dto) {
        try {
            return ResponseEntity.ok(reportService.createReport(dto));
        } catch (ValidationException e) {
            return ResponseEntity.badRequest()
                .body(Collections.singletonMap("errors", e.getErrors()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getReportsByUser(@PathVariable Long userId) {
        try {
            List<FraudReport> reports = reportService.getReportsByUserId(userId);
            if (reports.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(reports);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest()
                .body(Collections.singletonMap("errors", e.getErrors()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(Collections.singletonMap("error", e.getMessage()));
        }
        
    }

    @PutMapping("/{reportId}")
    public ResponseEntity<?> updateStatus(@PathVariable Long reportId, @RequestBody Map<String, String> payload) {
        try {
            FraudReportStatus status = FraudReportStatus.valueOf(payload.get("status"));
            return ResponseEntity.ok(reportService.updateReportStatus(reportId, status));
        } catch (ValidationException e) {
            return ResponseEntity.badRequest()
                .body(Collections.singletonMap("errors", e.getErrors()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(Collections.singletonMap("error", e.getMessage()));
        }
        
    }

    @PostMapping("/queue/process")
    public ResponseEntity<?> startProcessing() {
        try {
            reportService.triggerProcessing();
            return ResponseEntity.ok("Fila Consimida");
        } catch (ValidationException e) {
            return ResponseEntity.badRequest()
                .body(Collections.singletonMap("errors", e.getErrors()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(Collections.singletonMap("error", e.getMessage()));
        }
        
    }

    @GetMapping("/queue")
    public ResponseEntity<?> viewQueue() {
        try {
            return ResponseEntity.ok(reportService.getCurrentQueue());
        } catch (ValidationException e) {
            return ResponseEntity.badRequest()
                .body(Collections.singletonMap("errors", e.getErrors()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

}
