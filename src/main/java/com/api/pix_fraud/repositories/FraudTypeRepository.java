package com.api.pix_fraud.repositories;

import com.api.pix_fraud.models.FraudType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FraudTypeRepository extends JpaRepository<FraudType, Long> {
}
