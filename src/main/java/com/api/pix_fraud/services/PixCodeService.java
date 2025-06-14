package com.api.pix_fraud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.api.pix_fraud.models.PixCode;
import com.api.pix_fraud.models.PixCodeHistory;
import com.api.pix_fraud.models.Person;
import com.api.pix_fraud.repositories.PixCodeHistoryRepository;
import com.api.pix_fraud.repositories.PixCodeRepository;

@Service
public class PixCodeService {

    private final PixCodeRepository pixCodeRepository;
    private final PixCodeHistoryRepository pixCodeHistoryRepository;
    private final AuditService auditService;

    public PixCodeService(
        PixCodeRepository pixCodeRepository,
        PixCodeHistoryRepository pixCodeHistoryRepository,
        AuditService auditService
    ) {
        this.pixCodeRepository = pixCodeRepository;
        this.pixCodeHistoryRepository = pixCodeHistoryRepository;
        this.auditService = auditService;
    }

    public PixCode createPixCode(PixCode pixCode) {
        PixCode created = pixCodeRepository.save(pixCode);
        auditService.log(
            pixCode.getUser(),
            "Criação de código PIX",
            "Código PIX criado com chave: " + created.getCode()
        );
        return created;
    }

    public List<PixCode> getPixCodesByUser(Person user) {
        List<PixCode> pixCodes = pixCodeRepository.findByUser(user);
        auditService.log(user, "Consulta de códigos PIX", "Usuário consultou seus códigos PIX");
        return pixCodes;
    }

    public Optional<PixCode> getPixCodeById(Long id, Person user) {
        Optional<PixCode> pixCode = pixCodeRepository.findByIdAndUser(id, user);
        auditService.log(user, "Consulta de código PIX por ID", "ID consultado: " + id);
        return pixCode;
    }

    public List<PixCodeHistory> getPixCodeHistory(Long pixCodeId) {
        return pixCodeHistoryRepository.findByPixCodeId(pixCodeId);
    }
}
