package com.api.pix_fraud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.pix_fraud.models.PixCode;
import com.api.pix_fraud.models.User;

import java.util.List;
import java.util.Optional;

public interface PixCodeRepository extends JpaRepository<PixCode, Long> {
    
    List<PixCode> findByUser(User user);

    Optional<PixCode> findByIdAndUser(Long id, User user);
}