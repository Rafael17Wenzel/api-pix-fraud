package com.api.pix_fraud.services;

import com.api.pix_fraud.queue.FraudReportProcessor;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import com.api.pix_fraud.models.FraudReport;
import com.api.pix_fraud.models.FraudReportStatus;
import com.api.pix_fraud.models.PixCode;
import com.api.pix_fraud.models.dto.request.FraudReportDTO;
import com.api.pix_fraud.models.Person;
import com.api.pix_fraud.repositories.FraudReportRepository;
import com.api.pix_fraud.repositories.PixCodeRepository;
import com.api.pix_fraud.repositories.PersonRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class FraudReportService {

    private final FraudReportRepository reportRepository;
    private final PixCodeRepository pixCodeRepository;
    private final PersonRepository userRepository;
    private final FraudReportProcessor reportProcessor;
    private final AuditService auditService;

    public FraudReportService(
        FraudReportRepository reportRepository,
        PixCodeRepository pixCodeRepository,
        PersonRepository userRepository,
        FraudReportProcessor reportProcessor,
        AuditService auditService
    ) {
        this.reportRepository = reportRepository;
        this.pixCodeRepository = pixCodeRepository;
        this.userRepository = userRepository;
        this.reportProcessor = reportProcessor;
        this.auditService = auditService;
    }

    public FraudReport createReport(FraudReportDTO dto) {
        List<String> errors = new ArrayList<>();

        if (dto.getPixCodeId() == null) {
            errors.add("Id do código de pix é obrigatório.");
        }

        if (dto.getPersonId() == null) {
            errors.add("Id do usuário reportador é obrigatório.");
        }

        PixCode pixCode = pixCodeRepository.findById(dto.getPixCodeId())
                .orElseThrow(() -> new EntityNotFoundException("Código PIX não encontrado"));
        Person user = userRepository.findByIdAndActiveTrue(dto.getPersonId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        FraudReport report = new FraudReport();
        report.setPixCode(pixCode);
        report.setPerson(user);
        report.setDescription(dto.getDescription());

        report.setStatus(FraudReportStatus.PENDING);

        FraudReport saved = reportRepository.save(report);
        reportProcessor.enqueue(saved);

        auditService.log(user, "Criação de relatório de fraude",
                "Criado relatório ID " + saved.getId() + " para PixCode ID " + dto.getPixCodeId());

        return saved;
    }

    public List<FraudReport> getReportsByUserId(Long userId) {
        Person user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        auditService.log(user, "Consulta relatórios de fraude", "Consultou relatórios do usuário ID " + userId);

        return reportRepository.findByPersonId(userId);
    }

    public FraudReport updateReportStatus(Long reportId, FraudReportStatus status) {
        FraudReport report = reportRepository.findById(reportId)
                .orElseThrow(() -> new EntityNotFoundException("Relatório não encontrado"));
        report.setStatus(status);
        FraudReport updated = reportRepository.save(report);

        auditService.log(report.getPerson(), "Atualização status relatório de fraude",
                "Relatório ID " + reportId + " atualizado para status " + status);

        return updated;
    }

    public void triggerProcessing() {
        reportProcessor.processQueue();
    }

    public List<FraudReport> getCurrentQueue() {
        return List.copyOf(reportProcessor.getQueue());
    }
}
