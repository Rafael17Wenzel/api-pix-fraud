package com.api.pix_fraud.services;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.api.pix_fraud.models.FraudReport;
import com.api.pix_fraud.models.FraudReportStatus;
import com.api.pix_fraud.models.PixCode;
import com.api.pix_fraud.models.User;
import com.api.pix_fraud.repositories.FraudReportRepository;
import com.api.pix_fraud.repositories.PixCodeRepository;
import com.api.pix_fraud.repositories.UserRepository;

import java.util.List;

@Service
public class FraudReportService {

    private final FraudReportRepository reportRepository;
    private final PixCodeRepository pixCodeRepository;
    private final UserRepository userRepository;

    public FraudReportService(FraudReportRepository reportRepository, PixCodeRepository pixCodeRepository, UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.pixCodeRepository = pixCodeRepository;
        this.userRepository = userRepository;
    }

    public FraudReport createReport(Long pixCodeId, Long userId, Long fraudType) {
        PixCode pixCode = pixCodeRepository.findById(pixCodeId)
                .orElseThrow(() -> new EntityNotFoundException("Código PIX não encontrado"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        FraudReport report = new FraudReport();
        report.setPixCode(pixCode);
        report.setUser(user);
        report.setFraudType(fraudType);
        return reportRepository.save(report);
    }

    public List<FraudReport> getReportsByUserId(Long userId) {
        return reportRepository.findByUserId(userId);
    }

    public FraudReport updateReportStatus(Long reportId, FraudReportStatus status) {
        FraudReport report = reportRepository.findById(reportId)
                .orElseThrow(() -> new EntityNotFoundException("Relatório não encontrado"));
        report.setStatus(status);
        return reportRepository.save(report);
    }
}
