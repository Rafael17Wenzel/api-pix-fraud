package com.api.pix_fraud.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.pix_fraud.models.UserReputation;

public interface UserReputationRepository extends JpaRepository<UserReputation, Long> {
    Optional<UserReputation> findByUserId(Long userId);
}
