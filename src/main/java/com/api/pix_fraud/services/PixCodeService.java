package com.api.pix_fraud.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.api.pix_fraud.models.PixCode;
import com.api.pix_fraud.models.PixCodeHistory;
import com.api.pix_fraud.models.dto.request.PixCodeRequest;
import com.api.pix_fraud.models.dto.response.PixCodeResponse;
import com.api.pix_fraud.exceptions.ValidationException;
import com.api.pix_fraud.models.Person;
import com.api.pix_fraud.repositories.PersonRepository;
import com.api.pix_fraud.repositories.PixCodeHistoryRepository;
import com.api.pix_fraud.repositories.PixCodeRepository;

@Service
public class PixCodeService {

    private final PixCodeRepository pixCodeRepository;
    private final PixCodeHistoryRepository pixCodeHistoryRepository;
    private final PersonRepository personRepository;
    private final AuditService auditService;

    public PixCodeService(
        PixCodeRepository pixCodeRepository,
        PixCodeHistoryRepository pixCodeHistoryRepository,
        PersonRepository personRepository,
        AuditService auditService
    ) {
        this.pixCodeRepository = pixCodeRepository;
        this.pixCodeHistoryRepository = pixCodeHistoryRepository;
        this.personRepository = personRepository;
        this.auditService = auditService;
    }

    public PixCode createPixCode(PixCodeRequest pixCodeRequest) {
        List<String> errors = new ArrayList<>();

        if (pixCodeRequest.getCode() == null || pixCodeRequest.getCode().trim().isEmpty()) {
            errors.add("Código é obrigatório.");
        }

        if (pixCodeRequest.getPersonId() == null) {
            errors.add("person_id é obrigatório.");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        Optional<PixCode> existing = pixCodeRepository.findByCodeAndPersonActiveTrue(pixCodeRequest.getCode());
        if (existing.isPresent()) {
            throw new RuntimeException("Já existe um código PIX com esse valor para um usuário ativo.");
        }

        Person person = personRepository.findByIdAndActiveTrue(pixCodeRequest.getPersonId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        PixCode pixCode = new PixCode();

        pixCode.setCode(pixCodeRequest.getCode());
        pixCode.setPerson(person);

        PixCode created = pixCodeRepository.save(pixCode);

        auditService.log(
            created.getPerson(),
            "Criação de código PIX",
            "Código PIX criado com chave: " + created.getCode()
        );
        
        return created;
    }

    public List<PixCodeResponse> getPixCodesByUser(Long personId) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<PixCode> pixCodes = pixCodeRepository.findByPerson(person);

        auditService.log(person, "Consulta de códigos PIX", "Usuário consultou seus códigos PIX");

        return pixCodes.stream()
            .map(PixCodeResponse::new)
            .toList();
    }

    public Optional<PixCodeResponse> getPixCodeById(Long id) {
        return pixCodeRepository.findById(id)
            .map(PixCodeResponse::new);
    }

    public List<PixCodeHistory> getPixCodeHistory(Long pixCodeId) {
        return pixCodeHistoryRepository.findByPixCodeId(pixCodeId);
    }
}
