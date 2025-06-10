package com.api.pix_fraud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.pix_fraud.models.PixCode;
import com.api.pix_fraud.models.PixCodeHistory;
import com.api.pix_fraud.models.User;
import com.api.pix_fraud.models.dto.PixCodeStatusDTO;
import com.api.pix_fraud.repositories.PixCodeHistoryRepository;
import com.api.pix_fraud.repositories.PixCodeRepository;

@Service
public class PixCodeService {

    private final PixCodeRepository pixCodeRepository;
    private final PixCodeHistoryRepository pixCodeHistoryRepository;

    @Autowired
    public PixCodeService(PixCodeRepository pixCodeRepository, PixCodeHistoryRepository pixCodeHistoryRepository) {
        this.pixCodeRepository = pixCodeRepository;
        this.pixCodeHistoryRepository = pixCodeHistoryRepository;
    }

    public PixCode createPixCode(PixCode pixCode) {
        return pixCodeRepository.save(pixCode);
    }

    public List<PixCode> getPixCodesByUser(User user) {
        return pixCodeRepository.findByUser(user);
    }

    public Optional<PixCode> getPixCodeById(Long id, User user) {
        return pixCodeRepository.findByIdAndUser(id, user);
    }

    public PixCode updateStatus(Long pixCodeId, PixCodeStatusDTO status) {
        PixCode pixCode = pixCodeRepository.findById(pixCodeId).orElseThrow();
        pixCode.setStatus(status.getStatus());
        pixCodeRepository.save(pixCode);

        PixCodeHistory history = new PixCodeHistory();
        history.setPixCode(pixCode);
        history.setStatus(status.getStatus());
        pixCodeHistoryRepository.save(history);

        return pixCode;
    }

    public List<PixCodeHistory> getPixCodeHistory(Long pixCodeId) {
        return pixCodeHistoryRepository.findByPixCodeId(pixCodeId);
    }
}