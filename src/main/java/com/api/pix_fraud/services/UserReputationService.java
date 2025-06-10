package com.api.pix_fraud.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.pix_fraud.models.UserReputation;
import com.api.pix_fraud.repositories.UserReputationRepository;

@Service
public class UserReputationService {

    @Autowired
    private UserReputationRepository userReputationRepository;

    public UserReputation getUserReputation(Long userId) {
        return userReputationRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Reputação do usuário não encontrada."));
    }
}
