package com.api.pix_fraud.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.pix_fraud.models.Whitelist;

public interface WhitelistRepository extends JpaRepository<Whitelist, Long> {
    Optional<Whitelist> findByUserId(Long userId);
}
